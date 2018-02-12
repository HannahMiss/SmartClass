package SmartClass.Resource;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.SgininfoDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.SgininfoDaoImp;
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
@Path("signinfo")
public class SginInfoResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private SgininfoDao sgininfoDao = new SgininfoDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    /*得到某门课某次的签到信息*/
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSginInfoByCourse(@PathParam("courseId") short courseId,
                                      @QueryParam("time") int time,
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
            Course course = courseDao.getByIdWithStudent(courseId);
            if (time == 0)              /*代表查询全部的签到信息*/
            {

                List<Sgininfo> result = sgininfoDao.getAllByCourseId(courseId);

            }else                       /*代表查询某次的签到信息*/
            {
                List<String> result = sgininfoDao.getOneTimeByCourseId(courseId,(short)time);
                for (Student s:course.getStudents())
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
            }
        }catch (Exception e)
        {

        }
        return null;
    }
}
