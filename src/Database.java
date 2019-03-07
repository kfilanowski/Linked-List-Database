import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The driver of the program, the controller of the linked list database.
 * This is where tables are initialized, operated on, and printed.
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public class Database {
	
	/** The number of fields that are needed in one table record **/
	private final static int FIELD_NUMBER = 7;
	
	/**
	 * Checks for empty record fields. The method will throw an exception
	 * if there is not enough information to fill a record in the table. 
	 * It does this by counting every element in the file, and then knowing
	 * that there should be 'FIELD_NUMBER' elements per record in a table,
	 * performs a modulus operator to determine if there exists at least
	 * one element that is missing.
	 * @param filename The file's name which will be scanned.
	 * @throws NoSuchFieldException This exception is thrown if there are
	 * not enough elements in the file to fill any record.
	 */
	private static void checkForMissingElements(String filename)
			throws NoSuchFieldException, FileNotFoundException {
		
		// Create a scanner to iterate through the file.
		File readFile = new File(filename);
		Scanner in = new Scanner(readFile);
		// Keep count of the amount of fields that the file has.
		int counter = 0;
		
		// Count all of the fields and increment the counter.
		while(in.hasNext()) {
			in.next();
			counter++;
		}
		in.close();
		
		// Since a table requires a certain number of fields, if there are not
		// enough of those fields, then we throw an exception.
		if (counter % FIELD_NUMBER != 0) {
			throw new NoSuchFieldException("There are missing fields in"
					+ " the database file: " + filename);
		}	
	}
	
	/**
	 * This method is used to check if an element is an integer. If the
	 * element is in fact an integer, it will return the element. If it
	 * is not an integer, it will throw an exception.
	 * @param element The string element to check.
	 * @return The same element passed in if the element is a number,
	 * throws an exception otherwise.
	 * @throws NumberFormatException This exception is thrown if the
	 * element is not an integer.
	 */
	private static String checkInteger(String element) throws NumberFormatException {
		// Attempt to parse the element to an integer.
		// The exception will be thrown here if this does not work.
		Long.parseLong(element);
		
		// Test passed, return the element.
		return element;
	}
	
	/**
	 * Similar to checkInteger, this method will check an element to ensure
	 * that it contains only letters, and no numerical digits. 
	 * This method will throw an exception if it finds any numerical digits
	 * in the String 'element', and return the element back if it does not.
	 * @param element The string element to check for digits.
	 * @return The same element passed in if the element does not contain any
	 * digits, throws an exception otherwise.
	 */
	private static String checkString(String element) {
		if (element.matches(".*[\\d+|\\W].*")) {
			throw new IllegalArgumentException("Field argument: '" + element +
					"' should not contain numerical digits."
					+ "\nor non-letter characters.");
		}
		return element;
	}
	
	/**
	 * The visual and logic part of the menu. The menu has several options
	 * that the user can choose from:
	 * 0) Quit       		- Quits the program
     * 1) Intersect  		- Finds the intersection between two tables
     * 2) Difference 		- Finds the difference between two tables
     * 3) Union		 		- Finds the union between two tables
     * 4) Select     		- Select a specified attribute and value in a table
	 * 5) Remove     		- Removes a row from a table
     * 6) Print both tables - Prints both tables
	 * @param faculty The first table, which consists of faculty members.
	 * @param admin The second table, which consists of administrative members.
	 */
	private static void menu(Table<Employee> faculty, Table<Employee> admin) {
		// Initializing a scanner for user input.
		Scanner in = new Scanner(System.in);
		// Option denotes a menu option, this variable saves user selection.
		String option = "";

		System.out.println("Welcome to Database Deluxe 5000\n");

		// Loop the menu until the user wants to exit.
		while (!option.equals("0")) {
			System.out.print("\nPlease make choice:" +
					"\n\t0) Quit"       	   +
					"\n\t1) Intersect"  	   +
					"\n\t2) Difference" 	   +
					"\n\t3) Union"      	   +
					"\n\t4) Select"     	   +
					"\n\t5) Remove" 		   +
					"\n\t6) Print both tables" +
					"\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
			option = in.next();

			// Handle the menu option chosen by the user.
			switch(option) {
			case "0": { 
				System.out.println("\nGoodbye!");
				in.close();
				System.exit(0);
			} break;
			case "1": { // 1) Intersect
				// Variables declared to save user responses.
				String attribute, value;
				System.out.print("Enter attribute >");
				attribute = in.next();
				System.out.print("Enter value >");
				value = in.next();
				
				printTable(faculty.intersect(attribute, value, admin));
			} break;
			case "2": { // 2) Difference
				// Variable declared to save user response.
				String table;
				System.out.print("Enter table (F/A) >");
				table = in.next();
				switch (table) {
				case "F":
				case "f": printTable(faculty.difference(admin));
				break;
				case "A":
				case "a": printTable(admin.difference(faculty));
				break;
				default: System.out.println("Invalid input. "
						+ "Must choose table 'F' or 'A'.");
				}
			} break;
			case "3": { // 3) Union
				printTable(faculty.union(admin));
			} break;
			case "4": { // 4) Select
				// Variables declared to save user responses.
				String table, attribute, value;
				System.out.print("Enter table (F/A) >");
				table = in.next();
				System.out.print("Enter attribute >");
				attribute = in.next();
				System.out.print("Enter value >");
				value = in.next();
				
				switch (table) {
				case "F":
				case "f": printTable(faculty.select(attribute, value));
				break;
				case "A":
				case "a": printTable(admin.select(attribute, value));
				break;
				default: System.out.println("Invalid input. "
						+ "Must choose table 'F' or 'A'.");
				}
			} break;
			case "5": { // 5) Remove
				// Variables declared to save user responses.
				String table, value;
				System.out.print("Enter table (F/A) >");
				table = in.next();
				System.out.print("Enter value >");
				value = in.next();
				
				switch (table) {
				case "F":
				case "f": faculty.remove(value);
				break;
				case "A":
				case "a": admin.remove(value);
				break;
				default: System.out.println("Invalid input. "
						+ "Must choose table 'F' or 'A'.");
				}
			} break;
			case "6": { // 6) Print Both Tables
				printTable(faculty);
				printTable(admin);
			} break;
			default: { // Anything other than 0-6
				// Default is reached if the user entered an option outside
				// the range of choices.
				System.out.println("Option " + option + " does not exist.");
			}
			}
		}
	}

	/**
	 * This method populates a table with information from a text file.
	 * @param table The table to fill in with records.
	 * @param filename The name of the file to read information from.
	 * @throws FileNotFoundException This exception is thrown if the name of
	 * 			file is incorrect or does not exist.
	 */
	private static void populateTable(Table<Employee> table, String filename)
			throws FileNotFoundException {

		// Initialize the file and scanner to read the file.
		File readFile = new File(filename);
		Scanner in = new Scanner(readFile);

		// Keep grabbing values as long as they exist.
		while (in.hasNext()) {
			
			// Create a new person object.
			Person person = new Person(checkString(in.next()),
					checkString(in.next()), new Status(in.next()));
			
			// Create a new employee object.
			Employee employee = new Employee(checkInteger(in.next()),
					checkInteger(in.next()), checkInteger(in.next()),
					checkInteger(in.next()), person, table.getTitle());
			
			// Add the employee to the table.
			table.insert(employee);
		}
		in.close();
	}
	
	/**
	 * Simple function that calls the toString method of the table and prints
	 * that out. The purpose was to increase readability and reduce keystrokes.
	 * @param table The table to be printed.
	 */
	private static void printTable(Table<Employee> table) {
		System.out.println(table);
	}

	/**
	 * The main driver. Declares and initializes two tables, and then 
	 * populates them with information from two separate text files. Calls
	 * upon the menu for input commands.
	 * @param args Any command line arguments. Not necessary in this program.
	 */
	public static void main(String[] args) {
		
		// Declaring and Initializing two tables, Faculty and Admin.
		Table<Employee> faculty = new Table<Employee>("Faculty");
		Table<Employee> admin = new Table<Employee>("Admin");

		// Check to ensure that the database has no missing elements.
		// Populate the tables with information inside the file.
		// Try-Catch handles the exception if the file does not exist.
		try {
			checkForMissingElements("faculty.txt");
			checkForMissingElements("admin.txt");
			populateTable(faculty, "faculty.txt");
			populateTable(admin, "admin.txt");
		} catch (FileNotFoundException ex) {
			System.out.println("Cannot read file " + ex.getMessage()
			+ ".\nProgram exiting.");
			System.exit(1);
		} catch (NumberFormatException ex) {
			System.out.println("Invalid field, expected an integer,"
								+ "\nor the integer is too long.\n");
			System.out.println(ex.getMessage() + "\nProgram Exiting.");
			System.exit(1);
		} catch (NoSuchFieldException ex) {
			System.out.println(ex.getMessage() + "\nProgram Exiting.");
			System.exit(1);
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage() + "\nProgram Exiting.");
			System.exit(1);
		} catch (NullPointerException ex) {
			System.out.println("Filename null. \nProgram Exiting.");
			System.exit(1);
		}
		
		menu(faculty, admin);
	}
}
