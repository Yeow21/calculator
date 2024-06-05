package com.overtime.calculator;

import java.util.*;

import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@RestController
public class CalculatorController {

    private final OvertimeShiftRepository repository;

    CalculatorController(OvertimeShiftRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees/{id}")
    EntityModel<OvertimeShift> one(@PathVariable Long id) {

        OvertimeShift shift = repository.findById(id) //
                .orElseThrow(() -> new OvertimeShiftNotFoundException(id));

        return EntityModel.of(employee, //
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }



    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/overtime")
    List<OvertimeShift> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/overtime")
    OvertimeShift newOvertimeShift(@RequestBody OvertimeShift newShift) {
        return repository.save(newShift);
    }

    // Single item

    @GetMapping("/overtime/{id}")
    OvertimeShift one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new OvertimeShiftNotFoundException(id));
    }
    /**
    @PutMapping("/overtime/{id}")
    OvertimeShift replaceOvertimeShift(@RequestBody OvertimeShift newShift, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }
     */

    @DeleteMapping("/calculator/{id}")
    void deleteOvertimeShift(@PathVariable Long id) {
        repository.deleteById(id);
    }








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
        System.out.println("MAP = " + coverMap + "AllCoverMaps = " + StoreCoverMaps.getAllCoverMaps());


        model.addAttribute("coverMap", coverMap);
        model.addAttribute("allCoverMaps", StoreCoverMaps.getAllCoverMaps());




        return "calculator_success";
    }


}
