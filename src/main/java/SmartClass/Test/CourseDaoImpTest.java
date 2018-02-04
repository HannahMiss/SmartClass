package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Student;
import SmartClass.POJO.Teacher;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class CourseDaoImpTest
{
    CourseDao courseDaoImp = new CourseDaoImp();
    @Test
    public void testSave()
    {
        Course course = new Course();
        course.setCourseName("计算机通信新技术");

    }


    @Test
    public void deleteByIdTest()
    {
        try
        {
            courseDaoImp.deleteById((short)1003);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllTest()
    {
        try
        {
            List<Course> rows = courseDaoImp.getAll();
            for (Course c : rows)
            {
                System.out.println(c.getCourseName());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void getByIdTest()
    {
        try
        {
            Course course = courseDaoImp.getById((short)1018);
            System.out.println(course.getCourseName() + course.getTeacherByTeacherId().getName());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void updateTest()
    {
        try
        {

            //courseDaoImp.update();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
