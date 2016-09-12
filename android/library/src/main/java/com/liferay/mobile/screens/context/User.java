/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.context;

import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class User {

	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String USER_ID = "userId";
	public static final String UUID = "uuid";
	public static final String PORTRAIT_ID = "portraitId";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String SCREEN_NAME = "screenName";
	private final Map<String, Object> attributes;
	private final JSONObject jsonObject;

	public User(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		attributes = new HashMap<>(jsonObject.length());

		Iterator<String> it = jsonObject.keys();

		while (it.hasNext()) {
			String key = it.next();

			try {
				attributes.put(key, jsonObject.get(key));
			} catch (JSONException e) {
				LiferayLogger.e("Error parsing json", e);
			}
		}
	}

	public long getId() {
		return JSONUtil.castToLong(attributes.get(USER_ID));
	}

	public String getUuid() {
		return attributes.get(UUID).toString();
	}

	public long getPortraitId() {
		return JSONUtil.castToLong(attributes.get(PORTRAIT_ID));
	}

	public String getFirstName() {
		return getString(FIRST_NAME);
	}

	public String getLastName() {
		return getString(LAST_NAME);
	}

	public String getEmail() {
		return getString(EMAIL_ADDRESS);
	}

	public String getScreenName() {
		return (String) attributes.get(SCREEN_NAME);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public String getString(String key) {
		return (String) attributes.get(key);
	}

	public long getInt(String key) {
		return (int) attributes.get(key);
	}

	public long getLong(String key) {
		return (long) attributes.get(key);
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}
}
