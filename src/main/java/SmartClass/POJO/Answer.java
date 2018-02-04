package SmartClass.POJO;

import javax.persistence.*;

/**
 * Created by 73681 on 2018/2/3.
 */
@Entity
@IdClass(AnswerPK.class)
public class Answer
{
    private short courseId;
    private int studentId;
    private short opt;

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

    @Basic
    @Column(name = "opt", nullable = false)
    public short getOpt()
    {
        return opt;
    }

    public void setOpt(short opt)
    {
        this.opt = opt;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (courseId != answer.courseId) return false;
        if (studentId != answer.studentId) return false;
        if (opt != answer.opt) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + studentId;
        result = 31 * result + (int) opt;
        return result;
    }
}
