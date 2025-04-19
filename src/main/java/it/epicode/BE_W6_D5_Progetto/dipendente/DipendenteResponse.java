package it.epicode.BE_W6_D5_Progetto.dipendente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DipendenteResponse {
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String fotoProfilo;
}
