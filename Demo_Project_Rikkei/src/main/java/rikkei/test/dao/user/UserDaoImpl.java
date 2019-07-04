package rikkei.test.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import rikkei.test.dao.ResponsitoryDaoImpl;
import rikkei.test.entity.UserCase;

/**
 * UserDaoImpl
 *
 * @author NhatLKH
 *
 */

@Repository
public class UserDaoImpl extends ResponsitoryDaoImpl<UserCase, Integer> implements UserDao {

    @Override
    public List<UserCase> listUserByStatus(Integer status) {
        List<UserCase> userList = createQuery("from UserCase where status='" + status + "' ORDER BY idRoleUser ASC")
                .list();
        return userList;
    }

    @Override
    public int getRecordsTotalByStatus(Integer status) {
        int rowCount = createQuery("from UserCase where status='" + status + "'").list().size();
        return rowCount;
    }

    @Override
    public int getRecordsTotalbyIdUser(String idUser) {
        int rowCount = createQuery("from UserCase where idUser='" + idUser + "'").list().size();
        return rowCount;
    }

    @Override
    public UserCase findByIdUser(String idUser) {
        UserCase user = createQuery("from UserCase where idUser='" + idUser + "'").list().get(0);
        return user;
    }

    @Override
    public int getRecordsTotal() {
        int rowCount = createQuery("from UserCase").list().size();
        return rowCount;
    }

    @Override
    public List<UserCase> listUser() {
        List<UserCase> userList = createQuery("from UserCase").list();
        return userList;
    }

}
