package application;
//Add event with reusable handler class 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.stage.Stage; 

public class HelloWorld25 extends Application 
{ 
	private Button b;

	// launch the application 
	public void start(Stage s) 
	{ 
		// set title for the stage 
		s.setTitle("creating buttons"); 

		// create a button 
		b = new Button("button");
		ColorHandlerClass handlerColor = new ColorHandlerClass();  //create new handler event class (it's own reusable class below)
		b.setOnAction(handlerColor); //set the action to that handler - when button is pressed invokes handler	

		// create a stack pane 
		TilePane r = new TilePane(); 

		// add button 
		r.getChildren().add(b); 

		// create a scene 
		Scene sc = new Scene(r, 200, 200); 

		// set the scene 
		s.setScene(sc); 

		s.show(); 
	} 

	public static void main(String[] args) {
		launch(args);
	}

}