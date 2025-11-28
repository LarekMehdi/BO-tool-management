package fr.mehdi.tool_management.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class UtilNumber {

    /** BIG DECIMAL **/

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

    public static BigDecimal divideBigDecimal(BigDecimal value1, BigDecimal value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2) || BigDecimal.ZERO.compareTo(value2) == 0) return BigDecimal.ZERO;
        return value1.divide(value2, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divideBigDecimal(BigDecimal value1, Integer value2) {
        if (UtilEntity.isEmpty(value1) || UtilEntity.isEmpty(value2) || BigDecimal.ZERO.compareTo(BigDecimal.valueOf(value2)) == 0) return BigDecimal.ZERO;
        return value1.divide(BigDecimal.valueOf(value2), 2, RoundingMode.HALF_UP);
    }
    
}
