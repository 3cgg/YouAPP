package j.jave.framework.android.activity.tab.item;

import j.jave.framework.android.R;
import j.jave.framework.android.activity.AbstractFragment;
import j.jave.framework.android.activity.pop.PopMenu;
import j.jave.framework.android.activity.pop.UserMenu;
import j.jave.framework.android.activity.tab.TabFragment;
import android.app.Activity;
import android.view.View;


public class MoreItem extends AbstractTabItem {
	
	public MoreItem(Activity activity, TabFragment tabFragment) {
		super(activity, tabFragment);
	}


	private static final int USER_SEARCH = 0;  
    private static final int USER_ADD = 1;  
    private UserMenu mMenu;  
      
      
    private void initMenu() {  
        mMenu = new UserMenu(activity);  
        mMenu.addItem("User Search", USER_SEARCH);  
        mMenu.addItem("User Add", USER_ADD);  
        mMenu.setOnItemSelectedListener(new PopMenu.OnItemSelectedListener() {  
            @Override  
            public void selected(View view, PopMenu.Item item, int position) {  
                switch (item.id) {  
                    case USER_SEARCH:  
//                        startActivity(new Intent(getActivity(), UserSearchActivity.class));  
                        break;  
                    case USER_ADD:  
//                        startActivity(new Intent(getActivity(), UserAddActivity.class));  
                        break;  
                }  
            }  
        });  
    }  
    
    

	@Override
	public AbstractFragment onClickLoad(View view) {
		if(mMenu==null){
			initMenu();
		}
		mMenu.showAsDropDown(view);
		return null;
	}
	

	@Override
	protected boolean isLoadFragment() {
		return false;
	}

	
	@Override
	public int id() {
		return R.id.module_tab_item_more;
	}
	
	
}
