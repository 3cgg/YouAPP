package j.jave.framework.android.components.demo;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import android.os.Bundle;
import android.widget.GridView;

public class DemoMain extends AbstractBaseActivity {

	@Override
	protected void createView(Bundle savedInstanceState) {
		setContentView(R.layout.demo_main);
		GridView gridView= (GridView) findViewById(R.id.demo_main_gridview);
		gridView.setAdapter(new DemoAdapter(this));
	}

}
