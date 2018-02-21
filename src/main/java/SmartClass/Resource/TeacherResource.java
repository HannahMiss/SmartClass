package SmartClass.Resource;

import SmartClass.Dao.AnswerDao;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.SgininfoDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.SgininfoDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Sgininfo;
import SmartClass.POJO.Teacher;
import SmartClass.dbutil.DbUtil;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/5.
 */
@Path("teacher")
public class TeacherResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private TeacherDao teacherDao = new TeacherDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private AnswerDao answerDao = new AnswerDaoImp();
    private SgininfoDao sgininfoDao = new SgininfoDaoImp();

    /********************************已测试******************************/
    /*添加老师*/
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> addteacher(String reqText,@Context HttpServletRequest request)
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
        String name = jsReq.getString("teacherName");
        String code = jsReq.getString("teacherWorkId");
        String password = jsReq.getString("teacherPwd");

        /*添加*/
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setCode(code);
        teacher.setPassword(password);
        teacher.setTimeCreated(DbUtil.now());
        teacher.setTimeModified(DbUtil.now());
        try
        {
            teacherDao.save(teacher);
        } catch (Exception e)
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

    /*删除老师*/
    @Path("{teacherId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> deleteTeacher(@PathParam("teacherId") short teacherId,
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
            /*删除*/
            teacherDao.deleteById(teacherId);
        } catch (Exception e)
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


    /*修改老师*/
    @Path("{teacherId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> updataTeacher(@PathParam("teacherId") short teacherId,String reqText,
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
        String name = jsReq.getString("teacherName");
        String code = jsReq.getString("teacherWorkId");
        String password = jsReq.getString("teacherPwd");
        try
        {
            Teacher teacher = teacherDao.getById(teacherId);
            teacher.setName(name);
            teacher.setCode(code);
            teacher.setPassword(password);
            teacher.setTimeModified(DbUtil.now());
            teacherDao.update(teacher);
        } catch (Exception e)
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


    /*得到所有老师*/
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
            List<Teacher> teachers = teacherDao.getAll();
            JSONArray teacherArray = new JSONArray();
            for (Teacher t:teachers)
            {
                JSONObject jsTeacher = new JSONObject();
                jsTeacher.put("teacherId",t.getId());
                jsTeacher.put("teacherName",t.getName());
                teacherArray.put(jsTeacher);
            }
            reply.put("data", teacherArray);
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


    /*老师登录*/
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> login(String reqText, @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();

        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        String username = jsReq.getString("username");
        String password = jsReq.getString("password");

        /*查询*/
        try
        {
            Teacher teacher = teacherDao.getByUserName(username);
            if (teacher == null)
            {
                throw new Exception("用户不出存在！");
            }
            if (!teacher.getPassword().equals(password))
            {
                throw new Exception("密码错误！");
            }
            /*校验成功*/
            HttpSession session = request.getSession();
            session.setAttribute("role","teacher");
            session.setAttribute("user", teacher);
        } catch (Exception e)
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

    /*老师是否登录*/
    @Path("islogin")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> isLogin(@Context HttpServletRequest request)
    {


        Map reply = new HashMap<String,Object>();
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (role.equals("teacher"))
        {
            Teacher teacher =(Teacher) session.getAttribute("user");
            reply.put("status", 200);
            reply.put("msg","OK");
            reply.put("user",teacher.getName());
            return reply;
        }else
        {
            reply.put("status", 1000);
            reply.put("msg","未登录");
        }
        return reply;
    }


    /*老师所交的所有课程*/
    @Path("classlist/{teacherId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String classList(@PathParam("teacherId") short teacherId,@Context HttpServletRequest request)
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
            Teacher teacher = teacherDao.getById(teacherId);
            JSONArray classes = new JSONArray();
            for (Course course : teacher.getCoursesById())
            {
                JSONObject obj = new JSONObject();
                obj.put("classId",course.getId());
                obj.put("className",course.getCourseName());
                classes.put(obj);
            }
            reply.put("data",classes);
        } catch (Exception e)
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

    /*老师补签*/
    @Path("sgininfo")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map sgin(String reqText,@Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        /*判断是否处于登录状态*/
        if (!HttpSessionUtil.islogin(request,"role","teacher"))
        {
            reply.put("status",1000);
            reply.put("msg","此操作只能由老师执行，请先登录！");
            return reply;
        }
        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentCode");
        short time = (short)jsReq.getInt("time");

        Sgininfo sgininfo = new Sgininfo();
        sgininfo.setCourseId(courseId);
        sgininfo.setStudentCode(studentCode);
        sgininfo.setTimes(time);
        sgininfo.setTimeCreated(DbUtil.now());
        try
        {
            /*添加*/
            sgininfoDao.add(sgininfo);
        } catch (Exception e)
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

    /*老师开始或结束点名*/
    @Path("checkin/{courseId}/{flag}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map checkin(@PathParam("courseId") short courseId,
                       @PathParam("flag") int flag,
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
            Course course = courseDao.getById(courseId);
            int time = course.getCheckinTime();                     /*本次签到的次数*/
            if (!course.getCheckinDate().equals(DbUtil.nowDate())
                    && flag == 1)   /*同一天多次访问这个资源，time只会加一次*/
            {
                time++;
                course.setCheckinDate(DbUtil.nowDate());
                course.setCheckinTime((short)time++);
            }
            course.setCheckinFlag((byte)flag);
            course.setTimeModified(DbUtil.now());
            courseDao.update(course);
        } catch (Exception e)
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


    /*学生开始答题*/
    /*flag:1代表开始答题，0代表结束答题*/
    @Path("/classtest/{courseId}/{flag}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map classtest(@PathParam("courseId") short courseId,
                            @PathParam("flag") int flag,
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
            if (flag == 1)/*在开始答题前清空该课的答题信息*/
            {
                answerDao.clearByCourseId(courseId);
            }
            Course course = courseDao.getById(courseId);
            course.setAnswerFlag((byte)flag);
            course.setTimeModified(DbUtil.now());
            courseDao.update(course);

        } catch (Exception e)
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


}
