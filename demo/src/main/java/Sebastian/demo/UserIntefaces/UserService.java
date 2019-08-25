package Sebastian.demo.UserIntefaces;

import Sebastian.demo.user.User;

public interface UserService {
	User findUserByEmail(String email);
	void saveUser(User user);
	void updateUserPass(String newPassword, String email);
	void updateUserProfile(String newName, String newLastName, String newEmail, String user_id);
	String getPasswordByEmail(String email);
	void updateuserActivation(int activeCode, String activationCode);
}
