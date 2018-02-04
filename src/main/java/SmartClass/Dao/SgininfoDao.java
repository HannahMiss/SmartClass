package SmartClass.Dao;

import SmartClass.POJO.Sgininfo;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public interface SgininfoDao
{
    /*添加一条签到信息*/
    void add(Sgininfo info) throws Exception;

    /*得到某门课程某次签到的结果*/
    List<String> getOneTimeByCourseId(short courseId, short timer) throws Exception;

    /*得到该课程所有的签到信息*/
    List<Sgininfo> getAllByCourseId(short courseId) throws Exception;


    /*删除某门课的签到信息*/
    void deleteByCourse(short courseId) throws Exception;

    /*删除某门课，某个学生的签到，信息*/
    void deleteInfo(short courseId, String stuCode) throws Exception;

    /*删除表的所有记录*/
    void deleteAll() throws Exception;
}
