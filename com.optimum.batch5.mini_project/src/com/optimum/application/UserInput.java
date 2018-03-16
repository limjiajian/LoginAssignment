package com.optimum.application;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.optimum.controller.LoginController;

public class UserInput {

	LoginController lc = new LoginController();
	static Scanner in = new Scanner(System.in);
	String name;

	static int optionInput() {// check if user key in valid option i.e a digit
		Scanner integerInput = new Scanner(System.in);
		try {
			int input = integerInput.nextInt();
			return input;
		} catch (InputMismatchException ime) {
			System.out.println("Please key in a valid option... ");
			return -1;
		}
	}// end of optionInput

	void firstPageOption() throws Exception {
		System.out.println("----------Select Option----------");
		System.out.println("1. Login");
		System.out.println("2. Forget Password");
		selectOptionFirstPage();
	}// end of firstPageOption

	public void selectOptionFirstPage() throws Exception { // check user's choice from first page
		int choice = optionInput();

		switch (choice) {
		case 1:
			loginPage();
			break;

		case 2:
			forgetPassPage();
			break;

		default:
			if (checkContinue() == true) {
				System.out.println("Select Option: ");
				selectOptionFirstPage();
				break;
			}
		}
	}// end of selectOptionFirstPage

	void loginPage() throws Exception {
		System.out.println("----------LogIn Page----------");
		System.out.println("LoginID (Your registered email): ");
		String loginID = in.nextLine();
		System.out.println("Password: ");
		String pass = in.nextLine();
		if (lc.loginLogic(loginID, pass) && lc.checkstatuss(loginID)==true) {

			System.out.println("Logging in...");
			if(lc.checkAdmin(loginID)) {
				adminLoginPage(loginID);


			}else if(lc.checkAdmin(loginID)==false) {
				this.name = lc.getUserName(loginID);

				if(lc.checkFirst(loginID)) {
					userFirstLoginPage(loginID);
				}else if(lc.checkFirst(loginID)==false){
					userLoginPage();
				}
			}
		}else{
			if (checkContinue() == true) {
				loginPage();
			}
		}
	}// end of loginPage

	void adminLoginPage(String loginID) throws Exception {
		System.out.println("----------Welcome Admin----------");
		System.out.println("----------Select Option----------");
		System.out.println("1. Register new User");
		System.out.println("2. View user list");
		System.out.println("3. Logout");
		selectOptionAdminPage(loginID);
	}// end of adminLoginPage

	public void selectOptionAdminPage(String loginID) throws Exception {
		int choice = optionInput();

		switch (choice) {
		case 1:
			registerUserPage(loginID);
			break;

		case 2:
			viewListPage(loginID);
			break;

		case 3:
			System.out.println("Logging out.....");
			firstPageOption();
			break;

		default:
			if (checkContinue() == true) {
				System.out.println("Select Option: ");
				selectOptionAdminPage(loginID);
				break;
			}
		}
	}// end of selectOptionAdminPage

	void registerUserPage(String loginID) throws Exception {
		System.out.println("----------Register New User----------");
		lc.addUser();
		boolean back = checkBack();
		if (back == true) {
			adminLoginPage(loginID);
		} else if (back == false) {
			if (checkContinue() == true) {
				registerUserPage(loginID);
			}
		}
	}// end of registerUserPage

	void viewListPage(String loginID) throws Exception {
		lc.showList();
		System.out.println("1. Edit Status");
		System.out.println("2. Back");
		selectOptionListPage(loginID);
	}// end of viewList

	public void selectOptionListPage(String loginID) throws Exception {
		int choice = optionInput();
		switch (choice) {
		case 1:
			editStatusPage(loginID);
			break;

		case 2:
			adminLoginPage(loginID);
			break;

		default:
			if (checkContinue() == true) {
				System.out.println("Select Option: ");
				selectOptionListPage(loginID);
				break;
			}
		}
	}// end of selectOptionSecurityPage

	void editStatusPage(String loginID) throws Exception {
		System.out.println("----------Edit User Status----------");
		System.out.println("Please key in NRIC of user to LOCK/UNLOCK: ");
		String nric = in.nextLine();
		lc.changeStatus(nric);
		viewListPage(loginID);
	}// end of editStatusPage

