package addressBook;

/*
* Allows a user to add, delete, modify, print to console, print to file Person objects
* inside of an address book
* @author Ryan Dawson - 9/30/20
* @class CSI 213
*/

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main extends Person {
	//Initializes Variables, array and Scanner
	static int choice = 0;
	static String fileName = "";
	static String firstName, lastName, streetAdd, state, teleNum;
	static int zip;
	static public Scanner input = new Scanner(System.in);
	public static ArrayList<Person> addressBook = new ArrayList<Person>();

	//Choices for the switch statements, easier on the eyes for programmers
	static final int addPerson = 1;
	static final int deletePerson = 2;
	static final int modifyPerson = 3;
	static final int printOne = 4;
	static final int printAll = 5;
	static final int writeToFile = 6;
	static final int exitMenu = 7;

//Menu method manages the choices and a majority of the program
//Called by main when program starts
	public static void menu() throws PersonNotFound, FileNotFoundException {
		//Try block to catch exceptions
		try {
			//While loop is used to make sure a choice that is assigned a value is chosen
			//Repeats sequence after each case statement runs
			while (choice != 7) {
				System.out.println("\nEnter 1 add a person");
				System.out.println("Enter 2 delete a person.");
				System.out.println("Enter 3 to modify a person.");
				System.out.println("Enter 4 to print one person.");
				System.out.println("Enter 5 print all people.");
				System.out.println("Enter 6 to write all elements to a file.");
				System.out.println("Enter 7 to quit.\n");
				System.out.println("Enter a choice: ");
				choice = input.nextInt();
				input.nextLine();

				switch (choice) {
				case addPerson:
					//Takes several inputs for adding a person
					int loop = 0;
					int loopZ = 0;
					System.out.println("Enter the FIRST name of the person: ");
					firstName = input.nextLine();
					System.out.println("Enter the LAST name of the person: ");
					lastName = input.nextLine();
					System.out.println("Enter the street address (Ex: 4 Lolly Ln): ");
					streetAdd = input.nextLine();
					System.out.println("Enter the state (Ex: NY); ");
					state = input.nextLine();		
					//Error checking: uses custom checkZip method to make sure ZIP is 5 digits long
					while(loopZ == 0) {						
					System.out.println("Enter the 5 digit ZIP code: ");
					zip = input.nextInt();
					input.nextLine();
					Boolean zipCheck = checkZip(zip);
					//Breaks loop if return is true
					if (zipCheck == true) {
						loopZ ++;
					}
					}
					//Error checking: uses custom teleFormat method to make sure telephone number
					//is formated with '-' and the correct length
					while(loop == 0) {
					System.out.println("Enter the phone number (Format: xxx-xxx-xxxx): ");
					teleNum = input.nextLine();
					Boolean check = teleFormat(teleNum);					
					if (check == true) {
						loop ++;
					}
					}
					//constructs new person object with the several inputs taken from the user
					Person p = new Person(firstName, lastName, streetAdd, state, zip, teleNum);
					//Custom display method outputs all information to the console
					p.display();
					addressBook.add(p);
					break;
				case deletePerson:
					//Case statement for deleting a person
					System.out.println("Enter the FIRST NAME of the person to be deleted: ");
					String del = input.nextLine();
					Boolean verifyD = false;
					//Uses for loop to sort through list
					for (int i = 0; i < addressBook.size(); i++) {
						Person j = addressBook.get(i);
						//If statement makes sure that the first name input if equal to the first name in the Person object
						//If true, removes person from array
						if (del.equalsIgnoreCase(j.firstName)) {
							System.out.println("removed!");
							addressBook.remove(i);
							verifyD = true;
							//If statement for error checking. makes sure person EXISTS
						} else if(verifyD == false){
							System.out.println("Deleting...");
							if(i == addressBook.size() - 1 ) {
								System.out.println("Person not found!");
								}
						}
					}
					break;
				case modifyPerson:
					//Modifies an element in the person object, chosen by the user
					//Sorts the list by increasing telephone number
					Collections.sort(addressBook);
					int loopL = 0;
					String tNum = "";
					//While loop to make sure that the search telephone number follows same format
					while (loopL == 0){
					System.out.println("Enter the phone number of the person you would like to modify: ");			
					tNum = input.nextLine();
					Boolean check = teleFormat(tNum);
					if (check == true) {
						loopL++;
					}
					}
					//Uses binary search and recursion to search list by getting the mid point
					//and searching up and down until the object is found
					int result = binarySearch(addressBook, 0, (addressBook.size() - 1), tNum);
					Person o = addressBook.get(result);
					System.out.println("Person selected is: ");
					o.display();
					//Prompts user to select and element to modify from a new list of options
					System.out.println("\nWhich element would you like to modify?");
					System.out.println(
							"First Name (fname) \n Last Name (lname) \n Street Address (streetadd)\n State (state) \n Zip (zip) \n Telephone Number (telenum)");
					String pick = input.nextLine();
					//Calls new method to manage all of those options and their execution
					options(o,pick);
					break;
				case printOne:
					//Prints one person to the console
					int loopP = 0;
					//While loop makes sure person exists, if they don't asks for reinput
					while(loopP == 0) {
					System.out.println("Enter the FIRST NAME of the person to print");
					String temp = input.nextLine();
					Boolean verify = false;
					for (int i = 0; i < addressBook.size(); i++) {
						Person j = addressBook.get(i);
						if (temp.equalsIgnoreCase(j.firstName)) {
							System.out.println("Person found!\n");
							loopP ++;
							verify = true;
							j.display();

						} else if(verify == false){
							System.out.println("Trying... " + (i + 1));
							if(i == addressBook.size() - 1 ) {
							System.out.println("Person not found!");
							}
						}
					}
					}
					break;
				case printAll:
					//Print all contents of the array to console
					System.out.println("Printing all contents...");
					for (int i = 0; i < addressBook.size(); i++) {
						Person a = addressBook.get(i);
						System.out.println("\nPerson " + (i + 1) + ": ");
						//Uses display method to output all elements
						a.display();
					}
					break;
				case writeToFile:
					//Creates a file to be written to
					//Uses try block to catch exceptions
					try {
					PrintWriter outputFile = new PrintWriter("addresses.txt");
					//Uses a for loop to print all the contents of the array list to the file with
					//the custom toString method
					for (int i = 0; i < addressBook.size(); i++) {
						Person w = addressBook.get(i);
						outputFile.println("\nPerson " + (i + 1) + ": \n" + w.toString());
					}
					System.out.println("Printed to file!");
					outputFile.close();
					//Catches FileNotFound exception
					}catch(FileNotFoundException e) {
						System.out.println("File not found!");
					}
					break;
				case exitMenu:
					break;
				}
			}
			//Catches input mismatch when picking a menu option
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input! Please try again");
		} finally {
			System.out.println("Terminated");
			

		}
		
		
	}

	public static void options(Person m, String pick) {
		//Options method to compute all modify cases
		Person o = m;
		switch (pick.toLowerCase()) {
		//Changes the first name of the Person
		case "fname":
			System.out.println("What new first name would you like?");
			System.out.println("Current: " + o.getFirstName());
			String firstName = input.nextLine();
			o.setFirstName(firstName);
			System.out.println("New person is: \n");
			o.display();
			break;
		//Changes the last name of the person
		case "lname":
			System.out.println("What new last name would you like?");
			System.out.println("Current: " + o.getLastName());
			String lastName = input.nextLine();
			o.setLastName(lastName);
			System.out.println("New person is: \n");
			o.display();
			break;
		//Changes the street address of the person
		case "streetadd":
			System.out.println("What new street address would you like?");
			System.out.println("Current: " + o.getStreetAdd());
			String addName = input.nextLine();
			o.setStreetAdd(addName);
			System.out.println("New person is: \n");
			o.display();
			break;
		//Changes the sate of the person
		case "state":
			System.out.println("What new state would you like?");
			System.out.println("Current: " + o.getState());
			String state = input.nextLine();
			o.setState(state);
			System.out.println("New person is: \n");
			o.display();
			break;
		case "zip":
		//Changes the zip code of the person while also using ERROR CHECKING method
			int loopZ= 0;
			while(loopZ == 0) {
			System.out.println("What new zip code would you like?");
			System.out.println("Current: " + o.getZip());
			int zip = input.nextInt();
			input.nextLine();
			Boolean zipCheck = checkZip(zip);
			
			if(zipCheck == true) {
				loopZ ++;
			o.setZip(zip);
			System.out.println("New person is: \n");
			o.display();
			}
			}
			break;
		//Changes telephone number while using ERROR CHECKING method
		case "telenum":
			int loop = 0;
			while(loop == 0) {
				System.out.println("What new telephone number would you like? (xxx-xxx-xxxx)");
				System.out.println("Current: " + o.getTeleNum());;
				String tele = input.nextLine();
				Boolean check = teleFormat(tele);
				
				if (check == true) {
					loop ++;
					o.setTeleNum(tele);
					System.out.println("New person is: \n");
					o.display();
				}

				}		

			break;
		}

	}
//Main method calls menu method to being program
	public static void main(String args[]) throws Exception {	
		menu();
		
	}

}
