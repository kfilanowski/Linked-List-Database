/**
 * Interface class defines attribute related operations.
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public interface AttributeInterface {
	/**
	 * Check to see if a record has an attribute containing a specific value.
	 * @param attribute The attribute to check that value for.
	 * @param value The value to check with the attribute.
	 * @return True if there exists an attribute with the value specified,
	 * 		  false otherwise.
	 */
	public boolean check(String attribute, String value);
	
	/**
	 * Change the value of a specified attribute.
	 * @param attribute The attribute to change.
	 * @param value The new value to assign to the specified attribute.
	 * @return True if a change was made, false otherwise.
	 */
	public boolean change(String attribute, String value);
	
	/**
	 * Makes a deep copy of the employee object without using clone()
	 * or a copy constructor.
	 * @return A deep copy of the current employee object.
	 */
	public AttributeInterface makeCopy();
}
