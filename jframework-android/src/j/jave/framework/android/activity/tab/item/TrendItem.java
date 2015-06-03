package j.jave.framework.android.activity.tab.item;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import j.jave.framework.android.R;
import j.jave.framework.android.activity.tab.AbstractInnerFragmentForTab;
import j.jave.framework.android.activity.tab.TabFragment;


public class TrendItem extends AbstractTabItem {

	public TrendItem(Activity activity, TabFragment tabFragment) {
		super(activity, tabFragment);
	}

	public static class TrendFragment extends AbstractInnerFragmentForTab {

		 public TrendFragment(Activity activity, FrameLayout frameLayout) {
			super(activity, frameLayout);
		}

		@Override
		public View createView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.i_am_trend, null);
		}
		
		@Override
		public int getResource() {
			return R.layout.i_am_trend;
		}
		
	}
	

	@Override
	public AbstractInnerFragmentForTab getFragment() {
		return new TrendFragment(activity,(FrameLayout) tabFragment.getInnerView(R.id.module_tab_content));
	}

	@Override
	public int id() {
		return R.id.module_tab_item_trend;
	}
	
	
}
