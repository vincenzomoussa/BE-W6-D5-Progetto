package it.epicode.BE_W6_D5_Progetto.prenotazione;

import it.epicode.BE_W6_D5_Progetto.dipendente.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {



    @Query("SELECT p FROM Prenotazione p WHERE p.dipendente = :dipendente AND p.viaggio.data = :data")
    List<Prenotazione> findByDipendenteAndViaggio_Data(@Param("dipendente") Dipendente dipendente, @Param("data") LocalDate data);

    boolean existsByDipendenteAndViaggio_Data(Dipendente dipendente, LocalDate data);
}