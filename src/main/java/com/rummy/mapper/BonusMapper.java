package com.rummy.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class BonusMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("registrationId")
	private String registrationId;

	@JsonProperty("promoCode")
	private String promoCode;

	@JsonProperty("bonusAmt")
	private String bonusAmt;

	@JsonProperty("released")
	private String released;

	@JsonProperty("redeemed")
	private String redeemed;

	@JsonProperty("expires")
	private String expires;

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

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getBonusAmt() {
		return bonusAmt;
	}

	public void setBonusAmt(String bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getRedeemed() {
		return redeemed;
	}

	public void setRedeemed(String redeemed) {
		this.redeemed = redeemed;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

}
