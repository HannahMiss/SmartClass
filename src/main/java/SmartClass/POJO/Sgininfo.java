package SmartClass.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by 73681 on 2018/2/3.
 */
@Entity
public class Sgininfo implements Serializable
{
    private int id;
    private short courseId;
    private String studentCode;
    private short times;
    private Timestamp timeCreated;

    @Basic
    @Column(name = "courseId", nullable = false)
    public short getCourseId()
    {
        return courseId;
    }

    public void setCourseId(short courseId)
    {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "studentCode", nullable = false, length = 32)
    public String getStudentCode()
    {
        return studentCode;
    }

    public void setStudentCode(String studentCode)
    {
        this.studentCode = studentCode;
    }

    @Basic
    @Column(name = "times", nullable = false)
    public short getTimes()
    {
        return times;
    }

    public void setTimes(short times)
    {
        this.times = times;
    }

    @Basic
    @Column(name = "timeCreated", nullable = false)
    public Timestamp getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sgininfo sgininfo = (Sgininfo) o;

        if (courseId != sgininfo.courseId) return false;
        if (times != sgininfo.times) return false;
        if (id != sgininfo.id) return false;
        if (studentCode != null ? !studentCode.equals(sgininfo.studentCode) : sgininfo.studentCode != null)
            return false;
        if (timeCreated != null ? !timeCreated.equals(sgininfo.timeCreated) : sgininfo.timeCreated != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + (studentCode != null ? studentCode.hashCode() : 0);
        result = 31 * result + (int) times;
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
