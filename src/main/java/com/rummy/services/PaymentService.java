package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.Payment;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface PaymentService {
	/**
	 * 
	 * @param payment
	 * @return
	 * @throws RAException
	 */
	boolean createPayment(Payment payment) throws RAException;

	/**
	 * 
	 * @param payment
	 * @return
	 * @throws RAException
	 */
	boolean savePayment(Payment payment) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Payment> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	Payment findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	Payment findOneByPrimaryId(String value) throws RAException;

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
