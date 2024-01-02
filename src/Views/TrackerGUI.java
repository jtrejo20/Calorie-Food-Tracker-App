package Views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private Button mondayButton;
	private Button tuesdayButton;
	private Button wednesdayButton;
	private Button thursdayButton;
	private Button fridayButton;
	private Button saturdayButton;
	private Button sundayButton;
	private ArrayList<Button> dailyButtons;
	private HashMap<Integer, GridPane> foodCalorieData;
	private HashMap<Integer,String[]> savedFoodData;
	private BarChart<String,Number> weeklyCalorieChart;
	private HashMap<String,HashMap<Integer,String[]>> weeklyFoodData;
	private ArrayList<TextField> statsDataFields;
	private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    
    private String currentDay = "Monday";
	
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
		foodCalorieData = new HashMap<>();
		savedFoodData = new HashMap<>();
		weeklyFoodData = new HashMap<>();
		for(int j = 0; j < 50; j++) {
			
			GridPane tempGridPane = new GridPane();
			tempGridPane.add(new TextField("Food Name"), 0, 0);
			tempGridPane.add(new TextField("Calorie Amount"), 1, 0);
			
			String[] tempFoodCalorieData = new String[2];
			tempFoodCalorieData[0]=("Food Name");
			tempFoodCalorieData[1]=("Calorie Amount");
			
			savedFoodData.put(j,tempFoodCalorieData);
			foodCalorieData.put(j,tempGridPane);
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
		
		mondayButton = new Button("Monday");
		tuesdayButton = new Button("Tuesday");
		wednesdayButton = new Button("Wednesday");
		thursdayButton = new Button("Thursday");;
		fridayButton = new Button("Friday");
		saturdayButton = new Button("Saturday");
		sundayButton = new Button("Sunday");
		
		dailyButtons = new ArrayList<>();
		
		Collections.addAll(dailyButtons, mondayButton, tuesdayButton, wednesdayButton, thursdayButton,
				fridayButton, saturdayButton, sundayButton);
		
		dayOfWeekGrid.add(mondayButton, 0, 0);
		dayOfWeekGrid.add(tuesdayButton, 1, 0);
		dayOfWeekGrid.add(wednesdayButton, 2, 0);
		dayOfWeekGrid.add(thursdayButton, 0, 1);
		dayOfWeekGrid.add(fridayButton, 1, 1);
		dayOfWeekGrid.add(saturdayButton, 2, 1);
		dayOfWeekGrid.add(sundayButton, 1, 2);
		
		
		
		dayOfWeekField.setMaxWidth(50);
		
		statsPane.setBottom(dayOfWeekGrid);
		
		
		initializeBarChart();
		registerHandlers();
		
		mainPane = new GridPane();
		mainPane.add(statsPane,0,0);
		mainPane.add(weeklyCalorieChart, 1, 0);
	}
	
	private void initializeBarChart() {
        weeklyCalorieChart = new BarChart<String,Number>(xAxis,yAxis);
        weeklyCalorieChart.setTitle("Weekly Calorie Consumption Summary");
        xAxis.setLabel("Days of the Week");
        yAxis.setLabel("Calories");
        XYChart.Series initialSeries = new XYChart.Series();       
        initialSeries.setName("Daily Calories");
        initialSeries.getData().add(new XYChart.Data("Monday", 0.0));
        initialSeries.getData().add(new XYChart.Data("Tuesday", 0.0));
        initialSeries.getData().add(new XYChart.Data("Wednesday",0.0));
        initialSeries.getData().add(new XYChart.Data("Thursday", 0.0));
        initialSeries.getData().add(new XYChart.Data("Friday", 0.0));   
        initialSeries.getData().add(new XYChart.Data("Saturday", 0.0));
        initialSeries.getData().add(new XYChart.Data("Sunday", 0.0));
        
        weeklyCalorieChart.getData().add(initialSeries);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateBarChart() {
		
		double dayCalorieAverage = 0.0;
		for(int i = 0; i < savedFoodData.size(); i++) {
			double calorieAmount = 0.0;
			try {
				calorieAmount = Double.parseDouble(savedFoodData.get(i)[1]);
				dayCalorieAverage += calorieAmount;
			}
			catch(NumberFormatException numEx){
//				System.out.println("No calorie data at: " +i);
			}
			
		}
		
		dayCalorieAverage /= savedFoodData.size();
		
		XYChart.Series updatedSeries = weeklyCalorieChart.getData().get(0); 
		if(currentDay.equalsIgnoreCase("Monday")) {
			System.out.println("Data for Monday");
			updatedSeries.getData().set(0, new XYChart.Data("Monday", dayCalorieAverage));
		}
		else if(currentDay.equalsIgnoreCase("Tuesday")) {
			System.out.println("Data for Tuesday");
			updatedSeries.getData().set(1, new XYChart.Data("Tuesday", dayCalorieAverage));
		}
		else if(currentDay.equalsIgnoreCase("Wednesday")) {
			System.out.println("Data for Wednesday");
			updatedSeries.getData().set(2, new XYChart.Data("Wednesday", dayCalorieAverage));
		}
		else if(currentDay.equalsIgnoreCase("Thursday")) {
			System.out.println("Data for Thursday");
			updatedSeries.getData().set(3, new XYChart.Data("Thursday", dayCalorieAverage));
		}
		else if(currentDay.equalsIgnoreCase("Friday")) {
			System.out.println("Data for Friday");
			updatedSeries.getData().set(4, new XYChart.Data("Friday", dayCalorieAverage));
		}
		else if(currentDay.equalsIgnoreCase("Saturday")) {
			System.out.println("Data for Saturday");
			updatedSeries.getData().set(5, new XYChart.Data("Saturday", dayCalorieAverage));
		}
		else {
			System.out.println("Data for Sunday");
			updatedSeries.getData().set(6, new XYChart.Data("Sunday", dayCalorieAverage));
		}
	
//		System.out.println("Updated Series is : "+updatedSeries.getData().size()+" long");
	}

	/**
	 *  Register handlers
	 */
	private void registerHandlers() {
		statsDataFields = new ArrayList<>();
		for(int i = 0; i < foodCalorieData.size();i++) {
			GridPane tempGrid = foodCalorieData.get(i);
			if(tempGrid.getChildren().size() != 2) {
				System.out.println("Temp Grid does not have exactly 2 node elements");
				return;
			}
			TextField foodField = (TextField)tempGrid.getChildren().get(0);
			TextField calorieField = (TextField)tempGrid.getChildren().get(1);

			foodField.setOnAction(new TextFieldListener(i,0));
			calorieField.setOnAction(new TextFieldListener(i,1));
			
			statsDataFields.add(foodField);
			statsDataFields.add(calorieField);
		}
		
		for(Button button : dailyButtons) {
			button.setOnAction(new DailyButtonListener());
		}
		
	}
	
	private class TextFieldListener implements EventHandler<ActionEvent> {

		private int order;
		private int fieldSection;
		public TextFieldListener(int order, int fieldSection) {
			this.order = order;
			this.fieldSection = fieldSection;
		}
		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("Handling Text Field Action");
			if(fieldSection == 0) {
				TextField foodField = (TextField)arg0.getSource();
				String foodText = foodField.getText();
				if(!foodText.equalsIgnoreCase("Food Name") && !foodText.isBlank()) {
					System.out.println("Food Name at " +order+" changed to: "+foodText);
					foodCalorieData.get(order).getChildren();
					String[] tempStringArr = savedFoodData.get(order);
					tempStringArr[0] = foodText;
					savedFoodData.put(order,tempStringArr);
				}
				
			}
			else {
				TextField calorieField = (TextField)arg0.getSource();
				double calorieAmount = 0;
				try {
					calorieAmount = Double.parseDouble(calorieField.getText());
				}
				catch(NumberFormatException numEx) {
					System.out.println("Error: Non-double format detected");
					return;
				}
				
				System.out.println("Calorie Amount at " +order+" changed to: "+ calorieAmount);
				foodCalorieData.get(order).getChildren();
				String[] tempStringArr = savedFoodData.get(order);
				tempStringArr[1] = calorieAmount+"";
				savedFoodData.put(order,tempStringArr);
				updateBarChart();
			}
		}
	}
	
	private class DailyButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
				Button dailyButton = (Button)arg0.getSource();
				String buttonText = dailyButton.getText();
				if(buttonText.equalsIgnoreCase("Monday")) {
					currentDay = "Monday";
					
					System.out.println("Clicked the Monday button");
				}
				else if(buttonText.equalsIgnoreCase("Tuesday")) {
					currentDay = "Tuesday";
					System.out.println("Clicked the Tuesday button");
				}
				else if(buttonText.equalsIgnoreCase("Wednesday")) {
					currentDay = "Wednesday";
					System.out.println("Clicked the Wednesday button");
				}
				else if(buttonText.equalsIgnoreCase("Thursday")) {
					currentDay = "Thursday";
					System.out.println("Clicked the Thursday button");
				}
				else if(buttonText.equalsIgnoreCase("Friday")) {
					currentDay = "Friday";
					System.out.println("Clicked the Friday button");
				}
				else if(buttonText.equalsIgnoreCase("Saturday")) {
					currentDay = "Saturday";
					System.out.println("Clicked the Saturday button");
				}
				else {
					currentDay = "Sunday";
					System.out.println("Clicked the Sunday button");
				}
		}
		
	}

}
