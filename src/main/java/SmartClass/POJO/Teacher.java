package SmartClass.POJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by 73681 on 2018/1/30.
 */
@Entity
public class Teacher
{
    private short id;
    private String code;
    private String name;
    private String password;
    private Timestamp timeCreated;
    private Timestamp timeModified;
    private Collection<Course> coursesById = new HashSet<Course>();

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
    @Column(name = "code", nullable = false, length = 64)
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

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (code != null ? !code.equals(teacher.code) : teacher.code != null) return false;
        if (name != null ? !name.equals(teacher.name) : teacher.name != null) return false;
        if (password != null ? !password.equals(teacher.password) : teacher.password != null) return false;
        if (timeCreated != null ? !timeCreated.equals(teacher.timeCreated) : teacher.timeCreated != null) return false;
        if (timeModified != null ? !timeModified.equals(teacher.timeModified) : teacher.timeModified != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (timeModified != null ? timeModified.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "teacherByTeacherId", fetch = FetchType.LAZY)
    public Collection<Course> getCoursesById()
    {
        return coursesById;
    }

    public void setCoursesById(Collection<Course> coursesById)
    {
        this.coursesById = coursesById;
    }
}
