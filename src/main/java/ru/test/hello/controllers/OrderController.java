package ru.test.hello.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.test.hello.models.Order;
import ru.test.hello.models.TacoWithIngredients;
import ru.test.hello.models.ValidationError;
import ru.test.hello.helpers.Helpers;
import ru.test.hello.repositories.JDBCTacoRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private JDBCTacoRepository jdbcTacoRepository;

    @GetMapping("current")
    public String orderForm(Model model) {
        Iterable<TacoWithIngredients> tacos = jdbcTacoRepository.getTacos();

        model.addAttribute("order", new Order());
        model.addAttribute("tacos", tacos);

        return "orderForm";
    }

    @PostMapping("current")
    public String processOrder(@Valid Order order, Errors errors) {
        if (errors.hasErrors()) {
            List<ValidationError> validationErrors = Helpers.getValidationErrors(errors);

            log.info("Error in form, data: " + order + ", error: " + validationErrors);

            return "redirect:/orders/current";
        }

        log.info("Order submitted: " + order);
        return "redirect:/";
    }

}
