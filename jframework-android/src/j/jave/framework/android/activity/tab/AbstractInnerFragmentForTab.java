package j.jave.framework.android.activity.tab;

import j.jave.framework.android.activity.AbstractFragment;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

public abstract class AbstractInnerFragmentForTab extends AbstractFragment
		implements TabFragment.OnClickLoadListener {


	public AbstractInnerFragmentForTab(Activity activity,
			FrameLayout frameLayout) {
		super(activity, frameLayout);
	}

	@Override
	public AbstractFragment onClickLoad(View v) {
		return this;
	}
}
