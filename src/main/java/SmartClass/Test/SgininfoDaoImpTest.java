package SmartClass.Test;
import SmartClass.Dao.SgininfoDao;
import SmartClass.DaoImp.SgininfoDaoImp;
import SmartClass.POJO.Sgininfo;
import SmartClass.dbutil.DbUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class SgininfoDaoImpTest
{
    SgininfoDao sgininfoDaoImp = new SgininfoDaoImp();

    @Test
    public void addTest()
    {
        Sgininfo sgininfo = new Sgininfo();
        sgininfo.setCourseId((short)1000);
        sgininfo.setStudentCode("20171000");
        sgininfo.setTimes((short)1);
        sgininfo.setTimeCreated(DbUtil.now());


        Sgininfo sgininfo2 = new Sgininfo();
        sgininfo2.setCourseId((short)1000);
        sgininfo2.setStudentCode("20171001");
        sgininfo2.setTimes((short)1);
        sgininfo2.setTimeCreated(DbUtil.now());

        Sgininfo sgininfo3 = new Sgininfo();
        sgininfo3.setCourseId((short)1001);
        sgininfo3.setStudentCode("20171002");
        sgininfo3.setTimes((short)1);
        sgininfo3.setTimeCreated(DbUtil.now());
        try
        {
            sgininfoDaoImp.add(sgininfo);
            sgininfoDaoImp.add(sgininfo2);
            sgininfoDaoImp.add(sgininfo3);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getOneTimeByCourseIdTest()
    {
        try
        {
            List<String> rows = sgininfoDaoImp.getOneTimeByCourseId((short)1000,(short)1);
            for (String str : rows)
            {
                System.out.println("studenCode:" + str);
            }
        } catch (Exception e)
        {

            e.printStackTrace();
        }
    }

    @Test
    public void getsTest()
    {
        try
        {
            List<Sgininfo> rows = sgininfoDaoImp.getAllByCourseId((short)1000);
            for (Sgininfo row : rows)
            {
                System.out.println("studenCode:" + row.getStudentCode() + " time:" + row.getTimes());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteByCourse()
    {
        try
        {
            sgininfoDaoImp.deleteByCourse((short)1000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteInfoTest()
    {
        try
        {
            sgininfoDaoImp.deleteInfo((short)1001,"20171002");
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
            sgininfoDaoImp.deleteAll();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
