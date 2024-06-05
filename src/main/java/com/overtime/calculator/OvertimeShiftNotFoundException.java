package com.overtime.calculator;

class OvertimeShiftNotFoundException extends RuntimeException {

    OvertimeShiftNotFoundException(Long id) {
        super("Could not find Overtime shift " + id);
    }
}
