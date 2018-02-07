package SmartClass.DaoImp;

import SmartClass.Dao.StudentDao;
import SmartClass.POJO.Student;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class StudentDaoImp implements StudentDao
{
    @Override
    public void save(Student stu) throws Exception
    {
        DbUtil.save(stu);
    }

    @Override
    public void deleteById(int studentId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", studentId);
        String sql = "delete from student" + where.toString();
        DbUtil.execute(sql,true);
    }

    @Override
    public void update(Student stu) throws Exception
    {
        DbUtil.update(stu);
    }

    @Override
    public Student getById(int studentId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", studentId);
        String hql = "select s from Student s" + where.toString();
        Student student = (Student) DbUtil.get(hql,false);
        return student;
    }

    @Override
    public Student getByCode(String studentCode) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("code", studentCode);
        String hql = "select s from Student s" + where.toString();
        Student student = (Student) DbUtil.get(hql,false);
        return student;
    }

    @Override
    public List<Student> getAll() throws Exception
    {

        String hql = "select s from Student s";
        List results = DbUtil.list(hql,false);
        return results;
    }

}
