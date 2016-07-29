package com.liferay.mobile.screens.comment.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorListener;
import com.liferay.mobile.screens.comment.list.interactor.delete.CommentDeleteInteractor;
import com.liferay.mobile.screens.comment.list.interactor.delete.CommentDeleteInteractorImpl;
import com.liferay.mobile.screens.comment.list.interactor.list.CommentListInteractor;
import com.liferay.mobile.screens.comment.list.interactor.list.CommentListInteractorImpl;
import com.liferay.mobile.screens.comment.list.interactor.update.CommentUpdateInteractor;
import com.liferay.mobile.screens.comment.list.interactor.update.CommentUpdateInteractorImpl;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.Locale;
import java.util.Stack;

/**
 * @author Alejandro Hernández
 */
public class CommentListScreenlet
	extends BaseListScreenlet<CommentEntry, Interactor>
	implements CommentListInteractorListener {

	public static final String IN_DISCUSSION_ACTION = "in_discussion";
	public static final String OUT_DISCUSSION_ACTION = "out_discussion";
	public static final String DELETE_COMMENT_ACTION = "delete_comment";
	public static final String UPDATE_COMMENT_ACTION = "update_comment";

	public CommentListScreenlet(Context context) {
		super(context);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override protected void onScreenletAttached() {
		if (!isInEditMode()) {
			getCommentListViewModel().setHtmlBody(_htmlBody);
		}
		super.onScreenletAttached();
	}

	@Override protected void loadRows(Interactor interactor, int startRow, int endRow, Locale locale)
		throws Exception {
		long commentId = _discussionStack.empty() ? 0 : getLastCommentInStack().getCommentId();
		((CommentListInteractor) interactor).loadRows(_groupId, _className, _classPK, commentId, startRow, endRow);
	}

	@Override protected Interactor createInteractor(String actionName) {
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				return new CommentDeleteInteractorImpl(getScreenletId());
			case UPDATE_COMMENT_ACTION:
				return new CommentUpdateInteractorImpl(getScreenletId());
		}
		return new CommentListInteractorImpl(getScreenletId(), _offlinePolicy);
	}

	@Override protected void onUserAction(String actionName, Interactor interactor, Object... args) {
		if (args.length == 2) {
			switch ((String) args[0]) {
				case IN_DISCUSSION_ACTION:
					_discussionStack.push((CommentEntry) args[1]);
					changeToCommentDiscussion();
					break;
			}
		} else {
			if (args[0] instanceof String && args[0] == OUT_DISCUSSION_ACTION) {
				_discussionStack.pop();
				changeToCommentDiscussion();
			} else {
				long commentId = (long) args[0];
				try {
					((CommentDeleteInteractor) interactor).deleteComment(commentId);
				} catch (Exception e) {
					onDeleteCommentFailure(commentId, e);
				}
			}
		}
	}

	private void changeToCommentDiscussion() {
		getCommentListViewModel().changeToCommentDiscussion(getLastCommentInStack());
		loadPage(0);
	}

	private CommentEntry getLastCommentInStack() {
		return _discussionStack.empty() ? null : _discussionStack.peek();
	}

	@Override public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	@Override public void onDeleteCommentFailure(long commentId, Exception e) {
		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentFailure(commentId, e);
		}
		loadPage(0);
	}

	@Override public void onDeleteCommentSuccess(long commentId) {
		if (!_discussionStack.isEmpty() && commentId == getLastCommentInStack().getCommentId()) {
			_discussionStack.pop();
		}
		if (getCommentListListener() != null) {
			getCommentListListener().onDeleteCommentSuccess(commentId);
		}
		changeToCommentDiscussion();
	}

	@Override protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.CommentListScreenlet, 0, 0);

		_className = typedArray.getString(
			R.styleable.CommentListScreenlet_className);

		_classPK = castToLong(typedArray.getString(
			R.styleable.CommentListScreenlet_classPK));

		Integer offlinePolicy = typedArray.getInteger(
			R.styleable.CommentListScreenlet_offlinePolicy,
			OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		_groupId = castToLongOrUseDefault(typedArray.getString(
			R.styleable.CommentListScreenlet_groupId), groupId);

		_htmlBody = typedArray.getBoolean(R.styleable.CommentListScreenlet_htmlBody, false);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this._offlinePolicy = offlinePolicy;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		this._className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		this._classPK = classPK;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		this._groupId = groupId;
	}

	public void setHtmlBody(boolean htmlBody) {
		this._htmlBody = htmlBody;
		this.getCommentListViewModel().setHtmlBody(htmlBody);
	}

	private CommentListListener getCommentListListener() {
		return (CommentListListener) getListener();
	}

	private CommentListViewModel getCommentListViewModel() {
		return (CommentListViewModel) getViewModel();
	}

	private OfflinePolicy _offlinePolicy;
	private String _className;
	private long _classPK;
	private long _groupId;
	private boolean _htmlBody;
	private Stack<CommentEntry> _discussionStack = new Stack<>();
}
