package com.rummy.transfer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.ToStringSerializer;

import com.rummy.mapper.RoleMapper;

/**
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class UserTransfer implements Serializable {

	@JsonSerialize(using = ToStringSerializer.class)
	private String _id;

	@JsonProperty("username")
	private String username;

	@JsonProperty("newPwd")
	private String newPwd;

	@JsonProperty("confirmPwd")
	private String confirmPwd;

	@JsonProperty("password")
	private String password;

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;

	@JsonProperty("status")
	private String status;

	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;

	private String registrationId;

	private List<RoleMapper> roleMapper;

	private Map<String, String> roles;

	@JsonProperty("mobile")
	private String mobile;

	@JsonProperty("dob")
	private String dob;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("zipcode")
	private String zipcode;

	@JsonProperty("address")
	private String address;

	@JsonProperty("promEmails")
	private String promEmails;

	@JsonProperty("promMsgs")
	private String promMsgs;

	@JsonProperty("mailId")
	private String mailId;

	@JsonProperty("kyc")
	private String kyc;

	@JsonProperty("otpVal")
	private String otpVal;

	@JsonProperty("operationType")
	private String operationType;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOtpVal() {
		return otpVal;
	}

	public void setOtpVal(String otpVal) {
		this.otpVal = otpVal;
	}

	public String getKyc() {
		return kyc;
	}

	public void setKyc(String kyc) {
		this.kyc = kyc;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPromMsgs() {
		return promMsgs;
	}

	public void setPromMsgs(String promMsgs) {
		this.promMsgs = promMsgs;
	}

	public String getPromEmails() {
		return promEmails;
	}

	public void setPromEmails(String promEmails) {
		this.promEmails = promEmails;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserTransfer() {
	}

	public String getMobile() {

		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<RoleMapper> getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(List<RoleMapper> roleMapper) {
		this.roleMapper = roleMapper;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public Map<String, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

}