	void userFirstLoginPage(String loginID) throws Exception {
		System.out.println("----------Welcome " + name + "----------");
		System.out.println("Please key in your current password: ");
		String tempPass = in.nextLine();
		if (lc.loginLogic(loginID, tempPass)) {
			changePass(tempPass,loginID);
			} else {
				System.out.println("Authetication Error or invalid password...");
				userFirstLoginPage(loginID);
			}		
	}// end of userFirstLoginPage
	
	void changePass(String tempPass,String loginID)throws Exception{
		System.out.println("----------You have to change your password----------");
		System.out.println("Please key in your new password: ");
		String pass = in.nextLine();
		System.out.println("Please retype your new password: ");
		String checkPass = in.nextLine();
		if (pass.equals(checkPass) && pass.matches("[a-zA-Z0-9]{6,}") && (!tempPass.equals(pass))) {
			lc.changePass(loginID, pass);
			System.out.println("Password confirmed and changed..");
			securityQPage(loginID);
		}else {
			System.out.println("Authetication Error or invalid password...");
			changePass(tempPass,loginID);
		}
	}//end of changePass

	public void userLoginPage() throws Exception {
		System.out.println("----------Welcome " + name + "----------");
		System.out.println("Only Logout");
		//
		firstPageOption();
	}// end of userLoginPage

	void forgetPassPage() throws Exception {
		System.out.println("----------Forgotten Password----------");
		System.out.println("LoginID (Your registered email): ");
		String loginID = in.nextLine();
		if (lc.checkUserLogic(loginID) == false) {
			System.out.println("User does not exist or has been locked");
			forgetPassPage();
		} else {
			String secQ = lc.getUserQuestion(loginID);

			System.out.println("Security Question: " + secQ);
			System.out.println("Security Answer	: ");
			String secA = lc.getUserAnswer(loginID);
			String answer = in.nextLine();
			if (secA.equals(answer)) {
				changePass(lc.getPass(loginID), loginID);
			} else {
				System.out.println("Authetication Error...");
				forgetPassPage();
			}
		}
	}// end of forgetPassPage

	void securityQPage(String loginID) throws Exception {
		System.out.println("----------Set Security Question----------");
		System.out.println("Please select your security question: ");
		System.out.println("1.What is your favourite colour?");
		System.out.println("2.What is your favourite animal?");
		System.out.println("3.What is your favourite month?");
		selectOptionSecurityPage(loginID);
	}// end of securityQPage

	public void selectOptionSecurityPage(String loginID) throws Exception {
		int choice = optionInput();
		String question;
		switch (choice) {
		case 1:
			question = "What is your favourite colour?";
			lc.setSecQ(question,loginID);
			securityAPage(loginID);
			break;

		case 2:
			question = "What is your favourite animal?";
			lc.setSecQ(question,loginID);
			securityAPage(loginID);
			break;

		case 3:
			question = "What is your favourite month?";
			lc.setSecQ(question,loginID);
			securityAPage(loginID);
			break;

		default:
			if (checkContinue() == true) {
				System.out.println("Select Option: ");
				selectOptionSecurityPage(loginID);
				break;
			}
		}
	}// end of selectOptionSecurityPage

	void securityAPage(String loginID) throws Exception {
		System.out.println("----------Set Security Answer----------");
		System.out.println("Please key in your security answer(1 word only): ");
		String secA = in.nextLine();
		if (secA.matches("(\\w+)")) {
			lc.setSecA(secA, loginID);
			firstPageOption();
		} else {
			System.out.println("Invalid security answer...");
			securityAPage(loginID);
		}
	}// end of securityAPage

	boolean checkContinue() { // check whether the user want to continue using application
		System.out.println("Do you wish to Continue: (Y/N)?");
		String cont = in.nextLine();
		cont = cont.toUpperCase();
		if (cont.equals("Y")) {
			return true;
		} else {
			System.out.println("Thanks for using the application! Have a nice day!");
			return false;
		}
	}// end of checkContinue method

	boolean checkBack() { // check whether the user want to continue using application
		System.out.println("Do you wish to go back: (Y/N)?");
		String cont = in.nextLine();
		cont = cont.toUpperCase();
		if (cont.equals("Y")) {
			return true;
		} else {
			return false;
		}
	}// end of checkContinue method

}
