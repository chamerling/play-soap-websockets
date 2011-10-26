/**
 * 
 */
package models;

/**
 * @author chamerling
 * 
 */
public class Topic {

	/**
	 * TOpic name
	 */
	String name;

	/**
	 * Last message received for this topic
	 */
	String lastReceived;

	/**
	 * Total nb of received messages for the current topic
	 */
	long nb;

}
