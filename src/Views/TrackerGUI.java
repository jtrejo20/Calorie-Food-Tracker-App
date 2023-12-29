package Views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrackerGUI extends Application {

	private Stage stage;
	
	private GridPane mainPane;
	private BorderPane statsPane;
	private VBox foodCaloriePane;
	private ScrollPane scroller;
	private TextField dataInput;
	
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
	    stage.setTitle("Calorie & Food Tracker App");
	    layoutGUI();
	    Scene trackerSceneAlpha = new Scene(mainPane, 740, 600);
	    stage.setScene(trackerSceneAlpha);
	    stage.show();
	}

	/**
	 * Sets up layout of the main pane.
	 */
	private void layoutGUI() {
		//Contains the stats (foodName, calories) for each day of the week
		statsPane = new BorderPane();
		foodCaloriePane = new VBox();
		scroller = new ScrollPane();
		for(int i = 0; i < 50; i++) {
			
			GridPane tempGridPane = new GridPane();
			tempGridPane.add(new TextField("Food Name"), 0, 0);
			tempGridPane.add(new TextField("Calorie Amount"), 1, 0);
			foodCaloriePane.getChildren().add(tempGridPane);
		}
		
		
		scroller.setContent(foodCaloriePane);
		scroller.setMaxHeight(400);
		scroller.setMinWidth(315);
		statsPane.setTop(scroller);
		
		
		
		
		
		mainPane = new GridPane();
		mainPane.add(statsPane,0,0);
		
//	    mainPane.setCenter(theButton);
	}

	/**
	 *  Register handlers
	 */
	private void registerHandlers() {

	}

}
