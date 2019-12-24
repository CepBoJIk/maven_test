package ru.test.hello.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.test.hello.helpers.Helpers;
import ru.test.hello.models.Order;
import ru.test.hello.models.Taco;
import ru.test.hello.models.ValidationError;
import ru.test.hello.repositories.OrderRepository;
import ru.test.hello.repositories.TacoRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("current")
    public String orderForm(Model model) {
        Iterable<Taco> tacos = tacoRepository.findAll();

        model.addAttribute("tacos", tacos);

        return "orderForm";
    }

    @PostMapping("current")
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, Model model) {
        log.info("session attribute: " + model.getAttribute("order"));
        if (errors.hasErrors()) {
            List<ValidationError> validationErrors = Helpers.getValidationErrors(errors);

            log.info("Error in form, data: " + order + ", error: " + validationErrors);

            return "redirect:/orders/current";
        }

        Order sessionOrder = (Order) model.getAttribute("order");
        order.setTacos(sessionOrder.getTacos());

        Order savedOrder = orderRepository.save(order);
        log.info("order saved: " + savedOrder);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
