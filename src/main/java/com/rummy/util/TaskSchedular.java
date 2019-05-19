
package com.rummy.util;

/**
 * 
 * @author skkhadar
 *
 */
public class TaskSchedular {

	/*
	 * @Autowired private SendMail sendMail;
	 * 
	 * @Autowired MongoDBClient mongoDBClient;
	 * 
	 * @Autowired RegistrationDAO registrationDAO;
	 * 
	 * //@Scheduled(cron = "0 0 0 * * ?") public void run() { Registration
	 * registration = new Registration();
	 * 
	 * DB db = mongoDBClient.getReadMongoDB();
	 * registrationDAO.getCollection("registration", db);
	 * registrationDAO.setPojo(new Registration());
	 * 
	 * MongoSortVO sort = new MongoSortVO(); sort.setPrimaryKey("_id");
	 * sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
	 * 
	 * for (Registration re : registrationDAO.getAllObjects(sort, 1, 10)) {
	 * 
	 * String currentDate = re.getRegisteredDate().toString() +
	 * registration.getTrailPeriod(); int result = new
	 * Date().toString().compareTo(currentDate); if (result == 0) {
	 * 
	 * UserAccount userAccount = new UserAccount();
	 * userAccount.setStatus("inactive");
	 * 
	 * String subject = "Trail Period Expired.."; String msg =
	 * "your tail period is expired please buy now..!";
	 * sendMail.sendMail(registration.getMailId(), subject, msg);
	 * 
	 * } }
	 * 
	 * }
	 */
}
