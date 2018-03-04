package SmartClass.System;

import SmartClass.Dao.AnswerDao;
import SmartClass.Dao.CommunicationDao;
import SmartClass.Dao.CourseDao;
import SmartClass.DaoImp.AnswerDaoImp;
import SmartClass.DaoImp.CommunicationDaoImp;
import SmartClass.DaoImp.CourseDaoImp;
import SmartClass.POJO.Course;
import SmartClass.dbutil.DbUtil;
import org.hibernate.Session;

import javax.servlet.http.*;

/**
 * Created by 73681 on 2018/1/19.
 */
public class SystemSessionListener implements HttpSessionAttributeListener,HttpSessionListener
{

    private AnswerDao answerDao = new AnswerDaoImp();
    private CommunicationDao communicationDao = new CommunicationDaoImp();
    private CourseDao courseDao = new CourseDaoImp();
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent)
    {
        String key = httpSessionBindingEvent.getName();
        if (key.equals("courseId"))
        {
            System.out.println(key);
            HttpSession session = httpSessionBindingEvent.getSession();
            System.out.println(session.getAttribute(key));
        }
    }

    /*课程结束的时候，做一些清除工作*/
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent)
    {
        /*这节课结束时，做一些善尾工作*/
        String key = httpSessionBindingEvent.getName();
        if (key.equals("courseId"))
        {
            HttpSession session = httpSessionBindingEvent.getSession();
            short courseId = (Short) httpSessionBindingEvent.getValue();
            System.out.println(key + ":" + courseId);
            try
            {
                answerDao.clearByCourseId(courseId);            /*初始化答题信息表*/
                communicationDao.deleteBycourseId(courseId);    /*删除某门课的交流信息*/
                Course course = courseDao.getById(courseId);
                course.setClassFlag((byte)0);
                course.setTimeModified(DbUtil.now());
                courseDao.update(course);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent)
    {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        System.out.println(httpSessionEvent.getSession().getId());
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {

    }
}
