package SmartClass.Resource;

import SmartClass.Dao.*;
import SmartClass.DaoImp.*;
import SmartClass.POJO.*;
import SmartClass.dbutil.DbUtil;
import SmartClass.tool.HttpSessionUtil;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/5.
 */
@Path("student")
public class StudentResource
{
    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private TeacherDao teacherDao = new TeacherDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private AnswerDao answerDao = new AnswerDaoImp();
    private SgininfoDao sgininfoDao = new SgininfoDaoImp();
    private CommunicationDao communicationDao = new CommunicationDaoImp();
    private StudentCourseDao studentCourseDao = new StudentCourseDaoImp();
    private StudentDao studentDao = new StudentDaoImp();

    /*******************************已测试**************************************************/
    /*上传学生名单，并添加某门课的所有学生*/
    @Path("upload/{courseId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> uploadStudents(@PathParam("courseId") short courseId,
                                             @Context HttpServletRequest request,
                                             @Context HttpServletResponse response)
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
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            request.setCharacterEncoding("UTF-8");
            ServletFileUpload upload = new ServletFileUpload();

            // 用ServletFileUpload类来解析请求
            FileItemIterator itemIterator = upload.getItemIterator(request);
            while (itemIterator.hasNext())
            {
                FileItemStream item = itemIterator.next();
                /*不是普通的表单域*/
                if (!item.isFormField())
                {
                    String filename = String.valueOf(courseId)+"studentsinfo.xlsx";
                    InputStream filedStream = item.openStream();
                    // 从FieldStream读取数据, 保存到目标文件
                    File tmpDir = new File(request.getRealPath("/"));
                    File dst = new File(tmpDir,filename);
                    System.out.println(dst);
                    FileOutputStream streamOut = new FileOutputStream(dst);
                    byte[] buf = new byte[1024];
                    while (true)
                    {
                        int n = filedStream.read(buf);
                        if (n<0)break;
                        if (n==0) continue;
                        streamOut.write(buf,0,n);
                    }
                    filedStream.close();
                    streamOut.close();
                }
            }


            /*文件上传后解析excel文件，将数据导入到数据库*/
            Set<Student> students = new HashSet<Student>();              /*保存该课程下的学生*/
            File stuExcel = new File(request.getRealPath("/"),
                    String.valueOf(courseId)+"studentsinfo.xlsx");
            FileInputStream inputStream = new FileInputStream(stuExcel);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet stuInfoSheet = workbook.getSheetAt(0);
            int count = 0;                                      // 为跳过第一行目录设置count
            for (Row row:stuInfoSheet)
            {
                /*跳过第一行目录*/
                if (count == 0)
                {
                    count++;
                    continue;
                }
                // 如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) break;
                row.getCell(0).setCellType(CellType.STRING);
                String studentCode = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(CellType.STRING);
                String name = row.getCell(1).getStringCellValue();

                Student student = null;

                student = studentDao.getByCode(studentCode);        /*查询此学生是否已经在数据库中*/
                if (student == null)
                {
                    student = new Student();
                    student.setCode(studentCode);
                    student.setPassword(studentCode.substring(studentCode.length()-6));
                    student.setName(name);
                    student.setTimerCreated(DbUtil.now());
                    student.setTimerModified(DbUtil.now());
                    studentDao.save(student);
                }
                students.add(student);
            }

            /*给这门课程添加学生*/
            Course course = courseDao.getById(courseId);
            course.setStudents(students);
            courseDao.update(course);

            /*初始化answer表*/
            answerDao.addByCourseId(courseId,students);

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



