package com.rummy.mapper;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class CashLimitMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("registrationId")
	private String registrationId;

	@JsonProperty("date")
	private Date date;

	@JsonProperty("dailyLimit")
	private String dailyLimit;

	@JsonProperty("monthlyLimit")
	private String monthlyLimit;

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

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(String dailyLimit) {
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
