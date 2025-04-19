package it.epicode.BE_W6_D5_Progetto.prenotazione;

import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
@Transactional
public class PrenotazioneController {

    @Autowired public PrenotazioneService prenotazioneService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Prenotazione> findAll() {
        return prenotazioneService.findAll();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PrenotazioneResponse findById(@PathVariable Long id) {
        return prenotazioneService.findById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseId create(@RequestBody PrenotazioneRequest request) throws MessagingException {
        return prenotazioneService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PrenotazioneResponse update(@PathVariable Long id, @RequestBody PrenotazioneRequest request) {
        return prenotazioneService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
    }
}
