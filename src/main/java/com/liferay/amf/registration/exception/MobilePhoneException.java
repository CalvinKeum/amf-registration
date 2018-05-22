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

package com.liferay.amf.registration.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Calvin Keum
 */
public class MobilePhoneException extends PortalException {

	public static class MustBeNumeric extends MobilePhoneException {

		public MustBeNumeric() {
			super("The home phone must only contain numeric characters");
		}

	}

	public static class MustHaveLength extends MobilePhoneException {

		public MustHaveLength(int length) {
			super(
				String.format(
					"The mobile phone number must have %s characters", length));

			this.length = length;
		}

		public final int length;

	}

	private MobilePhoneException(String msg) {
		super(msg);
	}

}