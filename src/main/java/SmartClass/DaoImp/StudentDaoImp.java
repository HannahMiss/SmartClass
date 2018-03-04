package SmartClass.DaoImp;

import SmartClass.Dao.StudentCourseDao;
import SmartClass.Dao.StudentDao;
import SmartClass.POJO.Student;
import SmartClass.dbutil.DBCol;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 73681 on 2018/1/29.
 */
public class StudentDaoImp implements StudentDao
{
    private StudentCourseDao studentCourseDao = new StudentCourseDaoImp();
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

    @Override
    public List<Student> getByCourseId(short courseId, int offset, int limit) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        String sql = "select a.studentId, b.name, b.code from student_course a JOIN student b " +
                "ON a.studentId=b.id " + where.toString() + " limit " + offset +',' + limit;
        List result = DbUtil.list(sql,true);
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < result.size(); i++)
        {
            Student student = new Student();
            Object[] values = (Object[])result.get(i);
            student.setId(DBCol.asInt(values[0],0));
            student.setName(DBCol.asString(values[1],""));
            student.setCode(DBCol.asString(values[2],""));
            students.add(student);
        }
        return students;
    }

}
