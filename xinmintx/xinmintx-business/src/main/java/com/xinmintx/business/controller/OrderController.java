package com.xinmintx.business.controller;

import com.xinmintx.business.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


}
