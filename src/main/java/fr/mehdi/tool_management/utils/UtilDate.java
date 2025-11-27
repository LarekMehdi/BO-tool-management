package fr.mehdi.tool_management.utils;

import java.time.LocalDateTime;

public abstract class UtilDate {

    /** DAYS **/

    public static LocalDateTime removeDays(LocalDateTime date, long daysToRemove) {
        return date.minusDays(daysToRemove);
    }

    public static LocalDateTime removeDaysFromNow(long daysToRemove) {
        return removeDays(LocalDateTime.now(), daysToRemove);
    }
    
}
