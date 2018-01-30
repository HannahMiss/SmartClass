package SmartClass.Dao;


import SmartClass.POJO.Student;

import java.util.List;

/**
 * Created by 73681 on 2018/1/23.
 */
public interface StudentDao
{
    /*保存一个学生*/
    void save(Student stu);

    /*根据学生ID删除一个学生*/
    void deleteById(int studentId);

    /*更新一个学生的信息*/
    void  update(Student stu);

    /*根据id得到一个学生信息*/
    Student getById(int studentId);


    /*得到所有的学生信息*/
    List<Student> getAll();

    /*根据学生ID得到得到学生课程的信息*/
    public List<Integer> getCoursesByStudentId(int studentId);
}
