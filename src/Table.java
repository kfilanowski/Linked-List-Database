/**
 * Represents a single table in the database.
 * @author Kevin Filanowski
 * @version 09/21/18
 */
public class Table<T extends AttributeInterface> {

	/** First record in the table */
	private Node head;
	/** Last record in the table */
	private Node tail;
	/** Label for the table */
	private String title;
	/** Counter to keep size of the array */
	private int size;
	/** 
	 * This temp node is only used to iterate through the table. It is declared
	 * here so that we don't constantly declare it throughout the code.
	 */
	private Node temp;
	
	/**
	 * Initializes an empty table with a label.
	 * @param title The label for the table.
	 */
	public Table(String title) {
		this(null, title);
	}
	
	/**
	 * Initializes a table with the first record and a label.
	 * @param head The first record in the table.
	 * @param title The label for the table.
	 */
	public Table(Node head, String title) {
		this.head = head;
		this.title = title;
		tail = head;
		size = 0;
	}
	
	/**
	 * Checks the table to see if a row with the data specified exists.
	 * NOTE: The data in the table should have a valid and appropriate
	 * equals method to compare data.
	 * @param data The data that will be used to check the table for.
	 * @return True if there is a matching data piece to a row in the table,
	 * 			false otherwise.
	 */
	public boolean contains(T data) {
		// Reset the temporary node back to the head to iterate the table.
		temp = head;
		
		for (int i = 0; i < size; i++) {
			// Compare the data
			if (temp.data.equals(data)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	/**
	 * Creates a new table comprised of nodes in this table, but not in the
	 * table being passed in.
	 * @param table The table used to compare with the original one.
	 * @return A new table of type T, containing nodes contained in the 
	 * 		   original table but not in the table passed in.
	 */
	Table<T> difference(Table<T> table) {
		// The new table comprised of differences.
		Table<T> differenceTable = new Table<T>(title + ", " + table.title);
		
		// Reset the temporary node back to the head to iterate the table.
		temp = head;
		
		// Iterate through the first table, only adding rows unique
		// to the first table.
		for (int i = 0; i < size; i++) {
			if (!table.contains(temp.data)) {
				differenceTable.insert(temp.data);
			}
			temp = temp.next;
		}
		return differenceTable;
	}

	/**
	 * Adds a new record to the end of the table.
	 * @param data The information that would be contained in a row of the table.
	 */
	void insert(T data) {
		// If this is the first element, set it to head. Otherwise,
		// add it to the end of the tail.
		if (head == null) {
			head = new Node(data);
			tail = head;
		} else {
			tail.next = new Node(data);
			tail = tail.next;
		}
		size++;
	}

	/**
	 * Creates a new table comprised of nodes having a value for a specific
	 * attribute, created from two tables.
	 * @param attribute The attribute to restrict to.
	 * @param value The value of that attribute to restrict to.
	 * @param table The other table to compare with.
	 * @return A new table composed of the same specified attributes and values
	 * 		   in both tables.
	 */
	Table<T> intersect(String attribute, String value, Table<T> table) {
		// The new table comprised of intersecting attributes and values.
		Table<T> intersectTable = new Table<T>(title + ", " + table.title);
		
		// First we select the attributes and values consisted in both tables
		Table<T> selectedOne = select(attribute, value);
		Table<T> selectedTwo = table.select(attribute, value);
		
		// Reset the temporary node back to the head to iterate the table.
		temp = selectedOne.head;
		
		// Then we add the first table into intersectTable.
		for (int i = 0; i < selectedOne.size; i++) {
			if (selectedTwo.contains(temp.data)) {
				// Create a copy to modify department without affecting
				// the original record.
				// We know the copy is of type T since we copied it from type T.
				@SuppressWarnings("unchecked") 
				T copy = (T)temp.data.makeCopy();
				copy.change("department", title + ", " + table.title);
				intersectTable.insert(copy);
			} else {
				intersectTable.insert(temp.data);
			}
			temp = temp.next;
		}
		
		// Then we ensure there are no duplicates by checking the second
		// table before we insert the row into intersectTable.
		temp = selectedTwo.head;
		for (int i = 0; i < selectedTwo.size; i++) {
			if (!intersectTable.contains(temp.data)) {
				intersectTable.insert(temp.data);
			}
			temp = temp.next;
		}
		return intersectTable;
	}
	
	/**
	 * Getter method to retrieve the label of the table.
	 * @return The title/label of the table.
	 */
	protected String getTitle() {
		return title;
	}

	/**
	 *  Removes the node/row with a matching ID from the table.
	 * @param id The id of the node/row.
	 */
	void remove(String id) {
		// Checking the first record, then every other one afterwards.
		if (head != null) {
			if (head.data.check("id", id)) {
				head = head.next;
				size--;
			} else {
				Node prev = head;
				temp = head.next;
				
				// Walk the list and check for the record to remove.
				while(!temp.data.check("id", id) && temp.next != null) {
					temp = temp.next;
					prev = prev.next;
				}
				// Move the node previous to temp past temp.
				if (temp.data.check("id", id)) {
					prev.next = temp.next;
					size--;
					// Boundary checking. Update tail if tail is removed.
					if (prev.next == null) {
						tail = prev;
					}
				}
			}
		}
	}

	/**
	 * Creates a new table comprised of nodes having a value for a specific
	 * attribute. 
	 * @param attribute The attribute to select and restrict to.
	 * @param value The value to select and restrict to.
	 * @return A new table consisting of only the attributes and values
	 * 		   selected.
	 */
	Table<T> select(String attribute, String value) {
		// Create the new table
		Table<T> selectTable = new Table<T>(title);
		
		// Reset the temporary node back to the head to iterate the table.
		temp = head;
		
		// Iterate through the table to select the attribute value.
		for (int i = 0; i < size; i++) {
			if (temp.data.check(attribute, value)) {
				selectTable.insert(temp.data);
			}
			temp = temp.next;
		}
		return selectTable;
	}

	/**
	 * Creates a new table comprised of nodes that occur in both tables.
	 * @param table The other table to compare with.
	 * @return A new table containing nodes that are contained in both tables.
	 */
	Table<T> union(Table<T> table) {
		// The new table consisting of elements of both tables.
		Table<T> unionTable = new Table<T>(title + ", " + table.title);
		
		// Reset the temporary node back to the head to iterate the table.
		temp = head;
		
		// Copy the original table into the unionTable.
		for (int i = 0; i < size; i++) {
			if (table.contains(temp.data)) {
				// Create a copy to modify department without affecting
				// the original record.
				// We know the copy is of type T since we copied it from type T.
				@SuppressWarnings("unchecked")
				T copy = (T)temp.data.makeCopy();
				copy.change("department", title + ", " + table.title);
				unionTable.insert(copy);
			} else {
				unionTable.insert(temp.data);
			}
			temp = temp.next;
		}
		
		// Switch control to the table parameter.
		temp = table.head;
		
		// Copy the parameter table into unionTable and check for duplicates.
		for (int i = 0; i < table.size; i++) {
			if (!unionTable.contains(temp.data)) {
				unionTable.insert(temp.data);
			}
			temp = temp.next;
		}
		return unionTable;
	}
	
	/**
	 * Prints the data in this entire table.
	 * @return A string representation of the data in the table.
	 */
	public String toString() {
		// Creating a string buffer to concatenate strings to. 
		StringBuffer string = new StringBuffer();
		
		// Reset the temporary node back to the head to iterate the table.
		temp = head;
		
		string.append("\n=========================" + title
				+ "=========================\n");
		
		// Iterate through the entire table and append to the string.
		for (int i = 0; i < size; i++) {
			string.append(temp.toString());
			string.append("\n-----------------------------"
					+ "---------------------------------\n");
			temp = temp.next;
		}
		
		string.append("=========================" + title
				+ "=========================\n");
		return string.toString();
	}
	
	/**
	 * Inner class node, each node is a row in the table. Each node also
	 * contains the data and a pointer to the next row in the table.
	 * @author Kevin Filanowski
	 * @version 09/21/18
	 */
	private class Node {
		/** 
		 * The data contained in this node. This contains the information 
		 * of one row in the table. 
		 */
		private T data;
		/** The next row in the table.*/
		private Node next;
		
		/** 
		 * Constructor initializes a new row in a table
		 * @param data The data that will be contained in the node.
		 **/
		public Node(T data) {
			this.data = data;
			this.next = null;
		}
		
		/**
		 * Prints the data in this node.
		 * @return A string representation of the data in a row of the table.
		 */
		@Override
		public String toString() {
			return this.data.toString();
		}
	}
}
