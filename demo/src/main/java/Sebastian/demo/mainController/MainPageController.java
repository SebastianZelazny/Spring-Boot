package Sebastian.demo.mainController;

import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainPageController.class);
	
	@GET
	@RequestMapping(value = {"/", "/index"})
	public String showMainPage() {
		logger.debug("*****Wywolano - > showMainPage()");
		return "index";
	}
}
