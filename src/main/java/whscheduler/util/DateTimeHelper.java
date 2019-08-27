package whscheduler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

	private final static SimpleDateFormat DF_D_MM_YYYY = new SimpleDateFormat("dd MMMM, yyyy");
	
	public static String getDateTimeFromLong(long date) {
		String dateTime = "";
		if (date > 0) {
			try {
				dateTime = DF_D_MM_YYYY.format(new Date(date));
				if (dateTime == null || dateTime.length() == 0
						|| dateTime.equals("01/01/1970")) {
					dateTime = "";
				}
			} catch (RuntimeException ex) {
				
			}
		}
		return dateTime;
	}
	public static void main(String args[]) {
		System.out.println(DateTimeHelper.getDateTimeFromLong(1545177600000L));
	}
}