package SmartClass.Resource;

import SmartClass.Dao.AnswerDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.tool.HttpSessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
@Path("answer")
public class AnswerResource
{

    /********************************已测试**********************************************/
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private AnswerDao answerDao = new AnswerDaoImp();
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> getResult(@PathParam("courseId")short courseId,
                                        @Context HttpServletRequest request,
                                        @Context HttpServletResponse response)
    {
        Map reply = new HashMap<String,Object>();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            try
            {
                response.sendRedirect("/teacher/html/index.html");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

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
            reply.put("B",ansewerB);
            reply.put("C",ansewerC);
            reply.put("D",ansewerD);
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
