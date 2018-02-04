package SmartClass.Test;

import SmartClass.Dao.AnswerDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.POJO.Answer;
import SmartClass.dbutil.DbUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class AnswerDaoImpTest
{
    AnswerDao answerDao = new AnswerDaoImp();

    @Test
    public void addTest()
    {
        Answer answer = new Answer();
        answer.setCourseId((short)1000);
        answer.setStudentId(1001);
        answer.setOpt((short)0);

        Answer answer1 = new Answer();
        answer1.setCourseId((short)1000);
        answer1.setStudentId(1002);
        answer1.setOpt((short)0);

        Answer answer2 = new Answer();
        answer2.setCourseId((short)1000);
        answer2.setStudentId(1003);
        answer2.setOpt((short)0);

        Answer answera = new Answer();
        answera.setCourseId((short)1001);
        answera.setStudentId(1001);
        answera.setOpt((short)0);

        Answer answerb = new Answer();
        answerb.setCourseId((short)1001);
        answerb.setStudentId(1002);
        answerb.setOpt((short)0);

        try
        {
            DbUtil.save(answer);
            DbUtil.save(answer1);
            DbUtil.save(answer2);
            DbUtil.save(answera);
            DbUtil.save(answerb);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Test
    public void updateTest()
    {
        Answer answer = new Answer();
        answer.setCourseId((short)1000);
        answer.setStudentId(1001);
        answer.setOpt((short)1);

        Answer answer1 = new Answer();
        answer1.setCourseId((short)1000);
        answer1.setStudentId(1002);
        answer1.setOpt((short)2);

        Answer answer2 = new Answer();
        answer2.setCourseId((short)1000);
        answer2.setStudentId(1003);
        answer2.setOpt((short)3);

        Answer answera = new Answer();
        answera.setCourseId((short)1001);
        answera.setStudentId(1001);
        answera.setOpt((short)4);


        try
        {
            answerDao.update(answer);
            answerDao.update(answer1);
            answerDao.update(answer2);
            answerDao.update(answera);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void getByCourseIdTest()
    {
        try
        {
            List<Answer> rows = answerDao.getByCourseId((short)1000);
            for (Answer row:rows)
            {
                System.out.println(row.getOpt());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByCourseIdTest()
    {
        try
        {
            answerDao.deleteByCourseId((short)1000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAnswerTest()
    {
        try
        {
            answerDao.deleteAnswer((short)1000,(short)1001);
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
            answerDao.deleteAll();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void clearByCourseIdTest()
    {
        try
        {
            answerDao.clearByCourseId((short)1001);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void clearTest()
    {
        try
        {
            answerDao.clear();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
