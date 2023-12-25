package com.Automation.APIAutomation.POJO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author psawale
 */
@Data
@Component
public class cityRequest {
    private String cityName;
    private String cityTemp;
}
