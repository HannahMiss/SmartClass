package SmartClass.POJO;

import javax.persistence.*;

/**
 * Created by 73681 on 2018/2/6.
 */
@Entity
@Table(name = "student_course", schema = "smartclasstest", catalog = "")
@IdClass(StudentCoursePK.class)
public class StudentCourse
{
    private int studentId;
    private short courseId;
    private String uuid;
    @Id
    @Column(name = "studentId", nullable = false)
    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    @Id
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
    @Column(name = "uuid",nullable = true, length = 32)
    public String getUuid() {return uuid;}

    public void setUuid(String uuid) {this.uuid = uuid;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse that = (StudentCourse) o;

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
