package com.vehicle.app.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RootController {


    @RequestMapping("/vehicle")
    public String vehicle(){
        return "vehicle";
    }

    @RequestMapping("/device")
    public String device(){
        return "device";
    }

    @RequestMapping("/map")
    public String map(){
        return "map";
    }

    @RequestMapping("/project")
    public String project(){
        return "project";
    }
}
