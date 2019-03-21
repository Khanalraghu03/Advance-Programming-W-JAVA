package application;
//Add event with handler inline code
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.Label; 
import javafx.stage.Stage; 

public class HelloWorld3 extends Application 
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
		// action event is object named handler 
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 
				l.setText("Hello World "); 
			} 
		}; 

		// when button is pressed 
		b.setOnAction(handler); 

		// create a stack pane 
		TilePane r = new TilePane(); 

		// create a label 
		l = new Label("Goodbye All"); 



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

	/*class HelloHandlerClass implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent e) 
		{ 
			l.setText("   Hello World   "); 
		}
}*/ 
}