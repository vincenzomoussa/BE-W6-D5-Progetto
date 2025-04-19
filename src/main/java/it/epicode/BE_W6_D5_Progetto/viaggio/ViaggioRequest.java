package it.epicode.BE_W6_D5_Progetto.viaggio;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaggioRequest {
    @NotBlank(message = "Il nome del viaggio non può essere vuoto")
    private String destinazione;

    private LocalDate data;
}
