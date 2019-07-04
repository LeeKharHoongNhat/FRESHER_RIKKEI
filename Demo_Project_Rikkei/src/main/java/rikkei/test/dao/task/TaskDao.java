package rikkei.test.dao.task;

import java.util.List;

import rikkei.test.dao.RepositoryDao;
import rikkei.test.entity.Task;

/**
 * TaskDao
 *
 * @author NhatLKH
 *
 */
public interface TaskDao extends RepositoryDao<Task, Integer> {

    public List<Task> getTaskByIdUser(String idUser, Integer delFlag);

    public List<Task> getTaskByIdParticipants(String idUser, Integer delFlag);

    public Task getTaskByIdUser(String idUser);

    public Task getTaskById(Integer id);

    public Integer checkTaskParticipants(String idUser, Integer delFlag);

    public Integer getRecordsTotal();
}
