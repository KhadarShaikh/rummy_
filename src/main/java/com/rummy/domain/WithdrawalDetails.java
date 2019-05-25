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
public class WithdrawalDetails implements Serializable {

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("registrationId")
	private ObjectId registrationId;

	@JsonProperty("withdrawalId")
	private ObjectId withdrawalId;

	@JsonProperty("withdrawalType")
	private String withdrawalType;

	@JsonProperty("requestTime")
	private String requestTime;

	@JsonProperty("amount")
	private String amount;

	@JsonProperty("status")
	private String status;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("action")
	private String action;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public ObjectId getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(ObjectId registrationId) {
		this.registrationId = registrationId;
	}

	public ObjectId getWithdrawalId() {
		return withdrawalId;
	}

	public void setWithdrawalId(ObjectId withdrawalId) {
		this.withdrawalId = withdrawalId;
	}

	public String getWithdrawalType() {
		return withdrawalType;
	}

	public void setWithdrawalType(String withdrawalType) {
		this.withdrawalType = withdrawalType;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
