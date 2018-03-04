package SmartClass.Dao;

import SmartClass.POJO.Communication;
import SmartClass.POJO.Student;
import org.apache.poi.ss.formula.functions.Offset;
import sun.security.provider.SHA;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public interface CommunicationDao
{
    /*添加一条记录*/
    void add(Communication communication) throws Exception;

    /*根据communicationId,将问题标记为已读*/
    void setAnswered(int cId,int flag) throws Exception;

    /*得到某段时间的提问*/
    List<Communication> getQuestions(short courseId, String timestamp) throws Exception;

    /*得到某段时间的提问*/
    List<Communication> getQuestionsLimit(short courseId, String timestamp, int offset, int limit) throws Exception;
    /*得到某段时间的反馈*/
    List<Communication> getFeedbacks(short courseId, String timestamp) throws Exception;


    /*根据courseId，studentCode删除记录*/
    void deleteByCidScode(short courseId, String studentCode) throws Exception;

    /*删除一条记录*/
    void deleteById(int id) throws Exception;

    /*删除某门课的交流信息*/
    void deleteBycourseId(short courseId) throws Exception;

    /*删除这个表的所以记录*/
    void deleteAll() throws Exception;
}
