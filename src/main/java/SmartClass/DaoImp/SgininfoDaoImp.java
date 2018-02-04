package SmartClass.DaoImp;

import SmartClass.Dao.SgininfoDao;
import SmartClass.POJO.Sgininfo;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class SgininfoDaoImp implements SgininfoDao
{
    /*已测试*/
    @Override
    public void add(Sgininfo info) throws Exception
    {
        DbUtil.save(info);
    }

    /*已测试*/
    @Override
    public List<String> getOneTimeByCourseId(short courseId, short times) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("times", times);

        String sql = "select studentCode from sgininfo" + where.toString();
        List results = DbUtil.list(sql,true);
        return results;
    }

    /*已测试*/
    @Override
    public List<Sgininfo> getAllByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String hql = "select s from Sgininfo s" + where.toString();
        List results = DbUtil.list(hql,false);
        return results;
    }

    /*已测试*/
    @Override
    public void deleteByCourse(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String sql = "delete from sgininfo" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteInfo(short courseId, String stuCode) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("studentCode", stuCode);
        String sql = "delete from sgininfo" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteAll() throws Exception
    {
        String sql = "delete from sgininfo";
        DbUtil.execute(sql,true);
    }
}
