package rikkei.test.service.task;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rikkei.test.dao.task.TaskDao;
import rikkei.test.entity.Task;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    @Transactional
    public boolean addNew(Task task) {
        boolean flag = false;
        try {
            this.taskDao.save(task);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean update(Task task) {
        boolean flag = false;
        try {
            this.taskDao.update(task);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean delete(Task task) {
        boolean flag = false;
        try {
            this.taskDao.delete(task);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional
    public Task getTaskById(Integer id) {
        return this.taskDao.getTaskById(id);
    }

    @Override
    @Transactional
    public List<Task> getTaskByIdUser(String idUser, Integer delFlag) {
        return this.taskDao.getTaskByIdUser(idUser, delFlag);
    }

    @Override
    @Transactional
    public Integer getRecordsTotal() {
        return this.taskDao.getRecordsTotal();
    }

    @Override
    @Transactional
    public List<Task> getAllTask() {
        return this.taskDao.findAll();
    }

    @Override
    @Transactional
    public Task getTaskByIdUser(String idUser) {
        return this.taskDao.getTaskByIdUser(idUser);
    }

    @Override
    public List<Task> getTaskByIdParticipants(String idUser, Integer delFlag) {
        return this.taskDao.getTaskByIdParticipants(idUser, delFlag);
    }

    @Override
    public Integer getRecordsTaskParticipants(String idUser, Integer delFlag) {
        return this.taskDao.checkTaskParticipants(idUser, delFlag);

    }

}
