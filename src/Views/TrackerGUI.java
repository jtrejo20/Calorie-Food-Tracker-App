package Views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
	private TextField dayOfWeekField;
	private Button dayOfWeekButton;
	private GridPane dayOfWeekGrid;
	private Label dayOfWeekLabel;
	private BarChart<String,Number> weeklyCalorieChart;
	private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
	
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
	    stage.setTitle("Calorie & Food Tracker App");
	    layoutGUI();
	    Scene trackerSceneAlpha = new Scene(mainPane, 840, 600);
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
		
		
		dayOfWeekLabel = new Label("Enter Day #");
		dayOfWeekField = new TextField("1");
		dayOfWeekButton = new Button("Enter");
		dayOfWeekGrid = new GridPane();
		
		dayOfWeekGrid.add(dayOfWeekLabel, 0, 0);
		dayOfWeekGrid.add(dayOfWeekField, 1, 0);
		dayOfWeekGrid.add(dayOfWeekButton, 1, 1);
		
		dayOfWeekField.setMaxWidth(50);
		
		statsPane.setBottom(dayOfWeekGrid);
		
		createBarChart();
		
		mainPane = new GridPane();
		mainPane.add(statsPane,0,0);
		mainPane.add(weeklyCalorieChart, 1, 0);
	}
	
	private void createBarChart() {
		
        weeklyCalorieChart = new BarChart<String,Number>(xAxis,yAxis);
        weeklyCalorieChart.setTitle("Weekly Calorie Consumption Summary");
        xAxis.setLabel("Days of the Week");       
        yAxis.setLabel("Calories");
        XYChart.Series series1 = new XYChart.Series();       
        series1.getData().add(new XYChart.Data("Monday", 0.0));
        series1.getData().add(new XYChart.Data("Tuesday", 0.0));
        series1.getData().add(new XYChart.Data("Wednesday", 10.0));
        series1.getData().add(new XYChart.Data("Thursday", 30.0));
        series1.getData().add(new XYChart.Data("Friday", 12.0));   
        series1.getData().add(new XYChart.Data("Saturday", 23.0));
        series1.getData().add(new XYChart.Data("Sunday", 20.0));
        
        weeklyCalorieChart.getData().add(series1);
	}

	/**
	 *  Register handlers
	 */
	private void registerHandlers() {

	}

}
