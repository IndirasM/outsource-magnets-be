package com.psk.demo.Helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	public static boolean isSooner(String date1, String date2) {
		return date1.compareTo(date2) < 0;
	}

	public static boolean isLater(String date1, String date2) {
		return date1.compareTo(date2) > 0;
	}

	public static String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static boolean validFormat(String date) {
		boolean isValid = true;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate = dateFormat.parse(date);
			isValid = formatDate(parsedDate).compareTo(date) == 0;
		} catch (Exception e) {
			isValid = false;
		}
		return isValid;
	}

	public static Date getDate(String formattedDate) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
	}
}
