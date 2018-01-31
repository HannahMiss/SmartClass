package SmartClass.DaoImp;

import SmartClass.Dao.CourseDao;
import SmartClass.POJO.Course;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class CourseDaoImp implements CourseDao
{
    /*已测试*/
    @Override
    public void save(Course course) throws Exception
    {
        DbUtil.save(course);
    }
    /*已测试*/
    @Override
    public void deleteById(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", courseId);
        String sql = "DELETE FROM course" + where.toString();
        System.out.println(sql);
        DbUtil.execute(sql,true);
    }

    @Override
    public void update(Course course) throws Exception
    {
        DbUtil.update(course);
    }

    /*已测试*/
    @Override
    public List<Course> getAll() throws Exception
    {

        String hql = "select c  from Course c";
        List list = DbUtil.list(hql,false);
        return list;
    }

    /*已测试*/
    @Override
    public Course getById(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", courseId);
        String hql = "select c from Course c" + where.toString();
        Course course = (Course) DbUtil.get(hql,false);
        return course;
    }


}
