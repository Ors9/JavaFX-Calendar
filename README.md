# JavaFX Calendar App

This project is a JavaFX-based **calendar and meeting scheduling app** that displays a monthly calendar grid and allows users to manage meetings for each day.

## Features

- Displays a full monthly calendar (42-cell grid)
- Highlights the current month with day labels in Hebrew
- Opens a dialog to add, remove, and edit meetings on a specific date
- Stores meeting data using a `CalendarManager`
- Hebrew locale support for date formatting

## Technologies Used

- Java 11+
- JavaFX
- FXML for layout (`MyCalendar.fxml`, `MyDialog.fxml`)

## Files

| File | Description |
|------|-------------|
| `MyCalendar.java` | Entry point that loads the FXML and launches the main stage |
| `MyCalendar.fxml` | Main layout containing the calendar grid and date picker |
| `CalendarManager.java` | Manages date selection and meetings storage |
| `CalendarLogic.java` | Static helpers for calendar math, formatting, and comparison |
| `MyDialog.java` | Opens a modal dialog for a selected date |
| `MyDialog.fxml` | FXML layout for the meeting management dialog |
| `MyDialogController.java` | Handles logic inside the meeting dialog |
| `run.bat` | (Optional) Windows batch file to compile and run the app |

## How to Run

### Option 1: Manually (Command Line)

```bash
javac *.java
java MyCalendar
```

> Make sure your JavaFX SDK is configured with the `--module-path` and `--add-modules javafx.controls,javafx.fxml` flags if needed.

### Option 2: With `run.bat` (Windows Only)

Double-click `run.bat` (requires JavaFX path inside the script).

## Sample Usage

1. Open the calendar – you will see the current month displayed.
2. Click any date to open the **meeting manager dialog**.
3. Add, delete, or edit meetings and save.

## Screenshot

> Add a screenshot showing the calendar grid and the meeting dialog

## Author

Or Saban