package Sebastian.demo.validators;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import Sebastian.demo.user.User;
import Sebastian.demo.utilities.Utils;
import Sebastian.demo.constants.Constants;

public class ChangePasswordValidation implements Validator {
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		@SuppressWarnings("unused")
		User u = (User) obj;
		
		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");
	}

	public void checkPasswords(String newPass,Errors errors) {
		if(!newPass.equals(null)) {
			Boolean isMatch = Utils.checkEmailOrPassword(Constants.PASSWORD_PATTERN, newPass);
			if(!isMatch) {
				errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
			}
		}
	}
	
	public void CheckOldPassword(String passwordLoginPage,String hashedPasswordDB, Errors errors) throws Exception{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//Boolean isMatch = encoder.matches(hashedPassword,passwordDB);
		encoder.encode(passwordLoginPage);
		if(!encoder.matches(hashedPasswordDB,passwordLoginPage)) {
			errors.rejectValue("oldPassword", "error.password.old");
		}
	}
	
	public void confirmPassword(String newPass, String confirmPassword, Errors errors){
			if(!newPass.equals(confirmPassword) ){
	            errors.rejectValue("confirmPassword", "error.password.confirmError");		
		}
    }
}
