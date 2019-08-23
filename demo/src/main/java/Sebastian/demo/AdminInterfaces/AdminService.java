package Sebastian.demo.AdminInterfaces;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import Sebastian.demo.user.User;

public interface AdminService {
	Page<User> findAll(Pageable pageable);
	User findUserById(int user_id);
	void updateUser(int id, int nrRoli, int activity);
	Page<User> findAllSearch(Pageable pagable2,String param);
	void insertInBatch(List<User> userList);
	void saveAll(List<User> userList);
	void deleteUserById(int id);
}

