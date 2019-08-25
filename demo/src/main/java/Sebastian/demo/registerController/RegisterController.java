package Sebastian.demo.registerController;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Sebastian.demo.UserIntefaces.UserService;
import Sebastian.demo.emailSender.EmailSender;
import Sebastian.demo.user.User;
import Sebastian.demo.utilities.Utils;
import Sebastian.demo.validators.UserRegisterValidator;

@Controller
public class RegisterController  {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	EmailSender emailSender;
	
	@Autowired
	MessageSource messageSource;
	
	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	@POST
	@RequestMapping(value = "adduser")
	public String registerAction(User user, BindingResult result, Model model, Locale local) {
		
		String returnpage = null;
		User userExist = userService.findUserByEmail(user.getEmail());
		
		new UserRegisterValidator().validateEmailExist(userExist, result);
		
		new UserRegisterValidator().validate(user, result);
		
		if(result.hasErrors()) {
			returnpage = "register";
		}else {
			user.setActivationCode(Utils.randomStringGenerator());
			
			String content = "Wymagane potwierdzenie rejestracji. Kliknij w poniższy link w celu potwierdzenia rejestracji: \n"+
			"http://localhost:8080/activatelink/"+user.getActivationCode();
			
			userService.saveUser(user);
			emailSender.sendEmail(user.getEmail(), messageSource.getMessage("user.register.success.email",null, local), content);
			
			model.addAttribute("message", messageSource.getMessage("user.register.success.email.beforeAccept", null,local));
			//model.addAttribute("user",new User());
			returnpage = "index";
		}
		
		return returnpage;
	}
	
	@POST
	@RequestMapping(value = "/activatelink/{activationCode}")
	public String authentication(@PathVariable("activationCode") String activationCode, Model model, Locale locale) {
		String returnPage = "index";
		
		logger.info("***** Wywolano = >>>>>> RegisterController.authentication with Params: " +activationCode);
		userService.updateuserActivation(1, activationCode);
		logger.info("***** Wywolano = >>>>>> userService.updateuserActivation");
		model.addAttribute("message",messageSource.getMessage("user.register.email.accepted",null, locale));
		
		return returnPage;
		
	}

}
