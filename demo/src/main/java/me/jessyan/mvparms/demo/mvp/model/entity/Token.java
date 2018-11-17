package me.jessyan.mvparms.demo.mvp.model.entity;

import java.util.List;

/**
 * @package me.jessyan.mvparms.demo.mvp.model.entity
 * @fileName Token
 * @date 2018/11/16
 * @describe
 * @email shenzhencuco@gmail
 */
public class Token {

    /**
     * code : 400
     * extrs : ["A user with that username already exists."]
     * data : {"username":"11111","password":"11111"}
     */

    private int code;
    private DataBean data;
    private List<String> extrs;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<String> getExtrs() {
        return extrs;
    }

    public void setExtrs(List<String> extrs) {
        this.extrs = extrs;
    }

    public static class DataBean {
        /**
         * username : 11111
         * password : 11111
         */

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
