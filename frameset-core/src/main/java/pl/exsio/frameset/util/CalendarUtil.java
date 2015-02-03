/* 
 * The MIT License
 *
 * Copyright 2014 exsio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.exsio.frameset.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author exsio
 */
public class CalendarUtil implements Serializable {

    public static final String DATE_START = "start";

    public static final String DATE_END = "stop";

    public static Map<String, Date> getWeekRange(Date date, Locale locale) {
        java.util.Calendar c = java.util.Calendar.getInstance(locale);
        c.setTime(date);
        int i = c.get(java.util.Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(java.util.Calendar.DATE, -i);
        final Date start = c.getTime();
        c.add(java.util.Calendar.DATE, 6);
        final Date end = c.getTime();
        return new HashMap() {
            {
                put(DATE_START, start);
                put(DATE_END, end);
            }
        };
    }

    public static Map<String, Date> getMonthRange(Date date, Locale locale) {
        java.util.Calendar c = java.util.Calendar.getInstance(locale);
        c.setTime(date);
        c.set(java.util.Calendar.DAY_OF_MONTH, 1);
        final Date start = c.getTime();
        c.add(java.util.Calendar.MONTH, 1);
        c.add(java.util.Calendar.DATE, -1);
        final Date end = c.getTime();
        return new HashMap() {
            {
                put(DATE_START, start);
                put(DATE_END, end);
            }
        };
    }

    public static Date addDays(Date date, int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.add(java.util.Calendar.DATE, days);
        return c.getTime();
    }

    public static String getDateTimeFormat(Locale locale) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale);
        String pattern = ((SimpleDateFormat) dateFormat).toPattern();
        return pattern;
    }
    
    public static String getDateFormat(Locale locale) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        String pattern = ((SimpleDateFormat) dateFormat).toPattern();
        return pattern;
    }
}
