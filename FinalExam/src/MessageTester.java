/**
 * /**Class: MessageTester
 *  * @author Raghu Khanal
 *  * @version 1.0
 *  * Course : ITEC 3150
 *  * Written: May 2, 2019
 *
 *  This class adds all the constants in the Level enum as the key
 *  and prints string message as it's value.
 */
import java.util.HashMap;

public class MessageTester {
    public static void main(String[] args) {
        HashMap<Level, String> map = new HashMap<Level, String>();

        map.put(Level.INFORMATION, "This message is informational only");
        map.put(Level.WARNING, "Please be careful, you are about to make a mistake");
        map.put(Level.ERROR, "Please correct your error");
        map.put(Level.FATAL, "Application is terminating");

        for (HashMap.Entry<Level, String> message : map.entrySet())
        {
            System.out.println(message.getKey() + ": " + message.getValue().toString());
        }

    }
}
