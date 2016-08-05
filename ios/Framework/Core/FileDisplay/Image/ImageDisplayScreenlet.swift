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
import Foundation


@objc public protocol ImageDisplayScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: ImageDisplayScreenlet, onImageAssetResponse url: NSURL)

	optional func screenlet(screenlet: ImageDisplayScreenlet, onImageAssetError error: NSError)
}

public class ImageDisplayScreenlet: BaseFileDisplayScreenlet {

	public var imageDisplayDelegate: ImageDisplayScreenletDelegate? {
		return delegate as? ImageDisplayScreenletDelegate
	}

	public var imageDisplayViewModel: BaseFileDisplayViewModel? {
		return screenletView as? BaseFileDisplayViewModel
	}

	//MARK: BaseFileDisplayScreenlet

	override public func createLoadAssetInteractor() -> Interactor? {
		let interactor = AssetDisplayInteractor(
			screenlet: self, entryId: entryId, classPK: classPK, className: className)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultAsset = interactor.assetEntry {
				self.fileEntry = FileEntry(attributes: resultAsset.attributes)
				self.loadFile()
			}
			else {
				self.imageDisplayDelegate?.screenlet?(self, onImageAssetError: NSError.errorWithCause(.InvalidServerResponse))
			}
		}

		interactor.onFailure = {
			self.imageDisplayDelegate?.screenlet?(self, onImageAssetError: $0)
		}

		return interactor
	}

}
