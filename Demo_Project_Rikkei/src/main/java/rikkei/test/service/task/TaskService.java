package rikkei.test.service.task;

import java.util.List;

import rikkei.test.entity.Task;

public interface TaskService {
    boolean addNew(Task task);

    boolean update(Task task);

    boolean delete(Task task);

    public Task getTaskById(Integer id);

    public List<Task> getAllTask();

    public List<Task> getTaskByIdUser(String idUser, Integer delFlag);

    public List<Task> getTaskByIdParticipants(String idUser, Integer delFlag);

    public Task getTaskByIdUser(String idUser);

    public Integer getRecordsTaskParticipants(String idUser, Integer delFlag);

    public Integer getRecordsTotal();
}
