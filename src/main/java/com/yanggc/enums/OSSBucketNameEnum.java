package com.yanggc.enums;

/**
 * @Author OxYGen
 * @Since 2023-05-26
 * @Description:
 */
public enum OSSBucketNameEnum {

    TEMPLATE("template","模版存储桶"),
    APP_INSTALL("app-install","APP安装包"),
    IOS_APP_SIGN_FILE("ios","IOS签名文件安装包"),
    COMMON_FILE("oss-file","通用文件存储桶"),
            ;

    private String bucketName;
    private String desc;

    OSSBucketNameEnum(String bucketName, String desc) {
        this.bucketName = bucketName;
        this.desc = desc;
    }

    public static Boolean includeBucketName(String bucketName) {
        for (OSSBucketNameEnum value : OSSBucketNameEnum.values()) {
            if(value.bucketName.equals(bucketName)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
