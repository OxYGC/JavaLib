package com.yanggc.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author OxYGen
 */
public enum MobileAreaEnum {
    CHINA(86, "中国大陆",11),
    CHINA_HK(852, "中国香港",8);

    private int areaCode;
    private String areaName;
    private int noLength;

    MobileAreaEnum(int areaCode, String areaName, int noLength) {
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.noLength = noLength;
    }

    private static final Map<Integer, MobileAreaEnum> lookup = new HashMap();
    static {
        EnumSet.allOf(MobileAreaEnum.class).stream().forEach(e -> {
            lookup.put(e.getAreaCode(), e);
        });
    }

    public static MobileAreaEnum areaCodeOf(Integer areaCode) {
        return lookup.get(areaCode);
    }


    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getNoLength() {
        return noLength;
    }

    public void setNoLength(int noLength) {
        this.noLength = noLength;
    }
}
