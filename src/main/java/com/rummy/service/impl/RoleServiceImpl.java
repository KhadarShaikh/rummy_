package com.rummy.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.RoleDAO;
import com.rummy.domain.Role;
import com.rummy.exception.RAException;
import com.rummy.services.RoleService;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Override
	public boolean createRole(Role role) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			roleDAO.getCollection("role", db);
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			b = roleDAO.insert(role);
			logger.debug("Role created..");
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
	public Role findOneByCondition(Map<String, Object> condition) throws RAException {
		Role role = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			try {
				role = roleDAO.findOneByCondition(condition);
				logger.debug("Role find by condition..");
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
		return role;
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		logger.debug("Database initialized..");
		roleDAO.setPojo(new Role());
		return db;

	}

	public List<Role> getByMapObjects(MongoSortVO sort, int page, int size, Map<String, Object> mapCondition)
			throws RAException {
		List<Role> roles = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			String mappedPojo = "roleMapping";
			try {
				roles = roleDAO.getByMapObjects(sort, page, size, mappedPojo, mapCondition);
				logger.debug("Role list get by MapObject..");
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
		return roles;
	}

	@Override
	public Role getByRoleName(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int pageNo, int pageSize) {
		return null;
	}

}
