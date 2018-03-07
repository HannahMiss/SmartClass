//package SmartClass.Test;
//import SmartClass.Dao.SigninfoDao;
//import SmartClass.DaoImp.SigninfoDaoImp;
//import SmartClass.POJO.Signinfo;
//import SmartClass.dbutil.DbUtil;
//import org.junit.Test;
//
//import java.util.List;
//
///**
// * Created by 73681 on 2018/2/3.
// */
//public class SigninfoDaoImpTest
//{
//    SigninfoDao signinfoDaoImp = new SigninfoDaoImp();
//
//    @Test
//    public void addTest()
//    {
//        signinfo signinfo = new signinfo();
//        signinfo.setCourseId((short)1000);
//        signinfo.setStudentCode("20171000");
//        signinfo.setTimes((short)1);
//        signinfo.setTimeCreated(DbUtil.now());
//
//
//        signinfo signinfo2 = new signinfo();
//        signinfo2.setCourseId((short)1000);
//        signinfo2.setStudentCode("20171001");
//        signinfo2.setTimes((short)1);
//        signinfo2.setTimeCreated(DbUtil.now());
//
//        signinfo signinfo3 = new signinfo();
//        signinfo3.setCourseId((short)1001);
//        signinfo3.setStudentCode("20171002");
//        signinfo3.setTimes((short)1);
//        signinfo3.setTimeCreated(DbUtil.now());
//        try
//        {
//            signinfoDaoImp.add(signinfo);
//            signinfoDaoImp.add(signinfo2);
//            signinfoDaoImp.add(signinfo3);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getOneTimeByCourseIdTest()
//    {
//        try
//        {
//            List<String> rows = signinfoDaoImp.getOneTimeByCourseId((short)1000,(short)1074);
//            for (String str : rows)
//            {
//                System.out.println("studenCode:" + str);
//            }
//        } catch (Exception e)
//        {
//
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getsTest()
//    {
//        try
//        {
//            List<signinfo> rows = signinfoDaoImp.getAllByCourseId((short)1000);
//            for (signinfo row : rows)
//            {
//                System.out.println("studenCode:" + row.getStudentCode() + " time:" + row.getTimes());
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void deleteByCourse()
//    {
//        try
//        {
//            signinfoDaoImp.deleteByCourse((short)1000);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void deleteInfoTest()
//    {
//        try
//        {
//            signinfoDaoImp.deleteInfo((short)1001,"20171002");
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void deleteAllTest()
//    {
//        try
//        {
//            signinfoDaoImp.deleteAll();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
