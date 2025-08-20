package com.quanxiaoha.framework.common.util;


import java.util.regex.Pattern;

public class ParamUtils {

    private ParamUtils(){}

    // ============================== 校验昵称 ==============================
    // 定义昵称长度范围
    private static final int NICK_NAME_MIN_LENGTH = 2;
    private static final int NICK_NAME_MAX_LENGTH = 24;

    // 定义特殊字符的正则表达式
    private static final String NICK_NAME_REGEX = "[!@#$%^&*(),.?\":{}|<>]";

    public static boolean checkNickName(String name){
        if (NICK_NAME_MAX_LENGTH < name.length() || NICK_NAME_MIN_LENGTH > name.length()) {
            return false;
        }
        Pattern compile = Pattern.compile(NICK_NAME_REGEX);

        return !compile.matcher(name).find();

    }


    // ============================== 校验小哈书号 ==============================
    // 定义 ID 长度范围
    private static final int ID_MIN_LENGTH = 6;
    private static final int ID_MAX_LENGTH = 15;

    // 定义正则表达式
    private static final String ID_REGEX = "^[a-zA-Z0-9_]+$";

    public static boolean checkXiaohashuId(String xiaohashuId){
        if (xiaohashuId.length() > ID_MAX_LENGTH || xiaohashuId.length() < ID_MIN_LENGTH) {
            return false;
        }
        Pattern compile = Pattern.compile(ID_REGEX);
        return compile.matcher(xiaohashuId).matches();
    }

    public static boolean checkStrLength(String str, int length) {
        return !str.isEmpty() && str.length() <= length;
    }
}
