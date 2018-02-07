package SmartClass.POJO;

import javax.persistence.*;

/**
 * Created by 73681 on 2018/2/7.
 */
@Entity
@IdClass(AnswerPK.class)
public class Answer
{
    private short courseId;
    private short opt;
    private String studentCode;

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
    @Column(name = "opt", nullable = false)
    public short getOpt()
    {
        return opt;
    }

    public void setOpt(short opt)
    {
        this.opt = opt;
    }

    @Id
    @Column(name = "studentCode", nullable = false, length = 32)
    public String getStudentCode()
    {
        return studentCode;
    }

    public void setStudentCode(String studentCode)
    {
        this.studentCode = studentCode;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (courseId != answer.courseId) return false;
        if (opt != answer.opt) return false;
        if (studentCode != null ? !studentCode.equals(answer.studentCode) : answer.studentCode != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + (int) opt;
        result = 31 * result + (studentCode != null ? studentCode.hashCode() : 0);
        return result;
    }
}
