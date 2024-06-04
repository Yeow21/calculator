package com.overtime.calculator;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class WebController {

    @GetMapping("/lol")
    public String lol()
    {
        return "err, ok then...";
    }

    /**
     * '@GetMapping' maps the get requests route to the function
     * RequestParam binds value of the query string parameter into the name parameter of greeting
     * in this case - name is added to the model object making it accessible to the view template
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/calculator")
    public String calculator(Model model)
    {
        Calculator calculator = new Calculator(); // represents the information in the form
        model.addAttribute("calculator", calculator);

        // list to be added to a drop-down box
        List<String> listOfficers = Arrays.asList("A", "B", "C", "D", "E");
        model.addAttribute("listOfficers", listOfficers);

        List<String> listDeskSides = Arrays.asList("operator", "officer");
        model.addAttribute("listDeskSides", listDeskSides);

        return "calculator";
    }

    @PostMapping("/calculator")
    public String calculator(@ModelAttribute("calculator") Calculator calculator, Model model) {
        // Handle the submitted data
        model.addAttribute("calculator", calculator);
        System.out.println("Submitted data: " + calculator);


        System.out.println("calculator = " + calculator);
        OvertimeShift shift = calculator.getShift();
        HashMap<String, ArrayList<String>> coverMap = calculator.populateCoverMap(shift);
        System.out.println("MAP = " + coverMap);

        model.addAttribute("coverMap", coverMap);




        return "calculator_success";
    }


}
