/**
 * @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * @version 1.0
 * This class provides litterTypes in constants
 */
 /** Class : Litter
 * @author ITEC 2150
 * @version 1.0
 * Course: ITEC 2250 Spring 2013
 * Written: February 9, 2013
 *This class - This class enumerates litter types.
 *Purpose:- The purpose of the class is use for enum.
 */
public enum Litter 
{
	SCOOPABLE, CRYSTALS, REGULAR, NONE;  //only valid vales.

	/* For SOPing to the console
	 * @see java.lang.Enum#toString()
	 */
	@Override 
	public String toString() 
	{
		String s = super.toString();
		return s.toLowerCase();
	}
}
