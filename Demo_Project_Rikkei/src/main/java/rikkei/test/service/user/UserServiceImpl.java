package rikkei.test.service.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rikkei.test.dao.user.UserDao;
import rikkei.test.entity.UserCase;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public boolean addNew(UserCase user) {
		boolean flag = false;
		try {
			this.userDao.save(user);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean update(UserCase user) {
		boolean flag = false;
		try {
			this.userDao.update(user);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean delete(UserCase user) {
		boolean flag = false;
		try {
			this.userDao.delete(user);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public UserCase getUserbyId(Integer id) {
		return this.userDao.findById(id);
	}

	@Override
	@Transactional
	public List<UserCase> listUserByStatus(Integer status) {
		return this.userDao.listUserByStatus(status);
	}

	@Override
	@Transactional
	public int getRecordsTotal(Integer status) {
		return this.userDao.getRecordsTotalByStatus(status);
	}

	@Override
	@Transactional
	public int getRecordsTotalbyIdUser(String idUser) {
		return this.userDao.getRecordsTotalbyIdUser(idUser);
	}

	@Override
	@Transactional
	public UserCase findByIdUser(String idUser) {
		return this.userDao.findByIdUser(idUser);
	}

	@Override
	@Transactional
	public List<UserCase> listUser() {
		return this.userDao.listUser();
	}


}
