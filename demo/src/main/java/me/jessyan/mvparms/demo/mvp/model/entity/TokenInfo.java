package me.jessyan.mvparms.demo.mvp.model.entity;

/**
 * @package me.jessyan.mvparms.demo.mvp.model.entity
 * @fileName TokenInfo
 * @date 2018/11/17
 * @describe
 * @email shenzhencuco@gmail
 */
public class TokenInfo {


    /**
     * expire : 300.0
     * time : 2018-11-17T07:04:42.399741Z
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxNywiZXhwIjoxNTQyNDM4NTgyLCJ1c2VybmFtZSI6ImFkbWluIiwiZW1haWwiOiIifQ.jdWBv8qqIl-jrkUbmRmcqCcmCVNuk2FYDQ0_OvOfJ-A
     * code : 200
     */

    private String expire;
    private String time;
    private String token;
    private int code;

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
