package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.CashLimitDAO;
import com.rummy.domain.CashLimit;
import com.rummy.exception.RAException;
import com.rummy.services.CashLimitService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class CashLimitServiceImpl implements CashLimitService {
	@Autowired
	CashLimitDAO cashLimitDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(CashLimitServiceImpl.class);

	@Override
	public boolean createCashLimit(CashLimit cashLimit) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			cashLimitDAO.setPojo(new CashLimit());
			cashLimitDAO.getCollection("cashLimit", db);
			b = cashLimitDAO.insert(cashLimit);
			logger.debug("CashLimit created..");
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
	public boolean saveCashLimit(CashLimit cashLimit) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			cashLimitDAO.getCollection("cashLimit", db);
			cashLimitDAO.setPojo(new CashLimit());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", cashLimit.get_id());
			b = cashLimitDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(cashLimit));
			logger.debug("CashLimitSaved..");
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
		cashLimitDAO.setPojo(new CashLimit());
		return db;
	}

	@Override
	public List<CashLimit> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<CashLimit> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			cashLimitDAO.getCollection("cashLimit", db);
			cashLimitDAO.setPojo(new CashLimit());
			try {
				list = cashLimitDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AllcashLimitObjects..");
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
	public CashLimit findOneByCondition(Map<String, Object> condition) throws RAException {
		CashLimit cashLimit;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			cashLimitDAO.setPojo(new CashLimit());
			cashLimitDAO.getCollection("cashLimit", db);
			try {
				cashLimit = cashLimitDAO.findOneByCondition(condition);
				logger.debug("CashLimit FindByCondition..");
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
		return cashLimit;
	}

	@Override
	public CashLimit findOneByPrimaryId(String value) throws RAException {
		CashLimit cashLimit;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			cashLimitDAO.setPojo(new CashLimit());
			cashLimitDAO.getCollection("cashLimit", db);
			try {
				cashLimit = cashLimitDAO.findOneByPrimaryId(value);
				logger.debug("CashLimit FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return cashLimit;
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
			cashLimitDAO.setPojo(new CashLimit());
			cashLimitDAO.getCollection("cashLimit", db);
			try {
				pages = cashLimitDAO.getPages(sort, pageSize);
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
			cashLimitDAO.setPojo(new CashLimit());
			cashLimitDAO.getCollection("cashLimit", db);
			count = cashLimitDAO.getCount(sort);
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
