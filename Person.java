package addressBook;

/*
* Allows a user to add, delete, modify, print to console, print to file Person objects
* inside of an address book
* @author Ryan Dawson - 9/30/20
* @class CSI 213
*/

import java.util.ArrayList;

//Creates a class which implements Comparable class for binary search
public class Person implements Comparable<Person> {
	//Initializes variables
	public String firstName, lastName, streetAdd, state, teleNum;
	public int zip;

//Person constructor will add arguments
	public Person(String firstName, String lastName, String streetAdd, String state, int zip, String teleNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAdd = streetAdd;
		this.state = state;
		this.zip = zip;
		this.teleNum = teleNum;
	}
//Blank Person constructor
	public Person() {

	}
//Getters and Setters for variables
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAdd() {
		return streetAdd;
	}

	public void setStreetAdd(String streetAdd) {
		this.streetAdd = streetAdd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getTeleNum() {
		return teleNum;
	}

	public void setTeleNum(String teleNum) {
		this.teleNum = teleNum;
	}
//To string method used to output all data to file formated
	public String toString() {
		return firstName + " " + lastName + "\n" + streetAdd + "\n" + state + ", " + zip + "\n" + teleNum;

	}
//Display method is called when entire person needs to be printed
	public void display() {
		System.out.println(firstName + " " + lastName + "\n" + streetAdd + "\n" + state + ", " + zip + "\n" + teleNum);

	}

//compareTo method is used in conjunction with binary to search for element that is input	
	@Override
	public int compareTo(Person z) {
		if (this.teleNum.compareTo(z.getTeleNum()) > 0) {
			return 1;
		} else if (this.teleNum.compareTo(z.getTeleNum()) < 0) {
			return -1;
		} else
			return 0;
	}
//Binary search find the mid point of the list, and searches up and down until item is found
	//Recurison is used to call itself as many times a needed until object is found
	public static int binarySearch(ArrayList<Person> addressBook, int low, int high, String teleNum) {
		// Finds middle and searches in both directions
		if (high >= low) {
			int mid = low + (high - low) / 2;
			if (addressBook.get(mid).getTeleNum().equals(teleNum))
				return mid;
			if (addressBook.get(mid).getTeleNum().compareToIgnoreCase(teleNum) > 0) {
				return binarySearch(addressBook, 0, mid - 1, teleNum);
			}
			return binarySearch(addressBook, mid + 1, high, teleNum);
		}
		return -1;
	}
//Method to require formatting of a telephone number
	public static Boolean teleFormat(String teleNum) {
		Boolean check = null;
		//Checks if length is 10 (# without '-')
		if (teleNum.length() == 10) {
			System.out.println("Formating incorrect, use - to seperate numbers");
			//Checks to make sure length is not less than or greater than 12
		} else if (teleNum.length() < 12 || teleNum.length() > 12) {
			System.out.println("Phone number incorrect length, input again!");
			check = false;
			return check;
			//If it is the right length AND has '-' at correct points, number is accepted and program moves forwards
		} else if ((teleNum.charAt(3) == '-' && teleNum.charAt(7) == '-')) {
			check = true;
			return check;
		}else
		System.out.println("Formatting and length incorrect, use - to seperate numbers");
		check = false;
		return check;
	}
//Checks to make sure length of zip is correct	
	public static Boolean checkZip(int zip) {
		Boolean zipCheck = null;
		//If statement to check if the length of an integer is 5 characters
		if(String.valueOf(zip).length() == 5) {
			zipCheck = true;
			return zipCheck;
		}else
			System.out.println("Zip code length incorrect, must be 5 digits!");
		zipCheck = false;
		return zipCheck;
		
	}

}
