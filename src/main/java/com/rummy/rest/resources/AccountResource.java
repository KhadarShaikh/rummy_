package com.rummy.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.rummy.domain.Account;
import com.rummy.exception.RAException;
import com.rummy.mapper.AccountMapper;
import com.rummy.response.Response;
import com.rummy.services.AccountService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/account")
public class AccountResource {

	@Autowired
	AccountService accountService;

	Logger logger = Logger.getLogger(AccountResource.class);

	/**
	 * 
	 * @param accountMapper
	 * @return
	 */
	@Path("/createAccount")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(@RequestBody AccountMapper accountMapper) {
		try {
			Account account = new Account();
			BeanUtils.copyProperties(accountMapper, account);
			account.setRegistrationId(new ObjectId(accountMapper.getRegistrationId()));
			Boolean result = accountService.createAccount(account);
			if (result == false) {
				logger.debug("account not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "account not created");
			}
			logger.debug("account created successfully");
			return new Response("Success", result, HttpStatus.OK, "account created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param accountMapper
	 * @param id
	 * @return
	 */
	@Path("/saveAccount/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveAccount(@RequestBody AccountMapper accountMapper, @PathParam("id") ObjectId id) {
		try {
			Account account = new Account();
			BeanUtils.copyProperties(accountMapper, account);
			account.set_id(id);
			account.setRegistrationId(new ObjectId(accountMapper.getRegistrationId()));
			Boolean result = accountService.saveAccount(account);
			if (result == false) {
				logger.debug("account not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "account not saved");
			}
			logger.debug("account saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "account saved");

		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Path("/listAccounts/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Account> list;
			List<AccountMapper> mapperList;
			try {
				list = accountService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = accountService.getCount(sort);
			int pages = accountService.getPages(sort, pageSize);
			if (mapperList == null || mapperList.size() == 0) {
				logger.debug("No records found");
				return new Response(mapperList, pages, count, HttpStatus.CONFLICT, "No records found");
			}
			logger.debug("Records found successfully");
			return new Response(mapperList, pages, count, HttpStatus.OK, "records found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param nameOftheProperty
	 * @param valueOftheProperty
	 * @return
	 */
	@Path("/findAccountByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAccountByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Account account;
			AccountMapper accountMapper;
			try {
				account = accountService.findOneByCondition(condition);
				accountMapper = convertDomainToMappar(account);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (accountMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", accountMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", accountMapper, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param context
	 * @param value
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Path("/findAccountByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAccountByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Account accounts;
			AccountMapper account;
			try {
				accounts = accountService.findOneByCondition(condition);
				account = convertDomainToMappar(accounts);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (account == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", account, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", account, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param accounts
	 * @return
	 */
	private List<AccountMapper> convertDomainToMapperList(List<Account> accounts) {
		try {
			List<AccountMapper> list = new ArrayList<AccountMapper>();
			for (Account account : accounts) {
				AccountMapper accountMapper = new AccountMapper();
				BeanUtils.copyProperties(account, accountMapper);
				accountMapper.set_id(account.get_id().toString());
				list.add(accountMapper);
			}
			logger.debug("Converting accountList to AccountMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param account
	 * @return
	 */
	private AccountMapper convertDomainToMappar(Account account) {
		try {
			AccountMapper accountMapper = new AccountMapper();
			BeanUtils.copyProperties(account, accountMapper);
			accountMapper.set_id(account.get_id().toString());
			logger.debug("Converting Account to AccountMapper");
			return accountMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
