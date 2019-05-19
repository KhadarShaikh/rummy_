package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.RegistrationDAO;
import com.rummy.domain.Registration;
import com.rummy.exception.RAException;
import com.rummy.services.RegistrationService;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(RegistrationServiceImpl.class);

	@Override
	public boolean create(Registration registration) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			b = registrationDAO.insert(registration);
			logger.debug("Registration Created..");
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
	public boolean save(Registration registration) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			b = registrationDAO.save(registration);
			logger.debug("Registration saved..");
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
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			b = registrationDAO.removeByCondition(condition);
			logger.debug("Registration deleted by Id..");
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

	@SuppressWarnings("deprecation")
	@Override
	public List<Registration> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException {
		List<Registration> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			try {
				list = registrationDAO.findAllByCondition(condition, sort);
				logger.debug("Registration List created..");
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
		return list;
	}

	@Override
	public int getCount(MongoSortVO sort) {
		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			count = registrationDAO.getCount(sort);
			logger.debug("Registration counted successfully..");
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return count;
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			b = registrationDAO.removeByPrimaryId(value);
			logger.debug("Registration removed by PrimaryId..");
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
	public Registration findOneByCondition(Map<String, Object> condition) throws RAException {
		Registration reg;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			try {
				reg = registrationDAO.findOneByCondition(condition);
				logger.debug("Registration find by condition..");
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
		return reg;
	}

	@Override
	public Registration findOneByPrimaryId(String value) throws RAException {
		Registration reg;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			try {
				reg = registrationDAO.findOneByPrimaryId(value);
				logger.debug("Registration find by PrimaryId..");
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
		return reg;
	}

	@Override
	public List<Registration> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		List<Registration> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.getCollection("registration", db);
			registrationDAO.setPojo(new Registration());
			try {
				list = registrationDAO.getAllObjects(sort, page, size);
				logger.debug("Registration list get successfully..");
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
		return list;
	}

	@Override
	public List<Registration> getAllObjects(MongoSortVO sort) throws RAException {
		List<Registration> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.getCollection("registration", db);
			registrationDAO.setPojo(new Registration());
			try {
				list = registrationDAO.getAllObjects(sort);
				logger.debug("Registration List get successfully..");
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
		return list;
	}

	@SuppressWarnings("unused")
	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		logger.debug("Database initialized..");
		registrationDAO.setPojo(new Registration());
		return db;

	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			b = registrationDAO.updateMapByCondition(condition, target);
			logger.debug("Registration Updated by condition..");
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
	public List<Registration> advancedFindByCondition(String nameOftheProperty, String valueOftheProperty,
			MongoSortVO sort, int pageNo, int pageSize) {
		List<Registration> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put(nameOftheProperty, new BasicDBObject("$regex", valueOftheProperty).append("$options", "i"));
			try {
				list = registrationDAO.getAllByRegex(sort, regexQuery, pageNo, pageSize);
				logger.debug("Registration List find in advanced successfully..");
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
		return list;
	}

	@Override
	public int getPages(MongoSortVO sort, int pageSize) {
		int pages;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			try {
				pages = registrationDAO.getPages(sort, pageSize);
				logger.debug("RegistrationPages counted..");
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
		return pages;
	}
}