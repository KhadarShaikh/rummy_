package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.TransactionHistory;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface TransactionHistoryService {
	/**
	 * 
	 * @param transactionHistory
	 * @return
	 * @throws RAException
	 */
	boolean createTransactionHistory(TransactionHistory transactionHistory) throws RAException;

	/**
	 * 
	 * @param transactionHistory
	 * @return
	 * @throws RAException
	 */
	boolean saveTransactionHistory(TransactionHistory transactionHistory) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<TransactionHistory> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	TransactionHistory findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	TransactionHistory findOneByPrimaryId(String value) throws RAException;

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
