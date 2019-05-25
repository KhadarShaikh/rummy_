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

import com.rummy.domain.TransactionHistory;
import com.rummy.exception.RAException;
import com.rummy.mapper.TransactionHistoryMapper;
import com.rummy.response.Response;
import com.rummy.services.TransactionHistoryService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/transactionHistory")
public class TransactionHistoryResource {

	@Autowired
	TransactionHistoryService transactionHistoryService;

	Logger logger = Logger.getLogger(TransactionHistoryResource.class);

	/**
	 * 
	 * @param transactionHistoryMapper
	 * @return
	 */
	@Path("/createTransactionHistory")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTransactionHistory(@RequestBody TransactionHistoryMapper transactionHistoryMapper) {
		try {
			TransactionHistory transactionHistory = new TransactionHistory();
			BeanUtils.copyProperties(transactionHistoryMapper, transactionHistory);
			transactionHistory.setRegistrationId(new ObjectId(transactionHistoryMapper.getRegistrationId()));
			Boolean result = transactionHistoryService.createTransactionHistory(transactionHistory);
			if (result == false) {
				logger.debug("transactionHistory not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "TransactionHistory not created");
			}
			logger.debug("TransactionHistory created successfully");
			return new Response("Success", result, HttpStatus.OK, "TransactionHistory created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param TransactionHistoryMapper
	 * @param id
	 * @return
	 */
	@Path("/saveTransactionHistory/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveTransactionHistory(@RequestBody TransactionHistoryMapper transactionHistoryMapper,
			@PathParam("id") ObjectId id) {
		try {
			TransactionHistory transactionHistory = new TransactionHistory();
			BeanUtils.copyProperties(transactionHistoryMapper, transactionHistory);
			transactionHistory.set_id(id);
			transactionHistory.setRegistrationId(new ObjectId(transactionHistoryMapper.getRegistrationId()));
			Boolean result = transactionHistoryService.saveTransactionHistory(transactionHistory);
			if (result == false) {
				logger.debug("TransactionHistory not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "TransactionHistory not saved");
			}
			logger.debug("TransactionHistory saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "TransactionHistory saved");

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
	@Path("/listTransactionHistorys/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<TransactionHistory> list;
			List<TransactionHistoryMapper> mapperList;
			try {
				list = transactionHistoryService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = transactionHistoryService.getCount(sort);
			int pages = transactionHistoryService.getPages(sort, pageSize);
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
	@Path("/findTransactionHistoryByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findTransactionHistoryByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			TransactionHistory transactionHistory;
			TransactionHistoryMapper transactionHistoryMapper;
			try {
				transactionHistory = transactionHistoryService.findOneByCondition(condition);
				transactionHistoryMapper = convertDomainToMappar(transactionHistory);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (transactionHistoryMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", transactionHistoryMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", transactionHistoryMapper, HttpStatus.OK, "record found");
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
	@Path("/findTransactionHistoryByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findTransactionHistoryByPrimaryId(@Context ServletContext context,
			@PathParam("value") ObjectId value) throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			TransactionHistory transactionHistorys;
			TransactionHistoryMapper transactionHistory;
			try {
				transactionHistorys = transactionHistoryService.findOneByCondition(condition);
				transactionHistory = convertDomainToMappar(transactionHistorys);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (transactionHistory == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", transactionHistory, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", transactionHistory, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param TransactionHistorys
	 * @return
	 */
	private List<TransactionHistoryMapper> convertDomainToMapperList(List<TransactionHistory> transactionHistorys) {
		try {
			List<TransactionHistoryMapper> list = new ArrayList<TransactionHistoryMapper>();
			for (TransactionHistory transactionHistory : transactionHistorys) {
				TransactionHistoryMapper transactionHistoryMapper = new TransactionHistoryMapper();
				BeanUtils.copyProperties(transactionHistory, transactionHistoryMapper);
				transactionHistoryMapper.set_id(transactionHistory.get_id().toString());
				list.add(transactionHistoryMapper);
			}
			logger.debug("Converting TransactionHistoryList to TransactionHistoryMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param transactionHistory
	 * @return
	 */
	private TransactionHistoryMapper convertDomainToMappar(TransactionHistory transactionHistory) {
		try {
			TransactionHistoryMapper transactionHistoryMapper = new TransactionHistoryMapper();
			BeanUtils.copyProperties(transactionHistory, transactionHistoryMapper);
			transactionHistoryMapper.set_id(transactionHistory.get_id().toString());
			logger.debug("Converting TransactionHistory to TransactionHistoryMapper");
			return transactionHistoryMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
