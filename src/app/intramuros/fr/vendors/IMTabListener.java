package app.intramuros.fr.vendors;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;

public class IMTabListener<T extends Fragment> implements TabListener {
	private Fragment imtlFragment;
	private final Activity imtlContextActivity;
	private final String imtlTag;
	private final Class<T> imtlClass;
	
	public IMTabListener(Activity activity, String tag, Class<T> fragment) {
		this.imtlContextActivity = activity;
		this.imtlTag = tag;
		this.imtlClass = fragment;
	}
	
	/**
	 * Callbacks
	 */
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// User #boulet
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (imtlFragment != null) {
			ft.attach(imtlFragment);
		} else {
			imtlFragment = Fragment.instantiate(imtlContextActivity, imtlClass.getName());
			ft.add(android.R.id.content, imtlFragment, imtlTag);
		}
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (imtlFragment != null) {
			ft.detach(imtlFragment);
		}
	}
}