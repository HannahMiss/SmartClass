package SmartClass.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by 73681 on 2018/1/30.
 */
@Entity
public class Administrator
{
    private int id;
    private String username;
    private String password;
    private Timestamp timerCreated;
    private Timestamp timerModified;

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
    @Column(name = "username", nullable = false, length = 32)
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

        Administrator that = (Administrator) o;

        if (id != that.id) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (timerCreated != null ? !timerCreated.equals(that.timerCreated) : that.timerCreated != null) return false;
        if (timerModified != null ? !timerModified.equals(that.timerModified) : that.timerModified != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (timerCreated != null ? timerCreated.hashCode() : 0);
        result = 31 * result + (timerModified != null ? timerModified.hashCode() : 0);
        return result;
    }
}
