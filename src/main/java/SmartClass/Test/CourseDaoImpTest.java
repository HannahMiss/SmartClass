package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Student;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.DbUtil;
import org.hibernate.Hibernate;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            courseDaoImp.deleteById((short)1067);
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
            Course course = courseDaoImp.getById((short)1067);
           // Hibernate.initialize(course.getStudents());
            System.out.println(course.getCourseName()+ course.getStudents().size());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void getByIdWithStudent()
    {
        Course course = null;
        try
        {
            course = courseDaoImp.getByIdWithStudent((short)1067);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Set<Student> stus = course.getStudents();
        System.out.println(course.getCourseName()+stus.size());
    }

    @Test
    public void updateTest()
    {
        try
        {
            String name = "hubiao";
            String code = "2017140429";
            Course course = courseDaoImp.getById((short)1067);        /*得到这门课程*/
            Student student = new Student();
            student.setName(name);
            student.setCode(code);
            student.setPassword(code.substring(code.length()-6));
            student.setTimerCreated(DbUtil.now());
            student.setTimerModified(DbUtil.now());
            Hibernate.initialize(course.getStudents());
            course.getStudents().add(student);                  /*添加学生*/
            course.setTimeModified(DbUtil.now());
            courseDaoImp.update(course);                         /*更新这门课程*/
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
