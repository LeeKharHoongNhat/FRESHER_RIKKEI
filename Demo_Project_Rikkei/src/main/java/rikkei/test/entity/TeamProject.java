package rikkei.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "team_project")
public class TeamProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Vui Lòng Nhập id team")
	@Column(name = "id_team", nullable = false)
	private String idTeam;

	@NotEmpty(message = "Vui Lòng Nhập name team")
	@Column(name = "name_team", nullable = false)
	private String nameTeam;

	@NotEmpty(message = "Vui Lòng chọn nhập description")
	@Column(name = "description", nullable = false)
	private String description;

	@NotEmpty(message = "Vui Lòng nhập leader")
	@Column(name = "id_leader", nullable = false)
	private String idLeader;

	@Column(name = "creat_at")
	private String creatAt;

	@Column(name = "creat_by", nullable = false)
	private String creatBy;

	@Column(name = "update_at")
	private String updateAt;

	@Column(name = "update_by", nullable = false)
	private String updateBy;

	@Column(name = "status", nullable = false)
	private Integer status;

	@Column(name = "del_flag", nullable = false)
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(String idTeam) {
		this.idTeam = idTeam;
	}

	public String getNameTeam() {
		return nameTeam;
	}

	public void setNameTeam(String nameTeam) {
		this.nameTeam = nameTeam;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdLeader() {
		return idLeader;
	}

	public void setIdLeader(String idLeader) {
		this.idLeader = idLeader;
	}

	public String getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(String creatAt) {
		this.creatAt = creatAt;
	}

	public String getCreatBy() {
		return creatBy;
	}

	public void setCreatBy(String creatBy) {
		this.creatBy = creatBy;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
