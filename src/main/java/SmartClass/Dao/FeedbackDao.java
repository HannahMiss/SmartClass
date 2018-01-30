package SmartClass.Dao;


import SmartClass.POJO.Feedback;

import java.util.List;

/**
 * Created by 73681 on 2018/1/24.
 * 反馈信息表数据接口
 * 目前共三种：
 * 1：讲太快（quick）
 * 2: 看不见（invisible）
 * 3: 听不清（inaudible）
 */
public interface FeedbackDao
{
    /*根据课程ID创建一个反馈信息表,表名的格式为courseId_feedback*/
    void createTable(int courseId);

    /*添加一条反馈信息*/
    void  save(int courseId, Feedback feedback);

    /*得到大于某个时间的反馈信息*/
    List getFeedback(int courseId, String time);

    /*清空这张表的信息,*/
    void clear(int courseId);
}
