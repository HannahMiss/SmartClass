package SmartClass.Controller;

import SmartClass.Dao.AnswerDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.POJO.Answer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
@Path("answer")
public class AnswerResource
{

    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private AnswerDao answerDao = new AnswerDaoImp();
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> getResult(@PathParam("courseId")short courseId)
    {
        Map reply = new HashMap<String,Object>();
        /*查询*/
        try
        {
            /*得到所有的答题信息*/
            int ansewerNone = answerDao.getbyOpt(courseId,(short)0).size();
            int ansewerA = answerDao.getbyOpt(courseId,(short)1).size();
            int ansewerB = answerDao.getbyOpt(courseId,(short)2).size();
            int ansewerC = answerDao.getbyOpt(courseId,(short)3).size();
            int ansewerD = answerDao.getbyOpt(courseId,(short)4).size();
            int total = ansewerA + ansewerB + ansewerC + ansewerD + ansewerNone;
            /*应答*/
            reply.put("num",total);
            reply.put("A",ansewerA);
            reply.put("B",ansewerA);
            reply.put("C",ansewerA);
            reply.put("D",ansewerA);
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
