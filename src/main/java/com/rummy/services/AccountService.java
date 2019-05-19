package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.Account;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface AccountService {
	/**
	 * 
	 * @param payment
	 * @return
	 * @throws RAException
	 */
	boolean createAccount(Account account) throws RAException;

	/**
	 * 
	 * @param payment
	 * @return
	 * @throws RAException
	 */
	boolean saveAccount(Account account) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Account> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	Account findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	Account findOneByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * @param sort
	 * @return
	 */
	int getCount(MongoSortVO sort);

	/**
	 * 
	 * @param sort
	 * @param pageSize
	 * @return
	 */
	int getPages(MongoSortVO sort, int pageSize);

}
