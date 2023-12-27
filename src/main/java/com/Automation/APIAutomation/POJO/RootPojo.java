package com.Automation.APIAutomation.POJO;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author psawale
 */
@Data
public class RootPojo {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public ArrayList<DatumPojo> data;
    public SupportPojo support;
}
