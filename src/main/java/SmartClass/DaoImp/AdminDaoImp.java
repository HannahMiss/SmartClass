package SmartClass.DaoImp;

import SmartClass.Dao.AdminDao;
import SmartClass.POJO.Administrator;
import SmartClass.dbutil.DbUtil;
import SmartClass.dbutil.SqlWhere;

/**
 * Created by 73681 on 2018/2/4.
 */
public class AdminDaoImp implements AdminDao
{
    @Override
    public Administrator getByUserName(String username) throws Exception
    {
        SqlWhere where = new SqlWhere();
        where.addExact("username", username);
        String hql = "select a from Administrator a"+where.toString();
        Administrator result = (Administrator)DbUtil.get(hql,false);
        return result;
    }
}
