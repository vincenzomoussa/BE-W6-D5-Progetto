package it.epicode.BE_W6_D5_Progetto.dipendente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "dipendenti")
@AllArgsConstructor
@NoArgsConstructor
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String nome;

    @Column(length = 50, nullable = false)
    private String cognome;

    @Column(length = 100, nullable = false)
    private String email;

    private String fotoProfilo;


}
