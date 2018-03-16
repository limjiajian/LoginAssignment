package com.optimum.controller;

import java.util.Scanner;

import com.optimum.connection.SendEmail;
import com.optimum.pojo.User;

public class Register {

	String name;
	String nric;
	String doB;
	String email;
	String logID;
	String mobile;
	String tempPass;
	Scanner sc = new Scanner(System.in);

	/* get user's name */
	void setName() {
		System.out.println("Please enter new user's name: ");
		String name = sc.nextLine();
		if (name.matches("([a-zA-Z]+\\s)*[a-zA-Z]*")) {
			name = name.toUpperCase();
			this.name = name;
		} else {
			System.out.println("Invalid name format....");
			setName();
		}
	}// end of getName

	/* get user's nric */
	void setNRIC() {
		System.out.println("Please enter new user's NRIC: ");
		String nric = sc.nextLine();
		if (nric.matches("[a-zA-Z]\\d{7}[a-zA-Z]")) {
			String first = nric.substring(0, 1).toUpperCase();
			String last = nric.substring((nric.length() - 1), nric.length()).toUpperCase();
			String middle = nric.substring(1, nric.length() - 1);
			nric = first + middle + last;
			this.nric = nric;
		} else {
			System.out.println("Invalid NRIC format....");
			setNRIC();
		}
	}// end of getNRIC

	/* get user's date of birth */
	void setDoB() {
		System.out.println("Please enter new user's Date Of Birth in the following format(DD/MM/YYYY): ");
		String doB = sc.nextLine();
		if (doB.matches("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|"
				+ "((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])"
				+ "(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")) {
			String doBArr[] = doB.split("/");
			doB = doBArr[2] + "/" + doBArr[1] + "/" + doBArr[0];
			this.doB = doB;
		} else {
			System.out.println("Invalid Date Of Birth format....");
			setDoB();
		}
	}// end of getDoB

	/* get user's email */
	void setEmail() {
		System.out.println("Please enter new user's valid e-mail: ");
		String email = sc.nextLine();
		if (email.matches(
				"[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})")) {
			this.email = email;
			this.logID = email; // set logID to be new user's email
		} else {
			System.out.println("Invalid Email format....");
			setEmail();
		}
	}// end of getEmail

	/* get user's mobile */
	void setMobile() {
		System.out.println("Please enter new user's mobile number:");
		String mobile = sc.nextLine();
		if (mobile.matches("[0-9]{8}")) {
			this.mobile = mobile;
		} else {
			System.out.println("Invalid Mobile format....");
			setMobile();
		}
	}// end of getMobile

	void setTempPass() {
		String firstHalf = nric.substring(1, 5);
		String lastHalf = mobile.substring(4, 8);
		this.tempPass = firstHalf + lastHalf;
	}// end of setTempPass

	public User regNew() {
		setName();
		setNRIC();
		setDoB();
		setEmail();
		setMobile();
		setTempPass();
		User refUser = new User(name, nric, doB, email, logID, mobile, tempPass);
		new SendEmail().sendMail(refUser);
		return refUser;
	}// end of regNew

	void validate() {
		System.out.println("----------New User----------");
		System.out.println("Name:" + name);
		System.out.println("NRIC:" + nric);
		System.out.println("Date Of Birth:" + doB);
		System.out.println("Email:" + email);
		System.out.println("Mobile:" + mobile);
		System.out.println("LoginID:" + logID);
		System.out.println("Temp password:" + tempPass);
	}// end of validate

	public String getName() {
		return name;
	}// end of getName

	public String getNric() {
		return nric;
	}// end of getNric

	public String getDoB() {
		return doB;
	}// end of getDoB

	public String getEmail() {
		return email;
	}// end of getEmail

	public String getMobile() {
		return mobile;
	}// end of getMobile

	public String getTempPass() {
		return tempPass;
	}// end of getTemPass

	public String getLogID() {
		return logID;
	}// end of getLogID

	public void run() {
		regNew();
		validate();
	}

}// end of Register class
