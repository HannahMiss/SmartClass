package SmartClass.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by 73681 on 2018/2/4.
 */
@Entity
public class Communication implements java.io.Serializable
{
    private short courseId;
    private int studenId;
    private byte flag;
    private String descr;
    private Timestamp timeCreated;
    private byte answered;
    private int id;

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
    @Column(name = "studenId", nullable = false)
    public int getStudenId()
    {
        return studenId;
    }

    public void setStudenId(int studenId)
    {
        this.studenId = studenId;
    }

    @Basic
    @Column(name = "flag", nullable = false)
    public byte getFlag()
    {
        return flag;
    }

    public void setFlag(byte flag)
    {
        this.flag = flag;
    }

    @Basic
    @Column(name = "descr", nullable = false, length = -1)
    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
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

    @Basic
    @Column(name = "answered", nullable = false)
    public byte getAnswered()
    {
        return answered;
    }

    public void setAnswered(byte answered)
    {
        this.answered = answered;
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

        Communication that = (Communication) o;

        if (courseId != that.courseId) return false;
        if (studenId != that.studenId) return false;
        if (flag != that.flag) return false;
        if (answered != that.answered) return false;
        if (id != that.id) return false;
        if (descr != null ? !descr.equals(that.descr) : that.descr != null) return false;
        if (timeCreated != null ? !timeCreated.equals(that.timeCreated) : that.timeCreated != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + studenId;
        result = 31 * result + (int) flag;
        result = 31 * result + (descr != null ? descr.hashCode() : 0);
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (int) answered;
        result = 31 * result + id;
        return result;
    }
}
