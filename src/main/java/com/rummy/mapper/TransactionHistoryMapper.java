package com.rummy.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class TransactionHistoryMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("registrationId")
	private String registrationId;

	@JsonProperty("transactionId")
	private String transactionId;

	@JsonProperty("transactionType")
	private String transactionType;

	@JsonProperty("transactionDate")
	private String transactionDate;

	@JsonProperty("timestamp")
	private String timestamp;

	@JsonProperty("validYear")
	private String validYear;

	@JsonProperty("details")
	private String details;

	@JsonProperty("transactionAmount")
	private String transactionAmount;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getValidYear() {
		return validYear;
	}

	public void setValidYear(String validYear) {
		this.validYear = validYear;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

}
