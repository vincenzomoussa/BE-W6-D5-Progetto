package it.epicode.BE_W6_D5_Progetto.dipendente;

import it.epicode.BE_W6_D5_Progetto.cloudinary.CloudinaryService;
import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@Service
@RequiredArgsConstructor
public class DipendenteService {

    @Autowired private DipendenteRepository dipendenteRepository;
    @Autowired private CloudinaryService cloudinaryService;


    public DipendenteResponse findById(@PathVariable Long id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new EntityNotFoundException("Dipendente con id " + id + " non trovato");
        }
        Dipendente dipendente = dipendenteRepository.getById(id);
        DipendenteResponse dipendenteResponse = new DipendenteResponse();
        BeanUtils.copyProperties(dipendente, dipendenteResponse);
        return dipendenteResponse;

    }


    public List<Dipendente> findAll() {
        return dipendenteRepository.findAll();

    }

    public void uploadAvatar (Long id, MultipartFile file) {
        if (!dipendenteRepository.existsById(id)) {
            throw new EntityNotFoundException("Dipendente con id " + id + " non trovato");
        }
        Dipendente dipendente= dipendenteRepository.findById(id).get();

        String url = cloudinaryService.uploadImage(file);

        dipendente.setFotoProfilo(url);
        dipendenteRepository.save(dipendente);
        System.out.println("L'avatar del dipendente " + dipendente.getNome() + " " + dipendente.getCognome() + " e' stato caricato con successo! Url: " + url);
    }

    public CommonResponseId create(@Valid DipendenteRequest request) {
        if (dipendenteRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new IllegalArgumentException("Username già esistente");
        }
        if (dipendenteRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new IllegalArgumentException("Utente gia registrato con questa email");
        }
        Dipendente dipendente = new Dipendente();
        BeanUtils.copyProperties(request, dipendente);
        dipendenteRepository.save(dipendente);
        CommonResponseId commonResponseId = new CommonResponseId();
        BeanUtils.copyProperties(dipendente, commonResponseId);
        System.out.println("Il dipendente " + dipendente.getNome() + " " + dipendente.getCognome() + " e' stato creato con successo! username:" + dipendente.getUsername() + " email:" + dipendente.getEmail());
        return commonResponseId;

    }

    public DipendenteResponse update(Long id, DipendenteRequest request) {
        if (!dipendenteRepository.existsById(id)) {
            throw new EntityNotFoundException("Dipendente con id " + id + " non trovato");
        }
        Dipendente dipendente= dipendenteRepository.findById(id).get();
        BeanUtils.copyProperties(request, dipendente);
        dipendenteRepository.save(dipendente);
        DipendenteResponse dipendenteResponse = new DipendenteResponse();
        BeanUtils.copyProperties(dipendente, dipendenteResponse);
        return dipendenteResponse;

    }
  public void delete(@PathVariable Long id) {
        if(!dipendenteRepository.existsById(id)) {
            throw new EntityNotFoundException("Dipendente con id " + id + " non trovato");
        }
        dipendenteRepository.deleteById(id);
        if (!dipendenteRepository.existsById(id)) {
            System.out.println("Il dipendente con id " + id + " e' stato eliminato con successo!");
        }
  }


}
