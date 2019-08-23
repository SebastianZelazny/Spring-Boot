package Sebastian.demo.UserIntefaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sebastian.demo.user.Role;
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {	
	public Role findByRole(String role);
}
