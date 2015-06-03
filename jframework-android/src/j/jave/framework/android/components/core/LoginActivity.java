package j.jave.framework.android.components.core;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import j.jave.framework.android.activity.BgActivity;
import j.jave.framework.android.model.MobileResult;
import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.http.JHttpFactory;
import j.jave.framework.json.JJSON;
import j.jave.framework.logging.JLoggerFactory;
import j.jave.framework.utils.JStringUtils;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AbstractBaseActivity {

	private JLogger LOGGER=JLoggerFactory.getLogger("login-activity");
	
	
	@Override 
    public void createView(Bundle savedInstanceState) { 
        setContentView(R.layout.login); 
        
       Button button= (Button) findViewById(R.id.submit);
       
       button.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			EditText uesNameText=(EditText) findViewById(R.id.userName); 
			final String userName=uesNameText.getEditableText().toString();
			EditText passwordText=(EditText) findViewById(R.id.password); 
			final String password=passwordText.getEditableText().toString();

			
			
			try{
				final String url="http://192.168.0.100:8686/youapp/mobile/service/dispatch/mobile.login.loginaction/login";
				
				new AsyncTask<String, Integer, MobileResult>(){
					@Override
					protected MobileResult doInBackground(String... params) {
						try{
							JHttpBase<?> post=JHttpFactory.getHttpGet()
							.setUrl(url).putParam("user.userName", userName)
							.putParam("user.password", password);
							
				             String page = (String) post.execute();
				             MobileResult mobileResult=JJSON.get().parse(page, MobileResult.class);
				             return mobileResult;
				             
						}catch(Exception e){
							e.printStackTrace();
						}
						return null;
					}
					
					protected void onPostExecute(MobileResult result) {
						
						try{
							String s=JStringUtils.bytestoBASE64String("abc".getBytes());
							
							byte[] bys= JStringUtils.base64stringtobytes(s);
							
							String orig=new String(bys);
						}catch(Exception e){
							e.printStackTrace();
						}
						
						DisplayMetrics metrics=new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(metrics);

						if(result==null){
								showError("cannot connect to "+url,new AlertCallback() {
								@Override
								public void postExecute() {
									Intent intent = new Intent();
									intent.setClass(LoginActivity.this, MainActivity.class);
									startActivity(intent);
									LoginActivity.this.finish();
								}
							});
							return ;
						}
						else{
							if("0".equals(result.getStatus())){
								showError(String.valueOf(result.getData()));
							}
							else{
								showError("login success!",new AlertCallback() {
									@Override
									public void postExecute() {
										Intent intent = new Intent();
										intent.setClass(LoginActivity.this, MainActivity.class);
										startActivity(intent);
										LoginActivity.this.finish();
									}
								});
								
							}
						}
						 
						
					};
					
				}.execute(url);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
       
      
       
    } 
	
	
    @Override  
    protected void onDestroy() {  
    	LOGGER.trace("destroy");  
        super.onDestroy();  
    }  
  
    @Override  
    protected void onPause() {  
    	LOGGER.trace("pause");  
        super.onPause();  
    }  
  
    @Override  
    protected void onResume() {  
    	LOGGER.trace("resume");  
        super.onResume();  
    }  
  
    @Override  
    protected void onStart() {  
    	LOGGER.trace( "start");  
        super.onStart();  
    }  
  
    @Override  
    protected void onStop() {  
    	LOGGER.trace("stop");  
        super.onStop();  
    }  
	
}
