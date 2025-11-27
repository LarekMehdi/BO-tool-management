package fr.mehdi.tool_management.utils;

import java.util.List;
import java.util.Objects;

import fr.mehdi.tool_management.usageLog.UsageLog;

public abstract class UtilMetrics {

    /** SESSION **/

    public static int computeAvgSessionMinutes(List<UsageLog> logs) {
        return (int) logs.stream().map(UsageLog::getUsageMinutes).filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(0);
    }
    
}
