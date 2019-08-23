package Sebastian.demo.AdminInterfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Sebastian.demo.user.User;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {
	User findUserById(int user_id);
	
	@Modifying
	@Query("UPDATE User u SET u.active = :intActive WHERE u.id = :id")
	void updateActivationUser(@Param("intActive")int active, @Param("id") int id);
	
	@Modifying
	@Query(value = "UPDATE user_role ur SET ur.role_id = :roleId where ur.user_id = :user_id",nativeQuery = true)
	void updateRoleUser(@Param("roleId") int roleID,@Param("user_id") int user_id);
	
	@Query(value ="SELECT * FROM User u WHERE u.name LIKE %:param% OR u.last_name LIKE %:param% OR u.email LIKE %:param%",nativeQuery = true)
	Page<User> findAllSearch(Pageable pagable2, @Param("param") String param);
	
	@Modifying
	@Query(value = "DELETE FROM user_role WHERE user_id = :id", nativeQuery = true)
	void deleteUserFromUserRole(@Param("id") int id);
	
	@Modifying
	@Query(value = "DELETE FROM user WHERE user_id = :id", nativeQuery = true)
	void deleteUserFromUser(@Param("id") int id);
}
