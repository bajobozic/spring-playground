package com.example.tacocloud.controllers;

import com.example.tacocloud.models.Order;
import com.example.tacocloud.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderPage";
    }

    /**
     * In this case(where GET and POST mappings differ) we need to go back one step from /orders/current to /order
     * since @Post implicitly have "@Post("/orders")",in this case this is done through
     * html post action param,after that redirect is executed
     *
     * @param order  Usually object from Form POST that is populated by user(other data we can retrieve from database)
     * @param errors
     * @return in case of post this is mostly redirect view(URL)
     */
    @PostMapping
    public String postOrder(@Valid @ModelAttribute Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            log.info("Submit errors" + errors);
            return "orderPage";
        }
        log.info("Submitted order" + order);
        orderRepository.save(order);
        sessionStatus.setComplete();
        //on this point form is already submitted to @PostMapping("post/path"),so we call url
        return "redirect:/";//this is not view,it's redirect view or precisely url
    }
}
