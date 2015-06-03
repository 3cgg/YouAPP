package j.jave.framework.android.activity;

import j.jave.framework.android.R;
import j.jave.framework.android.util.JAndroidUtils;
import android.os.Bundle;
import android.util.Log;

public class BgActivity extends AbstractBaseActivity {

	
	@Override 
    public void createView(Bundle savedInstanceState) { 
        setContentView(R.layout.bg); 
      //背景自动适应
      JAndroidUtils.AutoBackground(this,findViewById(R.id.bg) , R.drawable.background_2, R.drawable.background_2);
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
