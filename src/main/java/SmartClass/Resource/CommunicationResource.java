package SmartClass.Resource;
import SmartClass.Dao.AnswerDao;
import SmartClass.Dao.CommunicationDao;
import SmartClass.DaoImp.CommunicationDaoImp;
import SmartClass.POJO.Communication;
import SmartClass.dbutil.DbUtil;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
@Path("communication")
public class CommunicationResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private CommunicationDao communicationDao = new CommunicationDaoImp();


    /**********************************已测试******************************/
    /*得到提问信息*/
    @Path("0/{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String getQuestion(@PathParam("courseId")short courseId,
                                          @QueryParam("time")String timestamp,
                                          @QueryParam("offset")int offset,
                                          @QueryParam("limit")int limit,
                                          @Context HttpServletRequest request)
    {
        JSONObject reply = new JSONObject();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            return reply.toString();
        }
        /*查询*/
        try
        {
         List<Communication> results =  communicationDao.getQuestionsLimit(courseId, timestamp,offset,limit);
         JSONArray questionArray = new JSONArray();
         for (Communication c:results)
         {
             JSONObject question = new JSONObject();
             question.put("quesId",c.getId());
             question.put("description",c.getDescr());
             question.put("studentId",c.getStudentCode());
             question.put("isAnswered",c.getAnswered());
             questionArray.put(question);
         }
         reply.put("questions",questionArray);
        } catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误！"+e.getMessage());
            return reply.toString();
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply.toString();
    }

    /*得到反馈信息*/
    @Path("1/{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> getFeedback(@PathParam("courseId")short courseId,
                                     @QueryParam("time")String timestamp,
                                     @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            return reply;
        }
        /*查询*/
        try
        {
            /*查询的是反馈*/
            List<Communication> feedbacks = communicationDao.getFeedbacks(courseId,timestamp);
            int total = feedbacks.size();
            int i = 0;
            int quickNum = 0;                       /*反馈说太快的人数*/
            int invisibleNum = 0;                   /*反馈看不见的人数*/
            int inaudibleNum = 0;                   /*反馈听不清的人数*/
            Timestamp lastTimestamp = DbUtil.now();         /*最后一条反馈的时间戳*/
            for (Communication c : feedbacks)
            {
                i++;
                if (c.getDescr().equals("quick"))
                {
                    quickNum++;
                }else if (c.getDescr().equals("invisible"))
                {
                    invisibleNum++;
                }else if (c.getDescr().equals("inaudible"))
                {
                    inaudibleNum++;
                }
                if (i == total)
                {
                    lastTimestamp = c.getTimeCreated();
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(lastTimestamp);
            reply.put("quick",quickNum);
            reply.put("invisible",invisibleNum);
            reply.put("inaudible",inaudibleNum);
            reply.put("time",time);
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



    /*删除问题*/
    @Path("{questionId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> deleteQuestions(@PathParam("questionId")int questionId,
                                              @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            return reply;
        }
        /*查询*/
        try
        {
            communicationDao.deleteById(questionId);

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

    /***************************已测试****************************************/
    /*将问题修改已回答*/
    @Path("{questionId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> tagAnswered(@PathParam("questionId")int questionId,
                                          @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            return reply;
        }
        /*查询*/
        try
        {
            /*将问题标记成已读*/
            communicationDao.setAnswered(questionId,1);

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
