package SmartClass.DaoImp;
import SmartClass.Dao.TeacherDao;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;
import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class TeacherDaoImp implements TeacherDao
{
    /*已测试*/
    @Override
    public void save(Teacher teacher) throws Exception
    {
        DbUtil.save(teacher);
    }

    /*老师和课程是一对多的关系，删除老师会删除老师名下所有的课程*/
    /*已测试*/
    @Override
    public void deleteById(short teacherId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id",teacherId);
        String sql = "delete from teacher" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void update(Teacher teacher)
    {
        try
        {
            DbUtil.update(teacher);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*已测试*/
    @Override
    public Teacher getById(short teacherId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", teacherId);
        String hql = "select t from Teacher t" + where.toString();
        Teacher teacher = (Teacher) DbUtil.get(hql,false);
        return teacher;
    }

    @Override
    public Teacher getByUserName(String username) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("code", username);
        String hql = "select t from Teacher t" + where.toString();
        Teacher teacher = (Teacher) DbUtil.get(hql,false);
        return teacher;
    }

    /*已测试*/
    @Override
    public List<Teacher> getAll() throws Exception
    {
        String hql = "select t from Teacher t";
        List result = DbUtil.list(hql,false);
        return result;
    }
}
