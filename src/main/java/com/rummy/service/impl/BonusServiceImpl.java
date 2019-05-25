package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.BonusDAO;
import com.rummy.domain.Bonus;
import com.rummy.exception.RAException;
import com.rummy.services.BonusService;
import com.rummy.util.MongoSortVO;
import com.rummy.util.MongoUtil;

/**
 * 
 * @author skkhadar
 *
 */
public class BonusServiceImpl implements BonusService {
	@Autowired
	BonusDAO bonusDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(BonusServiceImpl.class);

	@Override
	public boolean createBonus(Bonus bonus) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			bonusDAO.setPojo(new Bonus());
			bonusDAO.getCollection("bonus", db);
			b = bonusDAO.insert(bonus);
			logger.debug("bonus created..");
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
	public boolean saveBonus(Bonus bonus) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			bonusDAO.getCollection("bonus", db);
			bonusDAO.setPojo(new Bonus());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", bonus.get_id());
			b = bonusDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(bonus));
			logger.debug("BonusSaved..");
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
		bonusDAO.setPojo(new Bonus());
		return db;
	}

	@Override
	public List<Bonus> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<Bonus> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			bonusDAO.getCollection("bonus", db);
			bonusDAO.setPojo(new Bonus());
			try {
				list = bonusDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("get AllbonusObjects..");
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
	public Bonus findOneByCondition(Map<String, Object> condition) throws RAException {
		Bonus bonus;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			bonusDAO.setPojo(new Bonus());
			bonusDAO.getCollection("bonus", db);
			try {
				bonus = bonusDAO.findOneByCondition(condition);
				logger.debug("Bonus FindByCondition..");
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
		return bonus;
	}

	@Override
	public Bonus findOneByPrimaryId(String value) throws RAException {
		Bonus bonus;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			bonusDAO.setPojo(new Bonus());
			bonusDAO.getCollection("bonus", db);
			try {
				bonus = bonusDAO.findOneByPrimaryId(value);
				logger.debug("Bonus FindByPrimaryId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("Connection closed..");
			return bonus;
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
			bonusDAO.setPojo(new Bonus());
			bonusDAO.getCollection("bonus", db);
			try {
				pages = bonusDAO.getPages(sort, pageSize);
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
			bonusDAO.setPojo(new Bonus());
			bonusDAO.getCollection("bonus", db);
			count = bonusDAO.getCount(sort);
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
