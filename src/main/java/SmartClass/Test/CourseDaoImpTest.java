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

        try
        {
            StudentDaoImp studentDaoImp = new StudentDaoImp();
            TeacherDaoImp teacherDaoImp = new TeacherDaoImp();
            Teacher teacher = teacherDaoImp.getById((short)1030);

//            Teacher teacher = new Teacher();
//            teacher.setCode("20171007");
//            teacher.setName("dai");
//            teacher.setPassword("111100");

            Course course = new Course();
            course.setCourseName("计算机网络c班");
            course.setAnswerFlag((byte)0);
            course.setCheckinFlag((byte)0);
            course.setTeacherByTeacherId(teacher);

            Student student1 = new Student();
            student1.setCode("10002");
            student1.setName("小王2");
            student1.setPassword("123456");

            Student student2 = new Student();
            student2.setCode("10003");
            student2.setName("老王3");
            student2.setPassword("123456");

//            Student student1 =  studentDaoImp.getById((short)1014);
            HashSet<Student> stus = new HashSet<Student>();
            stus.add(student1);
            stus.add(student2);
            course.setStudents(stus);
            courseDaoImp.save(course);
            System.out.println("hello");

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
