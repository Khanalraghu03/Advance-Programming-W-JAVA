import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**Class: Encrypt13
 * @author Raghu Khanal
 * @version 1.0
 * midterm.Course : ITEC 3150
 * Written: February 3, 2019
 *
 *
 * This class encrypt a message.
 *      for every A - Z or a - z: make it the 13th character
 *          when adding 13 if it reaches Z or z:
 *              come back to a
 *
 */

public class Encrypt13 extends Application {

    GridPane pane = new GridPane();
    Label omDialog = new Label("Original Message: ");
    TextField theMessage = new TextField();
    Label emDialog = new Label("Encrypted Message: ");
    Label encryptedMsg = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {

        pane.add(encryptedMsg,1,1);
        theMessage.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER) {
                //When enter pressed do this:

               //encrypt message here:
               char[] arrayOfOM = theMessage.getText().toCharArray();
               for(int i = 0; i < arrayOfOM.length; i++)
               {
                   int ascii = (int) arrayOfOM[i];
                   if(ascii >= 65 && ascii <= 90) {
                       //make the char value to value + 13
                       //If Z then go back to A
                       if(ascii + 13 > 90) {
                           //after Z go back to A => subtract 26
                           int num = (ascii + 13)-26;
                           arrayOfOM[i] = (char) (num);
                       }
                       else {
                           arrayOfOM[i] = (char) (ascii + 13);
                       }
                   }
                   else if(ascii >= 97 && ascii <= 122){
                       //after z go back to a.
                       if(ascii + 13 > 122) {
                           int num = (ascii + 13)-26;
                           arrayOfOM[i] = (char) (num);
                       }
                       else {
                           arrayOfOM[i] = (char) (ascii + 13);
                       }
                   }
                   else {
                       arrayOfOM[i] = arrayOfOM[i];
                   }
               }

               encryptedMsg.setText(new String(arrayOfOM));
            }

        });

        pane.add(omDialog,0,0); // omTField, encryptLabel
        pane.add(theMessage,1,0);
        pane.add(emDialog,0,1);

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Encrypt by Rotating 13");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
