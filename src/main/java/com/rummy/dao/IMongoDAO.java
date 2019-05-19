package com.rummy.dao;

import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.rummy.exception.RAException;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoSortVO;

public interface IMongoDAO<T> {

	/**
	 * 
	 * 
	 * @param object
	 *            pojo
	 * @return true|false
	 */
	boolean insert(T object) throws RAException;

	/**
	 * ("_id")
	 * 
	 * @param objects
	 *            _id"
	 * @return true|false
	 */
	boolean insertList(List<T> objects) throws RAException;

	/**
	 * 
	 * 
	 * @param object
	 *            pojo_id"
	 * @return true|false
	 */
	boolean save(T object) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 
	 * 
	 * @param condition
	 *            key:value:
	 * @param target
	 * @return true|false
	 */
	boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE field=value
	 * 
	 * 
	 * @param field
	 * @param value
	 * @param target
	 * @return true|false
	 */
	boolean updateMapByKV(String field, String value, Map<String, Object> target) throws RAException;;

	/**
	 * "_id""_id value UPDATE tblname SET xx=xx... WHERE _id=value
	 * 
	 * 
	 * @param value
	 *            "_id
	 * @param target
	 * @return true|false
	 */
	boolean updateMapByPrimaryId(String value, Map<String, Object> target) throws RAException;;

	/**
	 *
	 * UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 
	 * 
	 * @param condition
	 *            key:,value:
	 * @param object
	 *            pojo
	 * @return false
	 */
	boolean updateObjectByCondition(Map<String, Object> condition, T target) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE field=value
	 * 
	 * 
	 * @param field
	 * @param value
	 * @param object
	 * @return true|false
	 */
	boolean updateObjectByKV(String field, String value, T target) throws RAException;

	/**
	 * _id" "value" UPDATE tblname SET xx=xx... WHERE _id=value
	 * 
	 * 
	 * @param value
	 *            "_id"
	 * @param object
	 *            pojo
	 * @return true|false
	 */
	boolean updateObjectByPrimaryId(String value, T target) throws RAException;

	/**
	 * "_id" :UPDATE tblname field=field+step SET WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @param column
	 * @param step
	 * @return true|false
	 */
	boolean incrByPrimaryId(String value, String column, int step) throws RAException;

	/**
	 * "_id" :UPDATE tblname field=field-step SET WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @param column
	 * @param step
	 * @return true|false
	 */
	boolean decrByPrimaryId(String value, String column, int step) throws RAException;

	/**
	 * 
	 * DELETE FROM tblname WHERE key1=value1 AND key2=value2...
	 * 
	 * @param condition
	 *            key:,value:
	 * @return true|false
	 */
	boolean removeByCondition(Map<String, Object> condition) throws RAException;

	/**
	 * 
	 * DELETE FROM tblname WHERE field=value
	 * 
	 * @param field
	 * @param value
	 * @return true|false
	 */
	boolean removeByKV(String field, String value) throws RAException;

	/**
	 * "_id""_id" "value" DELETE FROM tblname WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @return true|false
	 */
	boolean removeByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * 
	 * @return
	 */
	@Deprecated
	boolean removeAll() throws RAException;

	/**
	 * 
	 * SELECT * FROM tblname WHERE key1=value1 AND key2=value2... LIMIT 1
	 * 
	 * @param condition
	 *            key:,value:
	 * @return
	 */
	T findOneByCondition(Map<String, Object> condition) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE field=value LIMIT 1
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	T findOneByKV(String field, String value) throws RAException;

	/**
	 * "_id" "_id""value" SELECT * FROM tblname WHERE _id=value LIMIT 1
	 * 
	 * @param value
	 *            "_id"
	 * @return
	 */
	T findOneByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC
	 * 
	 * @param condition
	 *            key:,value:
	 * @param sort
	 * @return
	 */
	@Deprecated
	List<T> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	List<T> findByCondition(Map<String, Object> condition, MongoSortVO sort, int page, int size) throws RAException;

	/**
	 * 
	 * SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2... ORDER
	 * BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:
	 * @return
	 */
	long countByCondition(Map<String, Object> condition) throws RAException;

	long countByCondition(BasicDBObject condition) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:AdvancedQuery
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page, int size)
			throws RAException;

	/**
	 *
	 * 类似:SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2...
	 * ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:AdvancedQuery
	 * @return
	 */
	long advancedCountByCondition(Map<String, MongoAdvancedQuery> condition) throws RAException;

	/**
	 * closes the underlying connector, which in turn closes all open
	 * connections. Once called, this Mongo instance can no longer be used.
	 */
	void close();

	/**
	 * 
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 * @throws RAException
	 */
	List<T> getAllObjects(MongoSortVO sort, int page, int size) throws RAException;

	/**
	 * 
	 * @param sort
	 * @return
	 * @throws RAException
	 */
	List<T> getAllObjects(MongoSortVO sort) throws RAException;

	/**
	 * 
	 * @param sort
	 * @return
	 * @throws RAException
	 */
	int getCount(MongoSortVO sort) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param page
	 * @param size
	 * @param mappedPojo
	 * @param mapCondition
	 * @return
	 * @throws RAException
	 */
	List<T> getByMapObjects(MongoSortVO sort, int page, int size, String mappedPojo, Map<String, Object> mapCondition)
			throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageSize
	 * @return
	 * @throws RAException
	 */
	int getPages(MongoSortVO sort, int pageSize) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param regexQuery
	 * @param page
	 * @param size
	 * @return
	 * @throws RAException
	 */
	List<T> getAllByRegex(MongoSortVO sort, BasicDBObject regexQuery, int page, int size) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param regexQuery
	 * @return
	 * @throws RAException
	 */
	List<T> getAllByRegex(MongoSortVO sort, BasicDBObject regexQuery) throws RAException;

	/**
	 * author raghuram
	 */
	public int getCount(Map<String, MongoAdvancedQuery> condition) throws RAException;

	public int getCount(BasicDBObject condition) throws RAException;

	public int getCountOf(Map<String, Object> condition) throws RAException;

	List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort) throws RAException;

	public boolean remove(T object) throws RAException;

}
