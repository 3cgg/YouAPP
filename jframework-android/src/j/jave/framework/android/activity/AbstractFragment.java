package j.jave.framework.android.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * extends from super-fragment.
 * @author J
 *
 */
public abstract class AbstractFragment extends Fragment{

	/**
	 * the activity that the fragment is tied to.
	 */
	protected final Activity activity;
	
	/**
	 * specially, the layout the fragment locates at .
	 */
	protected final FrameLayout frameLayout;
	
	/**
	 * the fragment manager of the activity.
	 */
	protected final FragmentManager fragmentManager;
	
	/**
	 * get the source resource id (the file locates in layout directory) from which the fragment is.  
	 * @return
	 */
	public abstract  int getResource();
	
	
	public AbstractFragment(Activity activity, FrameLayout frameLayout) {
		this.activity=activity;
		this.frameLayout=frameLayout;
		fragmentManager = this.activity.getFragmentManager();
	}
	
	/**
	 * switch fragment .
	 * @param fragment
	 */
	public void switchTo(AbstractFragment fragment){
		if(fragment!=null){
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(frameLayout.getId(), fragment);
			transaction.commit();
		}
	}
	
	/**
	 * get view in the fragment tied to a certain fragment layout.
	 * @param id
	 * @return
	 */
	public View getInnerView(int id){
		return LayoutInflater.from(activity).inflate(getResource(), null).findViewById(id);
	}
	
	/**
	 * get any view from the activity source.
	 * @param id
	 * @return
	 */
	public View getView(int id){
		return activity.findViewById(id);
	}
	
	
	
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return createView(inflater, container, savedInstanceState);
	}
	
	/**
	 * called by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
	 * <p>initialize any view that is fragment in any fragment layout..
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	public abstract View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	
	
	@Override
	public void onDestroyOptionsMenu() {
		super.onDestroyOptionsMenu();
		System.out.println("onDestroyOptionsMenu....");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.out.println("onDestroyView....");
	}
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
    	System.out.println("onCreate....");
    } 
	
    @Override  
    public void onDestroy() {  
        Log.v("--", "destroy");  
        super.onDestroy();  
    }  
  
    @Override  
    public void onPause() {  
        Log.v("--", "pause");  
        super.onPause();  
    }  
  
    @Override  
    public void onResume() {  
        Log.v("--", "resume");  
        super.onResume();  
    }  
  
    @Override  
    public void onStart() {  
        Log.v("--", "start");  
        super.onStart();  
    }  
  
    @Override  
    public void onStop() {  
        Log.v("--", "stop");  
        super.onStop();  
    }  
    
}
