package com.overtime.calculator.stateboard;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TugModelAssembler implements RepresentationModelAssembler<Tug, EntityModel<Tug>> {

    @Override
    public EntityModel<Tug> toModel(Tug tug) {
        return EntityModel.of(tug,
                linkTo(methodOn(StateboardController.class).getTug(tug.getId())).withSelfRel(),
                linkTo(methodOn(StateboardController.class).getAllTugs()).withRel("tugs"));
    }
}