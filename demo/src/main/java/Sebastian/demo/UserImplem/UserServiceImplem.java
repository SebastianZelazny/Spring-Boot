package Sebastian.demo.UserImplem;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Sebastian.demo.UserIntefaces.RoleRepository;
import Sebastian.demo.UserIntefaces.UserRepository;
import Sebastian.demo.UserIntefaces.UserService;
import Sebastian.demo.user.Role;
import Sebastian.demo.user.User;

@Service("userService")
@Transactional
public class UserServiceImplem implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role role = roleRepository.findByRole("ROLE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		userRepository.save(user);
	}

	@Override
	public void updateUserPass(String newPassword, String email) {
		userRepository.updateUserPassword(bCryptPasswordEncoder.encode(newPassword), email);
		
	}

	@Override
	public String getPasswordByEmail(String email) {
		return userRepository.getPasswordByEmail(email);
	}

	@Override
	public void updateUserProfile(String newName, String newLastName, String newEmail, String user_id) {
		userRepository.updateUserProfile(newName, newLastName, newEmail, user_id);
		
	}

}
