<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.amf.registration.exception.AddressCityException" %><%@
page import="com.liferay.amf.registration.exception.AddressStateException" %><%@
page import="com.liferay.amf.registration.exception.AddressStreetException" %><%@
page import="com.liferay.amf.registration.exception.AddressZipException" %><%@
page import="com.liferay.amf.registration.exception.BirthdayException" %><%@
page import="com.liferay.amf.registration.exception.EmailAddressException" %><%@
page import="com.liferay.amf.registration.exception.FirstNameException" %><%@
page import="com.liferay.amf.registration.exception.HomePhoneException" %><%@
page import="com.liferay.amf.registration.exception.LastNameException" %><%@
page import="com.liferay.amf.registration.exception.MobilePhoneException" %><%@
page import="com.liferay.amf.registration.exception.PasswordException" %><%@
page import="com.liferay.amf.registration.exception.SecurityAnswerException" %><%@
page import="com.liferay.amf.registration.exception.SecurityQuestionException" %><%@
page import="com.liferay.amf.registration.exception.UsernameException" %><%@
page import="com.liferay.portal.kernel.exception.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.kernel.exception.ContactBirthdayException" %><%@
page import="com.liferay.portal.kernel.exception.ContactNameException" %><%@
page import="com.liferay.portal.kernel.exception.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredFieldException" %><%@
page import="com.liferay.portal.kernel.exception.TermsOfUseException" %><%@
page import="com.liferay.portal.kernel.exception.UserEmailAddressException" %><%@
page import="com.liferay.portal.kernel.exception.UserIdException" %><%@
page import="com.liferay.portal.kernel.exception.UserPasswordException" %><%@
page import="com.liferay.portal.kernel.exception.UserScreenNameException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.Country" %><%@
page import="com.liferay.portal.kernel.service.CountryServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.Date" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />