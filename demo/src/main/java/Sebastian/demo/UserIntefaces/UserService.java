package Sebastian.demo.UserIntefaces;

import Sebastian.demo.user.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void updateUserPass(String newPassword, String email);
	public void updateUserProfile(String newName, String newLastName, String newEmail, String user_id);
	public String getPasswordByEmail(String email);
}
