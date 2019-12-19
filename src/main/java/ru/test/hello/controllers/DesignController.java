package ru.test.hello.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.test.hello.interfaces.IngredientRepository;
import ru.test.hello.interfaces.TacoRepository;
import ru.test.hello.models.Ingredient;
import ru.test.hello.models.Ingredient.Type;
import ru.test.hello.models.Order;
import ru.test.hello.models.Taco;
import ru.test.hello.models.ValidationError;
import ru.test.hello.helpers.Helpers;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @Autowired
    public DesignController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository; // есть ли более короткая запись данной конструкции?
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredientRepository.findAll().forEach(item -> ingredients.add(item));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        log.info("ingredients: " + ingredients);

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            List<ValidationError> errorsList = Helpers.getValidationErrors(errors);

            log.info("Error in form, data: " + design + ", error: " + errorsList);
            return "redirect:/design";
        }
        Taco taco = this.tacoRepository.save(design);

        order.addDesign(taco);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
