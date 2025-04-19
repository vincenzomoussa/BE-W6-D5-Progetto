package it.epicode.BE_W6_D5_Progetto.viaggio;


import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class ViaggioService {

    @Autowired private ViaggioRepository viaggioRepository;


    public ViaggioResponse findById(@PathVariable Long id) {
        if (!viaggioRepository.existsById(id)) {
            throw new EntityNotFoundException("Dipendente con id " + id + " non trovato");
        }
        Viaggio viaggio = viaggioRepository.getById(id);
        ViaggioResponse viaggioResponse = new ViaggioResponse();
        BeanUtils.copyProperties(viaggio, viaggioResponse);
        return viaggioResponse;

    }


    public List<Viaggio> findAll() {
        return viaggioRepository.findAll();

    }

    public CommonResponseId modificaStatoViaggio(@Valid ModificaStatoViaggioRequest request) {
        Viaggio viaggio = viaggioRepository.findById(request.getViaggioId())
                .orElseThrow(() -> new EntityNotFoundException("Viaggio con id " + request.getViaggioId() + " non trovato"));
        viaggio.setStato(request.getNuovoStato());
        viaggioRepository.save(viaggio);
        CommonResponseId commonResponseId = new CommonResponseId();
        commonResponseId.setId(viaggio.getId());
        return commonResponseId;
    }


    public CommonResponseId create(@Valid ViaggioRequest request) {
        LocalDate dataViaggio = request.getData();
        if (viaggioRepository.existsByDestinazioneAndData(request.getDestinazione(), dataViaggio)) {
            throw new IllegalArgumentException("Esiste già un viaggio per la stessa destinazione e data");
        }

        Viaggio viaggio = new Viaggio();
        BeanUtils.copyProperties(request, viaggio);
       viaggioRepository.save(viaggio);
        if (viaggio.getData().isBefore(LocalDate.now())) {
            viaggio.setStato(StatoViaggio.COMPLETATO);
        } else if (viaggio.getData().isAfter(LocalDate.now()) || viaggio.getData().isEqual(LocalDate.now())) {
            viaggio.setStato(StatoViaggio.IN_PROGRAMMA);
        }
        viaggioRepository.save(viaggio);
       CommonResponseId commonResponseId = new CommonResponseId();
        BeanUtils.copyProperties(viaggio, commonResponseId);
        System.out.println("Il viaggio per " + viaggio.getDestinazione() + " con partenza il " + viaggio.getData().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " e' stato creato con successo!");
        return commonResponseId;
    }

    public ViaggioResponse update(Long id, ViaggioRequest request) {
        if (!viaggioRepository.existsById(id)) {
            throw new EntityNotFoundException("Viaggio con id " + id + " non trovato");
        }
        Viaggio viaggio = viaggioRepository.findById(id).get();
        BeanUtils.copyProperties(request, viaggio);
        viaggioRepository.save(viaggio);
       ViaggioResponse viaggioResponse = new ViaggioResponse();
        BeanUtils.copyProperties(viaggio, viaggioResponse);
        return viaggioResponse;

    }

    public void delete(@PathVariable Long id) {
        if (!viaggioRepository.existsById(id)) {
            throw new EntityNotFoundException("Viaggio con id " + id + " non trovato");
        }
            viaggioRepository.deleteById(id);
       if (!viaggioRepository.existsById(id)) {
            System.out.println("Il viaggio con id " + id + " e' stato eliminato con successo!");
        }
    }

}
