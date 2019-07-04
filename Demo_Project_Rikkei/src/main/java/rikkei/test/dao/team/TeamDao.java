package rikkei.test.dao.team;

import java.util.List;

import rikkei.test.dao.RepositoryDao;
import rikkei.test.entity.TeamProject;

public interface TeamDao extends RepositoryDao<TeamProject, Integer> {
	
	public List<TeamProject> getAllTeamByDelFlag( Integer delFlag);
	
	public List<TeamProject> getAllTeamByStatus( Integer status);
	
	public List<TeamProject> getAllTeamByIdLeader( String idLeader);
	
	public List<TeamProject> getAllTeamByIdUser( String idUser);
	
	public List<TeamProject> getAllTeam();
}
