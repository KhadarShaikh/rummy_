package com.rummy.domain;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class CashLimit implements Serializable {

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("registrationId")
	private ObjectId registrationId;

	@JsonProperty("dailyLimit")
	private ObjectId dailyLimit;

	@JsonProperty("monthlyLimit")
	private String monthlyLimit;
	
	@JsonProperty("date")
	private Date date;

	@JsonProperty("currentDailyLimit")
	private String currentDailyLimit;

	@JsonProperty("currentMonthlyLimit")
	private String currentMonthlyLimit;

	@JsonProperty("cashAddedToday")
	private String cashAddedToday;

	@JsonProperty("cashAddedMonth")
	private String cashAddedMonth;

	@JsonProperty("status")
	private String status;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public ObjectId getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(ObjectId dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public String getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(String monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public String getCurrentDailyLimit() {
		return currentDailyLimit;
	}

	public void setCurrentDailyLimit(String currentDailyLimit) {
		this.currentDailyLimit = currentDailyLimit;
	}

	public String getCurrentMonthlyLimit() {
		return currentMonthlyLimit;
	}

	public void setCurrentMonthlyLimit(String currentMonthlyLimit) {
		this.currentMonthlyLimit = currentMonthlyLimit;
	}

	public String getCashAddedToday() {
		return cashAddedToday;
	}

	public void setCashAddedToday(String cashAddedToday) {
		this.cashAddedToday = cashAddedToday;
	}

	public String getCashAddedMonth() {
		return cashAddedMonth;
	}

	public void setCashAddedMonth(String cashAddedMonth) {
		this.cashAddedMonth = cashAddedMonth;
	}

}
