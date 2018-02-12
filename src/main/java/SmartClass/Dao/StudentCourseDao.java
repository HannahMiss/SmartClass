package SmartClass.Dao;

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
}
