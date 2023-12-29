package com.Automation.APIAutomation.POJO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author psawale
 */
@Component
@Data
public class postRequestBody {
    private String name;
    private String job;
    private List<String> languages;
    private List<cityRequest> cityRequestList;

}
