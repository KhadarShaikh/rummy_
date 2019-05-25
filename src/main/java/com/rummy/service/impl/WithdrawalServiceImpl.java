package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.WithdrawalDetailsDAO;
import com.rummy.domain.WithdrawalDetails;
import com.rummy.exception.RAException;
import com.rummy.services.WithdrawalService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class WithdrawalServiceImpl implements WithdrawalService {
	@Autowired
	WithdrawalDetailsDAO withdrawalDetailsDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(WithdrawalServiceImpl.class);

	@Override
	public boolean createWithdrawalDetails(WithdrawalDetails withdrawalDetails) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			b = withdrawalDetailsDAO.insert(withdrawalDetails);
			logger.debug("withdrawalDetails created..");
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
	public boolean saveWithdrawalDetails(WithdrawalDetails withdrawalDetails) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", withdrawalDetails.get_id());
			b = withdrawalDetailsDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(withdrawalDetails));
			logger.debug("WithdrawalDetailsSaved..");
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
		withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
		return db;
	}

	@Override
	public List<WithdrawalDetails> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<WithdrawalDetails> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			try {
				list = withdrawalDetailsDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AllWithdrawalDetailsObjects..");
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
	public WithdrawalDetails findOneByCondition(Map<String, Object> condition) throws RAException {
		WithdrawalDetails withdrawalDetails;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			try {
				withdrawalDetails = withdrawalDetailsDAO.findOneByCondition(condition);
				logger.debug("WithdrawalDetails FindByCondition..");
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
		return withdrawalDetails;
	}

	@Override
	public WithdrawalDetails findOneByPrimaryId(String value) throws RAException {
		WithdrawalDetails withdrawalDetails;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			try {
				withdrawalDetails = withdrawalDetailsDAO.findOneByPrimaryId(value);
				logger.debug("WithdrawalDetails FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return withdrawalDetails;
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
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			try {
				pages = withdrawalDetailsDAO.getPages(sort, pageSize);
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
			withdrawalDetailsDAO.setPojo(new WithdrawalDetails());
			withdrawalDetailsDAO.getCollection("withdrawalDetails", db);
			count = withdrawalDetailsDAO.getCount(sort);
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
