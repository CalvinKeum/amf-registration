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
public class LastNameException extends PortalException {

	public static class MaxLength extends LastNameException {

		public MaxLength(int maxLength) {
			super(
				String.format(
					"Last name must not be longer than %s characters",
					maxLength));

			this.maxLength = maxLength;
		}

		public final int maxLength;

	}

	public static class MustBeAlphanumeric extends LastNameException {

		public MustBeAlphanumeric() {
			super("Last name must only contain alphanumeric characters");
		}

	}

	public static class MustNotBeNull extends LastNameException {

		public MustNotBeNull() {
			super("Last name must not be null");
		}

	}

	private LastNameException(String msg) {
		super(msg);
	}

}