package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.Role;
import com.rummy.exception.RAException;
import com.rummy.util.MongoAdvancedQuery;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface RoleService {
	/**
	 * 
	 * @param role
	 * @return
	 * @throws RAException
	 */
	boolean createRole(Role role) throws RAException;

	/**
	 * 
	 * @param condition
	 * @return
	 */
	Role findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param sort
	 * @param page
	 * @param size
	 * @param mapCondition
	 * @return
	 * @throws RAException
	 */
	List<Role> getByMapObjects(MongoSortVO sort, int page, int size, Map<String, Object> mapCondition)
			throws RAException;

	/**
	 * 
	 * @param condition
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Role getByRoleName(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int pageNo, int pageSize);
}
