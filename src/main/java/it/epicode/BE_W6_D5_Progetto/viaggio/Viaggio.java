package it.epicode.BE_W6_D5_Progetto.viaggio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "viaggi")
@AllArgsConstructor
@NoArgsConstructor
public class Viaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String destinazione;

    @Column(nullable = false, length = 50)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoViaggio Stato;


}
