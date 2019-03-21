package application;
//add event using anonymous inner class
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.Label; 
import javafx.stage.Stage; 

public class HelloWorld4 extends Application 
{ 

	// launch the application 
	public void start(Stage s) 
	{ 
		// set title for the stage 
		s.setTitle("creating buttons"); 



		// create a stack pane 
		TilePane r = new TilePane(); 

		// create a label 
		Label l = new Label("Goodbye All"); 


		// create a button and use lambda to create the event handler and run the code! 
		Button b = new Button("button"); 



		//anonymous inner class  
		b.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				l.setText("   Hello World   "); 
			} 
		}); 

		// add button 
		r.getChildren().add(b); 
		r.getChildren().add(l); 

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