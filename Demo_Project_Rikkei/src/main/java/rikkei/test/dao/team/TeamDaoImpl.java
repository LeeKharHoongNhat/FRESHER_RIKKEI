package rikkei.test.dao.team;

import java.util.List;

import org.springframework.stereotype.Repository;

import rikkei.test.dao.ResponsitoryDaoImpl;
import rikkei.test.entity.TeamProject;
@Repository
public class TeamDaoImpl extends ResponsitoryDaoImpl<TeamProject,Integer> implements TeamDao {

	@Override
	public List<TeamProject> getAllTeamByDelFlag( Integer delFlag) {
		List<TeamProject> listTeam = createQuery("from TeamProject where delFlag='"+delFlag+"'").list();
		return listTeam;
	}

	@Override
	public List<TeamProject> getAllTeamByStatus(Integer status) {
		List<TeamProject> listTeam = createQuery("from TeamProject where status='"+status+"'").list();
		return listTeam;
	}

	@Override
	public List<TeamProject> getAllTeamByIdLeader(String idLeader) {
		List<TeamProject> listTeam = createQuery("from TeamProject where idLeader='"+idLeader+"'").list();
		return listTeam;
	}

	@Override
	public List<TeamProject> getAllTeamByIdUser(String idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamProject> getAllTeam() {
		List<TeamProject> listTeam = createQuery("from TeamProject ").list();
		return listTeam;
	}

}
