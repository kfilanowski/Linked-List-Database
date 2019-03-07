/**
 * An Employee represents a row in a table. This will contain certain
 * attributes pertaining to employees.
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public class Employee implements AttributeInterface {

	/** Identification number */
    private String id;
    /** Phone number */
    private String phone;
    /** Division within the institution */
    private String division;
    /** Number of years employed */
    private String years;
    /** Personal information */
    private Person person;
    /** Current department or classification */
    private String department;

    /**
     * Constructor initializing the fields of the employee.
     * @param id The identification number of the employee.
     * @param phone The phone number of the employee.
     * @param division The division number of the employee.
     * @param years How many years the employee has worked at the company.
     * @param person The personal information of the employee,
     * this includes their first and last name, and their marital status.
     * @param department The department that the employee works at.
     */
    public Employee(String id, String phone, String division, String years,
    		Person person, String department) {
    	
    	this.id         = id;
    	this.phone 		= phone;
    	this.division   = division;
    	this.years 		= years;
    	this.person 	= person;
    	this.department = department;
    }
    
    /**
     * Getter method to retrieve the identification number of the employee.
     * @return The identification number of the employee.
     */
    String getId() {
		return id;
	}

	/**
	 * Getter method to retrieve the employee's phone number.
	 * @return The employee's phone number.
	 */
    String getPhone() {
		return phone;
	}

	/**
	 * Getter method to retrieve the employee's division number.
	 * @return The division the employee works at.
	 */
    String getDivision() {
		return division;
	}

	/**
	 * Getter method to retrieve the number of years the employee has worked
	 * at the company.
	 * @return The number of years the employee worked at the company.
	 */
    String getYears() {
		return years;
	}

	/**
	 * Getter method to retrieve personal information of the employee.
	 * @return A person object containing the employee's personal information.
	 * This contains the first and last name, as well as their marital status.
	 */
	Person getPerson() {
		return person;
	}

	/**
	 * Getter method to retrieve the department the employee is assigned to.
	 * @return The department the employee is assigned to.
	 */
	String getDepartment() {
		return department;
	}

	/**
	 * Check to see if a record has an attribute containing a specific value.
	 * @param attribute The attribute to check that value for.
	 * @param value The value to check with the attribute.
	 * @return True if there exists an attribute with the value specified,
	 * 		  false otherwise.
	 */
	public boolean check(String attribute, String value) {
		
		// Check the appropriate attribute and run equals command.
		switch (attribute) {
		case "ID":
		case "id":		   return id.equals(value);
		case "phone":	   return phone.equals(value);
		case "division":   return division.equals(value);
		case "years":	   return years.equals(value);
		case "department": return department.equalsIgnoreCase(value);
		case "first":	   return person.getFirst().equalsIgnoreCase(value);
		case "last":	   return person.getLast().equalsIgnoreCase(value);
		case "status":	   return person.getStatus().toString().equalsIgnoreCase(value);
		default: 		   return false;
		}
	}

	/**
	 * Change the value of a specified attribute.
	 * @param attribute The attribute to change.
	 * @param value The new value to assign to the specified attribute.
	 * @return True if a change was made, false otherwise.
	 */
	public boolean change(String attribute, String value) {
		switch (attribute) {
		
		// Check the appropriate attribute and set the value.
		case "ID":
		case "id":		   id 		  = value; return true;
		case "phone":	   phone      = value; return true;
		case "division":   division   = value; return true;
		case "years":	   years      = value; return true;
		case "department": department = value; return true;
		case "first":	   person.setFirst(value); return true;
		case "last":	   person.setLast(value);  return true;
		case "status":	   person.setStatus(new Status(value)); return true;
		default: 		   return false;
		}
	}
	
	/**
	 * Equals method compares this employee object with another to determine
	 * if they are equal. They are considered equal if all of the fields are
	 * equivalent, except the department field. This is because the same 
	 * person can be in multiple departments at one time.
	 * @param obj The object to compare this employee with.
	 * @return True if the two objects contain the same fields.
	 */
	@Override
	public boolean equals(Object obj) {
		// Check to see if obj is an Employee obj, then cast and compare it.
		if (obj instanceof Employee) {
			// Casting Object obj to Employee for comparison.
			Employee o = (Employee)obj;
			
			// If every field matches up, then they are equal.
			if (id.equals(o.id) && phone.equals(o.phone) && 
					division.equals(o.division) && years.equals(o.years) && 
					person.equals(o.person)) {
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Makes a deep copy of the employee object without using clone()
	 * or a copy constructor.
	 * @return A deep copy of the current employee object.
	 */
	public AttributeInterface makeCopy() {
		AttributeInterface copy = new Employee(id, phone, division, years,
							new Person(person.getFirst(), person.getLast(), 
							new Status(person.getStatus().getMaritalStatus())),
							department);
		
		return copy;
	}
	
	/**
	 * Returns all of the employees information in a formatted string.
	 * @return A string containing all of the employee's information.
	 */
	@Override
	public String toString() {
		return "Employee(" + id + "): " + person +"\n\tRecord: " 
				+ years + " years in division " + "[" + division
				+ "] -- Dept: " + department;
	}

}
