package SmartClass.Dao;

import SmartClass.POJO.Answer;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public interface AnswerDao
{
    /*添加*/
    void add(Answer answer) throws Exception;

    /*更新*/
    void update(Answer answer) throws Exception;

    /*得到某门课的答题信息*/
    List<Answer> getByCourseId(short courseId) throws Exception;

    /*根据课程id，删除某个课程的答题信息*/
    void deleteByCourseId(short courseId) throws Exception;

    /*删除课程某个学生的答题信息*/
    void deleteAnswer(short courseId, int studentId) throws Exception;

    /*删除表中所有的数据*/
    void deleteAll() throws Exception;

    /*清空某门课的答题信息*/
    void clearByCourseId(short courseId) throws Exception;

    /*清空答题信息*/
    void clear() throws Exception;
}