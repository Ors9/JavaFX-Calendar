import java.util.ArrayList;
import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller class for the meeting dialog window. Handles adding, removing,
 * saving, and displaying meeting notes for a selected calendar day.
 */
public class MyDialogController {

	@FXML
	private Button addBtn;

	@FXML
	private Button deleteButton;

	@FXML
	private Button saveBtn;

	@FXML
	private Label dataLabel;

	@FXML
	private ListView<String> meetingList;

	@FXML
	private VBox myVbox;

	@FXML
	private HBox myHBox;

	@FXML
	private TextField newMeetingField;

	private CalendarManager calendarManager; // Handles meeting data
	private Calendar currentDate; // The date currently being viewed

	/**
	 * Initializes the dialog window with the selected date and meeting data. Called
	 * by MyDialog.showDialog() when opening the window.
	 *
	 * @param datePressed     the selected calendar date
	 * @param calendarManager the shared data manager instance
	 */
	public void initData(Calendar datePressed, CalendarManager calendarManager) {

		this.currentDate = datePressed;
		this.calendarManager = calendarManager;

		styleTextFieldAndMeetingList();
		displayTopDateLabel();// Show title with selected dat

		// Load existing meetings
		meetingList.getItems().setAll(calendarManager.getMeetingsForDate(currentDate));

		// Allow the user to edit meeting items directly in the list
		meetingList.setEditable(true);
		meetingList.setCellFactory(TextFieldListCell.forListView());

	}

	/**
	 * Called when the "Add" button is clicked. Adds a new meeting to the data model
	 * and updates the list display.
	 */
	@FXML
	void onAddMeeting(ActionEvent event) {
		String userInput = newMeetingField.getText();
		calendarManager.addMeeting(currentDate, userInput);
		meetingList.getItems().setAll(calendarManager.getMeetingsForDate(currentDate));
		newMeetingField.clear();
	}

	/**
	 * Called when the "Delete" button is clicked. Removes the selected meeting from
	 * both the UI list and the model.
	 */
	@FXML
	void onDeletePressed(ActionEvent event) {
		String selected = meetingList.getSelectionModel().getSelectedItem();
		if (selected != null) {
			meetingList.getItems().remove(selected); // Remove from ListView
			ArrayList<String> updated = new ArrayList<>(meetingList.getItems());
			calendarManager.updateMeetings(currentDate, updated);// Update Map
		}
	}

	/**
	 * Called when the "Save" button is clicked. Updates the meeting list in the map
	 * with the current ListView items.
	 */
	@FXML
	void onSavedPressed(ActionEvent event) {
		ArrayList<String> updated = new ArrayList<>(meetingList.getItems());
		calendarManager.updateMeetings(currentDate, updated); // Save list
	}

	/**
	 * Displays the selected date at the top of the dialog.
	 */
	private void displayTopDateLabel() {
		String hebrewDate = "פגישות: " + CalendarLogic.formatShortDate(currentDate);

		Label label = new Label(hebrewDate);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setStyle("-fx-border-color: lightgray; -fx-font-weight: bold; -fx-padding: 10; -fx-font-size: 20px;");
		label.setAlignment(javafx.geometry.Pos.CENTER);
		label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		myHBox.getChildren().add(0, label);
		myHBox.setSpacing(10);
	}

	/**
	 * Applies visual styles (font, padding, borders) to the input field and the
	 * meeting ListView to improve UX.
	 */
	private void styleTextFieldAndMeetingList() {
		// Styling for the text field where new meetings are added
		newMeetingField.setStyle("-fx-font-size: 16px; " + "-fx-font-weight: bold; " + "-fx-border-width: 2px; "
				+ "-fx-padding: 10px; ");

		// Styling for the ListView displaying meetings
		meetingList.setStyle("-fx-font-size: 14px; " + "-fx-font-weight: bold; " + "-fx-border-width: 2px; ");
	}

}
