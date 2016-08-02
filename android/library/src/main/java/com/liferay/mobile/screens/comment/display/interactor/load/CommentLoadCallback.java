package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.util.JSONUtil;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadCallback extends InteractorAsyncTaskCallback<CommentEntry> {
	public CommentLoadCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public CommentEntry transform(Object obj) throws Exception {
		return new CommentEntry(JSONUtil.toMap((JSONObject) obj));
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new CommentLoadEvent(targetScreenletId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, CommentEntry result) {
		return new CommentLoadEvent(targetScreenletId, result);
	}
}
