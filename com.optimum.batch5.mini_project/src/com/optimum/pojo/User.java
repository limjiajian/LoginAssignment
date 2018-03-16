package com.optimum.pojo;

public class User {
	/* primary key */
	int serialno;

	/* user's particulars */
	String name;
	String nric;
	String doB;
	String email;
	String logID;
	String mobile;

	/* security purpose */
	String password;
	String role;
	String secQ;
	String secAns;
	int firstLogin;
	int status;
	int noAttemps;

	public User(String name, String nric, String doB, String email, String logID, String mobile, String password) {
		this.name = name;
		this.nric = nric;
		this.doB = doB;
		this.email = email;
		this.logID = logID;
		this.mobile = mobile;
		this.password = password;
		this.role = "User";
		this.secQ = null;
		this.secAns = null;
		this.firstLogin = 0;
		this.status = 0;
		this.noAttemps = 0;
	}// end of User constructor1

	public User() {

	}// end of UUser constructor2

	/* getter & setter methods for all fields */
	public int getSerialno() {
		return serialno;
	}

	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public String getDoB() {
		return doB;
	}

	public void setDoB(String doB) {
		this.doB = doB;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSecQ() {
		return secQ;
	}

	public void setSecQ(String secQ) {
		this.secQ = secQ;
	}

	public String getSecAns() {
		return secAns;
	}

	public void setSecAns(String secAns) {
		this.secAns = secAns;
	}

	public int getfirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNoAttemps() {
		return noAttemps;
	}

	public void setNoAttemps(int noAttemps) {
		this.noAttemps = noAttemps;
	}

}// end of User
