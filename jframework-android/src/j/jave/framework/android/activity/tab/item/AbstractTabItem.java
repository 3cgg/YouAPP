package j.jave.framework.android.activity.tab.item;

import j.jave.framework.android.activity.AbstractFragment;
import j.jave.framework.android.activity.tab.AbstractInnerFragmentForTab;
import j.jave.framework.android.activity.tab.TabFragment;
import j.jave.framework.android.activity.tab.TabFragment.TabItem;
import j.jave.framework.exception.JOperationNotSupportedException;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

public abstract class AbstractTabItem implements TabItem {

	protected final TabFragment tabFragment;
	protected final Activity activity;
	
	protected int defaultBackGroundColor;
	
	public AbstractTabItem( Activity activity, TabFragment tabFragment){
		this.tabFragment=tabFragment;
		this.activity=activity;
	}
	
	@Override
	public  AbstractFragment onClickLoad(View view) {
		if(isLoadFragment()){
			return getFragment();
		}
		else{
			throw new JOperationNotSupportedException("sub-class is not for loading fragment.");
		}
	}

	@Override
	public final TabItem getItem() {
		return this;
	}

	protected boolean isLoadFragment() {
		return true;
	}
	
	protected AbstractInnerFragmentForTab getFragment() {
		throw new JOperationNotSupportedException("sub-class implements this.");
	}
	
	@Override
	public void init() {
		LinearLayout layout= (LinearLayout) tabFragment.getInnerView(id());
		int count=layout.getChildCount();
		for(int i=0;i<count;i++){
			layout.getChildAt(i).setClickable(false);
		}
		layout.setOnClickListener(tabFragment.registOnClickCallback(this, layout));
		
		Drawable drawable=layout.getBackground();
		if(drawable instanceof ColorDrawable){
			defaultBackGroundColor=((ColorDrawable)drawable).getColor();
		}
		else{
			
		}
	}

	
	@Override
	public void resetBackground() {
		activity.findViewById(id()).setBackgroundColor(defaultBackGroundColor);
	}
	
}
