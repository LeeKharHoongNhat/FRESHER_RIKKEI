package rikkei.test.service.team;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rikkei.test.dao.team.TeamDao;
import rikkei.test.entity.TeamProject;
@Service
public class TeamServiceImpl  implements TeamService{

	@Autowired
	private TeamDao teamDao;
	
	@Override
	@Transactional
	public boolean addNew(TeamProject team) {
		boolean flag = false;
		try {
			this.teamDao.save(team);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean update(TeamProject team) {
		boolean flag = false;
		try {
			this.teamDao.update(team);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean delete(TeamProject team) {
		boolean flag = false;
		try {
			this.teamDao.delete(team);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public TeamProject getTeambyId(Integer id) {
		return this.teamDao.findById(id);
	}

	@Override
	@Transactional
	public List<TeamProject> getAllTeamByDelFlag(Integer delFlag) {
		return this.teamDao.getAllTeamByDelFlag(delFlag);
	}

}
