package com.rummy;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.rummy.exception.RAException;

/**
 * 
 * @author skkhadar
 *
 */
public class MongoDBClientImpl implements MongoDBClient {

	MongoConfig mongoConfig;

	MongoClient mongoClient;

	MongoDBClientImpl(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	Logger logger = Logger.getLogger(MongoDBClientImpl.class);

	@SuppressWarnings("deprecation")
	public MongoClient getReadMongoClient() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));
			mongoClient.setReadPreference(ReadPreference.primaryPreferred());
			logger.debug("Read Connection opened..");
		} catch (Exception e) {
			logger.error("Error occur During the getting connection..");
			throw new RAException("Error occur During the getting connection");
		}
		return mongoClient;
	}

	@SuppressWarnings("deprecation")
	public MongoClient getWriteMongoClient() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));
			mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
			logger.debug("Write Connection opened..");
		} catch (Exception e) {
			logger.error("Error occur During the getting connection..");
			throw new RAException("Error occur During the getting connection");
		}
		return mongoClient;
	}

	@SuppressWarnings("deprecation")
	@Override
	public DB getReadMongoDB() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));
			mongoClient.setReadPreference(ReadPreference.primaryPreferred());
			logger.debug("Read Connection opened..");
		} catch (Exception e) {
			logger.error("Error occur During the getting connection..");
			throw new RAException("Error occur During the getting connection");
		}
		return mongoClient.getDB(mongoConfig.getDatabase());
	}

	@SuppressWarnings("deprecation")
	@Override
	public DB getWriteMongoDB() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));
			mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
			logger.debug("Write Connection opened..");
		} catch (Exception e) {
			logger.error("Error occur During the getting connection..");
			throw new RAException("Error occur During the getting connection");
		}
		return mongoClient.getDB(mongoConfig.getDatabase());
	}

	@Override
	public void closeMongoClient() throws RAException {
		try {
			mongoClient.close();
			logger.error("Connection closed..");
		} catch (Exception e) {
			logger.error("Error occur During the closing connection..");
			throw new RAException("Error occur During the closing connection");
		}

	}

}
