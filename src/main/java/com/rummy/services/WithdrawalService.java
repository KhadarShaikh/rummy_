package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.WithdrawalDetails;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface WithdrawalService {
	/**
	 * 
	 * @param withdrawalDetails
	 * @return
	 * @throws RAException
	 */
	boolean createWithdrawalDetails(WithdrawalDetails withdrawalDetails) throws RAException;

	/**
	 * 
	 * @param withdrawalDetails
	 * @return
	 * @throws RAException
	 */
	boolean saveWithdrawalDetails(WithdrawalDetails withdrawalDetails) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<WithdrawalDetails> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	WithdrawalDetails findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	WithdrawalDetails findOneByPrimaryId(String value) throws RAException;

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
