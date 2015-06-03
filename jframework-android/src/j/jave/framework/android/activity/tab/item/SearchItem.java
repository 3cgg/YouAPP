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


public class SearchItem extends AbstractTabItem {

	public SearchItem(Activity activity, TabFragment tabFragment) {
		super(activity, tabFragment);
	}

	public static class SearchFragment extends AbstractInnerFragmentForTab {
		 
		 public SearchFragment(Activity activity, FrameLayout frameLayout) {
			super(activity, frameLayout);
		}

		@Override
		public View createView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.i_am_search, null);
		}
		
		@Override
		public int getResource() {
			return R.layout.i_am_search;
		}
		 
	}
	
	

	@Override
	public AbstractInnerFragmentForTab getFragment() {
		return new SearchFragment(activity,(FrameLayout) tabFragment.getInnerView(R.id.module_tab_content));
	}

	@Override
	public int id() {
		return R.id.module_tab_item_search;
	}
	
	
}
