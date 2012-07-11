package app.intramuros.fr.vendors;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.util.Log;

public class IMDateFormatter extends DateFormat {
	private static final long serialVersionUID = 7149633839898408526L;
	private static final String TAG = "IMDateFormatter";
	
	public IMDateFormatter() {
		
	}
	
	@Override
	public StringBuffer format(Date arg0, StringBuffer arg1, FieldPosition arg2) {
		return null;
	}
	
	@Override
	public Date parse(String dateFromJson, ParsePosition arg1) {
		return null;
	}
	
	public GregorianCalendar getCalendarFromJson(String dateFromJson) {
		GregorianCalendar calendarFromJson = null;
		
		try {
			GregorianCalendar tmpCalendar = new GregorianCalendar();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
			tmpCalendar.setTime(df.parse(dateFromJson));
			calendarFromJson = tmpCalendar;
			Log.i(TAG, tmpCalendar.toString());
		}
		catch (NullPointerException NPE) {
			NPE.printStackTrace();
		}
		catch (ParseException PE) {
			PE.printStackTrace();
		}
		
		return calendarFromJson;
	}
}
