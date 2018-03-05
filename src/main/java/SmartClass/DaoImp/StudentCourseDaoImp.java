package SmartClass.DaoImp;

import SmartClass.Dao.StudentCourseDao;
import SmartClass.POJO.StudentCourse;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;
import org.hibernate.sql.Update;

/**
 * Created by 73681 on 2018/2/6.
 */
public class StudentCourseDaoImp implements StudentCourseDao
{
    @Override
    public void add(short courseId, int studentId) throws Exception
    {
        StudentCourse sc = new StudentCourse();
        sc.setCourseId(courseId);
        sc.setStudentId(studentId);
        DbUtil.save(sc);
    }

    @Override
    public void delete(short courseId, int studentId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        where.addExact("studentId",studentId);
        String sql = "delete from student_course" + where.toString();
        DbUtil.execute(sql,true);
    }

    @Override
    public void deleteByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        String sql = "delete from student_course" + where.toString();
        DbUtil.execute(sql,true);
    }

    @Override
    public void setUUidByStuCode(int studentId,short courseId, String uuid) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("studentId",studentId);
        where.addExact("courseId",courseId);
        String sql = "update student_course set uuid='" + uuid + "'" + where.toString();
        DbUtil.execute(sql,true);
    }

    @Override
    public String getUUid(short courseId, String uuid) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        where.addExact("uuid",uuid);
        String sql = "select uuid from student" + where.toString();
        String resUUid = (String) DbUtil.get(sql,true);
        return resUUid;
    }

    @Override
    public void clearUUidByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        String sql = "update student_course set uuid=null " + where.toString();
        DbUtil.execute(sql,true);
    }

}
