package com.diginamic.apiback.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.diginamic.apiback.dto.SpecificAbsenceDTO;
import com.diginamic.apiback.models.SpecificAbsence;
import com.diginamic.apiback.services.SpecificAbsenceService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/specific-absence")
public class SpecificAbsenceController {
    @Autowired
    SpecificAbsenceService specificAbsenceService;

    /**
     * Route pour récupérer toutes les absences spécifiques
     * 
     * @return une liste d'absences
     */
    @GetMapping("/all")
    public List<SpecificAbsenceDTO> findAll() {
        List<SpecificAbsenceDTO> absenceDtoList = new ArrayList<>();
        List<SpecificAbsence> absenceList = specificAbsenceService.findAll();
        for (SpecificAbsence specificAbsence : absenceList) {
            absenceDtoList.add(specificAbsence.toDto());
        }
        return absenceDtoList;
    }

    /**
     * Route pour récupérer une absence spécifique par son id
     * 
     * @param id l'ID de l'absence
     * @return l'absence
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@NonNull @PathVariable("id") Long id) {
        Optional<SpecificAbsence> specificAbsence = specificAbsenceService.findById(id);
        if (specificAbsence.isPresent()) {
            return ResponseEntity.ok().body(specificAbsence.get().toDto());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "No specific absence entity with corresponding id found in db"));
    }

    /**
     * Route pour créer une absence spécifique
     * 
     * @param specificAbsence l'absence
     * @return l'absence créée
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAbsence(@RequestBody @Valid SpecificAbsence specificAbsence) {
        SpecificAbsence abs = specificAbsenceService.createSpecificAbsence(specificAbsence);
        return ResponseEntity.ok().body(abs.toDto());
    }

    /**
     * Route pour mettre à jour une absence spécifique
     * 
     * @param specificAbsence l'absence
     * @param id              l'ID de l'absence
     * @return un message de confirmation
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSpecificAbsence(@NonNull @RequestBody @Valid SpecificAbsence specificAbsence,
            @NonNull @PathVariable("id") Long id) {
        try {
            specificAbsenceService.updateSpecificAbsence(specificAbsence, id);
            return ResponseEntity.ok().body(Map.of("message", "Your absence was updated successfuly"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "The specific absence you are trying to update does not exists"));
        }
    }

    /**
     * Route pour supprimer une absence spécifique
     * 
     * @param id l'ID de l'absence
     * @return un message de confirmation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@NonNull @PathVariable("id") Long id) {
        Optional<SpecificAbsence> abs = specificAbsenceService.findById(id);
        if (abs.isPresent()) {
            specificAbsenceService.deleteSpecificAbsence(id);
            return ResponseEntity.ok().body(Map.of("message", "Your absence was deleted successfuly"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "No specific absence entity with corresponding id found in db"));
    }

}
