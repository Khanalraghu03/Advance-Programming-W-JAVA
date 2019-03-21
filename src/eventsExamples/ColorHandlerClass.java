package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

//this reusable class changes the button color to red if the button is pressed
public class ColorHandlerClass implements EventHandler<ActionEvent> //inner class listener
{

	@Override
	public void handle(ActionEvent e) 
	{ 
		if (e.getSource() instanceof Button)
		{
		Button b = (Button) e.getSource();		
		b.setStyle("-fx-background-color: #ff0000; ");
		}
	} 
} 