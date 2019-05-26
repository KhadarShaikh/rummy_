package com.rummy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	// @Scheduled(cron = "0 0 0 * * ?")
	@Scheduled(cron = "*/5 * * * * ?")
	public void run() throws ParseException {
		try {
			System.out.println("LIMIT TASK PLANNER...");
			DB db = mongoDBClient.getReadMongoDB();
			cashLimitDAO.getCollection("cashLimit", db);
			cashLimitDAO.setPojo(new CashLimit());

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:SS");

			CashLimit cashLim = new CashLimit();
			for (CashLimit cashLimit : cashLimitDAO.getAllObjects(sort)) {
				if (cashLimit.getStatus().equals("InActive")) {
					Date storedDate = format.parse(cashLimit.getDate().toString());
					long diff = storedDate.getTime() - new Date().getTime();
					long diffHours = diff / (60 * 60 * 1000);
					System.out.println(diffHours);
					if (diffHours >= 72) {
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
