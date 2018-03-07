package SmartClass.DaoImp;

import SmartClass.Dao.SigninfoDao;
import SmartClass.POJO.Signinfo;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class SigninfoDaoImp implements SigninfoDao
{
    /*已测试*/
    @Override
    public void add(Signinfo info) throws Exception
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

        String sql = "select studentCode from signinfo" + where.toString();
        List results = DbUtil.list(sql,true);
        return results;
    }

    /*已测试*/
    @Override
    public List<Signinfo> getAllByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String hql = "select s from signinfo s" + where.toString();
        List results = DbUtil.list(hql,false);
        return results;
    }

    /*已测试*/
    @Override
    public void deleteByCourse(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String sql = "delete from signinfo" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteInfo(short courseId, String stuCode) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("studentCode", stuCode);
        String sql = "delete from signinfo" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteAll() throws Exception
    {
        String sql = "delete from signinfo";
        DbUtil.execute(sql,true);
    }
}
