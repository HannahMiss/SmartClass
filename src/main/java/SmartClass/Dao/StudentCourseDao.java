package SmartClass.Dao;

import SmartClass.POJO.Student;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by 73681 on 2018/2/6.
 */
public interface StudentCourseDao
{
    /*添加*/
    void add(short courseId,int studentId) throws Exception;

    /*删除单条记录*/
    void delete(short courseId,int studentId) throws Exception;

    /*根据课程Id，删除多条记录*/
    void deleteByCourseId(short courseId) throws Exception;

    /*设置某门课某个学生uuid*/
    void setUUidByStuCode(int studentId, short courseId, String uuid) throws Exception;

    /*查询某门课uuid*/
    String getUUid(short courseId, String uuid) throws Exception;

    /*清空某门课程的uuid*/
    void clearUUidByCourseId(short courseId) throws Exception;
}
