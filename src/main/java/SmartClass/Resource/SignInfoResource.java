package SmartClass.Resource;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.SigninfoDao;
import SmartClass.Dao.StudentDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.SigninfoDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Signinfo;
import SmartClass.POJO.Student;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/7.
 */
@Path("signinfo")
public class SignInfoResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private SigninfoDao signinfoDao = new SigninfoDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private StudentDao studentDao = new StudentDaoImp();

    /****************************************已测试********************************************/
    /*得到某门课某次的签到信息*/
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getsignInfoByCourse(@PathParam("courseId") short courseId,
                                      @QueryParam("offset") int offset,
                                      @QueryParam("limit") int limit,
                                      @Context HttpServletRequest request,
                                      @Context HttpServletResponse response)
    {
        JSONObject reply = new JSONObject();
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
        try
        {
            JSONArray info = new JSONArray();
            Course course = courseDao.getById(courseId);
            short time = course.getCheckinTime();
            List<Student> students = studentDao.getByCourseId(courseId, offset, limit);
            List<String> result = signinfoDao.getOneTimeByCourseId(courseId, time);
            for (Student s : students)
            {
                JSONObject jssignInfo = new JSONObject();
                jssignInfo.put("studentId", s.getCode());
                if (result.contains(s.getCode()))
                {
                    jssignInfo.put("attendances", 1);
                } else
                {
                    jssignInfo.put("attendances", 0);
                }
                info.put(jssignInfo);
            }
            reply.put("data", info);

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
