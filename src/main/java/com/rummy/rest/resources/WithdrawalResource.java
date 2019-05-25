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

import com.rummy.domain.WithdrawalDetails;
import com.rummy.exception.RAException;
import com.rummy.mapper.WithdrawalDetailsMapper;
import com.rummy.response.Response;
import com.rummy.services.WithdrawalService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/Withdrawal")
public class WithdrawalResource {

	@Autowired
	WithdrawalService withdrawalDetailsService;

	Logger logger = Logger.getLogger(WithdrawalResource.class);

	/**
	 * 
	 * @param withdrawalDetailsMapper
	 * @return
	 */
	@Path("/createWithdrawalDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createWithdrawalDetails(@RequestBody WithdrawalDetailsMapper withdrawalDetailsMapper) {
		try {
			WithdrawalDetails withdrawalDetails = new WithdrawalDetails();
			BeanUtils.copyProperties(withdrawalDetailsMapper, withdrawalDetails);
			withdrawalDetails.setRegistrationId(new ObjectId(withdrawalDetailsMapper.getRegistrationId()));
			Boolean result = withdrawalDetailsService.createWithdrawalDetails(withdrawalDetails);
			if (result == false) {
				logger.debug("withdrawalDetails not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "withdrawalDetails not created");
			}
			logger.debug("WithdrawalDetails created successfully");
			return new Response("Success", result, HttpStatus.OK, "WithdrawalDetails created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param WithdrawalDetailsMapper
	 * @param id
	 * @return
	 */
	@Path("/saveWithdrawalDetails/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveWithdrawalDetails(@RequestBody WithdrawalDetailsMapper withdrawalDetailsMapper,
			@PathParam("id") ObjectId id) {
		try {
			WithdrawalDetails withdrawalDetails = new WithdrawalDetails();
			BeanUtils.copyProperties(withdrawalDetailsMapper, withdrawalDetails);
			withdrawalDetails.set_id(id);
			withdrawalDetails.setRegistrationId(new ObjectId(withdrawalDetailsMapper.getRegistrationId()));
			Boolean result = withdrawalDetailsService.saveWithdrawalDetails(withdrawalDetails);
			if (result == false) {
				logger.debug("WithdrawalDetails not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "WithdrawalDetails not saved");
			}
			logger.debug("WithdrawalDetails saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "WithdrawalDetails saved");

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
	@Path("/listWithdrawalDetailss/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<WithdrawalDetails> list;
			List<WithdrawalDetailsMapper> mapperList;
			try {
				list = withdrawalDetailsService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = withdrawalDetailsService.getCount(sort);
			int pages = withdrawalDetailsService.getPages(sort, pageSize);
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
	@Path("/findWithdrawalDetailsByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findWithdrawalDetailsByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			WithdrawalDetails withdrawalDetails;
			WithdrawalDetailsMapper withdrawalDetailsMapper;
			try {
				withdrawalDetails = withdrawalDetailsService.findOneByCondition(condition);
				withdrawalDetailsMapper = convertDomainToMappar(withdrawalDetails);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (withdrawalDetailsMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", withdrawalDetailsMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", withdrawalDetailsMapper, HttpStatus.OK, "record found");
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
	@Path("/findWithdrawalDetailsByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findWithdrawalDetailsByPrimaryId(@Context ServletContext context,
			@PathParam("value") ObjectId value) throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			WithdrawalDetails withdrawalDetailss;
			WithdrawalDetailsMapper withdrawalDetails;
			try {
				withdrawalDetailss = withdrawalDetailsService.findOneByCondition(condition);
				withdrawalDetails = convertDomainToMappar(withdrawalDetailss);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (withdrawalDetails == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", withdrawalDetails, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", withdrawalDetails, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param WithdrawalDetailss
	 * @return
	 */
	private List<WithdrawalDetailsMapper> convertDomainToMapperList(List<WithdrawalDetails> withdrawalDetailss) {
		try {
			List<WithdrawalDetailsMapper> list = new ArrayList<WithdrawalDetailsMapper>();
			for (WithdrawalDetails withdrawalDetails : withdrawalDetailss) {
				WithdrawalDetailsMapper withdrawalDetailsMapper = new WithdrawalDetailsMapper();
				BeanUtils.copyProperties(withdrawalDetails, withdrawalDetailsMapper);
				withdrawalDetailsMapper.set_id(withdrawalDetails.get_id().toString());
				list.add(withdrawalDetailsMapper);
			}
			logger.debug("Converting WithdrawalDetailsList to WithdrawalDetailsMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param WithdrawalDetailsMapper
	 * @return
	 */
	private WithdrawalDetailsMapper convertDomainToMappar(WithdrawalDetails withdrawalDetails) {
		try {
			WithdrawalDetailsMapper withdrawalDetailsMapper = new WithdrawalDetailsMapper();
			BeanUtils.copyProperties(withdrawalDetails, withdrawalDetailsMapper);
			withdrawalDetailsMapper.set_id(withdrawalDetails.get_id().toString());
			logger.debug("Converting withdrawalDetails to WithdrawalDetailsMapper");
			return withdrawalDetailsMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
