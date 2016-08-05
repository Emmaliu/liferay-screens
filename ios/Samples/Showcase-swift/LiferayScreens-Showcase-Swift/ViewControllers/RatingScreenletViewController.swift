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
import LiferayScreens


class RatingScreenletViewController: UIViewController {

	@IBOutlet weak var screenlet: RatingScreenlet!
	@IBOutlet weak var switchControl: UISwitch!

	@IBAction func segmentedControlChanged(sender: UISegmentedControl) {
		// use your asset's ids here

		switch sender.selectedSegmentIndex {
		case 1:
			screenlet.entryId = 32049
			screenlet.themeName = "default-like"
			screenlet.ratingsGroupCount = 1
		case 2:
			screenlet.entryId = 31904
			screenlet.themeName = "default-stars"
			screenlet.ratingsGroupCount = 5
		case 3:
			screenlet.entryId = 36849
			screenlet.themeName = "default-emojis"
			screenlet.ratingsGroupCount = 5
		default:
			screenlet.entryId = 31869
			screenlet.themeName = "default-thumbs"
			screenlet.ratingsGroupCount = 2
		}

		screenlet.loadRatings()
		screenlet.editable = switchControl.on
	}

    @IBAction func switchChange(sender: UISwitch) {
		screenlet.editable = sender.on
    }
}
