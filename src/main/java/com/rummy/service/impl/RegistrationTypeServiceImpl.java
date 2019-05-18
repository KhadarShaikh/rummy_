package com.rummy.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.RegistrationTypeDAO;
import com.rummy.domain.RegistrationType;
import com.rummy.exception.RAException;
import com.rummy.services.RegistrationTypeService;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public class RegistrationTypeServiceImpl implements RegistrationTypeService {
	@Autowired
	RegistrationTypeDAO registrationTypeDAO;
	@Autowired
	MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(RegistrationTypeServiceImpl.class);

	@Override
	public boolean createRegistrationType(RegistrationType registrationType) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);
			b = registrationTypeDAO.insert(registrationType);
			logger.debug("RegistrationType created..");
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
	public boolean updateRegistrationType(RegistrationType registrationType) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);
			b = registrationTypeDAO.save(registrationType);
			logger.debug("RegistrationType updated..");
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
	public int getCount(MongoSortVO sort) {
		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);
			count = registrationTypeDAO.getCount(sort);
			logger.debug("RegistrationType Counted successfully..");
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
	public List<RegistrationType> getAllRegistrationType(MongoSortVO sort, int page, int size) {
		List<RegistrationType> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.getCollection("registrationType", db);
			registrationTypeDAO.setPojo(new RegistrationType());
			try {
				list = registrationTypeDAO.getAllObjects(sort, page, size);
				logger.debug("RegistrationType List Created..");
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

	public List<RegistrationType> getAllRegistrationType(MongoSortVO sort) {
		List<RegistrationType> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.getCollection("registrationType", db);
			registrationTypeDAO.setPojo(new RegistrationType());
			try {
				list = registrationTypeDAO.getAllObjects(sort);
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

	@Override
	public RegistrationType getOneByCondition(Map<String, Object> condition) {
		RegistrationType type = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);
			try {
				type = registrationTypeDAO.findOneByCondition(condition);
				logger.debug("RegistrationType get bu Condition successfully..");
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
		return type;
	}

	@Override
	public int getPages(MongoSortVO sort, int pageSize) {
		int pages;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);
			try {
				pages = registrationTypeDAO.getPages(sort, pageSize);
				logger.debug("RegistrationType Pages created..");
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
