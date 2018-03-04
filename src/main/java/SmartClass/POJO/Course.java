package SmartClass.POJO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 73681 on 2018/1/30.
 */
@Entity
public class Course implements java.io.Serializable
{
    private short id;
    private String courseName;
    private byte checkinFlag;
    private short checkinTime;
    private Date checkinDate;
    private byte answerFlag;
    private Timestamp timeCreated;
    private Timestamp timeModified;
    private Teacher teacherByTeacherId;
    private byte classFlag;
    private Set<Student> students = new HashSet<Student>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public short getId()
    {
        return id;
    }

    public void setId(short id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "courseName", nullable = false, length = 64)
    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "checkinFlag", nullable = false)
    public byte getCheckinFlag()
    {
        return checkinFlag;
    }

    public void setCheckinFlag(byte checkinFlag)
    {
        this.checkinFlag = checkinFlag;
    }

    @Basic
    @Column(name = "checkinTime",nullable = false)
    public short getCheckinTime() {return checkinTime;}

    public void setCheckinTime(short checkinTime) {this.checkinTime = checkinTime;}

    @Basic
    @Column(name = "checkinDate",nullable = false)
    public Date getCheckinDate() {return checkinDate;}

    public void setCheckinDate(Date checkinDate) {this.checkinDate = checkinDate;}
    @Basic
    @Column(name = "answerFlag", nullable = false)
    public byte getAnswerFlag()
    {
        return answerFlag;
    }

    public void setAnswerFlag(byte answerFlag)
    {
        this.answerFlag = answerFlag;
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

    @Basic
    @Column(name = "classFlag", nullable = true)
    public byte getClassFlag() {return classFlag;}

    public void setClassFlag(byte classFlag) {this.classFlag = classFlag;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (checkinFlag != course.checkinFlag) return false;
        if (answerFlag != course.answerFlag) return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (timeCreated != null ? !timeCreated.equals(course.timeCreated) : course.timeCreated != null) return false;
        if (timeModified != null ? !timeModified.equals(course.timeModified) : course.timeModified != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) id;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (int) checkinFlag;
        result = 31 * result + (int) answerFlag;
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (timeModified != null ? timeModified.hashCode() : 0);
        return result;
    }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherId", referencedColumnName = "id", nullable = false)
    public Teacher getTeacherByTeacherId()
    {
        return teacherByTeacherId;
    }

    public void setTeacherByTeacherId(Teacher teacherByTeacherId)
    {
        this.teacherByTeacherId = teacherByTeacherId;
    }


    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "courseId",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "studentId",referencedColumnName = "id")}
    )
    public Set<Student> getStudents()
    {
        return students;
    }

    public void setStudents(Set<Student> students)
    {
        this.students = students;
    }
}
