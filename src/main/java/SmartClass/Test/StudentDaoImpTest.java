package SmartClass.Test;

import SmartClass.Dao.StudentDao;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.POJO.Student;
import org.junit.Test;

/**
 * Created by 73681 on 2018/1/29.
 */
public class StudentDaoImpTest
{
    private StudentDao studentDaoImp = new StudentDaoImp();
    @Test
    public void saveTest()
    {
        Student student = new Student();
        student.setCode("20171100");
        student.setName("小红");
        student.setPassword("123456");

        try
        {
            studentDaoImp.save(student);
            System.out.println(student.getName());
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
            Student student = studentDaoImp.getById((short)1014);
            System.out.println(student.getName());
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
