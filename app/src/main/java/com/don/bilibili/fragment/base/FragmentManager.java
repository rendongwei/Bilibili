package com.don.bilibili.fragment.base;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class FragmentManager {

	private AppCompatActivity mActivity;
	private BaseFragment mFragment;

	private Class<? extends BaseFragment> mShowClass;
	private Map<Class<? extends BaseFragment>, BaseFragment> mFragments = new HashMap<Class<? extends BaseFragment>, BaseFragment>();

	public FragmentManager(AppCompatActivity activity) {
		mActivity = activity;
	}

	public FragmentManager(BaseFragment fragment) {
		mFragment = fragment;
	}

	private FragmentTransaction getTransaction() {
		if (mActivity == null) {
			return mFragment.getFragmentManager().beginTransaction();
		}
		if (mFragment == null) {
			return mActivity.getSupportFragmentManager().beginTransaction();
		}
		return null;
	}

	private boolean hasLayout(int id) {
		View v = null;
		if (mActivity == null) {
			v = mFragment.findViewById(id);
		}
		if (mFragment == null) {
			v = mActivity.findViewById(id);
		}
		if (v == null) {
			return false;
		}
		if (v instanceof ViewGroup) {
			return true;
		}
		return false;
	}

	public void addFragment(int id, BaseFragment fragment) {
		if (!hasLayout(id) || fragment == null) {
			return;
		}
		FragmentTransaction transaction = getTransaction();
		if (transaction == null) {
			return;
		}
		mFragments.put(fragment.getClass(), fragment);
		transaction.add(id, fragment);
		transaction.hide(fragment);
		transaction.commitAllowingStateLoss();
	}

	public void showFragment(int id, Class<? extends BaseFragment> cls) {
		if (!hasLayout(id) || cls == null) {
			return;
		}
		FragmentTransaction transaction = getTransaction();
		if (transaction == null) {
			return;
		}
		if (mShowClass == cls) {
			return;
		}
		mShowClass = cls;
		Map<Class<? extends BaseFragment>, BaseFragment> fragments = new HashMap<Class<? extends BaseFragment>, BaseFragment>();
		fragments.putAll(mFragments);
		if (fragments.containsKey(cls)) {
			for (Map.Entry<Class<? extends BaseFragment>, BaseFragment> entry : fragments
					.entrySet()) {
				BaseFragment fragment = entry.getValue();
				if (fragment == null) {
					try {
						fragment = cls.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mFragments.put(cls, fragment);
					transaction.add(id, fragment);
					if (cls == entry.getKey()) {
						transaction.show(fragment);
					} else {
						transaction.hide(fragment);
					}
				} else {
					if (cls == entry.getKey()) {
						transaction.show(fragment);
					} else {
						transaction.hide(fragment);
					}
				}
			}
		} else {
			BaseFragment fragment = null;
			try {
				fragment = cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mFragments.put(cls, fragment);
			transaction.add(id, fragment);
			transaction.show(fragment);
			for (Map.Entry<Class<? extends BaseFragment>, BaseFragment> entry : fragments
					.entrySet()) {
				fragment = entry.getValue();
				transaction.hide(fragment);
			}

		}
		transaction.commitAllowingStateLoss();
	}

	public void hideFragment(Class<? extends BaseFragment> cls) {
		if (mFragments.containsKey(cls)) {
			FragmentTransaction transaction = getTransaction();
			if (transaction == null) {
				return;
			}
			BaseFragment fragment = mFragments.get(cls);
			transaction.hide(fragment);
			transaction.commitAllowingStateLoss();
		}
	}

	public void removeFragment(Class<? extends BaseFragment> cls) {
		hideFragment(cls);
		if (mFragments.containsKey(cls)) {
			mFragments.remove(cls);
		}
	}

	public void deleteFragment(Class<? extends BaseFragment> cls) {
		if (mFragments.containsKey(cls)) {
			FragmentTransaction transaction = getTransaction();
			if (transaction == null) {
				return;
			}
			BaseFragment fragment = mFragments.get(cls);
			transaction.remove(fragment);
			transaction.commitAllowingStateLoss();
			mFragments.remove(cls);
		}
	}

	public Class<? extends BaseFragment> getShowClass() {
		return mShowClass;
	}

	@SuppressWarnings("unchecked")
	public <T> T getFragment(Class<? extends BaseFragment> cls) {
		if (cls == null || !mFragments.containsKey(cls)) {
			return null;
		}
		return (T) mFragments.get(cls);
	}
}
