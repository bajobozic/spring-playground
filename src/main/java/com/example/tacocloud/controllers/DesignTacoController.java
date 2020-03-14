package com.example.tacocloud.controllers;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
    /**
     * Populate model object
     *
     * @param model
     */
    @ModelAttribute
    private void getIngridients(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beff", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Dieced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEES),
                new Ingredient("JAK", "Monterrey Jack", Ingredient.Type.CHEES),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
        Ingredient.Type[] values = Ingredient.Type.values();
        for (Ingredient.Type value : values) {
            model.addAttribute(value.toString().toLowerCase(), filterByType(ingredients, value));
        }
    }

    /**
     * this implicitly add Taco object with name taco to GET Model,must match -> th:object="${taco}" inside form POST
     *
     * @return
     */
    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * Shows UI that usually have form inside(not always the case)
     *
     * @param model Carrier for data that we usually get from base and pass to view to populate UI
     * @return name of view that is populated from passed model
     */
    @GetMapping
    public String showTacoForm(Model model) {
        return "tacoPage";//this view must have Taco object with name "taco"(for GET method)
    }

    /**
     * @param taco   Usually object from Form POST that is populated by user(other data we can retrieve from database)
     * @param errors Used for validation errors on Form
     * @return in case of post this is mostly redirect view(URL-Relative url that should be open)
     */
    @PostMapping
    public String saveTaco(@Valid @ModelAttribute Taco taco, Errors errors) {
        if (errors.hasErrors())
            return "homePage";
        log.info("Processing taco " + taco);
        //on this point form is already submitted to @PostMapping("post/path"),so we call url
        return "redirect:/orders/current";//this is not view,it's redirect view or precisely url
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type value) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(value))
                .collect(Collectors.toList());
    }
}
