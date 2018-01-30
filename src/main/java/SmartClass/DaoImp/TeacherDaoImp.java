package SmartClass.DaoImp;

import SmartClass.Dao.TeacherDao;
import SmartClass.POJO.Student;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.AfDbUtil;
import SmartClass.dbutil.AfSqlWhere;

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
        AfDbUtil.save(teacher);
    }

    @Override
    public void deleteById(short teacherId)
    {

    }

    @Override
    public void update(Teacher teacher)
    {

    }

    @Override
    public Teacher getById(short teacherId) throws Exception
    {
        AfSqlWhere where = new AfSqlWhere();
        where.addExact("id", teacherId);
        String hql = "select t from Teacher t" + where.toString();
        Teacher teacher = (Teacher) AfDbUtil.get(hql,false);
        return teacher;
    }

    @Override
    public List<Teacher> getAll()
    {
        return null;
    }
}
