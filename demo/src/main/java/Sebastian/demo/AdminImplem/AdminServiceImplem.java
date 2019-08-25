package Sebastian.demo.AdminImplem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Sebastian.demo.AdminInterfaces.AdminRepository;
import Sebastian.demo.AdminInterfaces.AdminService;
import Sebastian.demo.UserIntefaces.RoleRepository;
import Sebastian.demo.user.Role;
import Sebastian.demo.user.User;

@Service("adminServicess")
@Transactional
public class AdminServiceImplem implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImplem.class);
	
	@Autowired
	private JpaContext jpaContext;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	public Page<User> findAll(Pageable pagable) {
		Page<User> userList = adminRepository.findAll(pagable);
		return userList;
	}

	@Override
	public User findUserById(int user_id) {
		User user = adminRepository.findUserById(user_id);
		return user;
	}

	@Override
	public void updateUser(int user_id, int nrRoli, int activity) {
		adminRepository.updateRoleUser(nrRoli, user_id);
		adminRepository.updateActivationUser(activity, user_id);
		
	}

	@Override
	public Page<User> findAllSearch(Pageable pagable2,String param) {
		Page<User> userList =  adminRepository.findAllSearch(pagable2,param);
		return userList;
	}

	@Override
	public void insertInBatch(List<User> userList) {
		EntityManager em = jpaContext.getEntityManagerByManagedType(User.class);
		
		for(int i = 0; i<userList.size(); i++) {
			User u = userList.get(i);
			Role role = roleRepository.findByRole("ROLE_USER");
			u.setRoles(new HashSet<Role>(Arrays.asList(role)));
			u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
			em.persist(u);
			if(i % 50 == 0 && i > 0) {
				em.flush();
				em.clear();
				System.out.println("**** Załadowano " + i + " rekordkow z "+ userList.size());
			}
		}
		
	}
	@Override
	public void saveAll(List<User> userList) {
		for(int i = 0; i<userList.size(); i++) {
			Role role = roleRepository.findByRole("ROLE_USER");
			userList.get(i).setRoles(new HashSet<Role>(Arrays.asList(role)));
			userList.get(i).setPassword(bCryptPasswordEncoder.encode(userList.get(i).getPassword()));
		}
		adminRepository.saveAll(userList);
	}

	@Override
	public void deleteUserById(int id) {
		logger.info("***** WYWOLANO =>>>>> AdminServiceImplem.deleteUserById, PARAM: "+id);
		adminRepository.deleteUserFromUserRole(id);
		logger.info("***** WYWOLANO =>>>>> adminRepository.deleteUserFromUserRole, PARAM: "+id);
		adminRepository.deleteUserFromUser(id);
		logger.info("***** WYWOLANO =>>>>> adminRepository.deleteUserFromUser, PARAM: "+id);
	}
}
