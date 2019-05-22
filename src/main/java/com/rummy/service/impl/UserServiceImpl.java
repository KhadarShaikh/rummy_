package com.rummy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.UserAccountStatus;
import com.rummy.dao.AccessTokenDAO;
import com.rummy.dao.RoleDAO;
import com.rummy.dao.RoleMappingDAO;
import com.rummy.dao.UserAccountDAO;
import com.rummy.domain.AccessToken;
import com.rummy.domain.Role;
import com.rummy.domain.UserAccount;
import com.rummy.exception.RAException;
import com.rummy.services.UserService;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoOrderByEnum;
import com.rummy.util.MongoSortVO;
import com.rummy.util.SendMail;

/**
 * 
 * @author skkhadar
 *
 */
public class UserServiceImpl implements UserService {

	@Autowired
	UserAccountDAO userAccountDAO;

	@Autowired
	AccessTokenDAO accessTokenDAO;

	@Autowired
	RoleMappingDAO roleMappingDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	SendMail sendMail;

	@Autowired
	private MongoDBClient mongoDBClient;

	Logger logger = Logger.getLogger(UserServiceImpl.class);

	public Role getRole(String role) {
		return null;
	}

	public boolean create(UserAccount user) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("username", 1));
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("mailId", 1));
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("mobile", 1));
			user.setStatus(UserAccountStatus.STATUS_DISABLED.name());
			b = userAccountDAO.insert(user);
			userAccountDAO.getCollection("userAccount", db).dropIndexes();
			logger.debug("user created..");
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

	public void save(UserAccount user) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("username", 1));
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("mailId", 1));
			userAccountDAO.getCollection("userAccount", db).createIndex(new BasicDBObject("mobile", 1));
			userAccountDAO.save(user);
			userAccountDAO.getCollection("userAccount", db).dropIndexes();
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error(e.getMessage());
			throw new RAException(e.getMessage());
		}
	}

	@Override
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			b = userAccountDAO.removeByCondition(condition);
			logger.debug("user deleted..");
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

	public UserAccount getByUsername(String username) throws RAException {
		UserAccount userAccount = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			Map<String, Object> condition = new HashMap<String, Object>();
			userAccountDAO.getCollection("userAccount", db);
			userAccountDAO.setPojo(new UserAccount());
			condition.put("username", username);
			try {
				userAccount = userAccountDAO.findOneByCondition(condition);
				logger.debug("user get by userName..");
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
		return userAccount;
	}

	@Override
	public int getCount(MongoSortVO sort) {
		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			count = userAccountDAO.getCount(sort);
			logger.debug("user counted successfully..");
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

	public AccessToken createAccessToken(UserAccount user) throws RAException {
		AccessToken accessToken = null;
		try {
			DB db = mongoDBClient.getWriteMongoDB();
			logger.debug("Database initialized..");
			accessToken = new AccessToken(user.getUsername(), UUID.randomUUID().toString());
			accessTokenDAO.setPojo(accessToken);
			accessTokenDAO.getCollection("accessToken", db);
			accessTokenDAO.insert(accessToken);
			mongoDBClient.closeMongoClient();
			logger.debug("connection closed..");
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			logger.error("connection closed..");
			logger.error("accesToken is not crated" + e.getMessage());
			throw new RAException(e.getMessage());
		}
		return accessToken;
	}

	@Override
	public UserAccount loadUserByUsername(String arg0) throws RAException {
		UserAccount userAccount = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			Map<String, Object> condition = new HashMap<String, Object>();
			userAccountDAO.getCollection("userAccount", db);
			userAccountDAO.setPojo(new UserAccount());
			condition.put("username", arg0);
			try {
				userAccount = userAccountDAO.findOneByCondition(condition);
				logger.debug("useraccount load by userName..");
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
		return userAccount;
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserAccount getUserByToken(String arg0) throws RAException {
		UserAccount userAccount = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("token", arg0);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("expiry");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			DB db = mongoDBClient.getWriteMongoDB();
			accessTokenDAO.setPojo(new AccessToken());
			accessTokenDAO.getCollection("accessToken", db);
			String userName = null;
			List<AccessToken> list;
			try {
				list = accessTokenDAO.findAllByCondition(condition, sort);
				logger.debug("userAccount get by userToken..");
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();
				logger.error("connection closed..");
				logger.error(e.getMessage());
				throw new RAException("Data Not Found !!");
			}
			if (null != list && 0 < list.size()) {
				for (AccessToken pojo : list) {
					userName = pojo.getUserName();
					// break;
				}
			}
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			Map<String, Object> usercondition = new HashMap<String, Object>();
			usercondition.put("username", userName);
			try {
				userAccount = userAccountDAO.findOneByCondition(usercondition);
				logger.debug("user account find by condition..");
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
		return userAccount;
	}

	public boolean removeByPrimaryId(ObjectId value) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			b = userAccountDAO.removeByCondition(condition);
			logger.debug("user removed by primaryId..");
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

	public List<UserAccount> findByRegistrationId(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort,
			int pageNo, int pageSize) throws RAException {
		List<UserAccount> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			try {
				list = userAccountDAO.advancedFindByCondition(condition, sort, pageNo, pageSize);
				logger.debug("userAccount list find by RegistrationId..");
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

	public UserAccount findOneByCondition(Map<String, Object> condition) throws RAException {
		UserAccount userAccount = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			try {
				userAccount = userAccountDAO.findOneByCondition(condition);
				logger.debug("userAccount find by condition..");
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
		return userAccount;
	}

	@Override
	public List<UserAccount> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) {
		List<UserAccount> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			try {
				list = userAccountDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("userAccount list pagination successfully created..");
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
		}
		return list;
	}

	@Override
	public List<Role> getAllRoles(MongoSortVO sort, int pageNo, int pageSize) {
		List<Role> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			try {
				list = roleDAO.getAllObjects(sort, pageNo, pageSize);
				logger.debug("Role list pagination completed..");
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
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			b = userAccountDAO.updateMapByCondition(condition, target);
			logger.debug("userAccountMap updated by condition..");
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
	public List<UserAccount> advancedFindByCondition(Map<String, MongoAdvancedQuery> requementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		List<UserAccount> list = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			try {
				list = userAccountDAO.advancedFindByCondition(requementMappingcondition, sort, pageNo, pageSize);
				logger.debug("userAccount list find by condition in advanced..");
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
	public List<UserAccount> advancedSearchUserString(BasicDBObject orQuery, MongoSortVO sort, int pageNo,
			int pageSize) {
		List<UserAccount> userlist;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			try {
				userlist = userAccountDAO.getAllByRegex(sort, orQuery, pageNo, pageSize);
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
		return userlist;
	}

	@Override
	public int getPages(MongoSortVO sort, int pageSize) {
		int pages;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			try {
				pages = userAccountDAO.getPages(sort, pageSize);
				logger.debug("userAccount pages gets sorted..");
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
	public int getCount(Map<String, MongoAdvancedQuery> userMappingcondition) {

		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			count = (int) userAccountDAO.advancedCountByCondition(userMappingcondition);
			logger.debug("user counted successfully..");
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
	public int getCount(BasicDBObject userMappingcondition) {

		int count;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			logger.debug("Database initialized..");
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			count = (int) userAccountDAO.countByCondition(userMappingcondition);
			logger.debug("user counted successfully..");
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

}
