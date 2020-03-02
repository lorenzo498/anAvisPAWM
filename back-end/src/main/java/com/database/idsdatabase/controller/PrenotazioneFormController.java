package com.database.idsdatabase.controller;

import java.util.List;

import com.database.idsdatabase.entity.PrenotazioneForm;
import com.database.idsdatabase.repository.PrenotazioneFormRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PrenotazioneFormController {

    public PrenotazioneFormController(PrenotazioneFormRepository prenotazioneFormRepository) {
        this.prenotazioneFormRepository = prenotazioneFormRepository;
    }

    private PrenotazioneFormRepository prenotazioneFormRepository;

    @GetMapping("/prenotazioneForms")
    public List<PrenotazioneForm> getPrenotazioneForm(){
        List<PrenotazioneForm>listaPrenotazioneForm = (List<PrenotazioneForm>) prenotazioneFormRepository.findAll();
        return listaPrenotazioneForm;
    }

    @PostMapping("/prenotazioneForms")
    void addPrenotazioneForm(@RequestBody PrenotazioneForm prenotazioneForm){
        prenotazioneFormRepository.save(prenotazioneForm);
    }




    
}
