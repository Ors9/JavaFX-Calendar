
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * MyCalendarController is the main controller for the calendar screen (FXML).
 * It is responsible for: - displaying the monthly calendar grid - responding to
 * date selection via the DatePicker - managing UI layout for weekdays and day
 * buttons - delegating logic to CalendarManager
 */
public class MyCalendarController {

	@FXML
	private GridPane calendarGrid;

	@FXML
	private DatePicker datePicker;

	@FXML
	private HBox myHBox;

	private Button[] btn;

	private String[] days = { "ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת" };
	private static final int COLS = 7;

	// Handles calendar data and meetings
	private CalendarManager calendarManager;

	/**
	 * Called automatically when the FXML file is loaded. Initializes the calendar
	 * grid and sets the default date (today).
	 */
	public void initialize() {
		btn = new Button[CalendarLogic.TOTAL_CELLS];
		setDaysInWeek(); // Set the weekday labels (ראשון - שבת)
		calendarManager = new CalendarManager(Calendar.getInstance());
		setPickedDate(calendarManager.getCurrentDate());

	}

	/**
	 * Triggered when a new date is selected from the DatePicker. Updates the
	 * calendar view accordingly.
	 */
	@FXML
	void onDateSelected(ActionEvent event) {
		myHBox.getChildren().remove(0);// Remove old date label
		calendarGrid.getChildren().clear(); // Clear day buttons
		setDaysInWeek();// Re-add weekday headers
		String dateText = datePicker.getEditor().getText(); // Parse the string into a Calendar object

		try {
			Calendar calendar = CalendarLogic.parseDateFromText(dateText);
			setPickedDate(calendar);
		} catch (ParseException e) {
			System.out.println("תאריך לא תקין: " + dateText);
		}
	}

	/**
	 * Creates the 7 weekday header labels ("ראשון" to "שבת") and places them in the
	 * first row of the calendar grid.
	 */
	private void setDaysInWeek() {

		for (int i = 0; i < COLS; i++) {
			Label label = new Label(days[i]);
			label.setMaxWidth(Double.MAX_VALUE);
			label.setStyle("-fx-border-color: #999999;" + "-fx-border-width: 1 0 1 0;" + "-fx-font-weight: bold;"
					+ "-fx-padding: 10;" + "-fx-alignment: center;" + "-fx-font-size: 13px;");
			label.setAlignment(javafx.geometry.Pos.CENTER);
			label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			GridPane.setHgrow(label, Priority.ALWAYS);
			GridPane.setVgrow(label, Priority.ALWAYS);
			calendarGrid.add(label, i, 0);

		}

	}

	/**
	 * Updates the calendar view to a specific month and year.
	 *
	 * @param calendar the new calendar base date to display
	 */
	private void setPickedDate(Calendar calendar) {
		calendarManager.setCurrentDate(calendar);
		ArrayList<Calendar> calendarDays = calendarManager.getCalendarDays();
		String dateAsText = CalendarLogic.formatShortDate(calendar);
		datePicker.getEditor().setText(dateAsText);
		displayDate(calendarDays, calendar);
	}

	/**
	 * Fills the grid with 42 buttons, each representing a day in the current view.
	 * Applies visual styles and sets click events for opening meeting dialogs.
	 *
	 * @param calendarDays the 42-day list to display
	 * @param calendar     the selected/current month
	 */
	private void displayDate(ArrayList<Calendar> calendarDays, Calendar calendar) {
		displayTopDateLabel(calendar); // Show current month/year label at the top

		for (int i = 0; i < CalendarLogic.TOTAL_CELLS; i++) {
			Calendar today = Calendar.getInstance();
			Calendar current = calendarDays.get(i);

			String btnText = CalendarLogic.formatDayLabel(current);
			btn[i] = new Button(btnText);
			btn[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			GridPane.setHgrow(btn[i], Priority.ALWAYS);
			GridPane.setVgrow(btn[i], Priority.ALWAYS);

			// Style the button
			setButtonStyle(btn[i], current, today, calendar);

			// Set an event for when the day is clicked
			btn[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					handleButtonPressed(arg0, current); // Open dialog for that date
				}
			});

			int col = i % COLS;
			int row = (i / COLS) + 1;
			calendarGrid.add(btn[i], col, row); // Add button to grid
		}
	}

	/**
	 * Called when a day button is clicked. Opens the meeting dialog for that day.
	 *
	 * @param arg0    the button event (not used)
	 * @param current the Calendar date of the button
	 */
	private void handleButtonPressed(ActionEvent arg0, Calendar current) {
		MyDialog.showDialog(current, calendarManager); // Open a dialog to show events for the selected date

	}

	/**
	 * Displays a large title label at the top showing the current month and year in
	 * Hebrew.
	 *
	 * @param calendar the currently selected date
	 */
	private void displayTopDateLabel(Calendar calendar) {
		String hebrewDate = CalendarLogic.formatFullHebrewDate(calendar);

		Label label = new Label(hebrewDate);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setStyle("-fx-border-color: lightgray; -fx-font-weight: bold; -fx-padding: 10; -fx-font-size: 20px;");
		label.setAlignment(javafx.geometry.Pos.CENTER);
		label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		myHBox.getChildren().add(0, label);
		myHBox.setSpacing(10);
	}

	/**
	 * Applies styling to a day button based on its date: - Today is highlighted -
	 * Non-current-month dates are faded - Current month dates are styled normally
	 *
	 * @param btn      the button to style
	 * @param current  the date the button represents
	 * @param today    today's date
	 * @param selected the currently selected calendar base date
	 */
	private void setButtonStyle(Button btn, Calendar current, Calendar today, Calendar selected) {
		if (CalendarLogic.isSameDate(today, current)) {
			// Highlight today's date
			btn.setStyle("-fx-background-color: gainsboro; -fx-border-color: gray; -fx-font-weight: bold;");
		} else if (!CalendarLogic.isSameMonth(current, selected)) {
			// Fade out dates from other months
			btn.setStyle(
					"-fx-background-color: #eeeeee; -fx-border-color: lightgray; -fx-font-weight: bold; -fx-padding: 10;");
		} else {
			// Regular date styling
			btn.setStyle(
					"-fx-background-color: transparent; -fx-border-color: lightgray; -fx-font-weight: bold; -fx-padding: 10;");
		}

		btn.setCursor(javafx.scene.Cursor.HAND); // Hand cursor

	}

}
