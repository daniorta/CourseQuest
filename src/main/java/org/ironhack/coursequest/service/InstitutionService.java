package org.ironhack.coursequest.service;

import org.ironhack.coursequest.dto.EnrollmentDTO;
import org.ironhack.coursequest.dto.InstitutionDTO;
import org.ironhack.coursequest.exception.BadRequestException;
import org.ironhack.coursequest.exception.NotFoundException;
import org.ironhack.coursequest.model.Institution;
import org.ironhack.coursequest.repository.InstitutionRepository;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.FormatterClosedException;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository, ResourcePatternResolver resourcePatternResolver){
        this.institutionRepository = institutionRepository;

    }

    public List<Institution> getAllInstitution(){
        return institutionRepository.findAll();
    }

    public Institution getById(Long id){
        return getInstitutionId(id);
    }


    public Institution createInstitution(InstitutionDTO institutionDTO){

        Institution newInstitution = new Institution();

        newInstitution.setName(institutionDTO.getName());
        newInstitution.setAddress(institutionDTO.getAddress());
        newInstitution.setEmail(institutionDTO.getEmail());
        newInstitution.setPhoneNumber(institutionDTO.getPhoneNumber());

        validateIstitutionEmail(institutionDTO.getEmail());
        Institution saveInstitution = institutionRepository.save(newInstitution);

        return saveInstitution;

    }

    public Institution UpdateInstitution(Long id, InstitutionDTO institutionDTO){

        Institution institutionFromDb = getInstitutionId(id);

        institutionFromDb.setName(institutionDTO.getName());
        institutionFromDb.setAddress(institutionDTO.getAddress());
        institutionFromDb.setEmail(institutionDTO.getEmail());
        institutionFromDb.setPhoneNumber(institutionDTO.getPhoneNumber());

//        validateIstitutionEmail(institutionDTO.getEmail()); // Aqui no lo llamamos ya que puede que el email no lo modifique
        Institution saveInstution = institutionRepository.save(institutionFromDb);

        return saveInstution;
    }

    public void deleteInstitution(Long id){

        boolean existsById = institutionRepository.existsById(id);

        if(!existsById){
            throw new NotFoundException(id);

        }else {
            institutionRepository.deleteById(id);
        }
    }

    //Métodos para redundancia de busqueda de ID
    public Institution getInstitutionId(Long id){
        return institutionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public void validateIstitutionEmail(String email){
        boolean existsByEmail = institutionRepository.existsByEmail(email);
        if(existsByEmail){
            throw new BadRequestException("Institution with email " + email + " already exists.");
        }
    }
}
