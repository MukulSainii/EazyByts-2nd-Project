package com.RODS.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String mainPage(){
        return "main";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/signup")
    public String registrationPage(){
        return "signup";
    }

    @RequestMapping("/basket")
    public String basketPage(){
        return "basket";
    }

    @RequestMapping("/orders")
    public String ordersPage(){
        return "orders";
    }

    @RequestMapping("/payment")
    public String paymentPage(){
        return "payment";
    }

    @RequestMapping("manager/manage_orders")
    public String ordersManagerPage(){
        return "orders_manager";
    }

    @RequestMapping("manager/manage_dishes")
    public String dishesManagerPage(){
        return "dishes_manager";
    }

    @RequestMapping("manager/dishes/create")
    public String dishesCreate(){
        return "dishes_create";
    }

    @RequestMapping("manager/dishes/update")
    public String dishesUpdate(){
        return "dishes_update";
    }
}

