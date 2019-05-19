package com.rummy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.mongodb.DB;
import com.rummy.MongoDBClient;
import com.rummy.dao.UserAccountDAO;
import com.rummy.domain.UserAccount;
import com.rummy.exception.RAException;

public class PromotionalTaskPlanner {

	@Autowired
	private SendMail sendMail;

	@Autowired
	MongoDBClient mongoDBClient;

	@Autowired
	UserAccountDAO userAccountDAO;

	@Scheduled(cron = "0 0 0 * * ?")
	public void run() {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.getCollection("userAccount", db);
			userAccountDAO.setPojo(new UserAccount());

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (UserAccount user : userAccountDAO.getAllObjects(sort)) {
				if (user.getPromEmails().equalsIgnoreCase("yes")) {
					String subject = user.getUsername() + "activate your account to get your special Welcome Bonus!";
					String msg = "";
					sendMail.sendMail(user.getMailId(), subject, msg);
				}
				if (user.getPromMsgs().equalsIgnoreCase("yes")) {

				}
			}

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
	}

}
