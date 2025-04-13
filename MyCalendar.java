import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that launches the calendar application.
 * Loads the FXML layout and initializes the primary stage.
 */
public class MyCalendar extends Application {

	
    /**
     * Entry point for the JavaFX application.
     * Loads the FXML file, sets up the scene and stage properties.
     *
     * @param stage the primary window for the application
     * @throws Exception if FXML loading fails
     */
    @Override
	public void start(Stage stage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("MyCalendar.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("לוח שנה");
		stage.setScene(scene);
		stage.setWidth(700);
		stage.setHeight(500);
		stage.show();

		stage.setMinWidth(500);
		stage.setMinHeight(500);
	}

    
	public static void main(String[] args) {
		launch(args);
		System.out.println();
	}
}
