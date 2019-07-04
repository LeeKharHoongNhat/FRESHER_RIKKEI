package rikkei.test.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role_in_user")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Vui Lòng Nhập id role")
	@Column(name = "id_role", nullable = false)
	private String idRole;

	@NotEmpty(message = "Vui Lòng Nhập name role")
	@Column(name = "name_role", nullable = false)
	private String nameRole;

	@Column(name = "creat_by", nullable = false)
	private String creatBy;

	@Column(name = "creat_at")
	@NotNull(message = "Vui Lòng Nhập ngày")
	private Timestamp creatAt;

	@Column(name = "update_by", nullable = false)
	private String updateBy;

	@Column(name = "update_at")
	@NotNull(message = "Vui Lòng Nhập ngày")
	private Timestamp updateAt;

	@OneToMany(mappedBy = "userRole", fetch = FetchType.EAGER)
	private List<UserCase> listUser;

//	@ManyToMany(mappedBy = "roles")
//	private Set<UserCase> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdRole() {
		return idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public String getCreatBy() {
		return creatBy;
	}

	public void setCreatBy(String creatBy) {
		this.creatBy = creatBy;
	}

	public Timestamp getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(Timestamp creatAt) {
		this.creatAt = creatAt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

//	public Set<UserCase> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<UserCase> users) {
//		this.users = users;
//	}

	public List<UserCase> getListUser() {
		return listUser;
	}

	public void setListUser(List<UserCase> listUser) {
		this.listUser = listUser;
	}

}
