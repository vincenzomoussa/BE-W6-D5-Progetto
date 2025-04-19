package it.epicode.BE_W6_D5_Progetto.viaggio;


import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
@RequiredArgsConstructor
@Transactional
public class ViaggioController {

    @Autowired private ViaggioService viaggioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Viaggio> findAll() {
        return viaggioService.findAll();

    }

    @PutMapping("/modificaStato")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseId modificaStatoViaggio(@RequestBody ModificaStatoViaggioRequest request) {
        return viaggioService.modificaStatoViaggio(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ViaggioResponse findById(@PathVariable Long id) {
        return viaggioService.findById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseId create(@RequestBody ViaggioRequest request) {
        return viaggioService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ViaggioResponse update(@PathVariable Long id, @RequestBody ViaggioRequest request) {
        return viaggioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        viaggioService.delete(id);
    }
}
