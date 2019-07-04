package rikkei.test.dao.task;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import rikkei.test.dao.ResponsitoryDaoImpl;
import rikkei.test.dao.user.UserDao;
import rikkei.test.entity.Task;

/**
 * TaskDaoImpl
 *
 * @author NhatLKH
 *
 */
@Repository
public class TaskDaoImpl extends ResponsitoryDaoImpl<Task, Integer> implements TaskDao {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> getTaskByIdUser(String idUser, Integer delFlag) {
        List<Task> listTask = createQuery("from Task where createBy='" + idUser + "' and delFlag='" + delFlag + "'")
                .list();
        listTask.forEach(task -> {
            task.setUserCase(userDao.findByIdUser(task.getCreateBy()));
        });
        return listTask;
    }

    @Override
    public Integer getRecordsTotal() {
        Integer count = createQuery("from Task ").list().size();
        return count;
    }

    @Override
    public Task getTaskByIdUser(String idUser) {
        List<Task> listTask = createQuery("from Task where createBy='" + idUser + "'").list();
        listTask.forEach(task -> {
            task.setUserCase(userDao.findByIdUser(task.getCreateBy()));
        });
        Task taskWork = listTask.get(0);
        return taskWork;
    }

    @Override
    public Task getTaskById(Integer id) {
        List<Task> listTask = createQuery("from Task where id='" + id + "'").list();
        listTask.forEach(task -> {
            task.setUserCase(userDao.findByIdUser(task.getCreateBy()));
        });
        Task taskWork = listTask.get(0);
        return taskWork;
    }

    @Override
    public List<Task> getTaskByIdParticipants(String idUser, Integer delFlag) {
        List<Task> listTask = createQuery("from Task where participants1='" + idUser + "' and delFlag='" + delFlag + "'").list();
        listTask.forEach(task -> {
            task.setUserCase(userDao.findByIdUser(task.getCreateBy()));
        });
        return listTask;
    }

    @Override
    public Integer checkTaskParticipants(String idUser, Integer delFlag) {
        int rowCount = createQuery("SELECT * FROM Task WHERE participants1='US001' and delFlag= '0'").list().size();
        return rowCount;
    }

}
