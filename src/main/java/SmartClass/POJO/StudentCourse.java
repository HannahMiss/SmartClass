package SmartClass.POJO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 73681 on 2018/1/30.
 */
@Entity
@Table(name = "student_course", schema = "smartclasstest", catalog = "")
public class StudentCourse
{
    private int id;
    private Timestamp timeCreated;
    private Timestamp timeModified;
    private Student studentByStudentId;
    private Course courseByCourseId;

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

    @Basic
    @Column(name = "timeCreated", nullable = true)
    public Timestamp getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "timeModified", nullable = true)
    public Timestamp getTimeModified()
    {
        return timeModified;
    }

    public void setTimeModified(Timestamp timeModified)
    {
        this.timeModified = timeModified;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse that = (StudentCourse) o;

        if (id != that.id) return false;
        if (timeCreated != null ? !timeCreated.equals(that.timeCreated) : that.timeCreated != null) return false;
        if (timeModified != null ? !timeModified.equals(that.timeModified) : that.timeModified != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (timeModified != null ? timeModified.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id", nullable = false)
    public Student getStudentByStudentId()
    {
        return studentByStudentId;
    }

    public void setStudentByStudentId(Student studentByStudentId)
    {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id", nullable = false)
    public Course getCourseByCourseId()
    {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId)
    {
        this.courseByCourseId = courseByCourseId;
    }
}
