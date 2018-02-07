package SmartClass.Dao;



import SmartClass.POJO.Student;
import SmartClass.POJO.Teacher;

import java.util.List;

/**
 * Created by 73681 on 2018/1/23.
 */
public interface TeacherDao
{
    /*保存老师信息*/
    void save(Teacher teacher) throws Exception;

    /*根据ID删除老师信息*/
    void deleteById(short teacherId) throws Exception;

    /*更新一个老师的信息*/
    void  update(Teacher teacher);

    /*根据id得到一个老师的信息*/
    Teacher getById(short teacherId) throws Exception;

    /*根据username查询一个老师*/
    Teacher getByUserName(String username) throws Exception;

    /*得到所有的老师的信息*/
    List<Teacher> getAll() throws Exception;


}
