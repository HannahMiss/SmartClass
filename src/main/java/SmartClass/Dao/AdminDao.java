package SmartClass.Dao;

import SmartClass.POJO.Administrator;

/**
 * Created by 73681 on 2018/2/4.
 */
public interface AdminDao
{
    /*根据username查询*/
    Administrator getByUserName(String username) throws Exception;
}
