package it.epicode.BE_W6_D5_Progetto.dipendente;

import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
@RequiredArgsConstructor
@Transactional
public class DipendenteController {

    @Autowired public DipendenteService dipendenteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Dipendente> findAll() {
        return dipendenteService.findAll();

    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DipendenteResponse findById(@PathVariable Long id) {
        return dipendenteService.findById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseId create(@RequestBody DipendenteRequest dipendenteRequest) {
        return dipendenteService.create(dipendenteRequest);
    }

    @PatchMapping(value = "/{id}/fotoProfilo",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar (@PathVariable Long id,@RequestPart MultipartFile file) throws Exception {
        dipendenteService.uploadAvatar(id, file);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DipendenteResponse update(@PathVariable Long id, @RequestBody DipendenteRequest request) {
        return dipendenteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        dipendenteService.delete(id);
    }
}
