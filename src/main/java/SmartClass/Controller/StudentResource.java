package SmartClass.Controller;

import SmartClass.Dao.CourseDao;
import SmartClass.Dao.StudentCourseDao;
import SmartClass.Dao.StudentDao;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.StudentCourseDaoImp;
import SmartClass.DaoImp.StudentDaoImp;
import SmartClass.POJO.Course;
import SmartClass.POJO.Student;
import SmartClass.POJO.StudentCourse;
import SmartClass.dbutil.DbUtil;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 73681 on 2018/2/5.
 */
@Path("student")
public class StudentResource
{
    private StudentDao studentDao = new StudentDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private StudentCourseDao studentCourseDao = new StudentCourseDaoImp();
    /*上传学生名单，并添加某门课的所有学生*/
    @Path("upload/{courseId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> uploadStudents(@PathParam("courseId") short courseId,
                                             @Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
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

            /*文件上传后解析文件，将数据导入到数据库*/


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


    /*为一门课添加一个学生*/
    @Path("{courseId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> addStudent(@PathParam("courseId") short courseId, String reqText)
    {

        Map reply = new HashMap<String,Object>();
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


    /*删除某门课的学生*/
    @Path("{courseId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> deleteStudent(@PathParam("courseId") short courseId, String reqText)
    {

        Map reply = new HashMap<String,Object>();
        /*处理请求*/
        JSONObject jsReq = new JSONObject(reqText);
        int id = jsReq.getInt("studentId");
        try
        {
            studentCourseDao.delete(courseId,id);
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



}