    /*******************************已测试**************************************************/
    /*为一门课添加一个学生*/
    @Path("{courseId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> addStudent(@PathParam("courseId") short courseId, String reqText,
                                         @Context HttpServletRequest request,
                                         @Context HttpServletResponse response)
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
        String name = jsReq.getString("studentName");
        String code =  jsReq.getString("studentCode");
        try
        {
            Student student = studentDao.getByCode(code);
            Course course = courseDao.getById(courseId);
            if (student != null)                /*如果这个学生已经在数据库中，更新*/
            {
                student.setName(name);
                student.setCode(code);
                student.setPassword(code.substring(code.length() - 6));
                student.getCourses().add(course);
                student.setTimerModified(DbUtil.now());
                studentDao.update(student);
            } else                           /*如果这个学生没在数据库中，new一个对象并保存*/
            {
                student = new Student();
                student.setName(name);
                student.setCode(code);
                student.setPassword(code.substring(code.length() - 6));
                student.getCourses().add(course);
                student.setTimerCreated(DbUtil.now());
                student.setTimerModified(DbUtil.now());
                studentDao.save(student);
            }
            studentCourseDao.add(courseId,student.getId());

            /*添加答案表记录*/
            Answer newAnswer = new Answer();
            newAnswer.setCourseId(courseId);
            newAnswer.setStudentCode(student.getCode());
            newAnswer.setOpt((short)0);
            answerDao.add(newAnswer);
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


    /********************************已测试*****************************************/
    /*删除某门课的学生*/
    @Path("{courseId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> deleteStudent(@PathParam("courseId") short courseId, String reqText,
                                            @Context HttpServletRequest request,
                                            @Context HttpServletResponse response)
    {

        Map reply = new HashMap<String,Object>();
        /*处理请求*/
        /*管理员是否登录*/
        if (!HttpSessionUtil.islogin(request,"role","admin"))
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员执行，请先登录！");
            return reply;
        }
        JSONObject jsReq = new JSONObject(reqText);
        int id = jsReq.getInt("studentId");
        try
        {
            Student student = studentDao.getById(id);
            String studentCode = student.getCode();
            studentCourseDao.delete(courseId,id);                       /*student_couse删除学生和课程的关系*/
            answerDao.deleteAnswer(courseId,studentCode);               /*删除answer表的相关数据*/
            sgininfoDao.deleteInfo(courseId,studentCode);               /*删除sgininfo表相关数据*/
            communicationDao.deleteByCidScode(courseId,studentCode);    /*删除communication表相关的数据*/
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


    /*****************************已测试*****************************************/
    /*得到某门课的所有学生,分页查询*/
    @Path("{courseId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getByCourseId(@PathParam("courseId") short courseId,
                                @QueryParam("offset") int offset,
                                @QueryParam("limit") int limit,
                                @Context HttpServletRequest request,
                                @Context HttpServletResponse response)
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
            if (offset == 0)
            {
                Course course = courseDao.getByIdWithStudent(courseId);
                int num = course.getStudents().size();
                reply.put("total",num);
            }
            List<Student> students = studentDao.getByCourseId(courseId,offset,limit);
            JSONArray data = new JSONArray();
            for (Student student:students)
            {
                JSONObject stu = new JSONObject();
                stu.put("studentId",student.getId());
                stu.put("studentName",student.getName());
                stu.put("studentCode",student.getCode());
                data.put(stu);
            }
            reply.put("data",data);
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






    /*-----------------------微信小程序模块-----------------------------

    -----------------------------------------------------------------
    -----------------------------------------------------------------
    -----------------------------------------------------------------

    */


    /*学生微信小程序登录 功能3.1.1*/
    @Path("login")
    @POST
    /*Produces指定响应体的数据格式,JSON格式*/
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> login(String reqText)
    {
        Map reply = new HashMap<String,Object>();
        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        String studentCode = jsReq.getString("studentId");
        String studentPass = jsReq.getString("studentPass");

        /*查询*/
        try
        {
            Student student = studentDao.getByCode(studentCode);
            if (student == null)
            {

                throw new Exception("学号错误！");
            }
            if (!student.getPassword().equals(studentPass))
            {
                throw new Exception("密码错误！");
            }

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


    /*微信小程序学生签到 功能3.2*/
    @Path("sginIn")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map sign(String reqText)
    {
        Map reply = new HashMap<String,Object>();
        /*处理请求*/
        /*将request中的文本转化为JSON格式*/
        JSONObject jsReq = new JSONObject(reqText);
        /*将JSON中相应的内容填写到不同的变量*/
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentCode");
        String uuidStu = jsReq.getString("idKey");

        boolean isAllowSign = false;
        try
        {
            /*第一次签到,生成一个uuid给小程序端*/
            if (uuidStu.equals("") || uuidStu == null)
            {
                UUID uuid = UUID.randomUUID();
                String uuidStr = uuid.toString();
                reply.put("idKey",uuidStr);

            }else
            {
                String resUUid = studentCourseDao.getUUid(courseId,uuidStu);
                if (resUUid.equals("") || uuidStu == null)
                {
                    isAllowSign = true;   /*允许签到*/
                }else
                {
                    /*不允许签到*/
                    reply.put("status",1000);
                    reply.put("msg","同一手机不能重复签到！");
                    return reply;
                }
            }
            /*得到签到次数*/
            Course course = courseDao.getById(courseId);
            short time = course.getCheckinTime();

            /*添加签到信息*/
            Sgininfo sgininfo = new Sgininfo();
            sgininfo.setCourseId(courseId);
            sgininfo.setStudentCode(studentCode);
            sgininfo.setTimes(time);
            sgininfo.setTimeCreated(DbUtil.now());
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


    /*-------------微信小程序 测验功能相关  功能3.3--------------*/
    @Path("test")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String studentTest(String reqText)
    {
        JSONObject reply = new JSONObject();
        /*处理请求*/
        /*将request中的文本转化为JSON格式*/
        JSONObject jsReq = new JSONObject(reqText);
        /*将JSON中相应的内容填写到不同的变量*/
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentCode");
        short opt = (short)jsReq.getInt("testOpt");
        /*如果没有开启测验，则返回未开启测验信息
        * 如果开启测验，则把收到的JSON信息写入库
        * */
        try
        {
            Course course = courseDao.getById(courseId);
            byte answerFlag = course.getAnswerFlag();
            /*answerFlag：
                1：测验开启了
                0：测验未开启*/
            if(answerFlag == 0)
            {
                reply.put("status",1000);
                reply.put("msg","本课程测验还未开始！");
            }
            else if(answerFlag == 1)
            {
                reply.put("status",200);
                reply.put("msg","提交结果成功");

                /*将学生的回答写到数据库内*/
                Answer answer = new Answer();
                answer.setCourseId(courseId);
                answer.setStudentCode(studentCode);
                answer.setOpt(opt);
                answerDao.update(answer);
            }
        } catch (Exception e)
        {
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
    public String studentAsk(String reqText)
    {
        JSONObject reply = new JSONObject();
        JSONObject jsReq = new JSONObject(reqText);
        /*courseId:学生要签到的课程号
            studentId:学生的学号
            questionContent:学生提问信息*/
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentId");
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
            if(classFlag == 0)
            {
                reply.put("status",1000);
                reply.put("msg","本课程未在上课中，无法提问");
            }
            else if(classFlag == 1)
            {
                reply.put("status",200);
                reply.put("msg","提交结果成功");
                /*将学生的回答写到数据库内*/
                Communication communication = new Communication();
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


    /*微信小程序  提问功能相关 功能3.4*/
    @Path("feedback")
    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public String stuFeedback(String reqText)
    {
        JSONObject reply = new JSONObject();

        JSONObject jsReq = new JSONObject(reqText);
        short courseId = (short) jsReq.getInt("courseId");
        String studentCode = jsReq.getString("studentId");
        String descr =  jsReq.getString("feedbackOpt");

        /*如果没有上课，则返回 还未上课
        * 如果开始上课，则把收到的JSON（提问内容）信息写入库
        * */
        try
        {
            Course course = courseDao.getById(courseId);
            byte classFlag = course.getClassFlag();
            /*answerFlag：
              1：课程上课中
              0：课程还未上课
             */
            if(classFlag == 0)
            {
                reply.put("status",1000);
                reply.put("msg","本课程未在上课中，无法提问");
            }
            else if(classFlag == 1)
            {
                reply.put("status",200);
                reply.put("msg","提交结果成功");
                /*将学生反馈写到数据库内*/
                Communication communication = new Communication();
                communication.setCourseId(courseId);
                communication.setStudentCode(studentCode);
                communication.setAnswered((byte)0);
                communication.setDescr(descr);
                communication.setFlag((byte)1);             /*反馈*/
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
