package SmartClass.Dao;


import SmartClass.POJO.Question;

import java.util.List;

/**
 * Created by 73681 on 2018/1/23.
 * 互动信息数据层接口
 * 包括问题和反馈
 */
public interface QuestionDao
{
    /*根据课程ID创建一个课堂问题表,表名的格式为courseId_question*/
    public void createTable(int courseId);

    /*添加一条问题信息表*/
    public void save(Question question);

    /*得到小于某个时间段的问题记录*/
    public List getQuestion(int courseId, String timer);

    /*根据问题ID,标记某个问题已经回答*/
    public void updateIsAnswer(int courseId, int questionId);

    /*根据问题ID,删除某个问题*/
    public void deleteQuestion(int courseId, int questionId);

    /*删除整张表的记录*/
    public void clear(int courseId);


}
