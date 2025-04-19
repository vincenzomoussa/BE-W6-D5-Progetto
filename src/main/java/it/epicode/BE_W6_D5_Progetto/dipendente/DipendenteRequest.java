package it.epicode.BE_W6_D5_Progetto.dipendente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DipendenteRequest {
    @NotBlank(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotBlank(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @NotBlank(message = "Il campo username non può essere vuoto")
    private String username;
    @Email(message = "L'email non è valida"  )
    private String email;
}
