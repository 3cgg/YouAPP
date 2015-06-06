package j.jave.framework.android.components;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import j.jave.framework.android.base64.JAndroidBase64Factory;
import j.jave.framework.android.components.core.LoginActivity;
import j.jave.framework.android.http.JAndroidHttpFactory;
import j.jave.framework.android.logging.JAndroidLoggerFactory;
import j.jave.framework.context.JContext;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class EntranceActivity extends AbstractBaseActivity {

	@Override
	protected void createView(Bundle savedInstanceState) {
		setContentView(R.layout.entrance);
		final ProgressBar progressBar = (ProgressBar) findViewById(R.id.entrance_bar);  
		progressBar.setMax(3);
		new AsyncTask<Object, Integer, Object>() {
			protected Object doInBackground(Object... params) {
				int time=0;
				JContext.get().getLoggerFactoryProvider().setLoggerFactory(JAndroidLoggerFactory.class);
				JContext.get().getBase64FactoryProvider().setBase64Factory(JAndroidBase64Factory.class);
				JContext.get().getHttpFactoryProvider().setHttpFactory(JAndroidHttpFactory.get());
				while(time<3){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					time++;
					publishProgress(time);
				}
				return null;
			}

			protected void onProgressUpdate(Integer... progress) {
				progressBar.setProgress(progress[0]);
		     }

			protected void onPostExecute(Object result) {
				Intent intent=new Intent();
				intent.setClass(EntranceActivity.this, LoginActivity.class);
				startActivity(intent); 
				EntranceActivity.this.finish();
			};
		     
		}.execute();

		System.out.println("end");
		
	}

}
