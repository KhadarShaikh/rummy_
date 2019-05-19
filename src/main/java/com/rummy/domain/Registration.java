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
public class Registration implements Serializable {

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("registeredDate")
	private Date registeredDate;

	@JsonProperty("status")
	private String status;

	@JsonProperty("mailId")
	private String mailId;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("mobile")
	private String mobile;

	@JsonProperty("file")
	private byte[] file;

	@JsonProperty("idProofType")
	private String idProofType;

	@JsonProperty("idProofNo")
	private String idProofNo;

	public String getIdProofType() {
		return idProofType;
	}

	public void setIdProofType(String idProofType) {
		this.idProofType = idProofType;
	}

	public String getIdProofNo() {
		return idProofNo;
	}

	public void setIdProofNo(String idProofNo) {
		this.idProofNo = idProofNo;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] bs) {
		this.file = bs;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
