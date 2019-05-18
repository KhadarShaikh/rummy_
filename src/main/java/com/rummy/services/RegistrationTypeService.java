package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.RegistrationType;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface RegistrationTypeService {
	/**
	 * 
	 * @param registrationType
	 * @return
	 */
	boolean createRegistrationType(RegistrationType registrationType);

	/**
	 * 
	 * @param registrationType
	 * @return
	 */
	boolean updateRegistrationType(RegistrationType registrationType);

	/**
	 * 
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	List<RegistrationType> getAllRegistrationType(MongoSortVO sort, int page, int size);

	/**
	 * 
	 * @param sort
	 * @return
	 */
	List<RegistrationType> getAllRegistrationType(MongoSortVO sort);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	RegistrationType getOneByCondition(Map<String, Object> condition);

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
