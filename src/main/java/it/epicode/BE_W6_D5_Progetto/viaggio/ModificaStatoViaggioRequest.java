package it.epicode.BE_W6_D5_Progetto.viaggio;

import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificaStatoViaggioRequest {
    @NotNull(message = "Il campo viaggioId non può essere null")
    private Long viaggioId;
    @NotNull(message = "Il campo nuovoStato non può essere null")
    private StatoViaggio nuovoStato;
}
