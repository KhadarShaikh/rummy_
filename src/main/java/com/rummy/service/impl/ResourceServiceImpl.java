package com.rummy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.rummy.MongoDBClient;
import com.rummy.dao.ResourceDAO;
import com.rummy.domain.Registration;
import com.rummy.domain.Resource;
import com.rummy.exception.RAException;
import com.rummy.services.RegistrationService;
import com.rummy.services.ResourceService;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceDAO resourceDAO;
	@Autowired
	RegistrationService registrationService;
	@Autowired
	private MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(ResourceServiceImpl.class);

	@Override
	public boolean createResource(Resource resource) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			b = resourceDAO.insert(resource);
			logger.debug("Resource created..");
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
	public boolean request(Resource resource) throws RAException {

		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("requestedresource", db);
			b = resourceDAO.insert(resource);
			logger.debug("Resource requested..");
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
	public boolean saveResource(Resource resource) {
		boolean b;
		try {
			DB db = initializeDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			b = resourceDAO.save(resource);
			logger.debug("Resource saved..");
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
	public List<Resource> advancedFindByCondition(String name, String value, MongoSortVO sort, int pageNo,
			int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put(name, new BasicDBObject("$regex", value).append("$options", "i"));
			regexQuery.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
			regexQuery.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));
			try {
				list = resourceDAO.getAllByRegex(sort, regexQuery, pageNo, pageSize);
				logger.debug("Resource list find by condition in advanced..");
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
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			count = resourceDAO.getCount(sort);
			logger.debug("Resource counted..");
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
        /**
	 *  Added by v karunakar reddy for getting requested resource count
	 */

	@Override
	public int getCount(BasicDBObject andQuery) {
		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			count = (int) resourceDAO.countByCondition(andQuery);
			logger.debug("Resource counted..");
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
	public List<Resource> requestFindByCondition(Map<String, MongoAdvancedQuery> resourceMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("requestedresource", db);
			try {
				list = resourceDAO.advancedFindByCondition(resourceMappingcondition, sort, pageNo, pageSize);
				logger.debug("Resource find by condition..");
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
	public List<Resource> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		List<Resource> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.getCollection("resource", db);
			resourceDAO.setPojo(new Resource());
			try {
				list = resourceDAO.getAllObjects(sort, page, size);
				logger.debug("Resource list get successfully..");
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
	public Resource findOneByCondition(Map<String, Object> condition) throws RAException {
		Resource res;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			try {
				res = resourceDAO.findOneByCondition(condition);
				logger.debug("Resource find by condition..");
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
		return res;
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		logger.debug("Database initialized..");
		resourceDAO.setPojo(new Resource());
		return db;

	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			b = resourceDAO.updateMapByCondition(condition, target);
			logger.debug("Resource updated by condition..");
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
	public List<Resource> getByMapObjects(MongoSortVO sort, int pageNo, int pageSize, Map<String, Object> condition) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			String mappedPojo = "resourceMapping";
			try {
				list = resourceDAO.getByMapObjects(sort, pageNo, pageSize, mappedPojo, condition);
				logger.debug("Resource list get by Map object..");
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
	public List<Resource> advancedFindByCondition(MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
			regexQuery.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));
			try {
				list = resourceDAO.getAllByRegex(sort, regexQuery, pageNo, pageSize);
				logger.debug("Resource list find by condition in advanced..");
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
	public List<Resource> advancedFindByConditions(String primarySkills, String jobCategory, String location,
			String experience, MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
			regexQuery.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));
			String[] s = primarySkills.split(",");
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			for (int i = 0; i < s.length; i++) {
				obj.add(new BasicDBObject("primarySkills",
						new BasicDBObject("$regex", ".*" + s[i] + ".*").append("$options", "i")));
			}
			if (!primarySkills.equalsIgnoreCase("undefined")) {
				regexQuery.put("$or", obj);
			}
			if (!jobCategory.equalsIgnoreCase("undefined")) {
				regexQuery.put("jobCategory",
						new BasicDBObject("$regex", ".*" + jobCategory + ".*").append("$options", "i"));
			}
			if (!location.equalsIgnoreCase("undefined")) {
				regexQuery.put("currentLocation",
						new BasicDBObject("$regex", ".*" + location + ".*").append("$options", "i"));
			}
			if (!experience.equalsIgnoreCase("undefined")) {
				regexQuery.put("yearsOfExperience",
						new BasicDBObject("$regex", ".*" + experience + ".*").append("$options", "i"));
			}
			try {
				list = resourceDAO.getAllByRegex(sort, regexQuery, pageNo, pageSize);
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return list;
	}
//new modified / customer/ all resource /sidefilter
	@Override
	public List<Resource> advancedFindByCondition(Map<String, MongoAdvancedQuery> resourceMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			try {
				list = resourceDAO.advancedFindByCondition(resourceMappingcondition, sort, pageNo, pageSize);
				int i= resourceDAO.getCount(resourceMappingcondition);
				logger.debug("Resource list find by condition in advanced..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				e.getMessage();
			}
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			e.getMessage();
		}
		return list;
	}

	/**
	 * 
	 */
	
//new modified /vendor /resources/ side filter	
//new modified /customer/ all resource /sidefilter
	
	@Override
	public List<Resource> advancedFindByConditions(String objectId,String skills, String jobCategory, String location,
			String experience, String vendors, String budget, MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			
			BasicDBObject regexQuery = new BasicDBObject();
			
			if(null!=objectId && !objectId.equalsIgnoreCase("undefined")){
			regexQuery.put("registrationId", new ObjectId(objectId));}
			regexQuery.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
			regexQuery.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));
			BasicDBObject obje = null;
			BasicDBList or = null;
			
			DBObject firstOr = null;
			DBObject secondOr = null;
			DBObject thirdOr = null;
			DBObject fourthOr = null;
			DBObject fifthOr = null;
			try {
				String[] skill = skills.split(",");
				String[] jobCat = jobCategory.split(",");
				String[] loc = location.split(",");
				String[] exp = experience.split(",");
				String[] bud = budget.split(",");
				
				if (!skills.equalsIgnoreCase("undefined")) {
					or = new BasicDBList();
					for (int i = 0; i < skill.length; i++) {
						obje = new BasicDBObject("primarySkills",
								new BasicDBObject("$regex", ".*" + skill[i] + ".*").append("$options", "i"));
						or.add(obje);
					}
					firstOr = new BasicDBObject("$or", or);
				}
				if (!jobCategory.equalsIgnoreCase("undefined")) {
					or = new BasicDBList();
					for (int i = 0; i < jobCat.length; i++) {
						obje = new BasicDBObject("jobCategory",
								new BasicDBObject("$regex", ".*" + jobCat[i] + ".*").append("$options", "i"));
						or.add(obje);
					}
					secondOr = new BasicDBObject("$or", or);
				}

				if (!location.equalsIgnoreCase("undefined")) {
					or = new BasicDBList();
					for (int i = 0; i < loc.length; i++) {
						obje = new BasicDBObject("currentLocation",
								new BasicDBObject("$regex", ".*" + loc[i] + ".*").append("$options", "i"));
						or.add(obje);
					}
					thirdOr = new BasicDBObject("$or", or);
				}

				if (!experience.equalsIgnoreCase("undefined")) {
					or = new BasicDBList();
					for (int i = 0; i < exp.length; i++) {
						obje = new BasicDBObject("yearsOfExperience",
								new BasicDBObject("$regex", ".*" + exp[i] + ".*").append("$options", "i"));
						or.add(obje);
					}
					fourthOr = new BasicDBObject("$or", or);
				}
				if (!budget.equalsIgnoreCase("undefined")) {
					or = new BasicDBList();
					for (int i = 0; i < bud.length; i++) {
						obje = new BasicDBObject("budget", new BasicDBObject("$regex", bud[i]).append("$options", "i"));
						or.add(obje);
					}
					fifthOr = new BasicDBObject("$or", or);
				}
			} catch (RAException e) {
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			BasicDBList andValues = new BasicDBList();
			if (null != firstOr) {
				andValues.add(firstOr);
			}
			if (null != secondOr) {
				andValues.add(secondOr);
			}
			if (null != thirdOr) {
				andValues.add(thirdOr);
			}
			if (null != fourthOr) {
				andValues.add(fourthOr);
			}
			if (null != fifthOr) {
				andValues.add(fifthOr);
			}
			
			regexQuery.put("$and", andValues);
			list = resourceDAO.getAllByRegex(sort, regexQuery, pageNo, pageSize);
			//int size = resourceDAO.getCount(regexQuery);
			logger.debug("Resource list find by condition..");
		} catch (RAException e) {
 			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException("Data Not Found !!");
		}
		return list;
	}

	@Override
	public List<Resource> getAllObjects(MongoSortVO sort) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			try {
				list = resourceDAO.getAllObjects(sort);
				logger.debug("Resource list get successfully..");
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
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			try {
				pages = resourceDAO.getPages(sort, pageSize);
				logger.debug("Resource pages created..");
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

	@Override
	public List<Resource> advancedFindByCondition(String name, String value, MongoSortVO sort) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put(name, new BasicDBObject("$regex", value).append("$options", "i"));
			regexQuery.put("hardLock", new BasicDBObject("$regex", "No").append("$options", "i"));
			regexQuery.put("status", new BasicDBObject("$regex", "Active").append("$options", "i"));
			try {
				list = resourceDAO.getAllByRegex(sort, regexQuery);
				logger.debug("Resource list sorted..");
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

	@SuppressWarnings("deprecation")
	@Override
	public List<Resource> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) {
		List<Resource> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			try {
				list = resourceDAO.findAllByCondition(condition, sort);
				logger.debug("Resource list sorted by condition..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Resource> findByCondition(Map<String, Object> condition, MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.getCollection("resource", db);
			resourceDAO.setPojo(new Resource());
			try {
				list = resourceDAO.findByCondition(condition, sort, pageNo, pageSize);
				logger.debug("Resource list pagination created..");
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
	public List<Resource> findAllByCondition(BasicDBObject condition, MongoSortVO sort, int pageNo, int pageSize) {
		List<Resource> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.getCollection("resource", db);
			resourceDAO.setPojo(new Resource());
			try {
				list = resourceDAO.findAllByCondition(condition, sort, pageNo, pageSize);
				int size =resourceDAO.getCount(condition);
				logger.debug("Resource list sorted by condition..");
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
	public List<Resource> findAllByCondition(BasicDBObject condition, MongoSortVO sort) {
		List<Resource> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			resourceDAO.getCollection("resource", db);
			resourceDAO.setPojo(new Resource());
			try {
				list = resourceDAO.findAllByCondition(condition, sort);
				logger.debug("Resource list sorted by condition..");
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
	
	
	
	
	/**
	 * Finds the Top vendor Form the Table
	 */
	@Override
	public Map<String, Integer> findTopVendors(MongoSortVO sort) {
		try {
			String companyName;
			List<Resource> allObjects = getAllObjects(sort);
			Map<String, Integer> duplicates = new HashMap<String, Integer>();
			Map<String, Object> duplicates1 = null;
			for (Resource resource : allObjects) {
				duplicates1 = new HashMap<String, Object>();
				duplicates1.put("_id", resource.getRegistrationId());
				companyName = registrationService.findOneByCondition(duplicates1).getCompanyName();
				if (null != companyName) {
					if (duplicates.containsKey(companyName)) {
						duplicates.put(companyName, duplicates.get(companyName) + 1);
					} else {
						if (null != companyName)
							duplicates.put(companyName, 1);
					}
				}
			}
			return duplicates;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> findTopVendors(MongoSortVO sort, ObjectId vendorId) {
		try {
			String companyName = null;
			List<Resource> allObjects = getAllObjects(sort);
			Map<String, Integer> duplicates = new HashMap<String, Integer>();
			Map<String, Object> duplicates1 = null;
			for (Resource resource : allObjects) {
				duplicates1 = new HashMap<String, Object>();
				duplicates1.put("_id", resource.getRegistrationId());
				Registration reg = registrationService.findOneByCondition(duplicates1);
				duplicates1 = new HashMap<String, Object>();
				duplicates1.put("_id", vendorId);
				Registration vendorReg = registrationService.findOneByCondition(duplicates1);
				if (reg.getSowUser().equals(vendorReg.getSowUser())) {
					if (null != reg)
						companyName = reg.getCompanyName();
					if (null != companyName) {
						if (duplicates.containsKey(companyName)) {
							duplicates.put(companyName, duplicates.get(companyName) + 1);
						} else {
							if (null != companyName)
								duplicates.put(companyName, 1);
						}
					}
				}
			}
			return duplicates;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Map<String, Integer> findTopVendorsByPrimarySkills() {
		final Map<String, Integer> repeatationMap = new ConcurrentHashMap<String, Integer>();
		final MongoSortVO sort = new MongoSortVO();

		try {
			final List<Resource> allObjects = getAllObjects(sort);
			for (final Resource resource : allObjects) {
				if (null != resource) {
					final String skill = resource.getPrimarySkills();
					final String[] skills = skill.split(",");
					for (final String str : skills) {
						if (repeatationMap.containsKey(str)) {
							repeatationMap.put(str, repeatationMap.get(str) + 1);
						} else {
							repeatationMap.put(str, 1);
						}
					}
				}
			}

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
		}
		return repeatationMap;
/*	}
//raghuram
	public int getCount(Object object){
		
		int count = resourceDAO.getCount(object);
		return count;
	}
	
public int getCount(DBObject dbobject){
		
		int count = resourceDAO.getCount(dbobject);
		return count;
	}*/
	
}

	@Override
	public int getCount(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount(DBObject dbobject) {
		// TODO Auto-generated method stub
		return 0;
	}

	}