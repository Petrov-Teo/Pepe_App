package PetrovTodor.PepeMedicalKids.repositorys;

import PetrovTodor.PepeMedicalKids.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
