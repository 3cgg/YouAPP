package j.jave.framework.android.activity;

import j.jave.framework.android.R;
import j.jave.framework.json.JJSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AbstractBaseActivity {

	
	@Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
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
				String url="http://192.168.0.102:8686/youapp/mobile/service/dispatch/mobile.login.loginaction/login";
				
				new AsyncTask<String, Integer, MobileResult>(){
					@Override
					protected MobileResult doInBackground(String... params) {
						try{
							HttpClient client = new DefaultHttpClient();  
							HttpPost httpPost=new HttpPost(params[0]);

							List<NameValuePair> formParams = new ArrayList<NameValuePair>(); 
							BasicNameValuePair param = new BasicNameValuePair("user.userName",userName); 
							formParams.add(param); 
							param = new BasicNameValuePair("user.password",password); 
							formParams.add(param); 
							httpPost.setEntity(new UrlEncodedFormEntity(formParams,HTTP.UTF_8)); 
							
				            HttpResponse response = client.execute(httpPost); 
				            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));    
				             StringBuffer sb = new StringBuffer("");   
				             String line = "";  
				             String NL = System.getProperty("line.separator");  
				             while((line = in.readLine()) != null){  
				                 sb.append(line + NL); 
				 
				             }  
				             in.close();  
				             String page = sb.toString(); 
				             MobileResult mobileResult=JJSON.get().parse(page, MobileResult.class);
				             return mobileResult;
				             
						}catch(Exception e){
							e.printStackTrace();
						}
						return null;
					}
					
					protected void onPostExecute(MobileResult result) {
						if("0".equals(result.getStatus())){
							showError(String.valueOf(result.getData()));
						}
						else{
							showError("login success!");
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
        Log.v("--", "destroy");  
        super.onDestroy();  
    }  
  
    @Override  
    protected void onPause() {  
        Log.v("--", "pause");  
        super.onPause();  
    }  
  
    @Override  
    protected void onResume() {  
        Log.v("--", "resume");  
        super.onResume();  
    }  
  
    @Override  
    protected void onStart() {  
        Log.v("--", "start");  
        super.onStart();  
    }  
  
    @Override  
    protected void onStop() {  
        Log.v("--", "stop");  
        super.onStop();  
    }  
	
}
