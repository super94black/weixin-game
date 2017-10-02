package org.cet.filter;

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author zhang
 * @Date 2017/9/29 17:09
 * @Content 防御CSRF生成token
 */
public final class CSRFTokenManager {
    static final String CSRF_PARAM_NAME = "CSRFToken";

    public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class.getName() + ".tokenval";

    private CSRFTokenManager(){

    }

    public static String getCsrfTokenForSessionAttrName(HttpSession session){
        String token = null;
        synchronized (session){
            token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            //保证token只能存在唯一值
            if(null == token){
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME,token);
            }
        }
        return token;
    }

    public static String getTokenFromRequest(HttpServletRequest request){
        return request.getParameter(CSRF_PARAM_NAME);
    }

}
