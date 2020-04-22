package com.example.tacocloud.controllers;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Order;
import com.example.tacocloud.models.Taco;
import com.example.tacocloud.repositories.IngredientRepository;
import com.example.tacocloud.repositories.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes(names = "order")
public class DesignTacoController {
    private IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    /**
     * this implicitly add Taco object with name taco to GET Model,must match -> th:object="${taco}" inside form POST
     *
     * @return
     */
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * Populate model object
     *
     * @param model
     */
    @ModelAttribute
    private void getIngredients(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        Ingredient.Type[] values = Ingredient.Type.values();
        for (Ingredient.Type value : values) {
            model.addAttribute(value.toString().toLowerCase(), filterByType(ingredients, value));
        }
    }


    /**
     * Shows UI that usually have form inside(not always the case)
     *
     * @param model Carrier for data that we usually get from base and pass to view to populate UI
     * @return name of view that is populated from passed model
     */
    @GetMapping
    public String showTacoForm(Model model) {
        getIngredients(model);
        return "tacoPage";//this view must have Taco object with name "taco"(for GET method)
    }

    /**
     * @param taco   Usually object from Form POST that is populated by user(other data we can retrieve from database)
     * @param errors Used for validation errors on Form
     * @return in case of post this is mostly redirect view(URL-Relative url that should be open)
     */
    @PostMapping
    public String saveTaco(@Valid @ModelAttribute Taco taco, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors())
            return "homePage";
        Taco savedTaco = tacoRepository.save(taco);
        order.addTaco(savedTaco);
        log.info("Saved taco " + savedTaco);
        //on this point form is already submitted to @PostMapping("post/path"),so we call url
        return "redirect:/orders/current";//this is not view,it's redirect view or precisely url
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type value) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(value))
                .collect(Collectors.toList());
    }
}
