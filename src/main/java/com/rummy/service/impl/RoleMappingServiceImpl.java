package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.RoleMappingDAO;
import com.rummy.domain.RoleMapping;
import com.rummy.exception.RAException;
import com.rummy.services.RoleMappingService;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoEqualQuery;

/**
 * 
 * @author skkhadar
 *
 */
public class RoleMappingServiceImpl implements RoleMappingService {

	@Autowired
	RoleMappingDAO roleMappingDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(RoleMappingServiceImpl.class);

	@Override
	public boolean createRoleMapping(RoleMapping roleMapping) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			roleMappingDAO.getCollection("roleMapping", db);
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			b = roleMappingDAO.insert(roleMapping);
			logger.debug("RoleMapping created..");
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public RoleMapping findOneByCondition(Map<String, Object> condition) throws RAException {
		RoleMapping roleMapping = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			try {
				roleMapping = roleMappingDAO.findOneByCondition(condition);
				logger.debug("RoleMapping find by condition..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return roleMapping;
	}

	@Override
	public List<RoleMapping> findOneRoleMappingByUserId(ObjectId id) throws RAException {
		List<RoleMapping> mappinglist = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			MongoEqualQuery equal = new MongoEqualQuery();
			equal.setEqualto(id);
			Map<String, MongoAdvancedQuery> roleMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			roleMappingcondition.put("user_id", equal);
			try {
				mappinglist = roleMappingDAO.advancedFindByCondition(roleMappingcondition, null, 1, 10);
				logger.debug("RloeMapping list find by userId..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return mappinglist;
	}

	@Override
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			b = roleMappingDAO.removeByCondition(condition);
			logger.debug("RoleMapping deleted..");
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			b = roleMappingDAO.removeByPrimaryId(value);
			logger.debug("RoleMapping removed by primaryId..");
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
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
		roleMappingDAO.setPojo(new RoleMapping());
		return db;
	}

	@Override
	public void removeByCondition(Map<String, Object> condition) {
		DB db = mongoDBClient.getReadMongoDB();
		logger.debug("Database initialized..");
		roleMappingDAO.setPojo(new RoleMapping());
		roleMappingDAO.getCollection("roleMapping", db);
		roleMappingDAO.removeByCondition(condition);
	}
}
