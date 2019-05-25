package com.rummy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.rummy.domain.UserAccount;

public class VerifySMS {

	public static String verifyOTP(UserAccount userAccount) {
		String response = null;
		// Your apikey key
		String apiKey = "YourApiKey";
		// OR
		String userId = "YourUserName";
		String password = "YourPassword";

		// Send Method (generate|verify)
		String sendMethod = "verify";

		// Multiple mobiles numbers separated by comma
		String mobile = "919999999999";

		// Your message to terminate, URLEncode the content
		String otp = "6789";

		// response format
		String format = "json";

		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		// URL encode message
		String urlencodedotp = URLEncoder.encode(otp);

		// API End Point
		String mainUrl = "http://enterprise.smsgatewaycenter.com/OTPApi/send?";

		// API Paramters
		StringBuilder sendSmsData = new StringBuilder(mainUrl);
		sendSmsData.append("apiKey=" + apiKey);
		sendSmsData.append("&userId=" + userId);
		sendSmsData.append("&password=" + password);
		sendSmsData.append("&sendMethod=" + sendMethod);
		sendSmsData.append("&mobile=" + mobile);
		sendSmsData.append("&otp=" + urlencodedotp);
		sendSmsData.append("&format=" + format);
		// final string
		mainUrl = sendSmsData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			// reading response

			while ((response = reader.readLine()) != null)
				// print response
				System.out.println(response);

			// finally close connection
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
