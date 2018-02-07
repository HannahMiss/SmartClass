package SmartClass.POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 73681 on 2018/2/6.
 */
public class StudentCoursePK implements Serializable
{
    private int studentId;
    private short courseId;

    @Column(name = "studentId", nullable = false)
    @Id
    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    @Column(name = "courseId", nullable = false)
    @Id
    public short getCourseId()
    {
        return courseId;
    }

    public void setCourseId(short courseId)
    {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCoursePK that = (StudentCoursePK) o;

        if (studentId != that.studentId) return false;
        if (courseId != that.courseId) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = studentId;
        result = 31 * result + (int) courseId;
        return result;
    }
}
