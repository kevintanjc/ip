package resources.util.parsers;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for converting between String representations of dates and LocalDateTime objects.
 * <p>
 * The {@link DateTimeUtil} class provides static methods to convert date strings in specific formats
 * to {@link LocalDateTime} objects and vice versa. It also handles formatting and parsing exceptions.
 * <p>
 * Supported formats:
 * <ul>
 *     <li>Input format: "dd/MM/yyyy HHmm" (e.g., "01/01/1990 1800")</li>
 *     <li>Output format: "MMM dd yyyy, h:mm a" (e.g., "Jan 01 1990, 6:00 PM")</li>
 * </ul>
 *
 * @author Kevin Tan
 */
public class DateTimeUtil {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DateTimeUtil() {}
    /**
     * Converts a date string in the format "dd/MM/yyyy HHmm" to a {@link LocalDateTime} object.
     * @param dateStr the date string to convert.
     * @return {@code LocalDateTime} — the corresponding LocalDateTime object.
     * @throws DateTimeParseException if the input string is not in the expected format.
     */
    public static LocalDateTime convertStringToLocalDate(String dateStr) throws DateTimeParseException {
        String[] parts = dateStr.split(" ");
        String time = parts[1].substring(0, 2) + ":" + parts[1].substring(2, 4);
        return LocalDateTime.parse(parts[0] + " " + time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    /**
     * Converts a {@link LocalDateTime} object to a formatted date string in the format "MMM dd yyyy, h:mm a".
     * @param date the LocalDateTime object to convert.
     * @return {@code String} — the formatted date string.
     */
    public static String convertLocalDateToFormattedString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"));
    }
    /**
     * Converts a formatted date string in the format "MMM dd yyyy, h:mm a" to a {@link LocalDateTime} object.
     * <p>
     * If the input string is null or equals "No date given", the method returns null.
     * @param dateStr the formatted date string to convert.
     * @return {@code LocalDateTime} — the corresponding LocalDateTime object, or null if input is invalid.
     * @throws DateTimeParseException if the input string is not in the expected format.
     */
    public static LocalDateTime convertFormattedStringDateToLocalDate(String dateStr) throws DateTimeParseException {
        if (isNull(dateStr) || dateStr.equals("No date given")) {
            return null;
        }
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"));
    }
}
