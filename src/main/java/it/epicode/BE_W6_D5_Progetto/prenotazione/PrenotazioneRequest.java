package it.epicode.BE_W6_D5_Progetto.prenotazione;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {

    @NotNull(message = "Il campo 'viaggioId' non può essere nullo")
    private Long viaggioId;

    @NotNull(message = "Il campo 'dipendenteId' non può essere nullo")
    private Long dipendenteId;

    private LocalDate dataRichiesta;

    private String preferenze;
}
