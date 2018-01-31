package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Teacher;
import org.junit.Test;

import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        teacher.setCode("20171002");
        teacher.setName("yasuo");
        teacher.setPassword("1002");

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
            Teacher teacher = teacherDaoImp.getById((short)1008);
            System.out.println(teacher.getName());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**/
    @Test
    public void deleteByIdTest()
    {
        try
        {
            teacherDaoImp.deleteById((short)1007);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void updataTest()
    {
        try
        {
            Teacher teacher = teacherDaoImp.getById((short)1008);
            teacher.setName("xiaoming");
            teacherDaoImp.update(teacher);
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
            List<Teacher> teachers = teacherDaoImp.getAll();
            for (Teacher t : teachers)
            {
                System.out.println(t.getName());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
