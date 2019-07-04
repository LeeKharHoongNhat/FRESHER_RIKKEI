package rikkei.test.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "task")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "big_task", nullable = false)
    private Integer bigTask;

    @NotEmpty(message = "Vui Lòng Nhập title")
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty(message = "Vui Lòng chọn nhập detail")
    @Column(name = "detail", nullable = false)
    private String detail;

    @NotEmpty(message = "Vui Lòng nhập location")
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "participants_1", nullable = false)
    private String participants1;

    @Column(name = "participants_2", nullable = false)
    private String participants2;

    @Column(name = "participants_3", nullable = false)
    private String participants3;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "all_day", nullable = false)
    private boolean allDay;

    @Column(name = "Image")
    private String image;

    @Column(name = "creat_by", nullable = false)
    private String createBy;

    @Column(name = "creat_at")
    private Timestamp createAt;

    @Column(name = "update_by", nullable = false)
    private String updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "del_flag", nullable = false)
    private boolean delFlag;

    @Transient
    private UserCase userCase;

    @Transient
    private String checkStartTime;

    @Transient
    private String checkEndTime;

    @Transient
    private boolean booleanTime;

    @Transient
    private String loopTask;

    @Transient
    private String loopWeek;

    @Transient
    private String loopMonth;

    @Transient
    private String loopYear;

    @Transient
    private String endLoopTask;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBigTask() {
        return bigTask;
    }

    public void setBigTask(Integer bigTask) {
        this.bigTask = bigTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParticipants1() {
        return participants1;
    }

    public void setParticipants1(String participants1) {
        this.participants1 = participants1;
    }

    public String getParticipants2() {
        return participants2;
    }

    public void setParticipants2(String participants2) {
        this.participants2 = participants2;
    }

    public String getParticipants3() {
        return participants3;
    }

    public void setParticipants3(String participants3) {
        this.participants3 = participants3;
    }

    public UserCase getUserCase() {
        return userCase;
    }

    public void setUserCase(UserCase userCase) {
        this.userCase = userCase;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public boolean getBooleanTime() {
        return booleanTime;
    }

    public void setBooleanTime(boolean booleanTime) {
        this.booleanTime = booleanTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(String checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getLoopTask() {
        return loopTask;
    }

    public void setLoopTask(String loopTask) {
        this.loopTask = loopTask;
    }

    public String getLoopWeek() {
        return loopWeek;
    }

    public void setLoopWeek(String loopWeek) {
        this.loopWeek = loopWeek;
    }

    public String getLoopMonth() {
        return loopMonth;
    }

    public void setLoopMonth(String loopMonth) {
        this.loopMonth = loopMonth;
    }

    public String getLoopYear() {
        return loopYear;
    }

    public void setLoopYear(String loopYear) {
        this.loopYear = loopYear;
    }

    public String getEndLoopTask() {
        return endLoopTask;
    }

    public void setEndLoopTask(String endLoopTask) {
        this.endLoopTask = endLoopTask;
    }
}
