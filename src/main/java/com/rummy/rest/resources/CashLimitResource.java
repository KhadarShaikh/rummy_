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

import com.rummy.domain.CashLimit;
import com.rummy.exception.RAException;
import com.rummy.mapper.CashLimitMapper;
import com.rummy.response.Response;
import com.rummy.services.CashLimitService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/cashLimit")
public class CashLimitResource {

	@Autowired
	CashLimitService cashLimitService;

	Logger logger = Logger.getLogger(CashLimitResource.class);

	/**
	 * 
	 * @param cashLimitMapper
	 * @return
	 */
	@Path("/createCashLimit")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCashLimit(@RequestBody CashLimitMapper cashLimitMapper) {
		try {
			CashLimit cashLimit = new CashLimit();
			BeanUtils.copyProperties(cashLimitMapper, cashLimit);
			cashLimit.setRegistrationId(new ObjectId(cashLimitMapper.getRegistrationId()));
			Boolean result = cashLimitService.createCashLimit(cashLimit);
			if (result == false) {
				logger.debug("cashLimit not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "CashLimit not created");
			}
			logger.debug("CashLimit created successfully");
			return new Response("Success", result, HttpStatus.OK, "CashLimit created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param CashLimitMapper
	 * @param id
	 * @return
	 */
	@Path("/saveCashLimit/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCashLimit(@RequestBody CashLimitMapper cashLimitMapper, @PathParam("id") ObjectId id) {
		try {
			CashLimit cashLimit = new CashLimit();
			BeanUtils.copyProperties(cashLimitMapper, cashLimit);
			cashLimit.set_id(id);
			cashLimit.setRegistrationId(new ObjectId(cashLimitMapper.getRegistrationId()));
			Boolean result = cashLimitService.saveCashLimit(cashLimit);
			if (result == false) {
				logger.debug("CashLimit not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "CashLimit not saved");
			}
			logger.debug("CashLimit saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "CashLimit saved");

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
	@Path("/listCashLimits/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<CashLimit> list;
			List<CashLimitMapper> mapperList;
			try {
				list = cashLimitService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = cashLimitService.getCount(sort);
			int pages = cashLimitService.getPages(sort, pageSize);
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
	@Path("/findCashLimitByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findCashLimitByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			CashLimit cashLimit;
			CashLimitMapper cashLimitMapper;
			try {
				cashLimit = cashLimitService.findOneByCondition(condition);
				cashLimitMapper = convertDomainToMappar(cashLimit);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (cashLimitMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", cashLimitMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", cashLimitMapper, HttpStatus.OK, "record found");
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
	@Path("/findCashLimitByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findCashLimitByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			CashLimit cashLimits;
			CashLimitMapper cashLimit;
			try {
				cashLimits = cashLimitService.findOneByCondition(condition);
				cashLimit = convertDomainToMappar(cashLimits);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (cashLimit == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", cashLimit, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", cashLimit, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param cashLimits
	 * @return
	 */
	private List<CashLimitMapper> convertDomainToMapperList(List<CashLimit> cashLimits) {
		try {
			List<CashLimitMapper> list = new ArrayList<CashLimitMapper>();
			for (CashLimit cashLimit : cashLimits) {
				CashLimitMapper cashLimitMapper = new CashLimitMapper();
				BeanUtils.copyProperties(cashLimit, cashLimitMapper);
				cashLimitMapper.set_id(cashLimit.get_id().toString());
				list.add(cashLimitMapper);
			}
			logger.debug("Converting CashLimitList to CashLimitMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param CashLimit
	 * @return
	 */
	private CashLimitMapper convertDomainToMappar(CashLimit cashLimit) {
		try {
			CashLimitMapper cashLimitMapper = new CashLimitMapper();
			BeanUtils.copyProperties(cashLimit, cashLimitMapper);
			cashLimitMapper.set_id(cashLimit.get_id().toString());
			logger.debug("Converting CashLimit to CashLimitMapper");
			return cashLimitMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
