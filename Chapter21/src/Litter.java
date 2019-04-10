 /** Class : Litter
 * @author Raghu Khanal
 * @version 1.0
 * Course: ITEC 2250 Spring 2019
 * Written: February 9, 2019
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
