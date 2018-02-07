package SmartClass.DaoImp;

import SmartClass.Dao.AnswerDao;
import SmartClass.POJO.Answer;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

import java.util.List;

/**
 * Created by 73681 on 2018/2/3.
 */
public class AnswerDaoImp implements AnswerDao
{
    /*已测试*/
    @Override
    public void add(Answer answer) throws Exception
    {
        DbUtil.save(answer);
    }

    /*已测试*/
    @Override
    public void update(Answer answer) throws Exception
    {
        DbUtil.update(answer);
    }

    /*已测试*/
    @Override
    public List<Answer> getByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String hql = "select a from Answer a" + where.toString();
        List results = DbUtil.list(hql,false);
        return results;
    }

    @Override
    public List<String> getbyOpt(short courseId, short opt) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("opt", opt);
        String sql = "select studentCode from answer" + where.toString();
        List results = DbUtil.list(sql,true);
        return results;
    }

    /*已测试*/
    @Override
    public void deleteByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        String sql = "delete from answer" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteAnswer(short courseId, String studentCode) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId", courseId);
        where.addExact("studentCode", studentCode);
        String sql = "delete from answer" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void deleteAll() throws Exception
    {
        String sql = "delete from answer";
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void clearByCourseId(short courseId) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("courseId",courseId);
        String sql = "update answer set opt=0" + where.toString();
        DbUtil.execute(sql,true);
    }

    /*已测试*/
    @Override
    public void clear() throws Exception
    {
        String sql = "update answer set opt=0";
        DbUtil.execute(sql,true);
    }
}
