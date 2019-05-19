package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.AccountDAO;
import com.rummy.domain.Account;
import com.rummy.exception.RAException;
import com.rummy.services.AccountService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDAO accountDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Override
	public boolean createAccount(Account account) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			accountDAO.setPojo(new Account());
			accountDAO.getCollection("account", db);
			b = accountDAO.insert(account);
			logger.debug("account created..");
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
	public boolean saveAccount(Account account) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			accountDAO.getCollection("account", db);
			accountDAO.setPojo(new Account());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", account.get_id());
			b = accountDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(account));
			logger.debug("AccountSaved..");
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
		accountDAO.setPojo(new Account());
		return db;
	}

	@Override
	public List<Account> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<Account> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			accountDAO.getCollection("account", db);
			accountDAO.setPojo(new Account());
			try {
				list = accountDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AllaccountObjects..");
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
	public Account findOneByCondition(Map<String, Object> condition) throws RAException {
		Account account;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			accountDAO.setPojo(new Account());
			accountDAO.getCollection("account", db);
			try {
				account = accountDAO.findOneByCondition(condition);
				logger.debug("account FindByCondition..");
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
		return account;
	}

	@Override
	public Account findOneByPrimaryId(String value) throws RAException {
		Account account;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			accountDAO.setPojo(new Account());
			accountDAO.getCollection("account", db);
			try {
				account = accountDAO.findOneByPrimaryId(value);
				logger.debug("account FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return account;
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
			accountDAO.setPojo(new Account());
			accountDAO.getCollection("account", db);
			try {
				pages = accountDAO.getPages(sort, pageSize);
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
			accountDAO.setPojo(new Account());
			accountDAO.getCollection("account", db);
			count = accountDAO.getCount(sort);
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
