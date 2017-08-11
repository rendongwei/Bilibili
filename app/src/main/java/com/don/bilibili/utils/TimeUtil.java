package com.don.bilibili.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "hh:mm";
	public final static String FORMAT_TIME_24 = "HH:mm";
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm";
	public final static String FORMAT_DATE_TIME_24 = "yyyy-MM-dd HH:mm";
	public final static String FORMAT_MONTH_DAY_TIME = "MM-dd hh:mm";
	public final static String FORMAT_MONTH_DAY_TIME_24 = "MM-dd HH:mm";
	private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat();
	private static final int YEAR = 365 * 24 * 60 * 60;
	private static final int MONTH = 30 * 24 * 60 * 60;
	private static final int DAY = 24 * 60 * 60;
	private static final int HOUR = 60 * 60;
	private static final int MINUTE = 60;

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + "年前";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + "个月前";
		} else if (timeGap > DAY) {
			timeStr = timeGap / DAY + "天前";
		} else if (timeGap > HOUR) {
			timeStr = timeGap / HOUR + "小时前";
		} else if (timeGap > MINUTE) {
			timeStr = timeGap / MINUTE + "分钟前";
		} else {
			timeStr = "刚刚";
		}
		return timeStr;

	}

	/**
	 * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @param format
	 *            指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getFormatTimeFromTimestamp(long timestamp,
			String format) {
		if (EmptyUtil.isEmpty(format)) {
			mSimpleDateFormat.applyPattern(FORMAT_DATE);
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int year = Integer.valueOf(mSimpleDateFormat.format(
					new Date(timestamp)).substring(0, 4));
			if (currentYear == year) {
				mSimpleDateFormat.applyPattern(FORMAT_MONTH_DAY_TIME);
			} else {
				mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
			}
		} else {
			mSimpleDateFormat.applyPattern(format);
		}
		Date date = new Date(timestamp);
		return mSimpleDateFormat.format(date);
	}

	/**
	 * 根据时间戳获取时间字符串，并根据指定的时间分割数partionSeconds来自动判断返回描述性时间还是指定格式的时间
	 * 
	 * @param timestamp
	 *            时间戳 单位是毫秒
	 * @param partionSeconds
	 *            时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
	 * @param format
	 * @return
	 */
	public static String getMixTimeFromTimestamp(long timestamp,
			long partionSeconds, String format) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;
		if (timeGap <= partionSeconds) {
			return getDescriptionTimeFromTimestamp(timestamp);
		} else {
			return getFormatTimeFromTimestamp(timestamp, format);
		}
	}

	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		if (EmptyUtil.isEmpty(format)) {
			mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
		} else {
			mSimpleDateFormat.applyPattern(format);
		}
		return mSimpleDateFormat.format(new Date());
	}

	/**
	 * 将日期字符串以指定格式转换为Date
	 * 
	 * @param time
	 *            日期字符串
	 * @param format
	 *            指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static Date getTimeFromString(String timeStr, String format) {
		if (EmptyUtil.isEmpty(format)) {
			mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
		} else {
			mSimpleDateFormat.applyPattern(format);
		}
		try {
			return mSimpleDateFormat.parse(timeStr);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * 将Date以指定格式转换为日期时间字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getStringFromTime(Date time, String format) {
		if (EmptyUtil.isEmpty(format)) {
			mSimpleDateFormat.applyPattern(FORMAT_DATE_TIME);
		} else {
			mSimpleDateFormat.applyPattern(format);
		}
		return mSimpleDateFormat.format(time);
	}

}
