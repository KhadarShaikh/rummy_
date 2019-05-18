package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.PaymentDAO;
import com.rummy.domain.Payment;
import com.rummy.exception.RAException;
import com.rummy.services.PaymentService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(PaymentServiceImpl.class);

	@Override
	public boolean createPayment(Payment payment) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);
			b = paymentDAO.insert(payment);
			logger.debug("payment created..");
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean savePayment(Payment payment) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			paymentDAO.getCollection("payment", db);
			paymentDAO.setPojo(new Payment());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", payment.get_id());
			b = paymentDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(payment));
			logger.debug("PaymentSaved..");
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return b;
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		logger.debug("Database initialized..");
		paymentDAO.setPojo(new Payment());
		return db;
	}

	@Override
	public List<Payment> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<Payment> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.getCollection("payment", db);
			paymentDAO.setPojo(new Payment());
			try {
				list = paymentDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AllPaymentObjects..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public Payment findOneByCondition(Map<String, Object> condition) throws RAException {
		Payment payment;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);
			try {
				payment = paymentDAO.findOneByCondition(condition);
				logger.debug("payment FindByCondition..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return payment;
	}

	@Override
	public Payment findOneByPrimaryId(String value) throws RAException {
		Payment payment;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);
			try {
				payment = paymentDAO.findOneByPrimaryId(value);
				logger.debug("Payment FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return payment;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	@Override
	public int getPages(MongoSortVO sort, int pageSize) {
		int pages;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);
			try {
				pages = paymentDAO.getPages(sort, pageSize);
				logger.debug("pages counted successfully..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return pages;
	}

	@Override
	public int getCount(MongoSortVO sort) {
		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);
			count = paymentDAO.getCount(sort);
			logger.debug("count calculated..");
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return count;
	}
}
