import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * CalendarManager manages calendar data including: - the currently selected
 * month - the list of dates displayed in a calendar view (42 days) - scheduled
 * meetings stored by date
 * 
 * It is used as a central logic layer for calendar-based applications.
 */
public class CalendarManager {
	// Constants for calendar grid structure
	public static final int TOTAL_CELLS = 42;
	public static final int DAYS_IN_WEEK = 7;
	public static final int FIRST_DAY_OFFSET = 6;

	// Internal state
	private Calendar currentDate;
	private ArrayList<Calendar> calendarDays;
	private final Map<Calendar, ArrayList<String>> meetings;

	/**
	 * Creates a CalendarManager for a given initial date (usually "today"). It also
	 * populates the list of visible calendar days.
	 *
	 * @param date the starting date for the calendar view
	 */
	public CalendarManager(Calendar date) {
		this.currentDate = date;
		calendarDays = new ArrayList<Calendar>();
		this.meetings = new HashMap<Calendar, ArrayList<String>>();
		fillCalendarDays();
	}

	/**
	 * @return the current date used to populate the calendar view
	 */
	public Calendar getCurrentDate() {
		return currentDate;
	}

	/**
	 * @return a list of 42 Calendar objects representing the visible grid of days
	 */
	public ArrayList<Calendar> getCalendarDays() {
		return calendarDays;
	}

	/**
	 * Updates the calendar to a new month (based on the given date), and
	 * regenerates the visible 42-day grid accordingly.
	 *
	 * @param newDate the date to use as the new base for the calendar
	 */
	public void setCurrentDate(Calendar newDate) {
		this.currentDate = newDate;
		this.calendarDays.clear();
		fillCalendarDays();
	}

	/**
	 * Fills the calendar view for the selected month. Returns an array of 42
	 * Calendar objects starting from the Sunday before the first of the month.
	 */
	private void fillCalendarDays() {
		Calendar firstDay = getFirstDayOfMonth();
		int leadingMissing = CalendarLogic.calculateLeadingMissing(firstDay);
		Calendar startDate = (Calendar) firstDay.clone();
		startDate.add(Calendar.DAY_OF_MONTH, -leadingMissing);
		generateFullCalendarView(startDate);
	}

	/**
	 * Fills calendarDays with 42 sequential Calendar objects starting from
	 * startDate.
	 *
	 * @param startDate the first visible date in the calendar view
	 */
	private void generateFullCalendarView(Calendar startDate) {
		for (int i = 0; i < TOTAL_CELLS; i++) {
			Calendar tempClone = (Calendar) startDate.clone();
			calendarDays.add(tempClone);
			startDate.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

	/**
	 * Returns the first day of the current month (e.g., April 1st, 2025).
	 *
	 * @return a Calendar object representing the first day of the current month
	 */
	private Calendar getFirstDayOfMonth() {
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);
		return new GregorianCalendar(year, month, 1);
	}

	/**
	 * Adds a new meeting message to a given date. If the date has no existing
	 * meetings, a new list is created.
	 *
	 * @param date    the date to which the meeting should be added
	 * @param message the message content (ignored if null or empty)
	 */
	public void addMeeting(Calendar date, String message) {
		if (!meetings.containsKey(date)) {
			meetings.put(date, new ArrayList<>());
		}
		if (message != null && !message.trim().isEmpty()) {
			meetings.get(date).add(message);
		}
	}

	/**
	 * Returns a list of meeting messages for a specific date.
	 *
	 * @param date the date to retrieve messages for
	 * @return a list of meeting strings (or an empty list if none exist)
	 */
	public ArrayList<String> getMeetingsForDate(Calendar date) {
		return meetings.getOrDefault(date, new ArrayList<>());
	}

	public void updateMeetings(Calendar date, ArrayList<String> updatedList) {
		meetings.put(date, updatedList);
	}

}
