package com.liferay.mobile.screens.comment.list.interactor.delete;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteCallback extends JSONObjectCallback {

	public CommentDeleteCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override public JSONObject transform(Object obj) throws Exception {
		return new JSONObject();
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new CommentDeleteEvent(targetScreenletId, e);
	}

	@Override protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new CommentDeleteEvent(targetScreenletId, result);
	}
}
