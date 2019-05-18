package com.rummy.services;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.rummy.domain.Registration;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface RegistrationService {
	/**
	 * 
	 * @param registration
	 * @return
	 * @throws RAException
	 */
	boolean create(Registration registration) throws RAException;

	/**
	 * 
	 * @param registration
	 * @return
	 * @throws RAException
	 */
	boolean save(Registration registration) throws RAException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RAException
	 */
	boolean delete(ObjectId id) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Registration> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean removeByPrimaryId(String id);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	Registration findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param condition
	 * @param sort
	 * @return
	 * @throws RAException
	 */
	List<Registration> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	Registration findOneByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * @param condition
	 * @param target
	 * @return
	 */
	boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

	/**
	 * 
	 * @param sort
	 * @return
	 */
	List<Registration> getAllObjects(MongoSortVO sort);

	/**
	 * 
	 * @param nameOftheProperty
	 * @param valueOftheProperty
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Registration> advancedFindByCondition(String nameOftheProperty, String valueOftheProperty, MongoSortVO sort,
			int pageNo, int pageSize);

	/**
	 * 
	 * @param sort
	 * @param pageSize
	 * @return
	 */
	int getPages(MongoSortVO sort, int pageSize);

	/**
	 * 
	 * @param sort
	 * @return
	 */
	int getCount(MongoSortVO sort);
}
