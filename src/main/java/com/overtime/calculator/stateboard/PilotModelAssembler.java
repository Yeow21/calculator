package com.overtime.calculator.stateboard;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PilotModelAssembler implements RepresentationModelAssembler<Pilot, EntityModel<Pilot>> {
    @Override
    public EntityModel<Pilot> toModel(Pilot pilot) {
        return EntityModel.of(pilot,
                linkTo(methodOn(StateboardController.class).getPilot(pilot.getId())).withSelfRel(),
                linkTo(methodOn(StateboardController.class).getAllPilots()).withRel("pilots"));
    }
}