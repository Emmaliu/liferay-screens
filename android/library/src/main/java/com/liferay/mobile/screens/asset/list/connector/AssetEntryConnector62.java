package com.liferay.mobile.screens.asset.list.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.assetentry.AssetEntryService;

/**
 * @author Javier Gamarra
 */
public class AssetEntryConnector62 implements AssetEntryConnector {

	public AssetEntryConnector62(Session session) {
		_assetEntryService = new AssetEntryService(session);
	}

	@Override
	public Integer getEntriesCount(JSONObjectWrapper entryQuery) throws Exception {
		return _assetEntryService.getEntriesCount(entryQuery);
	}

	private final AssetEntryService _assetEntryService;
}
