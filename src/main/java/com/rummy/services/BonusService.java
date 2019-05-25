package com.rummy.services;

import java.util.List;
import java.util.Map;

import com.rummy.domain.Bonus;
import com.rummy.exception.RAException;
import com.rummy.util.MongoSortVO;

/**
 * 
 * @author skkhadar
 *
 */
public interface BonusService {
	/**
	 * 
	 * @param bonus
	 * @return
	 * @throws RAException
	 */
	boolean createBonus(Bonus bonus) throws RAException;

	/**
	 * 
	 * @param Bonus
	 * @return
	 * @throws RAException
	 */
	boolean saveBonus(Bonus bonus) throws RAException;

	/**
	 * 
	 * @param sort
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Bonus> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	/**
	 * 
	 * @param condition
	 * @return
	 */
	Bonus findOneByCondition(Map<String, Object> condition);

	/**
	 * 
	 * @param value
	 * @return
	 * @throws RAException
	 */
	Bonus findOneByPrimaryId(String value) throws RAException;

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
