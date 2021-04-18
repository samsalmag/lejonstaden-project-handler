package edu.chalmers.axen2021.model;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for containing the SABO table values required for calculations.
 * @author Sara Vardheim
 * @author Tilda Grönlund
 *
 */
public class SaboTable {

	/**
	 * Variable for storing the table.
	 */
	private static Map<String, Double> sabo;
	
	/**
	 * Creates a new SABO table and fills it using the fillTable method.
	 */
	public SaboTable() {
		sabo = new HashMap<>();
		fillTable();
	}

	/**
	 * Maps the type of apartment to its +RE value. 
	 */
	private void fillTable() {
		sabo.put("1rks", 24.0);
		sabo.put("1rokv", 27.0);
		sabo.put("1rok", 34.0);
		sabo.put("1.5rok", 37.0);
		sabo.put("2rokv", 34.0);
		sabo.put("2rok", 40.0);
		sabo.put("2.5rok", 42.0);
		sabo.put("3rok", 44.0);
		sabo.put("3.5rok", 46.5);
		sabo.put("4rok", 49.0);
		sabo.put("4.5rok", 50.5);
		sabo.put("5rok", 52.0);
		sabo.put("6rok", 55.0);
		sabo.put("7rok", 57.0);
		sabo.put("8rok", 59.0);
	}
	
	/**
	 * Takes a String describing the number of rooms, e.g. 1rok, and returns
	 * the +RE value for that type of apartment. 
	 * @param rooms a String.
	 * @return +RE value.
	 */
	public double getRE(String rooms) {
		try {
			return sabo.get(rooms);
		} catch (NullPointerException npe) {
			throw new NullPointerException("Not a valid entry");
		}
	}
}
