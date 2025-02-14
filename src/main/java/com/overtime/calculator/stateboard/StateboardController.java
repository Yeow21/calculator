package com.overtime.calculator.stateboard;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stateboard")
public class StateboardController {

    private final TugRepository tugRepository;
    private final PilotRepository pilotRepository;
    private final PilotBoatRepository pilotBoatRepository;

    private final TugModelAssembler tugModelAssembler;
    private final PilotModelAssembler pilotModelAssembler;
    private final PilotBoatModelAssembler pilotBoatModelAssembler;

    public StateboardController(
            TugRepository tugRepository,
            PilotRepository pilotRepository,
            PilotBoatRepository pilotBoatRepository,
            TugModelAssembler tugModelAssembler,
            PilotModelAssembler pilotModelAssembler,
            PilotBoatModelAssembler pilotBoatModelAssembler) {
        this.tugRepository = tugRepository;
        this.pilotRepository = pilotRepository;
        this.pilotBoatRepository = pilotBoatRepository;
        this.tugModelAssembler = tugModelAssembler;
        this.pilotModelAssembler = pilotModelAssembler;
        this.pilotBoatModelAssembler = pilotBoatModelAssembler;
    }

    // Tug Endpoints

    @GetMapping("/tugs/{id}")
    public EntityModel<Tug> getTug(@PathVariable Long id) {
        Tug tug = tugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tug not found"));
        return tugModelAssembler.toModel(tug);
    }

    @GetMapping("/tugs")
    public CollectionModel<EntityModel<Tug>> getAllTugs() {
        List<EntityModel<Tug>> tugs = tugRepository.findAll().stream()
                .map(tugModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tugs, linkTo(methodOn(StateboardController.class).getAllTugs()).withSelfRel());
    }

    // Pilot Endpoints

    @GetMapping("/pilots/{id}")
    public EntityModel<Pilot> getPilot(@PathVariable Long id) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found"));
        return pilotModelAssembler.toModel(pilot);
    }

    @GetMapping("/pilots")
    public CollectionModel<EntityModel<Pilot>> getAllPilots() {
        List<EntityModel<Pilot>> pilots = pilotRepository.findAll().stream()
                .map(pilotModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pilots, linkTo(methodOn(StateboardController.class).getAllPilots()).withSelfRel());
    }

    // PilotBoat Endpoints

    @GetMapping("/pilotBoats/{id}")
    public EntityModel<PilotBoat> getPilotBoat(@PathVariable Long id) {
        PilotBoat pilotBoat = pilotBoatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PilotBoat not found"));
        return pilotBoatModelAssembler.toModel(pilotBoat);
    }

    @GetMapping("/pilotBoats")
    public CollectionModel<EntityModel<PilotBoat>> getAllPilotBoats() {
        List<EntityModel<PilotBoat>> pilotBoats = pilotBoatRepository.findAll().stream()
                .map(pilotBoatModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pilotBoats, linkTo(methodOn(StateboardController.class).getAllPilotBoats()).withSelfRel());
    }

    // New endpoint to update availability
    @PostMapping("/updateAvailability")
    public ResponseEntity<?> updateAvailability(@RequestBody UpdateAvailabilityRequest request) {
        switch (request.getAssetType()) {
            case "Tug":
                Tug tug = tugRepository.findById(request.getAssetId())
                        .orElseThrow(() -> new RuntimeException("Tug not found"));
                updateAvailabilityForAsset(tug.getAvailability(), request.getDateIndex(), request.getStatus());
                tugRepository.save(tug);
                break;
            case "Pilot":
                Pilot pilot = pilotRepository.findById(request.getAssetId())
                        .orElseThrow(() -> new RuntimeException("Pilot not found"));
                updateAvailabilityForAsset(pilot.getAvailability(), request.getDateIndex(), request.getStatus());
                pilotRepository.save(pilot);
                break;
            case "PilotBoat":
                PilotBoat pilotBoat = pilotBoatRepository.findById(request.getAssetId())
                        .orElseThrow(() -> new RuntimeException("PilotBoat not found"));
                updateAvailabilityForAsset(pilotBoat.getAvailability(), request.getDateIndex(), request.getStatus());
                pilotBoatRepository.save(pilotBoat);
                break;
            default:
                throw new RuntimeException("Invalid asset type");
        }
        return ResponseEntity.ok().build();
    }

    private void updateAvailabilityForAsset(ArrayList<Integer> availability, int dateIndex, int status) {
        if (dateIndex < 0 || dateIndex >= availability.size()) {
            throw new RuntimeException("Invalid date index");
        }
        availability.set(dateIndex, status);
    }
}
