package j.jave.framework.android.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class JAndroidUtils {

	// 获取屏幕方向 1, 竖屏，0横屏
	public static int screenOrient(Activity activity) {
		int orient = activity.getRequestedOrientation();
		if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				&& orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			// 宽>高为横屏,反正为竖屏
			WindowManager windowManager = activity.getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
					: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		return orient;
	}

	public static void AutoBackground(Activity activity, View view,
			int Background_v, int Background_h) {
		int orient = screenOrient(activity);
		if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { // 纵向
			view.setBackgroundResource(Background_v);
		} else { // 横向
			view.setBackgroundResource(Background_h);
		}
	}
	
	
	public static DisplayMetrics getDP(Activity activity){
		 DisplayMetrics metrics = new DisplayMetrics();
		 activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 return metrics;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
