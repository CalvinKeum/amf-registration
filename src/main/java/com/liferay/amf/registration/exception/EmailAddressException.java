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
public class EmailAddressException extends PortalException {

	public static class MaxLength extends EmailAddressException {

		public MaxLength(int maxLength) {
			super(
				String.format(
					"Email Address must not be longer than %s characters",
					maxLength));

			this.maxLength = maxLength;
		}

		public final int maxLength;

	}

	public static class MustBeAlphanumeric extends EmailAddressException {

		public MustBeAlphanumeric() {
			super("Email Address must only contain alphanumeric characters");
		}

	}

	public static class MustNotBeNull extends EmailAddressException {

		public MustNotBeNull() {
			super("Email Address must not be null");
		}

	}

	private EmailAddressException(String msg) {
		super(msg);
	}

}