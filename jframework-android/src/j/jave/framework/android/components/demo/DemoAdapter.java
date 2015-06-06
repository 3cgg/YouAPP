package j.jave.framework.android.components.demo;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractBaseActivity;
import j.jave.framework.android.activity.JAbstractAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DemoAdapter extends JAbstractAdapter {

	public DemoAdapter(Context context) {
		super(context);
		init();
	}
	LayoutInflater inflater=null;
	
	private void init(){
		inflater=  getLayoutInflater();
		
		final View view= inflater.inflate(R.layout.demo_item, null);
		final TextView textView= (TextView) view.findViewById(R.id.demo_item_nineGridText);
		textView.setText("Toast");
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AbstractBaseActivity activity= getActivity();
				Intent intent=new Intent();
				intent.setClass(activity, DemoToast.class);
				activity.startActivity(intent);
			}
		});
		addItem(view);

		final View view2= inflater.inflate(R.layout.demo_item, null);
		final TextView textView2= (TextView) view2.findViewById(R.id.demo_item_nineGridText);
		textView2.setText("Spinner");
		view2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AbstractBaseActivity activity= getActivity();
				Intent intent=new Intent();
				intent.setClass(activity, DemoSpinner.class);
				activity.startActivity(intent);
			}
		});
		addItem(view2);
	}
	

}
