package j.jave.framework.android.components.core;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractFragment;
import j.jave.framework.android.components.demo.DemoMain;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

public class NineGridFragment extends AbstractFragment {

	public NineGridFragment(Activity activity, FrameLayout frameLayout) {
		super(activity, frameLayout);
	}


	@Override
	public View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.ninegrid, null);
		GridView gridView=(GridView) view.findViewById(R.id.nineGrid_gridview);
		ImageAdapter imageAdapter= new ImageAdapter(getActivity());
		initItems(imageAdapter);
        gridView.setAdapter(imageAdapter);
		return view;
	}
	
	
	private void initItems(ImageAdapter imageAdapter){
		imageAdapter.addItem(R.drawable.sample, R.string.demo, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(activity, DemoMain.class);
				startActivity(intent);
			}
		});
	}
    
	@Override
	public int getResource() {
		return R.layout.ninegrid;
	}
	
}
