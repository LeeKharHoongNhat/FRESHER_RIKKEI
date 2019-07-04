package rikkei.test.service.team;

import java.util.List;

import rikkei.test.entity.TeamProject;

public interface TeamService {
	boolean addNew(TeamProject team);

	boolean update(TeamProject team);

	boolean delete(TeamProject team);

	public TeamProject getTeambyId(Integer id);

	public List<TeamProject> getAllTeamByDelFlag( Integer delFlag);
}
