package SmartClass.Resource;

import SmartClass.Dao.*;

import SmartClass.DaoImp.*;

import SmartClass.POJO.*;
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



/*微信学生微信登录*/
@Path("student")
public class WxStudentResource {
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private TeacherDao teacherDao = new TeacherDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private AnswerDao answerDao = new AnswerDaoImp();
    private SgininfoDao sgininfoDao = new SgininfoDaoImp();
    private CommunicationDao communicationDao = new CommunicationDaoImp();


    private StudentDao studentDao = new StudentDaoImp();


    /*学生微信小程序登录 功能3.1.1*/
    @Path("login")
    @POST
    /*Produces指定响应体的数据格式,JSON格式*/
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> login(String reqText, @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();

        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        int studentId = jsReq.getInt("studentId");
        String studentPass = jsReq.getString("studentPass");

        /*查询*/
        try
        {
            Student student = studentDao.getById(studentId);
            if (student == null)
            {
                throw new Exception("学号错误！");
            }
            if (!student.getPassword().equals(studentPass))
            {
                throw new Exception("密码错误！");
            }
            /*校验成功*/
//            HttpSession session = request.getSession();
//            session.setAttribute("role","teacher");
//            session.setAttribute("user", student);

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


    /*微信小程序获取该学生课程列表 功能3.1.2*/
    @Path("courseInfo/{studentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String courseList(@PathParam("studentId") short studentId, @Context HttpServletRequest request)
    {
        JSONObject reply = new JSONObject();

        /*判断是否处于登录状态*/
//        if (!HttpSessionUtil.islogin(request,"role","student"))
//        {
//            reply.put("status",1000);
//            reply.put("msg","请先登录！");
//            return reply.toString();
//        }
        /*查询*/
        try
        {
            Student student = studentDao.getById(studentId);
            JSONArray classes = new JSONArray();
            for (Course course : student.getCourses())
            {
                JSONObject obj = new JSONObject();
                obj.put("courseId",course.getId());
                obj.put("courseName",course.getCourseName());
                obj.put("teacher",course.getTeacherByTeacherId().getName());
                classes.put(obj);
            }
            reply.put("courseList",classes);
        } catch (Exception e)
        {
//            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply.toString();
        }
        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply.toString();
    }


//    /*微信小程序学生签到 功能3.2*/
//    /*老师补签*/
//    @Path("sginIn")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
//    public Map sign(String reqText,@Context HttpServletRequest request)
//    {
//        Map reply = new HashMap<String,Object>();
//        /*判断是否处于登录状态*/
////        if (!HttpSessionUtil.islogin(request,"role","teacher"))
////        {
////            reply.put("status",1000);
////            reply.put("msg","此操作只能由老师执行，请先登录！");
////            return reply;
////        }
//        /*处理请求*/
//        /*将request中的文本转化为JSON格式*/
//        JSONObject jsReq = new JSONObject(reqText);
//        /*将JSON中相应的内容填写到不同的变量*/
//        short courseId = (short) jsReq.getInt("courseId");
//        String studentCode = jsReq.getString("studentCode");
//        short time = (short)jsReq.getInt("time");
//
//        Sgininfo sgininfo = new Sgininfo();
//        sgininfo.setCourseId(courseId);
//        sgininfo.setStudentCode(studentCode);
//        sgininfo.setTimes(time);
//        sgininfo.setTimeCreated(DbUtil.now());
//        try
//        {
//            /*添加*/
//            sgininfoDao.add(sgininfo);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            reply.put("status",1000);
//            reply.put("msg","数据库错误"+e.getMessage());
//            return reply;
//        }
//        /*应答*/
//        reply.put("status",200);
//        reply.put("msg","OK");
//        return reply;
//    }


    /*-------------微信小程序 测验功能相关  功能3.3--------------*/
    @Path("test")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String studentTest(String reqText,@Context HttpServletRequest request){
        JSONObject reply = new JSONObject();

        /*处理请求*/
        /*将request中的文本转化为JSON格式*/
        JSONObject jsReq = new JSONObject(reqText);
        /*将JSON中相应的内容填写到不同的变量*/
        /*courseId:学生要签到的课程号
            studentId:学生的学号
            testOpt:学生选择的选项



            private short courseId;
            private short opt;
            private String studentCode;
        */

        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentCode");
        short opt = (short)jsReq.getInt("testOpt");



        /*如果没有开启测验，则返回未开启测验信息
        * 如果开启测验，则把收到的JSON信息写入库
        * */
        try{
            Course course = courseDao.getById(courseId);
            byte answerFlag = course.getAnswerFlag();
            /*answerFlag：
                1：测验开启了
                0：测验未开启*/
            if(answerFlag == 0){
                reply.put("status",2000);
                reply.put("msg","本课程测验还未开始！");
            }
            else if(answerFlag == 1){
                reply.put("status",200);
                reply.put("msg","提交结果成功");

                /*将学生的回答写到数据库内*/
                Answer answer = new Answer();
                answer.setCourseId(courseId);
                answer.setStudentCode(studentCode);
                answer.setOpt(opt);

                answerDao.update(answer);
            }
        } catch (Exception e){
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply.toString();
        }
        return reply.toString();

    }

    /*微信小程序  提问功能相关 功能3.4*/
    @Path("ask")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String studentAsk(String reqText,@Context HttpServletRequest request){
        JSONObject reply = new JSONObject();
        JSONObject jsReq = new JSONObject(reqText);

        /*courseId:学生要签到的课程号
            studentId:学生的学号
            questionContent:学生提问信息*/
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentCode");
        String descr =  jsReq.getString("questionContent");



        /*如果没有上课，则返回 还未上课
        * 如果开始上课，则把收到的JSON（提问内容）信息写入库
        * */
        try{
            Course course = courseDao.getById(courseId);
            byte classFlag = course.getClassFlag();
            /*answerFlag：
                1：课程上课中
                0：课程还未上课*/
            if(classFlag == 0){
                reply.put("status",2000);
                reply.put("msg","本课程未在上课中，无法提问");
//                reply.put("status",2000);
//                reply.put("msg","本课程测验还未开始！");
            }
            else if(classFlag == 1){
                reply.put("status",200);
                reply.put("msg","提交结果成功");

                /*将学生的回答写到数据库内*/
                Communication communication = new Communication();
                /*private short courseId;
    private byte flag;
    private String descr;
    private Timestamp timeCreated;
    private byte answered;
    private int id;
    private String studentCode;*/
                communication.setCourseId(courseId);
                communication.setStudentCode(studentCode);
                communication.setDescr(descr);

                communication.setAnswered((byte)0);
                communication.setFlag((byte)0);
                communication.setTimeCreated(DbUtil.now());
                
                communicationDao.add(communication);


            }
        } catch (Exception e){
            reply.put("status",1000);
            reply.put("msg","数据库错误"+e.getMessage());
            return reply.toString();
        }
        return reply.toString();



    }

}








