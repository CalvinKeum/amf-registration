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

package com.liferay.amf.registration.portlet.action;

import com.liferay.amf.registration.configuration.AMFRegistrationConfigurationValues;
import com.liferay.amf.registration.constants.AMFRegistrationPortletKeys;
import com.liferay.amf.registration.exception.AddressCityException;
import com.liferay.amf.registration.exception.AddressStateException;
import com.liferay.amf.registration.exception.AddressStreetException;
import com.liferay.amf.registration.exception.AddressZipException;
import com.liferay.amf.registration.exception.BirthdayException;
import com.liferay.amf.registration.exception.EmailAddressException;
import com.liferay.amf.registration.exception.FirstNameException;
import com.liferay.amf.registration.exception.HomePhoneException;
import com.liferay.amf.registration.exception.LastNameException;
import com.liferay.amf.registration.exception.MobilePhoneException;
import com.liferay.amf.registration.exception.PasswordException;
import com.liferay.amf.registration.exception.SecurityAnswerException;
import com.liferay.amf.registration.exception.SecurityQuestionException;
import com.liferay.amf.registration.exception.UsernameException;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.DuplicateOpenIdException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.OrganizationParentException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.RequiredFieldException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.exception.TermsOfUseException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserIdException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.exception.UserSmsException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Calvin Keum
*/
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AMFRegistrationPortletKeys.PORTLET_NAME,
		"mvc.command.name=/create_account"
	},
	service = MVCActionCommand.class
)
public class CreateAccountMVCActionCommand extends BaseMVCActionCommand {

	protected void addAddress(
			ActionRequest actionRequest, User user, String address,
			String address2, String city, String state, String zip)
		throws Exception {

		long classNameId = _portal.getClassNameId(Contact.class);

		long contactId = getContactId(user.getUserId());

		Country country = _countryService.getCountryByA3(_COUNTRY_A3_USA);

		Region region = _regionService.getRegion(country.getCountryId(), state);

		ListType listType = _listTypeLocalService.addListType(
			_LIST_TYPE_NAME_BILLING, ListTypeConstants.CONTACT_ADDRESS);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Address.class.getName(), actionRequest);

