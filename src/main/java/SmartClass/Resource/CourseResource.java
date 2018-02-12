package SmartClass.Resource;

import SmartClass.Dao.CourseDao;
import SmartClass.Dao.StudentCourseDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.StudentCourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.DbUtil;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/5.
 */
@Path("course")
public class CourseResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private CourseDao courseDao = new CourseDaoImp();
    private TeacherDao teacherDao = new TeacherDaoImp();
    private StudentCourseDao studentCourseDao = new StudentCourseDaoImp();
    /*添加课程*/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> addCourse(String reqText, @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*管理员是否登录*/
        if (!HttpSessionUtil.islogin(request,"role","admin"))
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员执行，请先登录！");
            return reply;
        }
        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        String courseName = jsReq.getString("courseName");
        short teacherId = (short) jsReq.getInt("teacherId");
        try
        {
            /*保存课程 */
            Teacher teacher = teacherDao.getById(teacherId);
            Course course = new Course();
            course.setCourseName(courseName);
            course.setTeacherByTeacherId(teacher);
            course.setCheckinFlag((byte)0);
            course.setCheckinTime((short)0);
            course.setCheckinDate(new Date(2000,1,1));
            course.setAnswerFlag((byte)0);
            course.setTimeCreated(DbUtil.now());
            course.setTimeModified(DbUtil.now());
            courseDao.save(course);
        }catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply;
        }

        reply.put("status",200);
        reply.put("msg","OK");
        return reply;
    }

    /*修改课程*/
    @Path("{courseId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> updataCourse(@PathParam("courseId")short courseId, String reqText,
                                           @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*管理员是否登录*/
        if (!HttpSessionUtil.islogin(request,"role","admin"))
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员执行，请先登录！");
            return reply;
        }
        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        String courseName = jsReq.getString("courseName");
        short teacherId = (short) jsReq.getInt("teacherId");

        try
        {
            Teacher teacher = teacherDao.getById(teacherId);
            /*修改课程 */
            Course course = courseDao.getById(courseId);
            course.setCourseName(courseName);
            course.setTeacherByTeacherId(teacher);
            course.setTimeModified(DbUtil.now());
            courseDao.update(course);
        }catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply;
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply;
    }

    /*删除某门课程*/
    @Path("{courseId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> deleteCourse(@PathParam("courseId") short courseId,
                                           @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*管理员是否登录*/
        if (!HttpSessionUtil.islogin(request,"role","admin"))
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员执行，请先登录！");
            return reply;
        }
        try
        {
            /*删除课程和学生的关系*/
            studentCourseDao.deleteByCourseId(courseId);
            /*删除课程,由于级联操作，
            *会同时删除sgininfo表中和courseId有关的记录
            *会同时删除comminication表中和courseId有关的记录
            * */
            courseDao.deleteById((short)courseId);
        }catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply;
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply;
    }

    /*得到所有课程*/
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String getAllCourse(@Context HttpServletRequest request)
    {
        JSONObject reply = new JSONObject();
        /*管理员是否登录*/
        if (!HttpSessionUtil.islogin(request,"role","admin"))
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员执行，请先登录！");
            return reply.toString();
        }
        try
        {
            /*查询*/
            List<Course> courses = courseDao.getAll();
            JSONArray courseArray = new JSONArray();
            for (Course c:courses)
            {
                JSONObject jsObj = new JSONObject();
                jsObj.put("courseId",c.getId());
                jsObj.put("courseName",c.getCourseName());
                jsObj.put("teacherName",c.getTeacherByTeacherId().getName());
                courseArray.put(jsObj);
            }
            reply.put("data", courseArray);
        }catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply.toString();
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply.toString();
    }
}
