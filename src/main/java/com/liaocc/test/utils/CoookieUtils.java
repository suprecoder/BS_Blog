package com.liaocc.test.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CoookieUtils {
    private HttpServletResponse response;
    public CoookieUtils(HttpServletResponse response){
        this.response=response;
    }
    public void addCookie(String name,String value){
        Cookie cookie=new Cookie(name,value);
        cookie.setMaxAge(10 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
