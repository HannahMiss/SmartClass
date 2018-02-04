package SmartClass.POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 73681 on 2018/2/3.
 */
public class AnswerPK implements Serializable
{
    private short courseId;
    private int studentId;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerPK answerPK = (AnswerPK) o;

        if (courseId != answerPK.courseId) return false;
        if (studentId != answerPK.studentId) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + studentId;
        return result;
    }
}
