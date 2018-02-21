package SmartClass.Resource;

import SmartClass.Dao.AdminDao;
import SmartClass.Dao.CourseDao;
import SmartClass.Dao.TeacherDao;
import SmartClass.DaoImp.AdminDaoImp;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.DaoImp.TeacherDaoImp;
import SmartClass.POJO.Administrator;
import SmartClass.tool.HttpSessionUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 73681 on 2018/2/4.
 */
@Path("admin")
public class AdminResource
{

    private final static String CHARSET_UTF_8 = "charset=utf-8";
    private AdminDao adminDao = new AdminDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    private TeacherDao teacherDao = new TeacherDaoImp();

    /*************************************已测试**************************************/
    /*处理管理员登录功能*/
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> login(String reqText, @Context HttpServletRequest request)
    {

        Map reply = new HashMap<String,Object>();
        /*处理请求*/
        JSONObject jsreq = new JSONObject(reqText);
        String userName = jsreq.getString("userName").trim();
        String password = jsreq.getString("password").trim();

        /*查询*/
        try
        {
            Administrator administrator =  adminDao.getByUserName(userName);
            if (administrator == null)
            {
                throw new Exception("用户名不正确！");
            }
            if (!password.equals(administrator.getPassword()))
            {
                throw new Exception("密码错误！");
            }
            /*校验成功*/
            HttpSession session = request.getSession();
            session.setAttribute("role","admin");
            session.setAttribute("user", administrator);
        } catch (Exception e)
        {
            e.printStackTrace();
            reply.put("status",1000);
            reply.put("msg",e.getMessage());
            return reply;
        }

        /*应答*/
        reply.put("status",200);
        reply.put("msg","OK");
        return reply;
    }


    /*退出登录*/
    /*处理管理员登录功能*/
    @Path("exit")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> exit(@Context HttpServletRequest request)
    {

        Map reply = new HashMap<String,Object>();

        if (HttpSessionUtil.islogin(request,"role","admin"))
        {
            HttpSession session = request.getSession();
            /*将session中对应的属性去掉*/
            session.removeAttribute("role");
            session.removeAttribute("user");
            reply.put("status", 200);
            reply.put("msg","OK");
        }else
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员操作，请先登录！");
        }
        return reply;
    }


    @Path("islogin")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";" + CHARSET_UTF_8)
    public Map<String,Object> isLogin(@Context HttpServletRequest request)
    {
        Map reply = new HashMap<String,Object>();
        if (HttpSessionUtil.islogin(request,"role","admin"))
        {
            Administrator administrator = (Administrator)HttpSessionUtil.get(request,"user");
            reply.put("status", 200);
            reply.put("msg","OK");
            reply.put("username",administrator.getUsername());
            return reply;
        }else
        {
            reply.put("status", 1000);
            reply.put("msg","此操作是只能由管理员操作，请先登录！");
        }
        return reply;
    }

}
