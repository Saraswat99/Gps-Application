package com.vehicle.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transporter/v1/")
@PreAuthorize("hasRole('ROLE_TRANSPORTER')")
public class TransporterController {
}
