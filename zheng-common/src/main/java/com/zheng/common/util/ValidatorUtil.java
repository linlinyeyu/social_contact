package com.zheng.common.util;

/**
 * Created by acer on 2017/12/15.
 */
public class ValidatorUtil {
    /**
     * 验证手机格式：1开头11位数字组成
     * @param phone
     * @return
     */
    public static boolean validPhone(String phone){
        if(phone == null){
            return false;
        }
        String regex = "^1\\d{10}$";
        return phone.matches(regex);
    }

    public static boolean validName(String name){
        if(name == null){
            return false;
        }

        String regex = "^[\\u4e00-\\u9fa5a-zA-Z]+$";
        return name.matches(regex);
    }



    /**
     * 验证密码格式:"0-9"、"a-z"、"A-Z"、"_",6-18位组成
     * @param password
     * @return
     */
    public static boolean validPassword(String password){
        if(password == null){
            return false;
        }
        String regex = "^[0-9a-zA-Z_]{6,18}$";
        return password.matches(regex);
    }

    public static boolean validIdentityCard(String identityCard){
        if(StringUtil.isEmpty(identityCard)){
            return false;
        }
        //String regex = "^\\d{18}";
        return identityCard.length() == 18;
    }

    /**
     * 验证邮箱：组成格式xx@xx.xx
     * @param email
     * @return
     */
    public static boolean validEmail(String email){
        if(email == null){
            return false;
        }
        String regex = "^\\S+[@]{1}\\S+[.]{1}\\w+$";
        return email.matches(regex);
    }
}
