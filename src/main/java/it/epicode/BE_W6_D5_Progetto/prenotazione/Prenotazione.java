package it.epicode.BE_W6_D5_Progetto.prenotazione;

import it.epicode.BE_W6_D5_Progetto.dipendente.Dipendente;
import it.epicode.BE_W6_D5_Progetto.viaggio.Viaggio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "prenotazioni")
@AllArgsConstructor
@NoArgsConstructor

public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    @Column(length = 50, nullable = false)
    private LocalDate dataRichiesta;

    private String preferenze;

}
