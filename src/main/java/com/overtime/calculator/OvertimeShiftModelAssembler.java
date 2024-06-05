package com.overtime.calculator;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class OvertimeShiftModelAssembler implements RepresentationModelAssembler<OvertimeShift, EntityModel<OvertimeShift>> {

    @Override
    public EntityModel<OvertimeShift> toModel(OvertimeShift shift) {

        return EntityModel.of(shift, //
                linkTo(methodOn(CalculatorController.class).one(shift.getId())).withSelfRel(),
                linkTo(methodOn(CalculatorController.class).all()).withRel("shifts"));
    }
}