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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

boolean male = ParamUtil.getBoolean(request, "male", true);

Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar();

birthdayCalendar.set(Calendar.MONTH, Calendar.JANUARY);
birthdayCalendar.set(Calendar.DATE, 1);
birthdayCalendar.set(Calendar.YEAR, 1970);

Country country = CountryServiceUtil.getCountryByA3("USA");

long countryId = country.getCountryId();

long regionId = 0;
%>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<liferay-ui:message key='<%= LanguageUtil.format(request, "hi-x-you-are-already-logged", HtmlUtil.escape(user.getFullName()), false) %>' />
	</c:when>
	<c:when test='<%= SessionMessages.contains(request, "accountCreated") %>'>
		<div class="alert alert-success">
			<liferay-ui:message key="success-thank-you-for-creating-an-account" translateArguments="<%= false %>" />
		</div>
	</c:when>
	<c:otherwise>
		<portlet:actionURL name="/create_account" var="createAccountURL">
			<portlet:param name="mvcRenderCommandName" value="/create_account" />
		</portlet:actionURL>

		<aui:form action="<%= createAccountURL %>" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

			<liferay-ui:error exception="<%= AddressCityException.MustBeAlphanumeric.class %>" message="the-city-address-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= AddressCityException.MustNotBeNull.class %>" message="the-city-address-cannot-be-empty" />

			<liferay-ui:error exception="<%= AddressCityException.MaxLength.class %>">

				<%
				AddressCityException.MaxLength fne = (AddressCityException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-city-address-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= AddressStateException.class %>" message="please-select-a-state" />
			<liferay-ui:error exception="<%= AddressStreetException.MustBeAlphanumeric.class %>" message="the-street-address-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= AddressStreetException.MustNotBeNull.class %>" message="the-street-address-cannot-be-empty" />

			<liferay-ui:error exception="<%= AddressStreetException.MaxLength.class %>">

				<%
				AddressStreetException.MaxLength fne = (AddressStreetException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-street-address-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= AddressZipException.MustBeNumeric.class %>" message="the-zip-code-must-only-contain-numbers" />
			<liferay-ui:error exception="<%= AddressZipException.MustNotBeNull.class %>" message="the-zip-code-cannot-be-empty" />

			<liferay-ui:error exception="<%= AddressZipException.MustHaveLength.class %>">

				<%
				AddressZipException.MustHaveLength fne = (AddressZipException.MustHaveLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.length) %>" key="the-zip-code-must-be-x-digits-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= BirthdayException.MinAge.class %>">

				<%
				BirthdayException.MinAge fne = (BirthdayException.MinAge)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.minAge) %>" key="you-must-be-at-least-x-years-old-to-sign-up" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-create-user-account-because-the-maximum-number-of-users-has-been-reached" />
			<liferay-ui:error exception="<%= ContactBirthdayException.class %>" message="please-enter-a-valid-birthday" />
			<liferay-ui:error exception="<%= ContactNameException.MustHaveFirstName.class %>" message="please-enter-a-valid-first-name" />
			<liferay-ui:error exception="<%= ContactNameException.MustHaveLastName.class %>" message="please-enter-a-valid-last-name" />
			<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />
			<liferay-ui:error exception="<%= EmailAddressException.MustBeAlphanumeric.class %>" message="the-email-address-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= EmailAddressException.MustNotBeNull.class %>" message="the-email-address-cannot-be-empty" />

			<liferay-ui:error exception="<%= EmailAddressException.MaxLength.class %>">

				<%
				EmailAddressException.MaxLength fne = (EmailAddressException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-email-address-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FirstNameException.MustBeAlphanumeric.class %>" message="the-first-name-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= FirstNameException.MustNotBeNull.class %>" message="the-first-name-cannot-be-empty" />

			<liferay-ui:error exception="<%= FirstNameException.MaxLength.class %>">

				<%
				FirstNameException.MaxLength fne = (FirstNameException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-first-name-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= GroupFriendlyURLException.class %>">

				<%
				GroupFriendlyURLException gfurle = (GroupFriendlyURLException)errorException;
				%>

				<c:if test="<%= gfurle.getType() == GroupFriendlyURLException.DUPLICATE %>">
					<liferay-ui:message key="the-screen-name-you-requested-is-associated-with-an-existing-friendly-url" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= HomePhoneException.MustBeNumeric.class %>" message="the-home-phone-number-must-only-contain-numbers" />

			<liferay-ui:error exception="<%= HomePhoneException.MustHaveLength.class %>">

				<%
				HomePhoneException.MustHaveLength fne = (HomePhoneException.MustHaveLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.length) %>" key="the-home-phone-number-must-be-x-digits-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LastNameException.MustBeAlphanumeric.class %>" message="the-last-name-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= LastNameException.MustNotBeNull.class %>" message="the-last-name-cannot-be-empty" />

			<liferay-ui:error exception="<%= LastNameException.MaxLength.class %>">

				<%
				LastNameException.MaxLength fne = (LastNameException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-last-name-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= MobilePhoneException.MustBeNumeric.class %>" message="the-mobile-phone-number-must-only-contain-numbers" />

			<liferay-ui:error exception="<%= MobilePhoneException.MustHaveLength.class %>">

				<%
				MobilePhoneException.MustHaveLength fne = (MobilePhoneException.MustHaveLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.length) %>" key="the-mobile-phone-number-must-be-x-digits-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= PasswordException.MustHaveNumber.class %>" message="the-password-must-contain-at-least-one-number" />
			<liferay-ui:error exception="<%= PasswordException.MustHaveSpecial.class %>" message="the-password-must-contain-at-least-one-special-character" />
			<liferay-ui:error exception="<%= PasswordException.MustHaveUppercase.class %>" message="the-password-must-contain-at-least-one-uppercase-character" />
			<liferay-ui:error exception="<%= PasswordException.MustNotBeNull.class %>" message="the-password-cannot-be-empty" />

			<liferay-ui:error exception="<%= PasswordException.MinLength.class %>">

				<%
				PasswordException.MinLength fne = (PasswordException.MinLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.minLength) %>" key="you-must-be-at-least-x-years-old-to-sign-up" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= RequiredFieldException.class %>" message="please-fill-out-all-required-fields" />

			<liferay-ui:error exception="<%= SecurityQuestionException.class %>" message="please-select-a-security-question" />

			<liferay-ui:error exception="<%= SecurityAnswerException.MustBeAlphanumeric.class %>" message="the-security-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= SecurityAnswerException.MustNotBeNull.class %>" message="the-security-answer-cannot-be-empty" />

			<liferay-ui:error exception="<%= SecurityAnswerException.MaxLength.class %>">

				<%
				SecurityAnswerException.MaxLength fne = (SecurityAnswerException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-security-answer-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= TermsOfUseException.class %>" message="you-must-agree-to-the-terms-of-use" />

			<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeDuplicate.class %>" message="the-email-address-you-requested-is-already-taken" />
			<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeNull.class %>" message="please-enter-an-email-address" />
			<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBePOP3User.class %>" message="the-email-address-you-requested-is-reserved" />
			<liferay-ui:error exception="<%= UserEmailAddressException.MustNotBeReserved.class %>" message="the-email-address-you-requested-is-reserved" />
			<liferay-ui:error exception="<%= UserEmailAddressException.MustNotUseCompanyMx.class %>" message="the-email-address-you-requested-is-not-valid-because-its-domain-is-reserved" />
			<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />
			<liferay-ui:error exception="<%= UserIdException.MustNotBeReserved.class %>" message="the-user-id-you-requested-is-reserved" />
			<liferay-ui:error exception="<%= UsernameException.MustBeAlphanumeric.class %>" message="the-username-must-only-contain-alphanumeric-characters" />
			<liferay-ui:error exception="<%= UsernameException.MustNotBeNull.class %>" message="the-username-cannot-be-empty" />

			<liferay-ui:error exception="<%= UsernameException.MinLength.class %>">

				<%
				UsernameException.MinLength fne = (UsernameException.MinLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.minLength) %>" key="the-username-is-too-short" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UsernameException.MaxLength.class %>">

				<%
				UsernameException.MaxLength fne = (UsernameException.MaxLength)errorException;
				%>

				<liferay-ui:message arguments="<%= String.valueOf(fne.maxLength) %>" key="the-username-is-too-long" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithModelListeners.class %>" message="that-password-is-invalid-please-enter-a-different-password" />

			<liferay-ui:error exception="<%= UserPasswordException.MustComplyWithRegex.class %>">

				<%
				UserPasswordException.MustComplyWithRegex upe = (UserPasswordException.MustComplyWithRegex)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(upe.regex) %>" key="that-password-does-not-comply-with-the-regular-expression" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UserPasswordException.MustMatch.class %>" message="the-passwords-you-entered-do-not-match" />
			<liferay-ui:error exception="<%= UserPasswordException.MustNotBeNull.class %>" message="the-password-cannot-be-blank" />
			<liferay-ui:error exception="<%= UserPasswordException.MustNotBeTrivial.class %>" message="that-password-uses-common-words-please-enter-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
			<liferay-ui:error exception="<%= UserPasswordException.MustNotContainDictionaryWords.class %>" message="that-password-uses-common-dictionary-words" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeDuplicate.class %>" focusField="screenName" message="the-screen-name-you-requested-is-already-taken" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeNull.class %>" focusField="screenName" message="the-screen-name-cannot-be-blank" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeNumeric.class %>" focusField="screenName" message="the-screen-name-cannot-contain-only-numeric-values" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeReserved.class %>" message="the-screen-name-you-requested-is-reserved" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeReservedForAnonymous.class %>" focusField="screenName" message="the-screen-name-you-requested-is-reserved-for-the-anonymous-user" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustNotBeUsedByGroup.class %>" focusField="screenName" message="the-screen-name-you-requested-is-already-taken-by-a-site" />
			<liferay-ui:error exception="<%= UserScreenNameException.MustProduceValidFriendlyURL.class %>" focusField="screenName" message="the-screen-name-you-requested-must-produce-a-valid-friendly-url" />

			<liferay-ui:error exception="<%= UserScreenNameException.MustValidate.class %>" focusField="screenName">

				<%
				UserScreenNameException.MustValidate usne = (UserScreenNameException.MustValidate)errorException;
				%>

				<liferay-ui:message key="<%= usne.screenNameValidator.getDescription(locale) %>" />
			</liferay-ui:error>

			<aui:fieldset column="<%= true %>" label="Basic Info">
				<aui:col width="<%= 50 %>">
					<aui:input label="firstName" name="first_name" type="text">
						<aui:validator name="alphanum" />
						<aui:validator name="required" />
					</aui:input>

					<aui:input label="lastName" name="last_name" type="text">
						<aui:validator name="alphanum" />
						<aui:validator name="maxLength">50</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input label="emailAddress" name="email_address" type="text">
						<aui:validator name="maxLength">255</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input name="username" type="text">
						<aui:validator name="alphanum" />
						<aui:validator name="maxLength">255</aui:validator>
						<aui:validator name="required" />
					</aui:input>
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:select name="male">
						<aui:option label="male" value="1" />
						<aui:option label="female" selected="<%= !male %>" value="0" />
					</aui:select>

					<aui:field-wrapper label="birthday">
						<liferay-ui:input-date
							dayParam="b_day"
							dayValue="<%= birthdayCalendar.get(Calendar.DATE) %>"
							disabled="<%= false %>"
							firstDayOfWeek="<%= birthdayCalendar.getFirstDayOfWeek() - 1 %>"
							lastEnabledDate="<%= new Date() %>"
							monthParam="b_month"
							monthValue="<%= birthdayCalendar.get(Calendar.MONTH) %>"
							name="birthday"
							yearParam="b_year"
							yearValue="<%= birthdayCalendar.get(Calendar.YEAR) %>"
						/>
					</aui:field-wrapper>

					<aui:input label="password" name="password1" size="30" type="password" value="">
						<aui:validator name="minLength">6</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input label="confirmPassword" name="password2" size="30" type="password" value="">
						<aui:validator name="equalTo">
							'#<portlet:namespace />password1'
						</aui:validator>

						<aui:validator name="required" />
					</aui:input>
				</aui:col>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>" label="phone">
				<aui:col width="<%= 50 %>">
					<aui:input label="homePhone" name="home_phone" type="text" />
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:input label="mobilePhone" name="mobile_phone" type="text" />
				</aui:col>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>" label="Billing Address (US-Only)">
				<aui:col width="<%= 50 %>">
					<aui:input label="address1" name="address" type="text">
						<aui:validator name="maxLength">255</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input label="address2" name="address2" type="text">
						<aui:validator name="maxLength">255</aui:validator>
					</aui:input>

					<aui:input label="city" name="city" type="text">
						<aui:validator name="maxLength">255</aui:validator>
						<aui:validator name="required" />
					</aui:input>
				</aui:col>

				<aui:col width="<%= 50 %>">
					<aui:select disabled="true" label="country" name="countryId" />

					<aui:select label="state" name="state" required="true" showEmptyOption="false" />

					<aui:input label="zipCode" name="zip" type="text">
						<aui:validator name="digits" />
						<aui:validator name="required" />
					</aui:input>
				</aui:col>
			</aui:fieldset>

			<aui:fieldset column="<%= true %>" label="misc">
				<aui:col width="<%= 50 %>">
					<aui:select label="securityQuestion" name="security_question">
						<aui:option label="What is your mother's maiden name?" value="what-is-your-mother's-maiden-name" />
						<aui:option label="What is the make of your first car?" value="what-is-the-make-of-your-first-car" />
						<aui:option label="What is your high school mascot?" value="what-is-your-high-school-mascot" />
						<aui:option label="Who is your favorite actor?" value="who-is-your-favorite-actor" />
					</aui:select>

					<aui:input label="answer" name="security_answer" type="text">
						<aui:validator name="maxLength">255</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input label="I have read, understand, and agree with the Terms of Use governing my access to and use of the Acme Movie Fanatics web site." name="accepted_tou" type="checkbox">
						<aui:validator name="required" />
					</aui:input>
				</aui:col>
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-address,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />countryId',
				selectData: Liferay.Address.getCountries,
				selectDesc: 'nameCurrentValue',
				selectId: 'countryId',
				selectSort: '<%= true %>',
				selectVal: '<%= countryId %>'
			},
			{
				select: '<portlet:namespace />state',
				selectData: Liferay.Address.getRegions,
				selectDesc: 'name',
				selectId: 'regionCode',
				selectVal: '<%= regionId %>'
			}
		]
	);
</aui:script>