		_addressLocalService.addAddress(
			user.getUserId(), _portal.getClassName(classNameId), contactId,
			address, address2, StringPool.BLANK, city, zip,
			region.getRegionId(), country.getCountryId(),
			listType.getListTypeId(), false, false, serviceContext);
	}

	protected void addPhone(
			ActionRequest actionRequest, User user, String phoneNumber,
			String listTypeName)
		throws Exception {

		if (Validator.isNull(phoneNumber)) {
			return;
		}

		long contactClassNameId = _portal.getClassNameId(Contact.class);

		long contactId = getContactId(user.getUserId());

		ListType listType = _listTypeLocalService.addListType(
			listTypeName, ListTypeConstants.CONTACT_PHONE);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Phone.class.getName(), actionRequest);

		_phoneLocalService.addPhone(
			user.getUserId(), _portal.getClassName(contactClassNameId),
			contactId, phoneNumber, StringPool.BLANK, listType.getListTypeId(),
			false, serviceContext);
	}

	protected User addUser(
			ActionRequest actionRequest, long companyId, String firstName,
			String lastName, String emailAddress, String username, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String password1, String password2, String securityQuestion,
			String securityAnswer, boolean acceptedTOU)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		User user = _userService.addUserWithWorkflow(
			companyId, false, password1, password2, false, username,
			emailAddress, 0, StringPool.BLANK,
			LocaleUtil.fromLanguageId(StringPool.BLANK), firstName,
			StringPool.BLANK, lastName, 0, 0, male, birthdayMonth, birthdayDay,
			birthdayYear, StringPool.BLANK, null, null, null, null, false,
			serviceContext);

		_userLocalService.updateReminderQuery(
			user.getUserId(), securityQuestion, securityAnswer);

		_userLocalService.updateAgreedToTermsOfUse(
			user.getUserId(), acceptedTOU);

		return user;
	}

	protected void createAccount(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		boolean autoScreenName = false;
		String username = ParamUtil.getString(actionRequest, "username");
		String emailAddress = ParamUtil.getString(
			actionRequest, "email_address");
		String firstName = ParamUtil.getString(actionRequest, "first_name");
		String lastName = ParamUtil.getString(actionRequest, "last_name");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(actionRequest, "b_month");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "b_day");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "b_year");
		String password1 = ParamUtil.getString(actionRequest, "password1");
		String password2 = ParamUtil.getString(actionRequest, "password2");
		String securityQuestion = ParamUtil.getString(
			actionRequest, "security_question");
		String securityAnswer = ParamUtil.getString(
			actionRequest, "security_answer");
		boolean acceptedTOU = ParamUtil.getBoolean(
			actionRequest, "accepted_tou", false);

		validateUser(
			firstName, lastName, emailAddress, username, birthdayMonth,
			birthdayDay, birthdayYear, password1, password2, securityQuestion,
			securityAnswer, acceptedTOU);

		String homePhone = ParamUtil.getString(actionRequest, "home_phone");
		String mobilePhone = ParamUtil.getString(actionRequest, "mobile_phone");

		validatePhone(homePhone, mobilePhone);

		String address = ParamUtil.getString(actionRequest, "address");
		String address2 = ParamUtil.getString(actionRequest, "address2");
		String city = ParamUtil.getString(actionRequest, "city");
		String state = ParamUtil.getString(actionRequest, "state");
		String zip = ParamUtil.getString(actionRequest, "zip");

		validateAddress(address, address2, city, state, zip);

		User user = addUser(
			actionRequest, company.getCompanyId(), firstName, lastName,
			emailAddress, username, male, birthdayMonth, birthdayDay,
			birthdayYear, password1, password2, securityQuestion,
			securityAnswer, acceptedTOU);

		addPhone(actionRequest, user, homePhone, _LIST_TYPE_NAME_HOME);
		addPhone(actionRequest, user, mobilePhone, _LIST_TYPE_NAME_MOBILE);

		addAddress(actionRequest, user, address, address2, city, state, zip);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			createAccount(actionRequest, actionResponse);

			HttpServletRequest request = _portal.getHttpServletRequest(
				actionRequest);

			SessionMessages.add(request, "accountCreated");

			hideDefaultSuccessMessage(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof AddressCityException ||
				e instanceof AddressStateException ||
				e instanceof AddressStreetException ||
				e instanceof AddressZipException ||
				e instanceof BirthdayException ||
				e instanceof CompanyMaxUsersException ||
				e instanceof ContactBirthdayException ||
				e instanceof ContactNameException ||
				e instanceof DuplicateOpenIdException ||
				e instanceof EmailAddressException ||
				e instanceof EmailAddressException ||
				e instanceof FirstNameException ||
				e instanceof GroupFriendlyURLException ||
				e instanceof HomePhoneException ||
				e instanceof LastNameException ||
				e instanceof MobilePhoneException ||
				e instanceof NoSuchCountryException ||
				e instanceof NoSuchListTypeException ||
				e instanceof NoSuchOrganizationException ||
				e instanceof NoSuchRegionException ||
				e instanceof OrganizationParentException ||
				e instanceof PasswordException ||
				e instanceof PhoneNumberException ||
				e instanceof RequiredFieldException ||
				e instanceof RequiredUserException ||
				e instanceof SecurityAnswerException ||
				e instanceof SecurityQuestionException ||
				e instanceof TermsOfUseException ||
				e instanceof UserEmailAddressException ||
				e instanceof UserIdException ||
				e instanceof UsernameException ||
				e instanceof UserPasswordException ||
				e instanceof UserScreenNameException ||
				e instanceof UserSmsException ||
				e instanceof WebsiteURLException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}

		sendRedirect(actionRequest, actionResponse);
	}

	protected long getContactId(long userId) {
		long userClassNameId = _portal.getClassNameId(User.class);

		List<Contact> contacts = _contactLocalService.getContacts(
			userClassNameId, userId, 0, 1, null);

		Contact contact = contacts.get(0);

		return contact.getContactId();
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	protected void validateAddress(
			String address, String address2, String city, String state,
			String zip)
		throws Exception {

		validateStreetAddress(address, address2);
		validateCityAddress(city);
		validateStateAddress(state);
		validateZipAddress(zip);
	}

	protected void validateBirthday(
			int birthdayMonth, int birthdayDay, int birthdayYear)
		throws Exception {

		Date birthdayDate = new GregorianCalendar(
			birthdayYear, birthdayMonth, birthdayDay).getTime();

		int age = CalendarUtil.getAge(birthdayDate, Calendar.getInstance());

		if (age < AMFRegistrationConfigurationValues.AGE_MIN) {
			throw new BirthdayException.MinAge(
				AMFRegistrationConfigurationValues.AGE_MIN);
		}
	}

	protected void validateCityAddress(String city) throws Exception {
		if (Validator.isNull(city)) {
			throw new AddressCityException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(city)) {
			throw new AddressCityException.MustBeAlphanumeric();
		}
		else if (city.length() >
					AMFRegistrationConfigurationValues.CITY_MAX_LENGTH) {

			throw new AddressCityException.MaxLength(
				AMFRegistrationConfigurationValues.CITY_MAX_LENGTH);
		}
	}

	protected void validateEmailAddress(String emailAddress) throws Exception {
		if (Validator.isNull(emailAddress)) {
			throw new EmailAddressException.MustNotBeNull();
		}
		else if (!emailAddress.matches(_REGEX_EMAIL_ADDRESS)) {
			throw new EmailAddressException.MustBeAlphanumeric();
		}
		else if (emailAddress.length() >
					AMFRegistrationConfigurationValues.
						EMAIL_ADDRESS_MAX_LENGTH) {

			throw new EmailAddressException.MaxLength(
				AMFRegistrationConfigurationValues.EMAIL_ADDRESS_MAX_LENGTH);
		}
	}

	protected void validateFirstName(String firstName) throws Exception {
		if (Validator.isNull(firstName)) {
			throw new FirstNameException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(firstName)) {
			throw new FirstNameException.MustBeAlphanumeric();
		}
		else if (firstName.length() >
					AMFRegistrationConfigurationValues.FIRST_NAME_MAX_LENGTH) {

			throw new FirstNameException.MaxLength(
				AMFRegistrationConfigurationValues.FIRST_NAME_MAX_LENGTH);
		}
	}

	protected void validateHomePhone(String homePhone) throws Exception {
		if (Validator.isNull(homePhone)) {
			return;
		}

		if (!Validator.isDigit(homePhone)) {
			throw new HomePhoneException.MustBeNumeric();
		}
		else if (homePhone.length() !=
					AMFRegistrationConfigurationValues.PHONE_LENGTH) {

			throw new HomePhoneException.MustHaveLength(
				AMFRegistrationConfigurationValues.PHONE_LENGTH);
		}
	}

	protected void validateLastName(String lastName) throws Exception {
		if (Validator.isNull(lastName)) {
			throw new LastNameException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(lastName)) {
			throw new LastNameException.MustBeAlphanumeric();
		}
		else if (lastName.length() >
					AMFRegistrationConfigurationValues.FIRST_NAME_MAX_LENGTH) {

			throw new LastNameException.MaxLength(
				AMFRegistrationConfigurationValues.LAST_NAME_MAX_LENGTH);
		}
	}

	protected void validateMobilePhone(String mobilePhone) throws Exception {
		if (Validator.isNull(mobilePhone)) {
			return;
		}

		if (!Validator.isDigit(mobilePhone)) {
			throw new MobilePhoneException.MustBeNumeric();
		}
		else if (mobilePhone.length() !=
					AMFRegistrationConfigurationValues.PHONE_LENGTH) {

			throw new MobilePhoneException.MustHaveLength(
				AMFRegistrationConfigurationValues.PHONE_LENGTH);
		}
	}

	protected void validatePassword(String password1, String password2)
		throws Exception {

		if (Validator.isNull(password1)) {
			throw new PasswordException.MustNotBeNull();
		}

		if (password1.length() <
				AMFRegistrationConfigurationValues.PASSWORD_MIN_LENGTH) {

			throw new PasswordException.MinLength(
				AMFRegistrationConfigurationValues.PASSWORD_MIN_LENGTH);
		}

		if (!password1.matches(_REGEX_UPPERCASE)) {
			throw new PasswordException.MustHaveUppercase();
		}
		else if (!password1.matches(_REGEX_DIGIT)) {
			throw new PasswordException.MustHaveNumber();
		}
		else if (!password1.matches(_REGEX_SPECIAL_CHARACTER)) {
			throw new PasswordException.MustHaveSpecial();
		}
		else if (!password1.equals(password2)) {
			throw new UserPasswordException.MustMatch(0L);
		}
	}

	protected void validatePhone(String homePhone, String mobilePhone)
		throws Exception {

		validateHomePhone(homePhone);
		validateMobilePhone(mobilePhone);
	}

	protected void validateSecurityAnswer(String securityAnswer)
		throws Exception {

		if (Validator.isNull(securityAnswer)) {
			throw new SecurityAnswerException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(securityAnswer)) {
			throw new SecurityAnswerException.MustBeAlphanumeric();
		}
		else if (securityAnswer.length() >
					AMFRegistrationConfigurationValues.
						SECURITY_ANSWER_MAX_LENGTH) {

			throw new SecurityAnswerException.MaxLength(
				AMFRegistrationConfigurationValues.SECURITY_ANSWER_MAX_LENGTH);
		}
	}

	protected void validateSecurityQuestion(String securityQuestion)
		throws Exception {

		if (Validator.isNull(securityQuestion)) {
			throw new SecurityQuestionException.MustNotBeNull();
		}
	}

	protected void validateStateAddress(String state) throws Exception {
		Country country = _countryService.getCountryByA3(_COUNTRY_A3_USA);

		Region region = _regionService.fetchRegion(
			country.getCountryId(), state);

		if (region == null) {
			throw new AddressStateException();
		}
	}

	protected void validateStreetAddress(String address, String address2)
		throws Exception {

		if (Validator.isNull(address)) {
			throw new AddressStreetException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(address)) {
			throw new AddressStreetException.MustBeAlphanumeric();
		}
		else if (address.length() >
					AMFRegistrationConfigurationValues.ADDRESS_MAX_LENGTH) {

			throw new AddressStreetException.MaxLength(
				AMFRegistrationConfigurationValues.ADDRESS_MAX_LENGTH);
		}

		if (Validator.isNotNull(address2)) {
			if (!Validator.isAlphanumericName(address2)) {
				throw new AddressStreetException.MustBeAlphanumeric();
			}
			else if (address2.length() >
						AMFRegistrationConfigurationValues.ADDRESS_MAX_LENGTH) {

				throw new AddressStreetException.MaxLength(
					AMFRegistrationConfigurationValues.ADDRESS_MAX_LENGTH);
			}
		}
	}

	protected void validateTermsOfUser(boolean acceptedTOU) throws Exception {
		if (!acceptedTOU) {
			throw new TermsOfUseException();
		}
	}

	protected void validateUser(
			String firstName, String lastName, String emailAddress,
			String username, int birthdayMonth, int birthdayDay,
			int birthdayYear, String password1, String password2,
			String securityQuestion, String securityAnswer, boolean acceptedTOU)
		throws Exception {

		validateFirstName(firstName);
		validateLastName(firstName);
		validateEmailAddress(emailAddress);
		validateUsername(username);
		validateBirthday(birthdayMonth, birthdayDay, birthdayYear);
		validatePassword(password1, password2);
		validateSecurityQuestion(securityQuestion);
		validateSecurityAnswer(securityAnswer);
		validateTermsOfUser(acceptedTOU);
	}

	protected void validateUsername(String username) throws Exception {
		if (Validator.isNull(username)) {
			throw new UsernameException.MustNotBeNull();
		}
		else if (!Validator.isAlphanumericName(username)) {
			throw new UsernameException.MustBeAlphanumeric();
		}
		else if (username.length() <
					AMFRegistrationConfigurationValues.USERNAME_MIN_LENGTH) {

			throw new UsernameException.MinLength(
				AMFRegistrationConfigurationValues.USERNAME_MIN_LENGTH);
		}
		else if (username.length() >
					AMFRegistrationConfigurationValues.USERNAME_MAX_LENGTH) {

			throw new UsernameException.MaxLength(
				AMFRegistrationConfigurationValues.USERNAME_MAX_LENGTH);
		}
	}

	protected void validateZipAddress(String zip) throws Exception {
		if (Validator.isNull(zip)) {
			throw new AddressZipException.MustNotBeNull();
		}
		else if (!Validator.isDigit(zip)) {
			throw new AddressZipException.MustBeNumeric();
		}
		else if (zip.length() !=
					AMFRegistrationConfigurationValues.ZIP_LENGTH) {

			throw new AddressZipException.MustHaveLength(
				AMFRegistrationConfigurationValues.ZIP_LENGTH);
		}
	}

	private static final String _COUNTRY_A3_USA = "USA";

	private static final String _LIST_TYPE_NAME_BILLING = "billing";

	private static final String _LIST_TYPE_NAME_HOME = "home";

	private static final String _LIST_TYPE_NAME_MOBILE = "mobile-phone";

	private static final String _REGEX_DIGIT = ".*\\d.*";

	private static final String _REGEX_EMAIL_ADDRESS =
		"[\\w\\d\\.]*\\@[\\w\\d\\.]*";

	private static final String _REGEX_SPECIAL_CHARACTER =
		".*[\\s!\"#$%&'()*+,\\-./:;<=>?@[\\\\]^_`{|}~].*";

	private static final String _REGEX_UPPERCASE = ".*[A-Z]+.*";

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AuthenticatedSessionManager _authenticatedSessionManager;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private CountryService _countryService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RegionService _regionService;

	private UserLocalService _userLocalService;
	private UserService _userService;

}