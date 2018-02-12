package SmartClass.Test;

import SmartClass.Dao.CourseDao;
import SmartClass.Dao.StudentCourseDao;
import SmartClass.Dao.StudentDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.StudentCourseDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Student;
import SmartClass.dbutil.DbUtil;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 73681 on 2018/1/29.
 */
public class StudentDaoImpTest
{
    private StudentDao studentDao = new StudentDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private StudentCourseDao studentCourseDao = new StudentCourseDaoImp();
    @Test
    public void saveTest()
    {
//        try
//        {
//            String name = "xiaoming";
//            String code = "2017140426";
//            short courseId = 1068;
//            Course course = courseDao.getById(courseId);
//            Student student = studentDao.getByCode(code);
//            if (student != null)                /*如果这个学生已经在数据库中，更新*/
//            {
//                student.setName(name);
//                student.setCode(code);
//                student.setPassword(code.substring(code.length() - 6));
//                student.getCourses().add(course);
//                student.setTimerModified(DbUtil.now());
//                studentDao.update(student);
//            } else                           /*如果这个学生没在数据库中，new一个对象并保存*/
//            {
//                student = new Student();
//                student.setName(name);
//                student.setCode(code);
//                student.setPassword(code.substring(code.length() - 6));
//                student.getCourses().add(course);
//                student.setTimerCreated(DbUtil.now());
//                student.setTimerModified(DbUtil.now());
//                studentDao.save(student);
//            }
//            studentCourseDao.add(courseId,student.getId());
//        }catch (Exception e)
//        {
//
//        }

        Set<Student> students = new HashSet<Student>();
        Student student = new Student();
        student.setName("111");
        student.setCode("2017333");
        student.setPassword("333");
        student.setTimerModified(DbUtil.now());
        student.setTimerCreated(DbUtil.now());
        students.add(student);

        Student student1 = new Student();
        student1.setName("111");
        student1.setCode("2017222");
        student1.setPassword("222");
        student1.setTimerModified(DbUtil.now());
        student1.setTimerCreated(DbUtil.now());
        students.add(student1);

        try
        {
            Course course = courseDao.getById((short)1068);
            course.setStudents(students);
            courseDao.update(course);
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
    public void updateTest()
    {

    }

    @Test
    public void getByIdTest()
    {
        try
        {
            Student student = studentDao.getById(2);
            Set<Course> courses = student.getCourses();
            for (Course c : courses)
            {
                System.out.println(c.getCourseName());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllTest()
    {

    }

}
