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
@Table(name = "member_team")
public class MemberTeam implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Vui Lòng Nhập id team")
	@Column(name = "id_team", nullable = false)
	private String idTeam;

	@NotEmpty(message = "Vui Lòng Nhập id user")
	@Column(name = "id_user", nullable = false)
	private String idUser;

	@NotEmpty(message = "Vui Lòng chọn chọn role team")
	@Column(name = "id_role_team", nullable = false)
	private String idRoleTeam;

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

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getIdRoleTeam() {
		return idRoleTeam;
	}

	public void setIdRoleTeam(String idRoleTeam) {
		this.idRoleTeam = idRoleTeam;
	}

}
