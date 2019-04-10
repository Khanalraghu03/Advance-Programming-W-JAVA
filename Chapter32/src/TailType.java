/**
 * @author: Raghu Khanal
 * @assign #: 7
 * @date: 04/08/2019
 * @course number: Itech 3150
 * @version 1.0
 * Purpose:- The purpose of the class is use for enum.
 */

 /** Class : Litter
 *@ author ITEC 2150
 * version 1.0
 * Course: ITEC 2150 Spring 2013
 * Written: February 9, 2013
 * This class - This class enumerates valid tail types.
 * Purpose:- The purpose of the class is use for enum.
 */
public enum TailType 
{
	BOBBED, RING, DOCKED, OTTER, WHIP, SICKLE;  //only valid values.;  //only valid vales.

	@Override 
	public String toString() 
	{
		String s = super.toString();
		return s.toLowerCase();
	}
}
