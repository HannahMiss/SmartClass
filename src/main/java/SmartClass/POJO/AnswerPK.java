package SmartClass.POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 73681 on 2018/2/7.
 */
public class AnswerPK implements Serializable
{
    private short courseId;
    private String studentCode;

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

    @Column(name = "studentCode", nullable = false, length = 32)
    @Id
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

        AnswerPK answerPK = (AnswerPK) o;

        if (courseId != answerPK.courseId) return false;
        if (studentCode != null ? !studentCode.equals(answerPK.studentCode) : answerPK.studentCode != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) courseId;
        result = 31 * result + (studentCode != null ? studentCode.hashCode() : 0);
        return result;
    }
}
