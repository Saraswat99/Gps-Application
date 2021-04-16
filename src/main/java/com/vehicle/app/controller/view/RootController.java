package com.vehicle.app.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RootController {


    @RequestMapping("/")
    public String getIndexView(){
        return "index";
    }
}
