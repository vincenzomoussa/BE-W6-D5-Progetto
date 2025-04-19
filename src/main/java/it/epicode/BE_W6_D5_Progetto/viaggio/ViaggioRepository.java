package it.epicode.BE_W6_D5_Progetto.viaggio;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {

    boolean existsByDestinazioneAndData(String destinazione, LocalDate data);

}
