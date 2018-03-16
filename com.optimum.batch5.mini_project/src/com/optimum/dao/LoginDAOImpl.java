package com.optimum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.optimum.connection.DatabaseConnection;
import com.optimum.pojo.User;

public class LoginDAOImpl implements LoginDAO {
	// private boolean status;

	// step 1: database connection
	DatabaseConnection refdbc = new DatabaseConnection();
	private Connection connect = DatabaseConnection.getInstance().getConnection();
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	@Override
	public boolean loginAuthentication(String loginID, String pass) throws SQLException {
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT email,password FROM users;";
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			String userID = resultSet.getString("email");
			String password = resultSet.getString("password");

			if (loginID.equals(userID) && pass.equals(password)) {
				String update = "UPDATE users set no_attempts='0' where email='" + loginID + "';";
				PreparedStatement updateStatement = connect.prepareStatement(update);
				updateStatement.executeUpdate();
				return true;

			} else if((loginID.equals(userID) && pass.equals(password)==false)){
				System.out.println("Wrong password"); 
				return false;
			}
		}
		return false;
	}// end of loginAuthentication	

	@Override
	public String getUserName(String loginID) throws SQLException {
		String userName;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT name FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			userName = resultSet.getString("name");
			return userName;
		} else {
			return userName = null;
		}
	}// end of getUserName
	
	@Override
	public String getPassDAO(String loginID) throws SQLException {
		String pass;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT password FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			pass = resultSet.getString("password");
			return pass;
		} else {
			return pass = null;
		}
	}// end of getPass

	@Override
	public void addUserDAO(User ref) throws SQLException {
		// step 2: // sql query
		try {
			statement = connect.createStatement();
			String query = "INSERT INTO users values(default,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, ref.getName());
			preparedStatement.setString(2, ref.getNric());
			preparedStatement.setString(3, ref.getEmail());
			preparedStatement.setString(4, ref.getDoB());
			preparedStatement.setString(5, ref.getMobile());
			preparedStatement.setString(6, ref.getPassword());
			preparedStatement.setString(7, ref.getRole());
			preparedStatement.setString(8, ref.getSecQ());
			preparedStatement.setString(9, ref.getSecAns());
			preparedStatement.setLong(10, ref.getfirstLogin());
			preparedStatement.setLong(11, ref.getStatus());
			preparedStatement.setLong(12, ref.getNoAttemps());
			preparedStatement.executeUpdate();
		}catch(SQLException e) {
			System.out.println("User is already in....");
		}
	}// end of addUser

	@Override
	public boolean userAuthentication(String loginID) throws Exception {//check if user exist in the databse
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT email FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			String userID = resultSet.getString("email");
			if (loginID.equals(userID)) {
			return true;			
			} else {	
				return false;
			}
		}
		return false;
	}// end of userAuthentication

	@Override
	public boolean checkStatus(String loginID) throws Exception {
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT status FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int status = resultSet.getInt("status");
			if (status==1){
				System.out.println("You are locked...Please inform Admin to unlock you...");
				return false;
			}else 
				return true;
		}
		return false;
	}//end of checkStatus

	@Override
	public boolean checkForAdmin(String loginID) throws SQLException {
		String userRole;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT role FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			userRole = resultSet.getString("role");
			if (userRole.equals("Admin")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}// end of checkForAdmin

	@Override
	public void lockUser(String loginID) throws SQLException {
		int attempts;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT no_attempts FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			attempts = resultSet.getInt("no_attempts");
			attempts = attempts + 1;
			System.out.println("No. of attempts: "+attempts);
			String update = "UPDATE users set no_attempts='" + attempts + "'where email='" + loginID + "';";
			PreparedStatement updateStatement = connect.prepareStatement(update);
			updateStatement.executeUpdate();

			if (attempts >= 3) {
				String update1 = "UPDATE users set status=1 where email='" + loginID + "';";
				PreparedStatement updateStatement1 = connect.prepareStatement(update1);
				String update2 = "UPDATE users set no_attempts=0 where email='" + loginID + "';";
				PreparedStatement updateStatement2 = connect.prepareStatement(update2);
				updateStatement1.executeUpdate();
				updateStatement2.executeUpdate();
			}
		}
	}//end of lockUser

	@Override
	public void showListDAO() throws SQLException {
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT name,nric,role,date_of_birth,status,mobile,email FROM users;";
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			String name = resultSet.getString("name");
			String nric = resultSet.getString("nric");
			String role = resultSet.getString("role");
			String date = resultSet.getString("date_of_birth");
			String dateArr[] = date.split("-");
			date = dateArr[2] + "/" + dateArr[1] + "/" + dateArr[0];
			String status = resultSet.getString("status");
			String mobile = resultSet.getString("mobile");
			String email = resultSet.getString("email");
			if (status.equals("1")) {
				status = "LOCK";
			} else if (status.equals("0")) {
				status = "UNLOCK";
			}
			System.out.println(
					name + "\t" + nric + "\t" + role + "\t" + date + "\t" + status + "\t" + mobile + "\t" + email);
		}
	}// end of showListDAO

	public void changeStatusDAO(String nric) throws Exception {
		String status;
		String nricData;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT status,nric FROM users where nric='" + nric + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			status = resultSet.getString("status");
			nricData = resultSet.getString("nric");
			if (nricData.equals(nric)) {
				if (status.equals("0")) {
					String update = "UPDATE users set status='1' where nric='" + nric + "';";
					PreparedStatement updateStatement = connect.prepareStatement(update);
					updateStatement.executeUpdate();
				} else if (status.equals("1")) {
					String update = "UPDATE users set status='0' where nric='" + nric + "';";
					PreparedStatement updateStatement = connect.prepareStatement(update);
					updateStatement.executeUpdate();
				}
			}
		} else {
			System.out.println("Please key in valid NRIC");
		}
	}// end of changeStatusDAO

	@Override
	public void setSecQDAO(String q, String loginID) throws Exception {
		// step 2: // sql query
		statement = connect.createStatement();
		String update = "UPDATE users set security_question='" + q + "' where email='" + loginID + "';";
		PreparedStatement updateStatement = connect.prepareStatement(update);
		updateStatement.executeUpdate();
	}// end of setSecQ

	@Override	
	public void setSecADAO(String a, String loginID) throws Exception {
		// step 2: // sql query
		statement = connect.createStatement();
		String update = "UPDATE users set security_answer='" + a + "' where email='" + loginID + "';";
		PreparedStatement updateStatement = connect.prepareStatement(update);
		updateStatement.executeUpdate();
	}

	@Override
	public boolean checkTempPassDAO(String loginID, String tempPass) throws Exception {
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT email,password FROM users;";
		return false;
	}// end of checkTempPassDAO

	@Override
	public boolean checkFirstDAO(String loginID) throws Exception {//check if user first time logging in
		String firstLogin;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT first_login FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			firstLogin = resultSet.getString("first_login");
			if (firstLogin.equals("0")) {				
				String update = "UPDATE users set first_login='1' where email='" + loginID + "';";

				PreparedStatement updateStatement = connect.prepareStatement(update);
				updateStatement.executeUpdate();
				return true;
			} else if (firstLogin.equals("1")) {
				return false;
			}
		}
		return false;
	}// end of checkFirstDAO

	@Override
	public void changePassDAO(String loginID, String pass) throws Exception {
		// step 2: // sql query
		statement = connect.createStatement();
		String update = "UPDATE users set password='" + pass + "' where email='" + loginID + "';";
		PreparedStatement updateStatement = connect.prepareStatement(update);
		updateStatement.executeUpdate();
	}// end of changePassDAO

	@Override
	public String getUseQuestionDAO(String loginID) throws Exception {
		String question;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT security_question FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			question = resultSet.getString("security_question");
			return question;
		} else {
			return question = null;
		}
	}// end of getUserQuestion

	@Override
	public String getUseAnswerDAO(String loginID) throws Exception {
		String answer;
		// step 2: // sql query
		statement = connect.createStatement();
		String query = "SELECT security_answer FROM users where email='" + loginID + "';";
		resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			answer = resultSet.getString("security_answer");
			return answer;
		} else {
			return answer = null;
		}
	}// end of getUseAnswerDAO

}// end of LoginDAOImpl
