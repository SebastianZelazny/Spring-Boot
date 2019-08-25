package Sebastian.demo.UserIntefaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Sebastian.demo.user.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	
	
	@Modifying
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.email= :email")
	public void updateUserPassword(@Param("newPassword") String password, @Param("email") String email);
	
	@Modifying
	@Query("UPDATE User u SET u.name = :newName, u.lastName = :newLastName, u.email = :newEmail  WHERE u.email= :email")
	public void updateUserProfile(@Param("newName") String newName, @Param("newLastName") String newLastName, 
			@Param("newEmail") String newEmail, @Param("email") String email);
	
	@Modifying
	@Query("UPDATE User u SET u.active = :activeParam WHERE u.activationCode = :activationCode")
	public void updateActivation(@Param("activeParam") int activeParam, @Param("activationCode") String activationCode);
	
	@Query("SELECT u.password FROM User u WHERE u.email=:email")
	public String getPasswordByEmail(@Param("email") String email);
}
