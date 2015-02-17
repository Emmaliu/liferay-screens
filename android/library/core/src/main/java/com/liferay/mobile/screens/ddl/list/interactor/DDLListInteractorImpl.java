/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.list.interactor;

import android.util.Pair;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.service.MobilewidgetsddlrecordService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl
	extends BaseListInteractor<DDLEntry,DDLListRowsListener> implements DDLListInteractor {

    public DDLListInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

    public void loadRows(
			long recordSetId, long userId, int startRow, int endRow, Locale locale)
		throws Exception {
        _recordSetId = recordSetId;
        _userId = userId;

        loadRows(startRow, endRow, locale);
	}

    @Override
    protected BaseListCallback<DDLEntry> getCallback(Pair<Integer, Integer> rowsRange) {
        return new DDLListCallback(getTargetScreenletId(), rowsRange);
    }

    @Override
    protected void sendPageRequests(BatchSessionImpl batchSession, int startRow, int endRow, Locale locale) throws Exception {
        sendGetPageRowsRequest(
                batchSession, _recordSetId, _userId, startRow, endRow, locale);

        sendGetEntriesCountRequest(batchSession, _recordSetId, _userId);
    }

	protected void sendGetEntriesCountRequest(
			Session session, long recordSetId, long userId)
		throws Exception {

		JSONObject entryQueryAttributes = addRecordSetIdParam(
                recordSetId, userId);

        MobilewidgetsddlrecordService service =
                new MobilewidgetsddlrecordService(session);
		service.getEntriesCount(entryQueryAttributes);
	}

	protected void sendGetPageRowsRequest(
			Session session, long recordSetId, long userId, int startRow, int endRow,
			Locale locale)
		throws Exception {

		JSONObject entryQueryAttributes = addRecordSetIdParam(recordSetId, userId);

		entryQueryAttributes.put("start", startRow);
		entryQueryAttributes.put("end", endRow);

		MobilewidgetsddlrecordService service =
			new MobilewidgetsddlrecordService(session);

		service.getDDLEntries(entryQueryAttributes, locale.toString());
	}

    protected JSONObject addRecordSetIdParam(
            long recordSetId, long userId)
            throws JSONException {

        JSONObject entryQueryAttributes = new JSONObject();
        entryQueryAttributes.put("ddlRecordSetId", recordSetId);

        if (userId != 0) {
            entryQueryAttributes.put("userId", _userId);
        }

        return entryQueryAttributes;
    }

	protected void validate(
		long recordSetId, int startRow, int endRow, Locale locale) {
        super.validate(startRow, endRow, locale);

		if (recordSetId <= 0) {
			throw new IllegalArgumentException(
				"ddlRecordSetId cannot be 0 or negative");
		}
	}

    private long _recordSetId;
    private long _userId;


}