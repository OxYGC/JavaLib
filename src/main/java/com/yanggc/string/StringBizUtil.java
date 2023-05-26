package com.yanggc.string;

import com.yanggc.enums.MobileAreaEnum;

/**
 * @Author OxYGen
 * @Since 2023-05-26
 * @Description:
 */
public class StringBizUtil {

    /**
     * 给字符串打马赛克
     * @param str 原字符串（如： 18600001887）
     * @param beforeNum 字符串前展示位数 如：3
     * @param mosaicNum  马赛克的数量 如：4
     * @param afterNum 字符串后展示位数 如：3
     * @return 打完马赛克后的字符串（如：186****887）
     */
    public static String addMosaic(String str, int beforeNum, int mosaicNum, int afterNum){
        if(StringUtils.isEmpty(str)){
            return str;
        }
        if(str.length() < beforeNum || str.length() < afterNum){
            return "***";
        }

        String beforeStr = str.substring(0, beforeNum);
        String afterStr = str.substring(str.length() - afterNum);
        StringBuilder mosaicStr = new StringBuilder();
        for (int i = 0; i < mosaicNum; i++) {
            mosaicStr.append("*");
        }

        return beforeStr + mosaicStr + afterStr;
    }


    /**
     * 默认马赛克手机号：只显示前2位和后3位,中间都为*
     * 例如：13312341234 添加后位：133*****234
     * 世界上最短手机号位7位：比如 +45 20506901 打码后：205**901
     * @param phoneNo
     */
    public static String defaultMosaicPhoneNo(String phoneNo){
        if(phoneNo.length() < 7){
            return phoneNo;
        }
        StringBuilder starStr = new StringBuilder("*");
        for (int i = 1; i < (phoneNo.length() - 6); i++) {
            starStr.append("*");
        }
        String resultPhoneNo = phoneNo.substring(0, 3) + starStr + phoneNo.substring(3, phoneNo.length() - 3);
        return resultPhoneNo;
    }

    /**
     * 给手机加马赛克
     * @param mobile
     * @return
     */
    public static String mosaicMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return StringUtils.EMPTY;
        }
        int len = mobile.length();
            if(MobileAreaEnum.CHINA_HK.getLength() == len){
            return addMosaic(mobile, 2, 4, 2);
        }
        return addMosaic(mobile, 3, 4, 4);
    }

    /**
     * 给手机加马赛克
     * @param mobile
     * @return
     */
    @Deprecated
    public static String mosaicHKMobile(String mobile){
        return addMosaic(mobile, 2, 4, 2);
    }
    /**
     * 给银行卡号加马赛克
     * @param mobile
     * @return
     */
    public static String mosaicAccountNo(String mobile){
        return addMosaic(mobile, 6, 6, 4);
    }


}
