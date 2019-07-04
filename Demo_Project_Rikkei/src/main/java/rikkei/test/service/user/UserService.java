package rikkei.test.service.user;

import java.util.List;

import rikkei.test.entity.UserCase;

public interface UserService {

	boolean addNew(UserCase user);

	boolean update(UserCase user);

	boolean delete(UserCase user);

	public UserCase getUserbyId(Integer id);
	
	public List<UserCase> listUser();

	public List<UserCase> listUserByStatus(Integer status);

	public int getRecordsTotal(Integer status);

	public int getRecordsTotalbyIdUser(String idUser);
	
	public UserCase findByIdUser(String idUser);

	
	
}
