import java.io.IOException;
import java.util.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Utility class for displaying a modal dialog window
 * to manage meetings for a selected calendar date.
 */
public class MyDialog {

	
    /**
     * Opens a modal dialog window that displays and allows editing
     * of meetings for a specific date.
     *
     * @param datePressed     the calendar date the user clicked
     * @param calendarManager the shared calendar data manager
     */
	public static void showDialog(Calendar datePressed, CalendarManager calendarManager) {
		try {
			// Load the FXML layout and controller
			FXMLLoader loader = new FXMLLoader(MyDialog.class.getResource("MyDialog.fxml"));
			Parent root = loader.load();

            // Access the controller and pass data to it
			MyDialogController controller = loader.getController();
			controller.initData(datePressed, calendarManager);


            // Create a new modal stage (blocking dialog)
			Stage dialogStage = new Stage();
			dialogStage.setTitle("ניהול פגישות");
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			

            // Set scene and display
			dialogStage.setScene(new Scene(root));
			dialogStage.setWidth(600);
			dialogStage.setHeight(600);
			dialogStage.showAndWait();

			dialogStage.setMinWidth(500);
			dialogStage.setMinHeight(500);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}