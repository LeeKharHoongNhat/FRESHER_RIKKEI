package rikkei.test.dao.user;

import java.util.List;

import rikkei.test.dao.RepositoryDao;
import rikkei.test.entity.UserCase;

/**
 * UserDao
 *
 * @author NhatLKH
 *
 */
public interface UserDao extends RepositoryDao<UserCase, Integer> {

    public UserCase findByIdUser(String idUser);

    public List<UserCase> listUser();

    public List<UserCase> listUserByStatus(Integer status);

    public int getRecordsTotalByStatus(Integer status);

    public int getRecordsTotal();

    public int getRecordsTotalbyIdUser(String idUser);

}
