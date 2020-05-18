package com.github.andygo298.rentCarPlatform.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUtilTest {

    @Test
    void getSkipRecords() {
        int page = 4;
        int skipRecords = ServiceUtil.getSkipRecords(page);
        int expResult = 9;
        assertEquals(expResult,skipRecords);
    }

    @Test
    void getCountPages() {
        double records = 10.0;
        int expCountPages = 4;
        int actualCountPages = ServiceUtil.getCountPages(records);
        assertEquals(expCountPages,actualCountPages);
    }
    @Test
    void getCountPagesWhenRecordsZero(){
        double records = 0;
        int expCountPages = 1;
        int actualCountPages = ServiceUtil.getCountPages(records);
        assertEquals(expCountPages,actualCountPages);
    }
}