package SmartClass.Dao;

/**
 * Created by 73681 on 2018/2/6.
 */
public interface StudentCourseDao
{
    /*添加*/
    void add(short courseId,int studentId) throws Exception;

    /*删除*/
    void delete(short courseId,int studentId) throws Exception;
}
