package org.ironhack.coursequest.controller;

import jakarta.validation.Valid;
import org.ironhack.coursequest.dto.InstitutionDTO;
import org.ironhack.coursequest.model.Institution;
import org.ironhack.coursequest.service.InstitutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService){
        this.institutionService = institutionService;
    }

    @GetMapping
    public ResponseEntity<List<Institution>> getInstitution(){
        List<Institution> institutions = institutionService.getAllInstitution();
        return  ResponseEntity.ok(institutions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Institution> getInstitutionById(@Valid @PathVariable Long id){
        Institution institution = institutionService.getById(id);
        return ResponseEntity.ok(institution);
    }

    @PostMapping
    public ResponseEntity<Institution> createInstitution(@Valid @RequestBody InstitutionDTO institutionDTO){
        Institution institution = institutionService.createInstitution(institutionDTO);
        return new ResponseEntity<>(institution,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Institution> updateInstitution(@Valid @PathVariable Long id, @Valid @RequestBody InstitutionDTO institutionDTO, Principal principal){
        Institution institution = institutionService.UpdateInstitution(id, institutionDTO);
        return ResponseEntity.ok(institution);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id){
        institutionService.deleteInstitution(id);
        return ResponseEntity.noContent().build();
    }


}
