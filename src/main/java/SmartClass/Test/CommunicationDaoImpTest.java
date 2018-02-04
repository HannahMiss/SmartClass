package SmartClass.Test;

import SmartClass.Dao.CommunicationDao;
import SmartClass.DaoImp.CommunicationDaoImp;
import SmartClass.POJO.Communication;
import SmartClass.dbutil.DbUtil;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class CommunicationDaoImpTest
{

    CommunicationDao communicationDaoImp = new CommunicationDaoImp();
    /*添加*/
    @Test
    public void addTest()
    {
        Communication communication = new Communication();
        communication.setCourseId((short)1000);
        communication.setStudenId(1000);
        communication.setFlag((byte)0);
        communication.setAnswered((byte)0);
        communication.setDescr("电路交换的优点是什么？");
        communication.setTimeCreated(DbUtil.now());

        Communication communication1 = new Communication();
        communication1.setCourseId((short)1000);
        communication1.setStudenId(1002);
        communication1.setFlag((byte)0);
        communication1.setAnswered((byte)1);
        communication1.setDescr("虚电路是什么？");
        communication1.setTimeCreated(DbUtil.now());

        Communication communication2 = new Communication();
        communication2.setCourseId((short)1001);
        communication2.setStudenId(1000);
        communication2.setFlag((byte)1);
        communication2.setDescr("讲太快");
        communication2.setTimeCreated(DbUtil.now());

        try
        {
            communicationDaoImp.add(communication);
            communicationDaoImp.add(communication1);
            communicationDaoImp.add(communication2);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /*更新*/
    @Test
    public void setAnsweredTest()
    {
        try
        {
            communicationDaoImp.setAnswered(1000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /*根据时间戳，得到问题*/
    @Test
    public void getQuestionsTest()
    {
        Timestamp timestamp = new Timestamp(10,12,30,10,15,11,30);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strtime = sdf.format( timestamp );
        try
        {
            List<Communication> rows = communicationDaoImp.getQuestions((short)1000, strtime);
            for (Communication row : rows)
            {
                System.out.println(row.getTimeCreated());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*根据时间戳，得到反馈*/
    @Test
    public void getFeedbacksTest()
    {
        String timeStamp = DbUtil.nowstr();
        try
        {
            List<Communication> communications = communicationDaoImp.getFeedbacks((short)1001, timeStamp);
            for (Communication c : communications)
            {
                System.out.println(c.getTimeCreated());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteByIdTest()
    {
        try
        {
            communicationDaoImp.deleteById((short)1001);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAllTest()
    {
        try
        {
            communicationDaoImp.deleteAll();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
