package com.optimum.dao;

import java.sql.SQLException;

import com.optimum.pojo.User;

public interface LoginDAO {

	boolean loginAuthentication(String login,String pass) throws SQLException;
	void addUserDAO(User ref) throws Exception;
	String getUserName(String id) throws Exception;
	boolean checkForAdmin(String login) throws Exception;
	void showListDAO() throws Exception;
	boolean userAuthentication(String loginID) throws Exception;
	boolean checkStatus(String loginID) throws Exception;
	void lockUser(String loginID) throws SQLException;
	void setSecQDAO(String q, String loginID) throws Exception;
	void setSecADAO(String a, String loginID) throws Exception;
	boolean checkTempPassDAO(String loginID, String tempPass) throws Exception;
	boolean checkFirstDAO(String loginID) throws Exception;
	void changePassDAO(String loginID, String pass) throws Exception;
	String getUseQuestionDAO(String loginID) throws Exception;
	String getUseAnswerDAO(String loginID) throws Exception;
	String getPassDAO(String loginID) throws SQLException; 

}//end of interface LoginDAO
