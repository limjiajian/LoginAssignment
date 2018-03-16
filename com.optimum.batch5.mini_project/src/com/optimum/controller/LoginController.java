package com.optimum.controller;

import java.sql.SQLException;
import java.util.Scanner;

import com.optimum.dao.LoginDAOImpl;
import com.optimum.pojo.User;

public class LoginController {

	private LoginDAOImpl refLoginDAO = new LoginDAOImpl();
	private User refUser = new User();

	Scanner in = new Scanner(System.in);

	public boolean loginLogic(String loginID, String pass) throws Exception {//confirm if user's username and password is correct
		if (refLoginDAO.loginAuthentication(loginID, pass)) {
			getUserName(loginID);
			return true;

		} else {
			refLoginDAO.lockUser(loginID);
			System.out.println("Try again...");
			return false;
		}
	}// end of loginLogic

	public boolean checkUserLogic(String loginID) throws Exception {//check if user exist in database
		if (refLoginDAO.userAuthentication(loginID)) {
			return true;

		} else if(refLoginDAO.userAuthentication(loginID)==false||refLoginDAO.checkStatus(loginID)==false){
			System.out.println("Not authorised...");
			return false;
		}
		return false;
	}// end of checkUserLogic

	public boolean checkstatuss(String loginID) throws Exception {
		return refLoginDAO.checkStatus(loginID);
	}// end of checkstatuss

	public String getUserName(String loginID) throws Exception {
		String name = refLoginDAO.getUserName(loginID);
		return name;
	}// end of getUserName

	public boolean checkAdmin(String loginID) throws SQLException {
		if (refLoginDAO.checkForAdmin(loginID)) {
			return true;
		} else
			return false;
	}// end of checkAdmin

	public void addUser() throws Exception {
		Register refReg = new Register();
		User refUser = refReg.regNew();
		refReg.validate();
		refLoginDAO.addUserDAO(refUser);
	}// end of addUser

	public void showList() throws Exception {
		System.out.println(
				"================================================================VIEW LIST==============================================================================1");
		System.out.println("Name\t\tNRIC\t\tRole\tDate Of Birth\tStatus\tMobile\t\tLoginID(Email)");
		System.out.println(
				"=======================================================================================================================================================");
		refLoginDAO.showListDAO();
	}// end of showList

	public void changeStatus(String nric) throws Exception {
		refLoginDAO.changeStatusDAO(nric);
	}// end of changeStatus

	public boolean checkFirst(String loginID) throws Exception {
		boolean check=refLoginDAO.checkFirstDAO(loginID);
		return check;		
	}//end of checkFirst

	public void changePass(String loginID,String pass) throws Exception {
		refLoginDAO.changePassDAO(loginID,pass);
	}//end of changePass

	public void setSecQ(String q, String loginID) throws Exception {
		refLoginDAO.setSecQDAO(q,loginID);
	}//end of setSecQ

	public void setSecA(String a,String loginID) throws Exception {
		refLoginDAO.setSecADAO(a,loginID);
	}//end of setSecA

	public boolean securityLogic(String loginID, String pass) throws Exception {
		if (refLoginDAO.loginAuthentication(loginID, pass)) {
			getUserName(loginID);
			return true;
		} else if(refLoginDAO.loginAuthentication(loginID, pass)==false){
			System.out.println("Not authorised...");
			return false;
		}
		return false;
	}// end of securityLogic

	public String getUserQuestion(String loginID) throws Exception {
		String q = refLoginDAO.getUseQuestionDAO(loginID);
		return q;
	}// end of getUserName

	public String getUserAnswer(String loginID) throws Exception {
		String a = refLoginDAO.getUseAnswerDAO(loginID);
		return a;
	}//end of getUserAnswer
	
	public String getPass(String loginID) throws Exception {
		String p = refLoginDAO.getPassDAO(loginID);
		return p;
	}//end of getUserAnswer


}// end of LoginController
