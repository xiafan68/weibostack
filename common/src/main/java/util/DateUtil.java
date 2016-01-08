package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static final int HOUR_GRANU = 3600 * 1000;
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static final long WEIBO_DATE = 1230739200000l;

	public static long getWeek(long timestamp) {
		return getWeek(new Date(timestamp)).getTime();
	}

	public static Date getWeek(Date cur) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		// System.out.println(cur);
		cal.setTime(cur);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DAY_OF_WEEK, 0 - (dayWeek - 1));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static int diffByWeiboStartTime(long timestamp) {
		return (int) ((timestamp - WEIBO_DATE) / HOUR_GRANU);
	}

	public static long timeFromWeiboDate(int hourGap) {
		return WEIBO_DATE + hourGap * HOUR_GRANU;
	}

	public static long roundByHour(long timestamp) {
		return roundByHour(new Date(timestamp)).getTime();
	}

	public static Date roundByHour(Date cur) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		// System.out.println(cur);
		cal.setTime(cur);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(roundByHour(format.parse("2009-01-01 00:00:00").getTime()));
		for (int i = 0; i < 7; i++) {
			Date cur = new Date(2015 - 1900, 8, 1 + i);
			System.out.println(cur);
			System.out.println(getWeek(cur));
		}

		for (int i = 0; i < 7; i++) {
			Date cur = new Date(2015 - 1900, 8, 1, i, i, i);
			System.out.println(getWeek(cur));
		}

		Date cur = new Date(1327208400000l);
		System.out.println(cur);
		Calendar cal = Calendar.getInstance();
		cal.setTime(cur);
		/*
		 * for (int i = 0; i < 1000000; i++) { cal.add(Calendar.SECOND, 1); Date
		 * newDate = getWeek(cal.getTime());
		 * Assert.assertEquals(newDate.getDay(), 0);
		 * System.out.println(getWeek(cal.getTime())); }
		 */
		cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		cur = new Date(2015 - 1900, 8, 1, 8, 1, 1);
		cal.setTime(cur);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DAY_OF_WEEK, 0 - (dayWeek - 1));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.println(cal.getTime());

		// gmt
		cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cur = new Date(2015 - 1900, 8, 6, 8, 1, 1);
		cal.setTime(cur);
		System.out.println(cal.getTime());
		dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		System.out.println(dayWeek);
		cal.add(Calendar.DAY_OF_WEEK, 0 - (dayWeek - 1));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.println(cal.getTime());
		System.out.println(TimeZone.getDefault());
	}
}
