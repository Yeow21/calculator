package com.overtime.calculator.stateboard;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PilotBoatModelAssembler implements RepresentationModelAssembler<PilotBoat, EntityModel<PilotBoat>> {
    @Override
    public EntityModel<PilotBoat> toModel(PilotBoat pilotBoat) {
        return EntityModel.of(pilotBoat,
                linkTo(methodOn(StateboardController.class).getPilotBoat(pilotBoat.getId())).withSelfRel(),
                linkTo(methodOn(StateboardController.class).getAllPilotBoats()).withRel("pilotBoats"));
    }
}