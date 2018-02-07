package SmartClass.Controller;

import SmartClass.Dao.AdminDao;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.AdminDaoImp;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Administrator;
import SmartClass.POJO.Course;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.DbUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    /*添加课程*/
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> addCourse(String reqText)
    {
        Map reply = new HashMap<String,Object>();
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
    public Map<String,Object> updataCourse(@PathParam("courseId")short courseId, String reqText)
    {
        Map reply = new HashMap<String,Object>();
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
    public Map<String,Object> deleteCourse(@PathParam("courseId") int courseId)
    {
        Map reply = new HashMap<String,Object>();

        try
        {
            /*删除课程*/
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
    public String getAllCourse()
    {
        JSONObject reply = new JSONObject();
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
