package j.jave.framework.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class JAbstractAdapter extends BaseAdapter {

	private final Context context;
	
	protected final List<View> views=new ArrayList<View>();

	public JAbstractAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object getItem(int position) {
		return views.get(position);
	}

	@Override
	public long getItemId(int position) {
		return views.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return views.get(position);
	}

	public void addItem(View view){
		views.add(view);
	}

	/**
	 * convert to {@link AbstractBaseActivity} if possible.
	 * @return 
	 * @see ClassCastException
	 */
	protected final AbstractBaseActivity getActivity(){
		return (AbstractBaseActivity) context;
	}
	
	protected final LayoutInflater getLayoutInflater(){
		 return (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
	}
	
}
