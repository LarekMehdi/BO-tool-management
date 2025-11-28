package fr.mehdi.tool_management.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class UtilNumber {

    /** BIG DECIMAL **/

    // multiply

    public static BigDecimal multiplyBigDecimal(BigDecimal value1, BigDecimal value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2)) return BigDecimal.ZERO;
        return value1.multiply(value2);
    }

    public static BigDecimal multiplyBigDecimal(BigDecimal value1, Integer value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2)) return BigDecimal.ZERO;
        return value1.multiply(BigDecimal.valueOf(value2));
    }

    public static BigDecimal multiplyBigDecimal(Integer value1, Integer value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2)) return BigDecimal.ZERO;
        return BigDecimal.valueOf(value1).multiply(BigDecimal.valueOf(value2));
    }

    // divide

    public static BigDecimal divideBigDecimal(BigDecimal value1, BigDecimal value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2) || BigDecimal.ZERO.compareTo(value2) == 0) return BigDecimal.ZERO;
        return value1.divide(value2, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divideBigDecimal(BigDecimal value1, Integer value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2) || BigDecimal.ZERO.compareTo(BigDecimal.valueOf(value2)) == 0) return BigDecimal.ZERO;
        return value1.divide(BigDecimal.valueOf(value2), 2, RoundingMode.HALF_UP);
    }

    // substract

    public static BigDecimal substract(Integer value1, BigDecimal value2) {
        return BigDecimal.valueOf(value1).subtract(value2);
    }

    // percentage

    public static BigDecimal computePercentage(BigDecimal part, BigDecimal total) {
        if (UtilEntity.isEmpty(part) || UtilEntity.isEmpty(total) || BigDecimal.ZERO.compareTo(total) == 0) return BigDecimal.ZERO;
        
        BigDecimal hundredTimesPart = multiplyBigDecimal(part, 100);
        return divideBigDecimal(hundredTimesPart, total); 
    }
    
}
