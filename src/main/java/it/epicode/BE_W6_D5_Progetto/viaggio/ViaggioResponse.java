package it.epicode.BE_W6_D5_Progetto.viaggio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaggioResponse {

    private String destinazione;
    private LocalDate data;
    private StatoViaggio Stato;
}
