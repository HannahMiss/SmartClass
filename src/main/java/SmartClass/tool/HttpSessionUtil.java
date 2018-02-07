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
}
