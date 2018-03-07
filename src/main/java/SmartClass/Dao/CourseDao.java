package SmartClass.Dao;

import SmartClass.POJO.Course;

import java.util.List;
 
/**
 * Created by 73681 on 2018/1/23.
 */
public interface CourseDao
{
    /*保存一门课程*/
    void save(Course course) throws Exception;

    /*根据课程id删除一门课程*/
    void deleteById(short courseId) throws Exception;
    /*得到所有的课程信息*/
    List getAll() throws Exception;
    /*更新一门课程*/
    void  update(Course course) throws Exception;

    /*根据id得到课程信息*/
    Course getById(short courseId) throws Exception;

    Course getByIdWithStudent(short courseId)throws Exception;
}
