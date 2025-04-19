package it.epicode.BE_W6_D5_Progetto.prenotazione;

import it.epicode.BE_W6_D5_Progetto.common.CommonResponseId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {

    private LocalDate dataRichiesta;
    private String preferenze;
    private Long viaggio;
    private Long dipendente;

}
