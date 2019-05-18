package com.rummy;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.rummy.exception.RAException;

/**
 * 
 * @author skkhadar
 *
 */
public interface MongoDBClient {

	/**
	 * 
	 * @return
	 * @throws RAException
	 */
	MongoClient getReadMongoClient() throws RAException;

	/**
	 * 
	 * @return
	 * @throws RAException
	 */
	MongoClient getWriteMongoClient() throws RAException;;

	/**
	 * 
	 * @return
	 * @throws RAException
	 */
	DB getReadMongoDB() throws RAException;;

	/**
	 * 
	 * @return
	 * @throws RAException
	 */
	DB getWriteMongoDB() throws RAException;;

	/**
	 * 
	 * @throws RAException
	 */
	void closeMongoClient() throws RAException;;

}
