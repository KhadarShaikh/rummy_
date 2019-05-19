package com.rummy.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class Account implements Serializable {

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("registrationId")
	private ObjectId registrationId;

	@JsonProperty("totalBalance")
	private String totalBalance;

	@JsonProperty("inPlayAmount")
	private String inPlayAmount;

	@JsonProperty("depositBalance")
	private String depositBalance;

	@JsonProperty("withdrawableBalance")
	private String withdrawableBalance;

	@JsonProperty("pendingWithdrawals")
	private String pendingWithdrawals;

	@JsonProperty("loyaltyPoints")
	private String loyaltyPoints;

	@JsonProperty("PracticeAmt")
	private String PracticeAmt;

	@JsonProperty("PracticeAmtInPlay")
	private String PracticeAmtInPlay;

	@JsonProperty("totalPracticeBalance")
	private String totalPracticeBalance;

	public ObjectId getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(ObjectId registrationId) {
		this.registrationId = registrationId;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getInPlayAmount() {
		return inPlayAmount;
	}

	public void setInPlayAmount(String inPlayAmount) {
		this.inPlayAmount = inPlayAmount;
	}

	public String getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(String depositBalance) {
		this.depositBalance = depositBalance;
	}

	public String getWithdrawableBalance() {
		return withdrawableBalance;
	}

	public void setWithdrawableBalance(String withdrawableBalance) {
		this.withdrawableBalance = withdrawableBalance;
	}

	public String getPendingWithdrawals() {
		return pendingWithdrawals;
	}

	public void setPendingWithdrawals(String pendingWithdrawals) {
		this.pendingWithdrawals = pendingWithdrawals;
	}

	public String getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(String loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public String getPracticeAmt() {
		return PracticeAmt;
	}

	public void setPracticeAmt(String practiceAmt) {
		PracticeAmt = practiceAmt;
	}

	public String getPracticeAmtInPlay() {
		return PracticeAmtInPlay;
	}

	public void setPracticeAmtInPlay(String practiceAmtInPlay) {
		PracticeAmtInPlay = practiceAmtInPlay;
	}

	public String getTotalPracticeBalance() {
		return totalPracticeBalance;
	}

	public void setTotalPracticeBalance(String totalPracticeBalance) {
		this.totalPracticeBalance = totalPracticeBalance;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Account() {
		super();
	}

}
