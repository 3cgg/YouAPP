package j.jave.framework.android.components.core;

import j.jave.framework.android.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private final Context context;

	public ImageAdapter(Context context) {
		this.context = context;
	}

	private List<Integer>images=new ArrayList<Integer>();
	
	private List<Integer> texts=new ArrayList<Integer>() ;

	private List<OnClickListener> clickListeners=new ArrayList<View.OnClickListener>();
	
	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	// get the current selector's id number
	@Override
	public long getItemId(int position) {
		return position;
	}

	// create view method
	@Override
	public View getView(int position, View view, ViewGroup viewgroup) {
		ImgTextWrapper wrapper;
		if (view == null) {
			wrapper = new ImgTextWrapper();
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.ninegrid_item, null);
			view.setTag(wrapper);
		} else {
			wrapper = (ImgTextWrapper) view.getTag();
		}

		wrapper.imageView = (ImageView) view
				.findViewById(R.id.nineGridImage);
		wrapper.imageView.setBackgroundResource(images.get(position));
		wrapper.textView = (TextView) view.findViewById(R.id.nineGridText);
		wrapper.textView.setText(texts.get(position));
		view.setOnClickListener(clickListeners.get(position));
		return view;
	}

	class ImgTextWrapper {
		ImageView imageView;
		TextView textView;
	}

	public void addItem(int image, int text, OnClickListener clickListener) {
		images.add(image);
		texts.add(text);
		clickListeners.add(clickListener);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}









