package Sebastian.demo.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import Sebastian.demo.constants.Constants;
import Sebastian.demo.user.User;
import Sebastian.demo.utilities.Utils;

public class EditUserProfileValidator implements Validator{

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User u = (User) obj;
		
		ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
		
		if(!u.getEmail().equals(null)){
			if(!Utils.checkEmailOrPassword(Constants.EMAIL_PATTERN, u.getEmail())) {
				errors.rejectValue("email", "error.userEmailisNotMatch");
			}
		}
	}

}
