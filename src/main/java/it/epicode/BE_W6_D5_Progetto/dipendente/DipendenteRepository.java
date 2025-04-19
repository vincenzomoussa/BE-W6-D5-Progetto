package it.epicode.BE_W6_D5_Progetto.dipendente;


import org.springframework.data.jpa.repository.JpaRepository;

public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    public boolean existsByUsernameIgnoreCase(String username);
    public boolean existsByEmailIgnoreCase(String email);
}
