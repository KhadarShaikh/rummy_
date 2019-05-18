package com.rummy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.rummy.exception.RAException;

/**
 * 
 * @author skkhadar
 *
 */
public class MongoServiceFactory implements FactoryBean<MongoDBClient> {

	@Autowired
	private MongoConfig mongoConfig;

	@Override
	public MongoDBClient getObject() throws RAException {
		return new MongoDBClientImpl(mongoConfig);
	}

	@Override
	public Class<?> getObjectType() {
		return MongoDBClientImpl.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
