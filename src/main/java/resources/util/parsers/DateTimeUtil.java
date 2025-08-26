package resources.util.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.isNull;


public class DateTimeUtil {

    public static LocalDateTime convertStringToLocalDate(String dateStr) throws DateTimeParseException {
        String[] parts = dateStr.split(" ");
        String time = parts[1].substring(0,2) + ":" + parts[1].substring(2,4);
        return LocalDateTime.parse(parts[0] + " " + time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public static String convertLocalDateToFormattedString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"));
    }

    public static LocalDateTime convertFormattedStringDateToLocalDate(String dateStr) throws DateTimeParseException {
        if (isNull(dateStr) || dateStr.equals("No date given")) {
            return null;
        }
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a"));
    }

    private DateTimeUtil() {}

}
