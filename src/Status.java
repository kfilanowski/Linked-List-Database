/**
 * This class models the marital status of a person. Marital statuses include:
 * Married, Widowed, Divorced, and Single
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public class Status {

	/** Marital status variable */
	private String maritalStatus;

	/**
	 * Constructor to set the marital status of the person.
	 * @param maritalStatus
	 */
	public Status(String maritalStatus) {
		setMartialStatus(maritalStatus);
	}
	
	/**
	 * Getter method to return the marital status.
	 * @return Marital status in the form of a string.
	 */
	String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * Sets the marital status of the person.
	 * @param maritalStatus The marital status in the form of a single letter.
	 * @throws IllegalArgumentException This exception is thrown if the
	 * 			marital status is invalid, or not one of the four:
	 * 			married, widowed, divorced, and single.
	 */
	private void setMartialStatus(String maritalStatus)
			throws IllegalArgumentException {
		
		// Assign the full marital status word.
		switch (maritalStatus) {
		case "Married":
		case "married":
		case "m":
		case "M": this.maritalStatus = "Married";  break;
		case "widowed":
		case "Widowed":
		case "w":
		case "W": this.maritalStatus = "Widowed";  break;
		case "divorced":
		case "Divorced":
		case "d":
		case "D": this.maritalStatus = "Divorced"; break;
		case "Single":
		case "single":
		case "s":
		case "S": this.maritalStatus = "Single";   break;
		default: {
			throw new IllegalArgumentException("Invalid Marital Status, "
					+ "Must be Married, Widowed, Divorced, or Single.");
		}
		}
	}
	
	/**
	 * Compares two Status objects by their marital status. If the marital
	 * status is the same, then they are considered equal.
	 * @param obj The object to compare with.
	 * @return True if the two objects are equal, false if they are not.
	 */
	@Override
	public boolean equals(Object obj) {
		// Check if obj is a Status object, then cast and compare it.
		if (obj instanceof Status) {
			Status o = (Status)obj;
			// If the fields are the same, then they are equal.
			if (o.maritalStatus.equalsIgnoreCase(o.maritalStatus)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the marital status of the associated Status object.
	 * @return The marital status of the person.
	 */
	@Override
	public String toString() {
		return maritalStatus;
	}
}
