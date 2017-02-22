package com.cvlh.util;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Created by 29140 on 2017/2/21.
 */
public class SpiderUtil {


    /**
     * normalize cookie from cookie string, and return CookieStore object
     *
     * @param cookies
     * @param domain
     * @return
     */
    public static CookieStore normalizeCookie(String cookies, String domain) {
        CookieStore cookieStore = new BasicCookieStore();
        String[] temp = cookies.split(";");
        for (String str : temp) {
            BasicClientCookie cookie = new BasicClientCookie(str.trim().split("=")[0], str.trim().split("=")[1]);
            cookie.setDomain(domain);
            cookieStore.addCookie(cookie);
        }

        return cookieStore;
    }
}
