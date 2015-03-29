package j.jave.framework.android.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

public abstract class AbstractBaseActivity extends Activity {
	

	/**
	 * show message . 
	 * @param message
	 */
	protected void showError(String message) {
		final Dialog dialog=new AlertDialog.Builder(AbstractBaseActivity.this)
		.setCancelable(true)
		.setInverseBackgroundForced(true)
		.setMessage(message)
		.show();
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				dialog.dismiss();
				timer.cancel();
			}
		};
		timer.schedule(task, 2*1000);
	}

}
