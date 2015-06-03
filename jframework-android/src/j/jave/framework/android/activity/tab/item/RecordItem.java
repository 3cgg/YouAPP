package j.jave.framework.android.activity.tab.item;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.tab.AbstractInnerFragmentForTab;
import j.jave.framework.android.activity.tab.TabFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class RecordItem extends AbstractTabItem {

	public RecordItem(Activity activity, TabFragment tabFragment) {
		super(activity, tabFragment);
	}

	public  static class RecordFragment extends AbstractInnerFragmentForTab {
		 public RecordFragment(Activity activity, FrameLayout frameLayout) {
			super(activity, frameLayout);
		}

		@Override
		public View createView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			 return inflater.inflate(R.layout.i_am_record, null);
		}
		
		@Override
		public int getResource() {
			return R.layout.i_am_record;
		}
	}

	@Override
	public AbstractInnerFragmentForTab getFragment() {
		return new RecordFragment(activity, (FrameLayout) tabFragment.getInnerView(R.id.module_tab_content));
	}

	
	
	@Override
	public void init() {
		super.init();
	}

	@Override
	public int id() {
		return R.id.module_tab_item_record;
	}
	
	
}
