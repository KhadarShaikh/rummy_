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

import com.rummy.domain.Payment;
import com.rummy.exception.RAException;
import com.rummy.mapper.PaymentMapper;
import com.rummy.response.Response;
import com.rummy.services.PaymentService;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
@Component
@Path("/payment")
public class PaymentResource {

	@Autowired
	PaymentService paymentService;

	Logger logger = Logger.getLogger(PaymentResource.class);

	/**
	 * 
	 * @param paymentMapper
	 * @return
	 */
	@Path("/createPayment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPayment(@RequestBody PaymentMapper paymentMapper) {
		try {
			Payment payment = new Payment();
			BeanUtils.copyProperties(paymentMapper, payment);
			payment.setRegistrationId(new ObjectId(paymentMapper.getRegistrationId()));
			Boolean result = paymentService.createPayment(payment);
			if (result == false) {
				logger.debug("payment not created");
				return new Response("Failed", result, HttpStatus.CONFLICT, "payment not created");
			}
			logger.debug("payment created successfully");
			return new Response("Success", result, HttpStatus.OK, "payment created");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param paymentMapper
	 * @param id
	 * @return
	 */
	@Path("/savePayment/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response savePayment(@RequestBody PaymentMapper paymentMapper, @PathParam("id") ObjectId id) {
		try {
			Payment payment = new Payment();
			BeanUtils.copyProperties(paymentMapper, payment);
			payment.set_id(id);
			payment.setRegistrationId(new ObjectId(paymentMapper.getRegistrationId()));
			Boolean result = paymentService.savePayment(payment);
			if (result == false) {
				logger.debug("payment not saved for id " + id);
				return new Response("Failed", result, HttpStatus.CONFLICT, "payment not saved");
			}
			logger.debug("payment saved for id " + id);
			return new Response("Success", result, HttpStatus.OK, "payment saved");

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
	@Path("/listPayments/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Payment> list;
			List<PaymentMapper> mapperList;
			try {
				list = paymentService.getAllObjects(sort, pageNo, pageSize);
				mapperList = convertDomainToMapperList(list);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			int count = paymentService.getCount(sort);
			int pages = paymentService.getPages(sort, pageSize);
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
	@Path("/findPaymentByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findPaymentByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Payment payment;
			PaymentMapper paymentMapper;
			try {
				payment = paymentService.findOneByCondition(condition);
				paymentMapper = convertDomainToMappar(payment);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (paymentMapper == null) {
				logger.debug("No records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
						+ valueOftheProperty);
				return new Response("Failed", paymentMapper, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found for nameOftheProperty " + nameOftheProperty + "and valueOftheProperty "
					+ valueOftheProperty);
			return new Response("Success", paymentMapper, HttpStatus.OK, "record found");
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
	@Path("/findPaymentByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findPaymentByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Payment payments;
			PaymentMapper payment;
			try {
				payments = paymentService.findOneByCondition(condition);
				payment = convertDomainToMappar(payments);
			} catch (RAException e) {
				logger.error("No records found " + e.getMessage());
				return new Response(HttpStatus.CONFLICT, "No records found");
			}
			if (payment == null) {
				logger.debug("No records found for ObjectId " + value);
				return new Response("Failed", payment, HttpStatus.CONFLICT, "No record found");
			}
			logger.debug("Records found successfully for ObjectId " + value);
			return new Response("Success", payment, HttpStatus.OK, "record found");
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param payments
	 * @return
	 */
	private List<PaymentMapper> convertDomainToMapperList(List<Payment> payments) {
		try {
			List<PaymentMapper> list = new ArrayList<PaymentMapper>();
			for (Payment payment : payments) {
				PaymentMapper paymentMapper = new PaymentMapper();
				BeanUtils.copyProperties(payment, paymentMapper);
				paymentMapper.set_id(payment.get_id().toString());
				list.add(paymentMapper);
			}
			logger.debug("Converting PaymentList to PaymentMapperList");
			return list;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param payment
	 * @return
	 */
	private PaymentMapper convertDomainToMappar(Payment payment) {
		try {
			PaymentMapper paymentMapper = new PaymentMapper();
			BeanUtils.copyProperties(payment, paymentMapper);
			paymentMapper.set_id(payment.get_id().toString());
			logger.debug("Converting Payment to PaymentMapper");
			return paymentMapper;
		} catch (RAException e) {
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}
}
