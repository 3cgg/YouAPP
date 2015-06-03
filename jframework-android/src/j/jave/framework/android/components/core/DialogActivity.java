package j.jave.framework.android.components.core;

import j.jave.framework.android.R;
import android.app.Activity;
import android.os.Bundle;

public class DialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog); 
	}
}
