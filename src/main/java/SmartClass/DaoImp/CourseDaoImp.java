package SmartClass.DaoImp;

import SmartClass.Dao.CourseDao;
import SmartClass.POJO.Course;
import SmartClass.dbutil.AfDbUtil;
import SmartClass.dbutil.AfSqlWhere;

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
        AfDbUtil.save(course);
    }


    /*已测试*/
    @Override
    public void deleteById(short courseId) throws Exception
    {
        AfSqlWhere where = new AfSqlWhere();
        where.addExact("id", courseId);
        String sql = "DELETE FROM course" + where.toString();
        System.out.println(sql);
        AfDbUtil.execute(sql,true);
    }

    @Override
    public List<Course> getAll() throws Exception
    {

        String hql = "select c  from Course c";
        List list = AfDbUtil.list(hql,false);

        return list;
    }

    @Override
    public void update(Course course)
    {

    }

    @Override
    public Course getById(int courseId)
    {
        return null;
    }

    @Override
    public void updateCheckInFlag(int courseId, boolean startFlag)
    {

    }

    @Override
    public void updateAnswerFlag(int courseId, boolean startFlag)
    {

    }

    @Override
    public List getStudentsByCourseId(int courseId)
    {
        return null;
    }
}
