package j.jave.framework.android.activity.tab;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractFragment;
import j.jave.framework.android.activity.tab.item.MoreItem;
import j.jave.framework.android.activity.tab.item.RecordItem;
import j.jave.framework.android.activity.tab.item.SearchItem;
import j.jave.framework.android.activity.tab.item.TrendItem;
import j.jave.framework.support._package.JDefaultPackageScan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class TabFragment extends AbstractFragment {
	
	public TabFragment(Activity activity, FrameLayout frameLayout) {
		super(activity, frameLayout);
	}

	private List<TabItem> tabItems=new ArrayList<TabFragment.TabItem>();
  
	@Override
	public View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.module_tab, null);
		init();
		return view;
	}

	@Override
	public int getResource() {
		return R.layout.module_tab;
	}
	
    /**
     * Interface definition for a callback to be invoked when a view is clicked.
     */
    public interface OnClickLoadListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        AbstractFragment onClickLoad(View view);
    }  
    
    public interface  TabItem extends OnClickLoadListener {
    	TabItem getItem();
		void init();
		int id();
		void resetBackground();
    }
    
    
    private void init(){
    	
    	JDefaultPackageScan classProvidedScan=new JDefaultPackageScan(TabItem.class);
    	classProvidedScan.setIncludePackages(new String[]{"j.jave.framework.android"});
    	Set<Class<?>> classes= classProvidedScan.scan();
    	
    	RecordItem recordItem=new RecordItem(activity, this);
    	recordItem.init();
    	tabItems.add(recordItem);
    	
    	
    	SearchItem searchItem=new SearchItem(activity,this);
    	searchItem.init();
    	tabItems.add(searchItem);
    	
    	
    	TrendItem trendItem=new TrendItem(activity,this);
    	trendItem.init();
    	tabItems.add(trendItem);
    	
    	
    	MoreItem moreItem=new MoreItem(activity,this);
    	moreItem.init();
    	tabItems.add(moreItem);
    	
    }
    
    
   public  class OnClickCallback implements OnClickListener{
	   
	   private TabItem tabItem;
	   private  View view;
	   
	   public OnClickCallback(TabItem tabItem,View view){
		   this.tabItem=tabItem;
		   this.view=view;
	   }
	   
	   @Override
		public void onClick(View v) {
			   onClickListener(tabItem, view);
		}
	   
   }
   
   
   public OnClickCallback registOnClickCallback(TabItem tabItem,View view){
	   return new OnClickCallback(tabItem, view);
   }
    
    
    
    public final void onClickListener(TabItem tabItem,View view){
    	int mycolor = getResources().getColor(R.color.mistyrose);
    	view.setBackgroundColor(mycolor);
    	if(tabItems!=null){
    		for (Iterator<TabItem> iterator = tabItems.iterator(); iterator.hasNext();) {
				TabItem item =  iterator.next();
				if(tabItem!=item){
					item.resetBackground();
				}
			}
    	}
    	AbstractFragment fragment = tabItem.onClickLoad(view);
    	switchTo(fragment);
    }
    
    
    
}
