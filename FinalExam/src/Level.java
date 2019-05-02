/**
 * /**Class: Level
 *  * @author Raghu Khanal
 *  * @version 1.0
 *  * Course : ITEC 3150
 *  * Written: May 2, 2019
 *
 *  This class provides constants INFORMATION, WARNING, ERROR, FATAL
 *  so that it can be used as a key in the MessageTester.java class.
 */
public enum Level {
    INFORMATION, WARNING, ERROR, FATAL;

    @Override
    public String toString()
    {
        String s = super.toString();
        return s.toLowerCase();
    }
}
