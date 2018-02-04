package SmartClass.DaoImp;

import SmartClass.Dao.CommunicationDao;
import SmartClass.POJO.Communication;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class CommunicationDaoImp implements CommunicationDao
{
    /*已测试*/
    /*添加*/
    @Override
    public void add(Communication communication) throws Exception
    {
        DbUtil.save(communication);
    }

    /*已测试*/
    /*更新*/
    @Override
    public void setAnswered(int cId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id", cId);
        String sql = "update communication set answered=1" + where.toString();
        DbUtil.execute(sql,true);
    }


    /*已测试*/
    /*根据时间戳，得到问题*/
    @Override
    public List<Communication> getQuestions(short courseId, String timestamp) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("flag", 0);
        where.addGtE("timeCreated", timestamp);
        String hql = "select c from Communication c" + where.toString();
        List results = DbUtil.list(hql,false);
        return results;
    }

    /*已测试*/
    /*根据时间戳，得到反馈*/
    @Override
    public List<Communication> getFeedbacks(short courseId, String timestamp) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("flag", 1);

        where.addLtE("timeCreated", timestamp);
        String hql = "select c from Communication c" + where.toString();
        List results = DbUtil.list(hql,false);
        return results;
    }

    /*已测试*/
    @Override
    public void deleteById(int id) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("id",id);
        String sql = "delete from communication" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteAll() throws Exception
    {
        String sql = "delete from communication";
        DbUtil.execute(sql,true);
    }

}
