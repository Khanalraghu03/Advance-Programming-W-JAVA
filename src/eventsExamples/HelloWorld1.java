package application;
//No events, just widgets (GUI controls)
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.control.Label; 
import javafx.stage.Stage; 

public class HelloWorld1 extends Application 
{ 
	private Button b;
	private Label l;

	// launch the application 
	public void start(Stage s) 
	{ 
		// set title for the stage 
		s.setTitle("creating buttons"); 

		// create a button 
		b = new Button("button"); 

		// create a stack pane 
		TilePane r = new TilePane(); 

		// create a label 
		l = new Label("Goodbye All"); 


		// add button 
		r.getChildren().add(b); 
		r.getChildren().add(l); 		

		// set the scene 
		s.setScene(new Scene(r, 200, 200) ); 

		s.show(); 
	} 

	public static void main(String[] args) {
		launch(args);
	}
}