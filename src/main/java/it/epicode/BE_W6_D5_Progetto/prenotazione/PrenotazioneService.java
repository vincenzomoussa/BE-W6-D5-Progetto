package it.epicode.BE_W6_D5_Progetto.prenotazione;

import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import it.epicode.BE_W6_D5_Progetto.dipendente.Dipendente;
import it.epicode.BE_W6_D5_Progetto.dipendente.DipendenteRepository;
import it.epicode.BE_W6_D5_Progetto.email.EmailSenderService;
import it.epicode.BE_W6_D5_Progetto.viaggio.Viaggio;
import it.epicode.BE_W6_D5_Progetto.viaggio.ViaggioRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PrenotazioneService {

    @Autowired private PrenotazioneRepository prenotazioneRepository;
    @Autowired private ViaggioRepository viaggioRepository;
    @Autowired private DipendenteRepository dipendenteRepository;
    @Autowired private EmailSenderService emailSenderService;


    public PrenotazioneResponse findById(@PathVariable Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione con id " + id + " non trovato");
        }
        Prenotazione prenotazione = prenotazioneRepository.getById(id);
        PrenotazioneResponse prenotazioneResponse = new PrenotazioneResponse();
        prenotazioneResponse.setDataRichiesta(prenotazione.getDataRichiesta());
        prenotazioneResponse.setPreferenze(prenotazione.getPreferenze());
        prenotazioneResponse.setDipendente(prenotazione.getDipendente().getId());
        prenotazioneResponse.setViaggio(prenotazione.getViaggio().getId());
        return prenotazioneResponse;

    }


    public List<Prenotazione> findAll() {
        return prenotazioneRepository.findAll();

    }

    public CommonResponseId create(@Valid PrenotazioneRequest request) throws MessagingException {
        Viaggio viaggio = viaggioRepository.findById(request.getViaggioId())
                .orElseThrow(() -> new EntityNotFoundException("Viaggio con id " + request.getViaggioId() + " non trovato"));
        Dipendente dipendente = dipendenteRepository.findById(request.getDipendenteId())
                .orElseThrow(() -> new EntityNotFoundException("Dipendente con id " + request.getDipendenteId() + " non trovato"));

        LocalDate dataPartenzaViaggio = viaggio.getData();
        LocalDate dataRichiesta = request.getDataRichiesta();

        if (dataRichiesta.isAfter(dataPartenzaViaggio)) {
            throw new IllegalArgumentException("La data della prenotazione non puo' avvenire dopo la partenza del viaggio");
        }

        if (prenotazioneRepository.existsByDipendenteAndViaggio_Data(dipendente, dataPartenzaViaggio)) {
            throw new IllegalStateException("Il dipendente " + dipendente.getNome() + " " + dipendente.getCognome() +
                    " ha già un viaggio prenotato per la data " + dataPartenzaViaggio);
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);
        prenotazione.setDataRichiesta(request.getDataRichiesta());
        prenotazione.setPreferenze(request.getPreferenze());

        prenotazioneRepository.save(prenotazione);

        CommonResponseId commonResponseId = new CommonResponseId();
        BeanUtils.copyProperties(prenotazione, commonResponseId);
        emailSenderService.sendEmail(dipendente.getEmail(), "Prenotazione effettuata", "La tua prenotazione per  " + viaggio.getDestinazione() + " è stata effettuata con successo! Ricordati di avere con te tutti i documenti necessari. Ci vediamo il giorno " + viaggio.getData().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "! Le tue preferenze: " + prenotazione.getPreferenze() + "\n  La direzione ti augura un buon viaggio!");
        System.out.println("La prenotazione per " + dipendente.getNome() + " " + dipendente.getCognome() + " è stata effettuata con successo! Procedo subito a inviargli una email!");
        return commonResponseId;
    }

    public PrenotazioneResponse update(Long id, PrenotazioneRequest request) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione con id " + id + " non trovato");
        }
        Prenotazione prenotazione = prenotazioneRepository.findById(id).get();
        BeanUtils.copyProperties(request, prenotazione);
        prenotazioneRepository.save(prenotazione);
        PrenotazioneResponse prenotazioneResponse = new PrenotazioneResponse();
        BeanUtils.copyProperties(prenotazione, prenotazioneResponse);
        return prenotazioneResponse;

    }

    public void delete(@PathVariable Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione con id " + id + " non trovato");
        }
        prenotazioneRepository.deleteById(id);
        System.out.println("La prenotazione con id " + id + " e' stata eliminata con successo!");
    }

}
