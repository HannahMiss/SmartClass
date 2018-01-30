package SmartClass.Dao;

import java.util.List;

/**
 * Created by 73681 on 2018/1/23.
 */
public interface SginAndAnswerDao
{
    /*根据courseId创建一个课程签到信息表,包括初始化,表名的格式为courseId_sgin_answer*/
    void  creat(int courseCode);

    /*根据课程id得到某一次签到信息*/
    List getSginResult(int courseId, int times);

    /*得到所有的签到信息*/
    List getAll(int courseId);

    /*添加一次签到*/
    void addColumn(int courseCode, int times);

    /*根据学生ID,课程号，第几次签到。更新签到信息*/
    void updateSgin(int courseId, int studentId, int times);

    /*更新答题信息*/
    void updateAnswer(int courseId, int studentId, int Opt);

    /*得到答题结果*/
    List<Integer> getAllAnswer(int courseId);

    /*删除签到答题信息表*/
    void delete(int courseId);

}