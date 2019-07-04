package rikkei.test.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class UserCase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Vui Lòng Nhập id user")
    @Column(name = "id_user", nullable = false)
    private String idUser;

    @NotEmpty(message = "Vui Lòng Nhập name user")
    @Column(name = "name_user", nullable = false)
    private String nameUser;

    @NotEmpty(message = "Vui Lòng chọn role user")
    @Column(name = "id_role_user", nullable = false)
    private String idRoleUser;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "creat_by", nullable = false)
    private String creatBy;

    @Column(name = "creat_at")
    private String creatAt;

    @Column(name = "update_by", nullable = false)
    private String updateBy;

    @Column(name = "update_at")
    private String updateAt;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "del_flag", nullable = false)
    private boolean delFlag;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role_user", referencedColumnName = "id_role", insertable = false, updatable = false, nullable = false)
    private UserRole userRole;

    @Transient
    private String oldPass;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getIdRoleUser() {
        return idRoleUser;
    }

    public void setIdRoleUser(String idRoleUser) {
        this.idRoleUser = idRoleUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy;
    }

    public String getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(String creatAt) {
        this.creatAt = creatAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

}
