package j.jave.framework.android.components.core;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AbstractBaseActivity {

	private FragmentManager fragmentManager;

	@Override
	protected void createView(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		fragmentManager = getFragmentManager();
		FrameLayout frameLayout= (FrameLayout) findViewById(R.id.main_content);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.main_content, new NineGridFragment(this,frameLayout));
		transaction.commit();
	}

}
