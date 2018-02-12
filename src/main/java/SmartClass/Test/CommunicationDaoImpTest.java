package SmartClass.Test;

import SmartClass.Dao.CommunicationDao;
import SmartClass.DaoImp.CommunicationDaoImp;
import SmartClass.POJO.Communication;
import SmartClass.dbutil.DbUtil;
import org.junit.Test;

import java.sql.Date;
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

    }

    /*更新*/
    @Test
    public void setAnsweredTest()
    {
//        try
//        {
//           // communicationDaoImp.setAnswered(1000);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
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
