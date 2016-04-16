package com.mengyou.library;

/**
 * Created by Administrator on 2016/3/1
 * 固定的全局常量.
 */
public class Constant {

    /**
     * 第三方配置常量类
     */
    public static class Config {
        public static final String APPID = "31zxQsYxpEgwgzefBuFrg1cQ-gzGzoHsz";
        public static final String APPKEY = "uI2v10wVrbjEBMu6SFTF961T";
    }

    public static class Regular {
        public static final String REGULAR_PHONE = "^[1][358][0-9]{9}$";
        public static final String REGULAR_PASSWORD = "^[0-9A-Za-z]{6,20}$";
        public static final String REGULAR_EMIAL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    }

    /**
     * 状态值
     */
    public static class State {
        public static final String SUCCESS_LOW = "success";
        public static final String SUCCESS_UP = "SUCCESS";
    }

    public static class IntentExtra{
        public static final String FROM="from";
    }

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.mengyou.localloop";

    public static final String MEMBER_ID = getPrefixConstant("member_id");
    public static final String MEMBER_NAME = getPrefixConstant("member_name");
    public static final String CONVERSATION_ID = getPrefixConstant("conversation_id");


    private static String getPrefixConstant(String value) {
        return LEANMESSAGE_CONSTANTS_PREFIX+value;
    }
}
