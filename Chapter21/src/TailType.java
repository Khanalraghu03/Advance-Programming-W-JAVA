 /** Class : TailType
 *@ author: Raghu Khanal
 * version 1.0
 * Course: ITEC 3150 Spring 2019
 * Written: February 9, 2019
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
