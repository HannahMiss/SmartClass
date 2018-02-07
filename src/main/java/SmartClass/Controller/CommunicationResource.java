package SmartClass.Controller;

import SmartClass.Dao.AnswerDao;
import SmartClass.Dao.CommunicationDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.DaoImp.CommunicationDaoImp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
public class CommunicationResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private CommunicationDao communicationDao = new CommunicationDaoImp();
    @Path("{flag}/{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> getCom(@PathParam("courseId")short courseId,
                                     @QueryParam("time")String time)
    {
        Map reply = new HashMap<String,Object>();
        /*查询*/
        try
        {
            /*得到所有的答题信息*/

        } catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误！"+e.getMessage());
            return reply;
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply;
    }
}
