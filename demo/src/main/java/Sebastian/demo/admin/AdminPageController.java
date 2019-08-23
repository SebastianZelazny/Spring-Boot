package Sebastian.demo.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Sebastian.demo.AdminInterfaces.AdminService;
import Sebastian.demo.FilesUpload.FileName;
import Sebastian.demo.mainController.MainPageController;
import Sebastian.demo.user.User;
import Sebastian.demo.utilities.UserUtilities;

@Controller
public class AdminPageController {

	private static int ELEMENTS = 10;
	private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

	@Autowired
	private AdminService adminService;
	
	@Autowired
	MessageSource messageSource;

	@DELETE
	@RequestMapping(value = "/admin/adminUsers/delete/{id}")
	@Secured(value = "ROLE_ADMIN")
	private String deleteUser(@PathVariable("id") int id) {
		logger.info("***** Wywolano - > AdminPageController.deleteUser > PARAMS: "+id);
		System.out.println(id);
		adminService.deleteUserById(id);
		return "redirect:/admin/adminUsers/1";
	}
	
	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminMainPage() {
		return "admin/admin";
	}

	@GET
	@RequestMapping(value = "/admin/adminUsers/{page}")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminAllUserPage(Model model, @PathVariable("page") int page) {
		
		logger.info("***** WYWOLANO > openAdminAllUserPage("+ page +", "+model+")");
		Page<User> pages = getAllUsersPageable(page - 1);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userListPages = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		/* List<User> userList = getAllUsers(); */
		model.addAttribute("userList", userListPages);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		return "admin/adminUsers";
	}

	@RequestMapping(value = "/admin/adminUsers/edit/{id}", method = RequestMethod.GET)
	@Secured(value = { "ROLE_ADMIN" })
	public String getusertoEdit(@PathVariable("id") int user_ids, Model model) {

		User user = new User();
		user = adminService.findUserById(user_ids);

		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap = prepareRoleMap();

		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap = prepareActivityMap();

		int rola = user.getRoles().iterator().next().getId();
		user.setNrRoli(rola);

		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		model.addAttribute("user", user);

		String returnPage = "admin/useredit";
		return returnPage;

	}

	@RequestMapping(value = "/admin/updateuser/{id}", method = RequestMethod.POST)
	@Secured(value = "ROLE_ADMIN")
	public String UpdateUser(@PathVariable("id") int id, User user) {
		String returnPage = "redirect:/admin/adminUsers/1";
		int nrRoli = user.getNrRoli();
		int ifActive = user.getActive();
		System.out.println("Active: " + ifActive);
		System.out.println("Nr roli:" + nrRoli);
		adminService.updateUser(id, nrRoli, ifActive);
		return returnPage;
	}

	// przygotowanie mapy ról
	private Map<Integer, String> prepareRoleMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap.put(1, messageSource.getMessage("word.admin", null, locale));
		roleMap.put(2, messageSource.getMessage("word.user", null, locale));
		return roleMap;
	}

	// przygotowanie may aktywny/nieaktywny
	private Map<Integer, String> prepareActivityMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap.put(0, messageSource.getMessage("word.nie", null, locale));
		activityMap.put(1, messageSource.getMessage("word.tak", null, locale));
		return activityMap;
	}

	@GET
	@RequestMapping(value = "/admin/adminUsers/search/{searchWord}/{page}")
	@Secured(value = "ROLE_ADMIN")
	public String openSearchUserPage(@PathVariable("searchWord") String searchWord, @PathVariable("page") int page,
			Model model) {
		String ReturnPage = "admin/usersearch";
		Page<User> pages = getAllSearchedPageable(page - 1, searchWord);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userListPages = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		/* List<User> userList = getAllUsers(); */
		model.addAttribute("userList", userListPages);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		model.addAttribute("searchWord", searchWord);
		return ReturnPage;
	}

	@GET
	@RequestMapping(value = "/admin/adminUsers/importusers")
	@Secured(value = "ROLE_ADMIN")
	public String ShowUsersUploadPageFromXML(Model model) {
		String ReturnPage = "admin/importusers";
		return ReturnPage;
	}

	@RequestMapping(value = "/admin/adminUsers/upload", method = RequestMethod.POST)
	@Secured(value = "ROLE_ADMIN")
	public String importUsersFromXML(@RequestParam("filename") MultipartFile mFile) {
		String ReturnPage = "redirect:/admin/adminUsers/1";
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		//System.out.println(uploadDir);
		File file;
		try {
			file = new File(uploadDir);
			if (!file.exists()) {
				file.mkdir();
			}
			Path fileAndPath = Paths.get(uploadDir, mFile.getOriginalFilename());
			
			//System.out.println(fileAndPath.toString());
			Files.write(fileAndPath, mFile.getBytes());
			
			FileName uploadedFile = new FileName(fileAndPath.toString(), '/', '.');
			String fileName = uploadedFile.filename()+"."+uploadedFile.extension();
			
			File fileTest = new File(uploadDir,fileName);
			Boolean isNewFile = false;
			for(int i = 0; fileTest.exists(); i++) {
				fileName = uploadedFile.filename()+"_("+i+")"+"."+uploadedFile.extension();
				fileAndPath = Paths.get(uploadDir, fileName);
				fileTest = new File(uploadDir,fileName);
				isNewFile = true;
			}
			if(isNewFile == true) {
				Files.write(fileAndPath, mFile.getBytes());
			}
			
			/*
			 * if(Files.ex(fileAndPath)) { String firstPath = fileAndPath.toString();
			 * System.out.println("Extension: "+uploadedFile.extension());
			 * System.out.println("File Name: "+uploadedFile.filename()); String fileName =
			 * uploadedFile.filename()+"_"+"("+ (i++) +")"+"."+uploadedFile.extension();
			 * fileAndPath = Paths.get(uploadDir,fileName); String secondPath =
			 * fileAndPath.toString(); System.out.println(fileAndPath.toString());
			 * 
			 * Files.write(fileAndPath, mFile.getBytes()); }
			 */
			file = new File(fileAndPath.toString());
			List<User> userList = UserUtilities.usersDataLoader(file);
			adminService.insertInBatch(userList);
			//adminService.saveAll(userList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ReturnPage;
	}
	
	
	// Pobranie listy uzykownikow Pageable
	private Page<User> getAllUsersPageable(int page) {
		Page<User> pages = adminService.findAll(PageRequest.of(page, ELEMENTS));
		for (User user : pages) {
			int nrRoli = user.getRoles().iterator().next().getId();
			user.setNrRoli(nrRoli);
		}
		return pages;
	}

	private Page<User> getAllSearchedPageable(int page, String param) {
		Page<User> pages = adminService.findAllSearch(PageRequest.of(page, ELEMENTS), param);
		for (User user : pages) {
			int nrRoli = user.getRoles().iterator().next().getId();
			user.setNrRoli(nrRoli);
		}
		return pages;
	}

	// Pobranie listy userow
	/*
	 * private List<User> getAllUsers() { List<User> userList =
	 * adminService.findAll(); for (User user : userList) { int nrRoli =
	 * user.getRoles().iterator().next().getId();
	 * 
	 * if(nrRoli == 1) { user.setNrRoli(nrRoli); }else if(nrRoli == 2){
	 * user.setNrRoli(nrRoli); }
	 * 
	 * user.setNrRoli(nrRoli); } return userList; }
	 */

}
