package SmartClass.Resource;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.SgininfoDao;
import SmartClass.Dao.StudentDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.SgininfoDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Sgininfo;
import SmartClass.POJO.Student;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
@Path("sgininfo")
public class SginInfoResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private SgininfoDao sgininfoDao = new SgininfoDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private StudentDao studentDao = new StudentDaoImp();

    /****************************************已测试********************************************/
    /*得到某门课某次的签到信息*/
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSginInfoByCourse(@PathParam("courseId") short courseId,
                                      @QueryParam("time") int time,
                                      @QueryParam("offset") int offset,
                                      @QueryParam("limit") int limit,
                                      @Context HttpServletRequest request)
    {
        JSONObject reply = new JSONObject();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status", 1000);
            reply.put("msg", "此操作只能由老师执行，请先登录！");
            return reply.toString();
        }
        try
        {
            JSONArray info = new JSONArray();
            List<Student> students = studentDao.getByCourseId(courseId,offset,limit);
            if (time == 0)              /*代表查询全部的签到信息*/
            {
                /*暂时不写*/
                List<Sgininfo> result = sgininfoDao.getAllByCourseId(courseId);

            }else                       /*代表查询某次的签到信息*/
            {
                List<String> result = sgininfoDao.getOneTimeByCourseId(courseId,(short)time);
                for (Student s:students)
                {
                    JSONObject jsSginInfo = new JSONObject();
                    jsSginInfo.put("studentId",s.getCode());
                    if (result.contains(s.getCode()))
                    {
                        jsSginInfo.put("attendances",1);
                    }else
                    {
                        jsSginInfo.put("attendances",0);
                    }
                    info.put(jsSginInfo);
                }
                reply.put("data",info);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply.toString();
        }

        reply.put("status",200);
        reply.put("msg","OK");
        return reply.toString();

    }
}
