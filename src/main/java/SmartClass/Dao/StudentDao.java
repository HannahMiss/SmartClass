package SmartClass.Dao;


import SmartClass.POJO.Student;

import java.util.List;

/**
 * Created by 73681 on 2018/1/23.
 */
public interface StudentDao
{
    /*保存一个学生*/
    void save(Student stu) throws Exception;

    /*根据学生ID删除一个学生*/
    void deleteById(int studentId) throws Exception;

    /*更新一个学生的信息*/
    void  update(Student stu) throws Exception;

    /*根据id得到一个学生信息*/
    Student getById(int studentId) throws Exception;


    /*得到所有的学生信息*/
    List<Student> getAll() throws Exception;

}
