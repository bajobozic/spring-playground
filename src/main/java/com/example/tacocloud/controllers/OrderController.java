package com.example.tacocloud.controllers;

import com.example.tacocloud.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderPage";
    }

    /**
     * In this case(where GET and POST mappings differ) we need to go back one step from /orders/current to /order
     * since @Post implicitly have "@Post("/orders")",in this case this is done through
     * html post action param,after that redirect is executed
     * @param order Usually object from Form POST that is populated by user(other data we can retrieve from database)
     * @param errors
     * @return in case of post this is mostly redirect view(URL)
     */
    @PostMapping
    public String postOrder(@Valid @ModelAttribute Order order, Errors errors) {
        if (errors.hasErrors()) {
            log.info("Submit errors" + errors);
            return "orderPage";
        }
        log.info("Submitted order" + order);
        //on this point form is already submitted to @PostMapping("post/path"),so we call url
        return "redirect:/";//this is not view,it's redirect view or precisely url
    }
}
