package j.jave.framework.android.components.demo;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class DemoToast extends AbstractBaseActivity {

	@Override
	protected void createView(Bundle savedInstanceState) {
		setContentView(R.layout.demo_toast);
		
		View view=findViewById(R.id.demo_toast_default_short);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(DemoToast.this, "click short"+R.id.demo_toast_default_short, Toast.LENGTH_SHORT).show();
			}
		});
		
		view=findViewById(R.id.demo_toast_default_long);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(DemoToast.this, "click long"+R.id.demo_toast_default_long, Toast.LENGTH_LONG).show();
			}
		});
		
		view=findViewById(R.id.demo_toast_center);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast toast=Toast.makeText(DemoToast.this, "click center"+R.id.demo_toast_center, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
		});
		
	}
	

}
