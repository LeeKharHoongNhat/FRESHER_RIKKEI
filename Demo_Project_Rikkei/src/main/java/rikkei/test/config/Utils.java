package rikkei.test.config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils
 *
 * @author NhatLKH
 *
 */
public class Utils {
    /**
     * formatDateTime
     *
     * @param date (thời gian đầu vào)
     * @return chuỗi sau khi format
     */
    public static String formatDateTime(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
    }

    /**
     * formatDateTimeWithCharacterT
     *
     * @param date (thời gian đầu vào)
     * @return chuỗi sau khi format
     */
    public static String formatDateTimeWithCharacterT(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * formatDate
     *
     * @param date (thời gian đầu vào)
     * @return chuỗi sau khi format
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     * formatDateSort
     *
     * @param date (thời gian đầu vào)
     * @return chuỗi sau khi format
     */
    public static String formatDateSort(Date date) {
        return new SimpleDateFormat("E : d-M").format(date);
    }

    /**
     * formatTime
     *
     * @param date (thời gian đầu vào)
     * @return chuỗi sau khi format
     */
    public static String formatTime(Date date) {
        return new SimpleDateFormat("HH:mm:ss a").format(date);
    }

}
