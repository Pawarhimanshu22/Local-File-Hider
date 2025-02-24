package org.himanshu.view;

import org.himanshu.dao.UserDAO;
import org.himanshu.model.User;
import org.himanshu.service.GenerateOTP;
import org.himanshu.service.SendOTPService;
import org.himanshu.service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Welcome {

    public void wellcomeScreen()
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        System.out.println("---------- WellCome to the Application -----------");
        System.out.println("Please select an option:");
        System.out.println("Press 1 for LogIn");
        System.out.println("Press 2 For SignUp");
        System.out.println("Press 0 to Exit");
        System.out.print("Enter your choice: ");

        try {
            choice = scanner.nextInt();

        } catch (InputMismatchException ex) {
            System.out.println("Please enter a valid number.");
            ex.printStackTrace();
        }

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> exit();
            default -> {
                System.out.println("Invalid option. Please try again.");

                scanner.close(); // Close the scanner when done
            }
        }

    }
    private void login() {

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Email");
            String email = sc.nextLine();

            try {
                if (UserDAO.isUserExist(email)) {
                    String genOTP = GenerateOTP.getOTP();
                    SendOTPService.sendOTP(email, genOTP);
                    System.out.println("Enter The OTP");
                    String otp = sc.nextLine();

                    if (otp.equals(genOTP)) {
                        UserView userView = new UserView(email);
                        userView.home();
                        //System.out.println("WELCOME");
                        // Add your logged-in user functionality here
                    } else {
                        System.out.println("WRONG OTP");
                        wellcomeScreen(); // Return to main menu
                    }
                } else {
                    System.out.println("User Not Found");
                    wellcomeScreen(); // Return to main menu
                }
            } catch (Exception e) {
                System.out.println("Error during login: " + e.getMessage());
                e.printStackTrace();
                wellcomeScreen(); // Return to main menu
            }
        }

    private void signUp() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = sc.nextLine();

        System.out.println("Enter Email");
        String eMail = sc.nextLine();

        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(eMail, genOTP);
        System.out.println("Enter The OTP");
        String otp = sc.nextLine();
        if (otp.equals(genOTP))
        {
            User user = new User(name, eMail);
            int response = UserService.saveUser(user );
            switch (response)
            {
                case 0 -> System.out.println("User Register");
                case 1 -> System.out.println("User Already Exists ");
            }

        }
        else
        {
            System.out.println("WRONG OTP");
        }




    }

    private static void exit() {
        System.out.println("Exiting program. Goodbye!");
        // Exit code here
    }
}
