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
public class PasswordException extends PortalException {

	public static class MinLength extends PasswordException {

		public MinLength(int minLength) {
			super(
				String.format(
					"User name must be longer than %s characters", minLength));

			this.minLength = minLength;
		}

		public final int minLength;

	}

	public static class MustHaveNumber extends PasswordException {

		public MustHaveNumber() {
			super("Password must contain a number");
		}

	}

	public static class MustHaveSpecial extends PasswordException {

		public MustHaveSpecial() {
			super("Password must contain a special character");
		}

	}

	public static class MustHaveUppercase extends PasswordException {

		public MustHaveUppercase() {
			super("Password must contain an uppercase character");
		}

	}

	public static class MustNotBeNull extends PasswordException {

		public MustNotBeNull() {
			super("Password must not be null");
		}

	}

	private PasswordException(String msg) {
		super(msg);
	}

}