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

package com.liferay.amf.registration.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Calvin Keum
 */
public class AMFRegistrationConfigurationValues {

	public static final int ADDRESS_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.ADDRESS_MAX_LENGTH));

	public static final int AGE_MIN = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.AGE_MIN));

	public static final int CITY_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.CITY_MAX_LENGTH));

	public static final int EMAIL_ADDRESS_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.EMAIL_ADDRESS_MAX_LENGTH));

	public static final int FIRST_NAME_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.FIRST_NAME_MAX_LENGTH));

	public static final int LAST_NAME_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.LAST_NAME_MAX_LENGTH));

	public static final int PASSWORD_MIN_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.PASSWORD_MIN_LENGTH));

	public static final int PHONE_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.PHONE_LENGTH));

	public static final int SECURITY_ANSWER_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.SECURITY_ANSWER_MAX_LENGTH));

	public static final int USERNAME_MAX_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.USERNAME_MAX_LENGTH));

	public static final int USERNAME_MIN_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.USERNAME_MIN_LENGTH));

	public static final int ZIP_LENGTH = GetterUtil.getInteger(
		AMFRegistrationConfigurationUtil.get(
			AMFRegistrationConfigurationKeys.ZIP_LENGTH));

}