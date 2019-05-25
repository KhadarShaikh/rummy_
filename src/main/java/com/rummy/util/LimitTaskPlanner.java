package com.rummy.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.CashLimitDAO;
import com.rummy.domain.CashLimit;
import com.rummy.exception.RAException;

public class LimitTaskPlanner {

	@Autowired
	MongoDBClient mongoDBClient;

	@Autowired
	CashLimitDAO cashLimitDAO;

	@Scheduled(cron = "0 0 0 * * ?")
	public void run() {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			cashLimitDAO.getCollection("cashLimit", db);
			cashLimitDAO.setPojo(new CashLimit());

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			CashLimit cashLim = new CashLimit();
			for (CashLimit cashLimit : cashLimitDAO.getAllObjects(sort)) {
				if (cashLimit.getStatus().equals("InActive")) {
					int i = cashLimit.getDate().toString().compareTo(new Date().toString());
					if (i == 0) {
						cashLim.set_id(cashLimit.get_id());
						cashLim.setStatus("Active");
						cashLimitDAO.save(cashLim);
					}
				}
			}

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
	}

}
