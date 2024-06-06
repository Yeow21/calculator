package com.overtime.calculator;

import java.util.*;

import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@RestController
public class CalculatorController {

    private final OvertimeShiftRepository repository;

    private final OvertimeShiftModelAssembler assembler;



    CalculatorController(OvertimeShiftRepository repository, OvertimeShiftModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // single item
    @GetMapping("/overtime/{id}")
    EntityModel<OvertimeShift> one(@PathVariable Long id) {

        OvertimeShift shift = repository.findById(id) //
                .orElseThrow(() -> new OvertimeShiftNotFoundException(id));

        return assembler.toModel(shift);
    }

    // aggregate get
    @CrossOrigin
    @GetMapping("/overtime")
    CollectionModel<EntityModel<OvertimeShift>> all() {

        List<EntityModel<OvertimeShift>> shifts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(shifts, linkTo(methodOn(CalculatorController.class).all()).withSelfRel());
    }

    @PostMapping("/overtime") // Maps HTTP POST requests to /overtime to this method.
    ResponseEntity<?> newShift(@RequestBody OvertimeShift newShift) { // Indicates that the request body should be deserialized into an OvertimeShift object.

        // Saves the new overtime shift to the repository and converts it to an EntityModel.
        EntityModel<OvertimeShift> entityModel = assembler.toModel(repository.save(newShift));

        // Returns a ResponseEntity with a created status (201) and the URI of the new resource in the Location header.
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) // Sets the Location header to the URI of the newly created resource.
                .body(entityModel); // Sets the body of the response to the EntityModel representation of the new shift.
    }


    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceShift(@RequestBody OvertimeShift newShift, @PathVariable Long id) {

        OvertimeShift updatedEmployee = repository.findById(id) //
                .map(shift -> {
                    shift.setLetter(newShift.getLetter());
                    shift.setDate(newShift.getDate());
                    return repository.save(shift);
                }) //
                .orElseGet(() -> {
                    newShift.setId(id);
                    return repository.save(newShift);
                });

        EntityModel<OvertimeShift> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/overtime/{id}")
    ResponseEntity<?> deleteShift(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
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
