import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * CalendarLogic provides static utility methods for: - formatting and parsing
 * calendar dates - generating calendar labels - date comparisons (same
 * day/month) - calculating weekday offsets for calendar grid alignment
 * 
 * This class contains no state and is used as a helper for CalendarManager and
 * UI.
 */
public class CalendarLogic {
	public static final int TOTAL_CELLS = 42;
	public static final int DAYS_IN_WEEK = 7;
	public static final int FIRST_DAY_OFFSET = 6;

	/**
	 * Calculates how many days must be added before the first of the month to align
	 * it with Sunday. This ensures the calendar grid starts correctly.
	 *
	 * @param firstDay the Calendar object set to the first of the month
	 * @return number of leading empty days (0 to 6)
	 */
	public static int calculateLeadingMissing(Calendar firstDay) {
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek + FIRST_DAY_OFFSET) % DAYS_IN_WEEK;
	}

	/**
	 * Parses a date string in Hebrew locale format (e.g., "2.04.2025") and returns
	 * a corresponding Calendar object.
	 *
	 * @param dateText the input date string (in format d.MM.yyyy)
	 * @return Calendar object representing the parsed date
	 * @throws ParseException if the date format is invalid
	 */
	public static Calendar parseDateFromText(String dateText) throws ParseException {
		@SuppressWarnings("deprecation")
		SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy", new Locale("he", "IL"));
		Date date = sdf.parse(dateText);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Formats a Calendar object into a short string format (e.g., "02.04.2025").
	 *
	 * @param calendar the Calendar object to format
	 * @return formatted date string
	 */
	public static String formatShortDate(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(calendar.getTime());
	}

	/**
	 * Formats the label for a calendar day button. - If the day is the 1st of the
	 * month, it includes the Hebrew month name. - Always includes the day of the
	 * month, with leading 0 if necessary.
	 *
	 * @param day the Calendar object representing the day
	 * @return formatted button label (e.g., "אפריל 01")
	 */
	public static String formatDayLabel(Calendar day) {
		StringBuilder btnInput = new StringBuilder();
		@SuppressWarnings("deprecation")
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("he", "IL")); // Hebrew month name

		if (day.get(Calendar.DAY_OF_MONTH) == 1) {// Add month name before day number
			btnInput.append(sdf.format(day.getTime())).append("  ");
		}

		if (day.get(Calendar.DAY_OF_MONTH) < 10) {
			btnInput.append("0"); // Leading 0 for single-digit days
		}

		btnInput.append(day.get(Calendar.DAY_OF_MONTH));
		return btnInput.toString();
	}

	/**
	 * Checks if two Calendar objects represent the same calendar day.
	 *
	 * @param a first Calendar object
	 * @param b second Calendar object
	 * @return true if both represent the same year, month, and day
	 */
	public static boolean isSameDate(Calendar a, Calendar b) {
		return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) && a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
				&& a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Checks if two Calendar objects represent dates in the same month and year.
	 *
	 * @param a first Calendar object
	 * @param b second Calendar object
	 * @return true if both are in the same month and year
	 */
	public static boolean isSameMonth(Calendar a, Calendar b) {
		return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) && a.get(Calendar.MONTH) == b.get(Calendar.MONTH);
	}

	/**
	 * Formats a Calendar date as "Month Year" in Hebrew (e.g., "אפריל 2025"). Used
	 * in the calendar title/header.
	 *
	 * @param calendar the Calendar object to format
	 * @return formatted string in Hebrew locale
	 */
	public static String formatFullHebrewDate(Calendar calendar) {
		@SuppressWarnings("deprecation")
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", new Locale("he", "IL"));
		return sdf.format(calendar.getTime());
	}

}
