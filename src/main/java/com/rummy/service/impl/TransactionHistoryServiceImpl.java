package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.TransactionHistoryDAO;
import com.rummy.domain.TransactionHistory;
import com.rummy.exception.RAException;
import com.rummy.services.TransactionHistoryService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
	@Autowired
	TransactionHistoryDAO transactionHistoryDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(TransactionHistoryServiceImpl.class);

	@Override
	public boolean createTransactionHistory(TransactionHistory transactionHistory) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			transactionHistoryDAO.setPojo(new TransactionHistory());
			transactionHistoryDAO.getCollection("transactionHistory", db);
			b = transactionHistoryDAO.insert(transactionHistory);
			logger.debug("transactionHistory created..");
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
	public boolean saveTransactionHistory(TransactionHistory transactionHistory) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			transactionHistoryDAO.getCollection("transactionHistory", db);
			transactionHistoryDAO.setPojo(new TransactionHistory());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", transactionHistory.get_id());
			b = transactionHistoryDAO.updateMapByCondition(condition,
					MongoUtil.getObjectByDBObject(transactionHistory));
			logger.debug("transactionHistorySaved..");
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
		transactionHistoryDAO.setPojo(new TransactionHistory());
		return db;
	}

	@Override
	public List<TransactionHistory> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<TransactionHistory> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			transactionHistoryDAO.getCollection("transactionHistory", db);
			transactionHistoryDAO.setPojo(new TransactionHistory());
			try {
				list = transactionHistoryDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AlltransactionHistoryObjects..");
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
	public TransactionHistory findOneByCondition(Map<String, Object> condition) throws RAException {
		TransactionHistory transactionHistory;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			transactionHistoryDAO.setPojo(new TransactionHistory());
			transactionHistoryDAO.getCollection("transactionHistory", db);
			try {
				transactionHistory = transactionHistoryDAO.findOneByCondition(condition);
				logger.debug("transactionHistory FindByCondition..");
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
		return transactionHistory;
	}

	@Override
	public TransactionHistory findOneByPrimaryId(String value) throws RAException {
		TransactionHistory transactionHistory;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			transactionHistoryDAO.setPojo(new TransactionHistory());
			transactionHistoryDAO.getCollection("transactionHistory", db);
			try {
				transactionHistory = transactionHistoryDAO.findOneByPrimaryId(value);
				logger.debug("TransactionHistory FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return transactionHistory;
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
			transactionHistoryDAO.setPojo(new TransactionHistory());
			transactionHistoryDAO.getCollection("transactionHistory", db);
			try {
				pages = transactionHistoryDAO.getPages(sort, pageSize);
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
			transactionHistoryDAO.setPojo(new TransactionHistory());
			transactionHistoryDAO.getCollection("transactionHistory", db);
			count = transactionHistoryDAO.getCount(sort);
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
