package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Teacher;
import org.junit.Test;

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

        try
        {
            TeacherDaoImp teacherDaoImp = new TeacherDaoImp();
            Teacher teacher = teacherDaoImp.getById((short)1006);

            Course course = new Course();
            course.setCourseName("交换原理");
            course.setAnswerFlag((byte)0);
            course.setCheckinFlag((byte)0);
            course.setTeacherByTeacherId(teacher);
            courseDaoImp.save(course);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
}
