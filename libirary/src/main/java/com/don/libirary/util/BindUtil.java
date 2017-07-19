package com.don.libirary.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.PopupWindow;

import com.don.libirary.annotation.Id;
import com.don.libirary.annotation.OnClick;
import com.don.libirary.annotation.Resource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BindUtil {

	private static Context mContext;
	private static SparseArray<View> mParentViews = new SparseArray<View>();

	public static void bind(Object object) {
		if (object == null) {
			return;
		}
		bindView(object);
	}

	private static void bindView(Object object) {
		if (object instanceof Activity || object instanceof Fragment
				|| object instanceof Dialog || object instanceof PopupWindow
				|| object instanceof View) {
			mContext = getContext(object);
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Id.class)) {
					int id = field.getAnnotation(Id.class).id();
					int parentViewId = field.getAnnotation(Id.class)
							.parentViewId();
					if (id == -1) {
						continue;
					}
					View parentView = findParentViewById(object, parentViewId);
					View v = findViewById(object, parentView, id);
					if (v == null) {
						continue;
					}
					field.setAccessible(true);
					try {
						field.set(object, v);
						setOnClickListener(object, field, v);
					} catch (Exception e) {
					}
				}

				if (field.isAnnotationPresent(Resource.class)) {
					Resource resource = field.getAnnotation(Resource.class);
					int color = resource.color();
					int drawable = resource.drawable();
					int string = resource.string();
					int dimen = resource.dimen();
					Object value = null;
					if (color != -1) {
						value = mContext.getResources().getColor(color);
					} else if (drawable != -1) {
						value = mContext.getResources().getDrawable(drawable);
					} else if (string != -1) {
						value = mContext.getResources().getString(string);
					} else if (dimen != -1) {
						value = mContext.getResources().getDimensionPixelSize(
								dimen);
					}
					if (value != null) {
						try {
							field.set(object, value);
						} catch (Exception e) {
						}
					}
				}
			}
			Method[] methods = object.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(OnClick.class)) {
					int id = method.getAnnotation(OnClick.class).id();
					int parentViewId = method.getAnnotation(OnClick.class)
							.parentViewId();
					if (id == -1) {
						continue;
					}
					View parentView = findParentViewById(object, parentViewId);
					View v = findViewById(object, parentView, id);
					if (v == null) {
						continue;
					}
					v.setOnClickListener(new OnClickListener(object, v, method
							.getName()));
				}
			}
		}
	}

	private static Context getContext(Object object) {
		if (object instanceof Activity) {
			return ((Activity) object);
		}

		if (object instanceof Fragment) {
			return ((Fragment) object).getActivity();
		}

		if (object instanceof Dialog) {
			return ((Dialog) object).getContext();
		}

		if (object instanceof PopupWindow) {
			return ((PopupWindow) object).getContentView().getContext();
		}

		if (object instanceof View) {
			return ((View) object).getContext();
		}

		return null;
	}

	private static View findParentViewById(Object object, int id) {
		View parentView = null;
		if (id == -1) {
			return parentView;
		}
		parentView = mParentViews.get(id);
		if (parentView != null) {
			return parentView;
		}
		try {
			parentView = View.inflate(mContext, id, null);
		} catch (Exception e) {
		}
		if (parentView == null) {
			parentView = findViewById(object, parentView, id);
		}
		if (parentView != null) {
			mParentViews.put(id, parentView);
		}
		return parentView;
	}

	private static View findViewById(Object object, View parentView, int id) {
		if (parentView != null) {
			return parentView.findViewById(id);
		}
		try {
			parentView = mParentViews.get(id);
			if (parentView != null) {
				return parentView;
			}
			parentView = View.inflate(mContext, id, null);
			if (parentView != null) {
				mParentViews.put(id, parentView);
			}
			return parentView;
		} catch (Exception e) {
		}

		if (object instanceof Activity) {
			return ((Activity) object).findViewById(id);
		}

		if (object instanceof Fragment) {
			return ((Fragment) object).getView().findViewById(id);
		}

		if (object instanceof Dialog) {
			return ((Dialog) object).findViewById(id);
		}

		if (object instanceof PopupWindow) {
			return ((PopupWindow) object).getContentView().findViewById(id);
		}

		if (object instanceof View) {
			return ((View) object).findViewById(id);
		}

		return null;
	}

	private static void setOnClickListener(Object object, Field field, View v) {
		if (field.isAnnotationPresent(OnClick.class)) {
			String methodName = field.getAnnotation(OnClick.class).methodName();
			if (EmptyUtil.isEmpty(methodName)) {
				if (View.OnClickListener.class.isAssignableFrom(object
						.getClass())) {
					v.setOnClickListener(new OnClickListener(object, v,
							"onClick"));
				}
			} else {
				v.setOnClickListener(new OnClickListener(object, v, methodName));
			}
		}
	}

	private static class OnClickListener implements View.OnClickListener {

		private Object mObject;
		private int mId;
		private String mMethodName;

		public OnClickListener(Object object, View v, String methodName) {
			mObject = object;
			mId = v.getId();
			mMethodName = methodName;
			v.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (mObject != null) {
				try {
					Method method = mObject.getClass().getMethod(mMethodName,
							View.class);
					v.setId(mId);
					if (method.getParameterTypes().length > 0) {
						method.invoke(mObject, v);
					} else {
						method.invoke(mObject);
					}
				} catch (Exception e) {
				}
			}
		}

	}
}
