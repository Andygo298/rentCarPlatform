package com.github.andygo298.rentCarPlatform.service;

import com.github.andygo298.rentCarPlatform.dao.utils.Constant;

public class ServiceUtil {

    public static int getSkipRecords(int page) {
        int skipRecords = 0;
        if (page != 1) {
            skipRecords = page - 1;
            skipRecords = skipRecords * Constant.LIMIT_RECORDS;
        }
        return skipRecords;
    }

    public static int getCountPages(double countRecords) {
        if (countRecords == 0) return 1;
        else return (int) Math.ceil((countRecords / Constant.LIMIT_RECORDS));
    }

}
