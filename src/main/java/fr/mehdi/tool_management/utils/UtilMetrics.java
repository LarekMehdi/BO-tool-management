package fr.mehdi.tool_management.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import fr.mehdi.tool_management.usageLog.UsageLog;

public abstract class UtilMetrics {

    /** SESSION **/

    public static int computeAvgSessionMinutes(List<UsageLog> logs) {
        return (int) logs.stream().map(UsageLog::getUsageMinutes).filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(0);
    }

    public static void keepSessionsSince(List<UsageLog> logs, LocalDateTime limit) {
        LocalDate date = limit.toLocalDate();
        logs.removeIf(log -> !log.getSessionDate().isAfter(date));
    }
    
}
