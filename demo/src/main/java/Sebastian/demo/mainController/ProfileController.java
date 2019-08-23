package Sebastian.demo.mainController;

import java.util.Locale;

import javax.ws.rs.GET;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Sebastian.demo.UserIntefaces.UserService;
import Sebastian.demo.user.User;
import Sebastian.demo.utilities.UserUtilities;
import Sebastian.demo.validators.ChangePasswordValidation;
import Sebastian.demo.validators.EditUserProfileValidator;

@Controller(value = "profilController")
@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
public class ProfileController {
	
	@Autowired
	private UserService userServices;
	
	@Autowired
	private MessageSource messageSource;
	
	@GET
	@RequestMapping(value = "/profil")
	public String showUserProfilePage(Model model) {
		String userName = UserUtilities.getLoggedUser();
		User user = userServices.findUserByEmail(userName);
		int nrRoli = user.getRoles().iterator().next().getId();
		user.setNrRoli(nrRoli);
		model.addAttribute("user", user);
		return "profil";
	}
	
	@GET
	@RequestMapping(value = "/editpassword")
	public String editUserPassword(Model model) {
		String username = UserUtilities.getLoggedUser();
		User user = userServices.findUserByEmail(username);
		model.addAttribute("user", user);
		return "editpassword";
	}
	
	@RequestMapping(value = "/updatepass",method = {RequestMethod.GET,RequestMethod.POST})
	public String changeUSerPassword(User user, BindingResult result, Model model, Locale locale) throws Exception {
		String returnPage = null;
		
		new ChangePasswordValidation().validate(user, result);
		new ChangePasswordValidation().checkPasswords(user.getNewPassword(), result);
		new ChangePasswordValidation().confirmPassword(user.getNewPassword(), user.getConfirmPassword(), result);
		/*String userName = UserUtilities.getLoggedUser();
		  String password = userServices.getPasswordByEmail(userName);
		  new ChangePasswordValidation().CheckOldPassword(user.getOldPassword(),password, result);*/
		if (result.hasErrors()) {
			returnPage = "editpassword";
		} else {
			userServices.updateUserPass(user.getNewPassword(), user.getEmail());
			returnPage = "editpassword";
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
		}
		return returnPage;
	}
	@GET
	@RequestMapping(value = "/editprofil")
	public String changeUserData(Model model) {
		String username = UserUtilities.getLoggedUser();
		User user = userServices.findUserByEmail(username);
		model.addAttribute("user", user);
		return "editprofil";
	}
	
	@RequestMapping(value = "/updateprofil", method = RequestMethod.POST)
	public String changeUserDataAction(User user, BindingResult result, Model model, Locale locale) {
		String userName = UserUtilities.getLoggedUser();
		
		String returnPage = null;
		new EditUserProfileValidator().validate(user, result);
		if(result.hasErrors()) {
			returnPage = "editprofil";
		}else {
			userServices.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), userName);
			model.addAttribute("message", messageSource.getMessage("profilEdit.success", null,locale));
			returnPage = "afteredit";
		}
		return returnPage;
	}
}
