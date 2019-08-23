package Sebastian.demo.mainController;

import javax.ws.rs.GET;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorPageController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@GET
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String showErrorPage(){
		return "error";
	}
}
