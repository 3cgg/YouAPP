package j.jave.framework.android.activity;

import j.jave.framework.android.R;
import j.jave.framework.android.components.core.MainActivity;
import j.jave.framework.android.resource.GlobalBackground;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public abstract class AbstractBaseActivity extends Activity {

	private static Executor executor=Executors.newSingleThreadExecutor(new ThreadFactory() {
		
		@Override
		public Thread newThread(final Runnable r) {
			Thread thread=new Thread("demo-return-task"){
				public void run() {
					r.run();
				};
			};
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.setDaemon(true);
			return thread;
		}
	});
	
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GlobalBackground globalBackground= GlobalBackground.get();
		Drawable drawable = getResources().getDrawable(globalBackground.getDynamicBackGround());
		getWindow().setBackgroundDrawable(drawable);
		
		createView(savedInstanceState);
		
		View view=findViewById(R.id.returnimage);
		if(view!=null){
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					executor.execute(new Runnable() {
						@Override
						public void run() {
							Instrumentation inst = new Instrumentation();
							inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
						}
					});
				}
			});
		}
		
	}

	/**
	 * called by {@link #onCreate(Bundle)} .
	 * @param savedInstanceState
	 */
	protected abstract void createView(Bundle savedInstanceState) ;
	
	
	public void showError(String message){
		showError(message, null);
	} 
	
	/**
	 * show message . 
	 * @param message
	 */
	public void showError(String message,final AlertCallback alertCallback) {
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
				if(alertCallback!=null){
					alertCallback.postExecute();
				}
			}
		};
		timer.schedule(task, 2*1000);
	}

	protected interface AlertCallback{
		public void postExecute();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(this.getClass()==MainActivity.class){
			
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				dialog();
				return true;
			}
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AbstractBaseActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// AccoutList.this.finish();
						// System.exit(1);
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	
	
	
	
	
	
	
	
	
}
