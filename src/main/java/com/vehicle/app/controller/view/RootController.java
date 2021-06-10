package com.vehicle.app.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RootController {

    @RequestMapping("/")
    public String getIndexView() {
        return "index";
    }

    @RequestMapping("/vehicle")
    public String getVehicleDetails() {
        return "vehicle";
    }

    @RequestMapping("/device")
    public String getDeviceDetails() {
        return "device";
    }

    @RequestMapping("/map")
    public String getMapDetails() {
        return "map";
    }
}
