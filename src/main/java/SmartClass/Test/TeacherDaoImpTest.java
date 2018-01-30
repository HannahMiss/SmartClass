package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Teacher;
import org.junit.Test;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class TeacherDaoImpTest
{
    TeacherDao teacherDaoImp = new TeacherDaoImp();
    @Test
    public void testSave()
    {
        Teacher teacher = new Teacher();
        teacher.setCode("20171000");
        teacher.setName("wang");
        teacher.setPassword("1000");
        try
        {
            teacherDaoImp.save(teacher);
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
            Teacher teacher = teacherDaoImp.getById((short)1006);
            System.out.println(teacher.getName());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByIdTest()
    {

    }

    @Test
    public void getAllTest()
    {

    }
}
