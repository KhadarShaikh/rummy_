package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.CashLimit;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface CashLimitService {
	/**
	 * 
	 * @param cashLimit
	 * @return
	 * @throws RAException
	 */
	boolean createCashLimit(CashLimit cashLimit) throws RAException;

	/**
	 * 
	 * @param cashLimit
	 * @return
	 * @throws RAException
	 */
	boolean saveCashLimit(CashLimit cashLimit) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CashLimit> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	CashLimit findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	CashLimit findOneByPrimaryId(String value) throws RAException;

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
