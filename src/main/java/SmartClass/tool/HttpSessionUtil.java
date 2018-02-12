package SmartClass.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 73681 on 2018/2/7.
 */
public class HttpSessionUtil
{
    public static String getString(HttpServletRequest httpReq, String key)
    {
        HttpSession ss = httpReq.getSession();
        String str = (String)ss.getAttribute(key);
        return str;
    }

    /*在session中，根据key----得到value（Objecet类型）*/
    public static Object get(HttpServletRequest httpReq, String key)
    {
        HttpSession ss = httpReq.getSession();
        Object obj = ss.getAttribute(key);
        return obj;
    }

    public static boolean islogin(HttpServletRequest httpReq,String key,String value)
    {
        String str = HttpSessionUtil.getString(httpReq,key);
        if (str == null || !str.equals(value)) return false;
        return true;
    }
}
