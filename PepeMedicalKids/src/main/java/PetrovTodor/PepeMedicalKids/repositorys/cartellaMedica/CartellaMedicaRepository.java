package PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica;

import PetrovTodor.PepeMedicalKids.entities.cartellaMedicha.CartellaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartellaMedicaRepository extends JpaRepository<CartellaMedica, UUID> {

    Optional<CartellaMedica> findCartellaMedicaByNumeroCartella(String numeroCartella);

    Optional<CartellaMedica> findCartellaMedicaByPaziente_CodPaziente(String codPaziente);
}
