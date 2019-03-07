/**
 * The person class is used to hold personal information about someone.
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public class Person {

	/** First name */
	private String first;
	/** Last name */
	private String last;
	/** Martial Status */
	private Status status;
	
	/**
	 * Constructor. Initializes a person object.
	 * @param first The first name of the person.
	 * @param last The last name of the person.
	 * @param status The marital status of the person.
	 */
	public Person(String first, String last, Status status) {
		this.first = first;
		this.last = last;
		this.status = status;
	}
	
	/**
	 * Getter method to retrieve the first name.
	 * @return The first name of the person.
	 */
	String getFirst() {
		return first;
	}
	
	/**
	 * Setter method to change the first name.
	 * @param first The new first name to be set to the person.
	 */
	void setFirst(String first) {
		this.first = first;
	}
	
	/**
	 * Getter method to retrieve the last name.
	 * @return The last name of the person.
	 */
	String getLast() {
		return last;
	}
	
	/**
	 * Setter method to change the last name.
	 * @param last The new last name to be set to the person.
	 */
	void setLast(String last) {
		this.last = last;
	}
	
	/**
	 * Getter method to return the marital status object of a person.
	 * This marital status object contains the marital status.
	 * @return The marital status object.
	 */
	Status getStatus() {
		return status;
	}
	
	/**
	 * Setter method to set the marital status object.
	 * @param status The new status object to set to the person.
	 */
	void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * Equals method compares two objects to check if they are the same.
	 * The two objects are considered equal if their fields contain
	 * the same values.
	 * @param obj The object to compare the person with.
	 * @return True if these two objects have equal fields. Otherwise,
	 * 			return false.
	 */
	@Override
	public boolean equals(Object obj) {
		// Check to see if obj is a Person obj, then cast and compare it.
		if (obj instanceof Person) {
			// Casting Object obj to Person to compare them.
			Person o = (Person)obj;
			
			// If every field matches, they are equal.
			if (first.equalsIgnoreCase(o.first) &&
					last.equalsIgnoreCase(o.last) &&
					status.equals(o.status)) {
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns first and last name, along with the marital status of the person.
	 * @return The first and last name, along with the marital status of the person.
	 */
	@Override
	public String toString() {
		return first + ", " + last + ": " + status;
	}
	
	
}
