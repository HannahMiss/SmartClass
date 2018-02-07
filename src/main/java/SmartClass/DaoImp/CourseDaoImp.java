package SmartClass.DaoImp;

import SmartClass.Dao.CourseDao;
import SmartClass.HibernateSessionFactory;
import SmartClass.POJO.Course;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;
import org.hibernate.Hibernate;
import org.hibernate.Session;

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

    @Override
    public Course getByIdWithStudent(short courseId) throws Exception
    {

        Session dbss = HibernateSessionFactory.getSession();
        try
        {
            List rawdata = null;
            Course course = null;
            rawdata = dbss.createQuery("select c from Course c where id=?")
                    .setParameter(0,courseId)
                    .list();
            if(rawdata.size()  > 0)
            {
                course = (Course) rawdata.get(0);
                int n = course.getStudents().size();
                return course;
            }

        } finally
        {
            if (dbss != null)
                dbss.close();
        }
        return null;
    }


}
