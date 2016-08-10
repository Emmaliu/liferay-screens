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
import UIKit


public class AssetDisplayView_default: BaseScreenletView, AssetDisplayViewModel {

	public override var progressMessages: [String : ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction: [.Working: NoProgressMessage]
		]
	}

	public var asset: Asset? {
		didSet {
			if let asset = self.asset {
				let frame = CGRect(x: 0, y: 0, width: self.frame.width, height: self.frame.height)

				let factory = AssetDisplayScreenletFactory(asset: asset)

				let screenlet = factory.createScreenlet(frame, screenlet: self.screenlet as? AssetDisplayScreenlet)
				
				if let view = screenlet {
					addSubview(view)
				}
			}
		}
	}
}
