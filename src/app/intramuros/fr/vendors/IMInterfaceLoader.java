package app.intramuros.fr.vendors;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;

public final class IMInterfaceLoader {
	private static final String TAG = "IM-InterfaceLoader";

	public static HashMap<String, Object> getInterfaceProperties(Context activityContext) {
		HashMap<String, Object> properties = null;
		
		try {
			properties = new HashMap<String, Object>();
			properties.put("tabbar_background", R.drawable.tabbar_background);
			
			User registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", activityContext);

			switch (registredUser.getTeam().getIndice()) {
				case 1:
					properties.put("icon_map", R.drawable.blue_map_icon);
					properties.put("icon_snail", R.drawable.blue_snail_icon);
					properties.put("navbar_background", R.drawable.interfaceblue_navbar_background);
					properties.put("icon_checkin", R.drawable.blue_checkin_icon);
					properties.put("icon_chat", R.drawable.blue_chat_icon);
					properties.put("icon_timeline", R.drawable.blue_timeline_icon);
					properties.put("icon_stats_user", R.drawable.blue_stats_user_icon);
					properties.put("icon_stats_team", R.drawable.blue_stats_team_icon);
					properties.put("icon_settings", R.drawable.blue_settings_icon);
					properties.put("team_color", Color.argb(1, 0, 0, 255));
					break;
				case 2:
					properties.put("icon_map", R.drawable.red_map_icon);
					properties.put("icon_snail", R.drawable.red_snail_icon);
					properties.put("navbar_background", R.drawable.interfacered_navbar_background);
					properties.put("icon_checkin", R.drawable.red_checkin_icon);
					properties.put("icon_chat", R.drawable.red_chat_icon);
					properties.put("icon_timeline", R.drawable.red_timeline_icon);
					properties.put("icon_stats_user", R.drawable.red_stats_user_icon);
					properties.put("icon_stats_team", R.drawable.red_stats_team_icon);
					properties.put("icon_settings", R.drawable.red_settings_icon);
					properties.put("team_color", Color.argb(1, 255, 0, 0));
					break;
				default:
					Log.e(TAG, "Uh Oh, what the fuck is wrong with this Team ? " + registredUser.getTeam().getIndice());
					break;
			}
		}
		catch (NullPointerException NPE) {
			NPE.printStackTrace();
		}
		
		return properties;
	}
}
