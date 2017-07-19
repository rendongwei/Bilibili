package com.don.libirary.util;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 判断空数据工具类
 * 
 * @author don
 *         <ul>
 *         <li><b>只支持Integer、String、Array、List、Map类型</b></li>
 *         </ul>
 */
public class EmptyUtil {

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		try {
			if (object.getClass().isAssignableFrom(Integer.class)
					|| object.getClass() == int.class) {
				return ((Integer) object) == 0;
			}

			if (object.getClass().isAssignableFrom(Double.class)
					|| object.getClass() == double.class) {
				return ((Double) object) == 0;
			}

			if (object.getClass().isAssignableFrom(Float.class)
					|| object.getClass() == float.class) {
				return ((Float) object) == 0;
			}

			if (object.getClass().isAssignableFrom(String.class)) {
				return String.valueOf(object).trim().length() == 0
						|| "null".equals(object);
			}

			if (object.getClass().isArray()) {
				return Arrays.asList(object).size() == 0;
			}

			if (object instanceof List) {
				return ((List) object).size() == 0;
			}

			if (object instanceof Map) {
				return ((Map) object).size() == 0;
			}

			if (object instanceof Bundle) {
				return ((Bundle) object).isEmpty();
			}

			if (object instanceof TextView) {
				return ((TextView) object).getText().toString().trim().length() == 0;
			}

			if (object instanceof Adapter) {
				return ((Adapter) object).getCount() == 0;
			}

		} catch (Exception e) {
		}

		return true;
	}
}
