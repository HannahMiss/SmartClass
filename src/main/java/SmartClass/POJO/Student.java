package SmartClass.POJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 73681 on 2018/1/30.
 */
@Entity
public class Student
{
    private int id;
    private String code;
    private String name;
    private String password;
    private Timestamp timerCreated;
    private Timestamp timerModified;
    private Set<Course> courses = new HashSet<Course>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "code", nullable = false, length = 32)
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Basic
    @Column(name = "timerCreated", nullable = true)
    public Timestamp getTimerCreated()
    {
        return timerCreated;
    }

    public void setTimerCreated(Timestamp timerCreated)
    {
        this.timerCreated = timerCreated;
    }

    @Basic
    @Column(name = "timerModified", nullable = true)
    public Timestamp getTimerModified()
    {
        return timerModified;
    }

    public void setTimerModified(Timestamp timerModified)
    {
        this.timerModified = timerModified;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (code != null ? !code.equals(student.code) : student.code != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (password != null ? !password.equals(student.password) : student.password != null) return false;
        if (timerCreated != null ? !timerCreated.equals(student.timerCreated) : student.timerCreated != null)
            return false;
        if (timerModified != null ? !timerModified.equals(student.timerModified) : student.timerModified != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (timerCreated != null ? timerCreated.hashCode() : 0);
        result = 31 * result + (timerModified != null ? timerModified.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    public Set<Course> getCourses()
    {
        return courses;
    }

    public void setCourses(Set<Course> courses)
    {
        this.courses = courses;
    }
}
