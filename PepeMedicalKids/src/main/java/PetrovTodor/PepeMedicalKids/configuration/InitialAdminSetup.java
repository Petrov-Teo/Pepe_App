package PetrovTodor.PepeMedicalKids.configuration;

import PetrovTodor.PepeMedicalKids.payload.user.AdminDTO;
import PetrovTodor.PepeMedicalKids.repositorys.users.AdminRepository;
import PetrovTodor.PepeMedicalKids.services.users.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class InitialAdminSetup {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Value("${PASSWORD_ADMIN}")
    private String password;
    private LocalDate dataDiNascita = LocalDate.ofEpochDay(1980 - 1 - 1);


    @PostConstruct
    public void createInitialAdmin() {
        if (adminRepository.count() == 0) {
            AdminDTO initialAdmin = new AdminDTO(
                    "AAABBB11C11D111E",
                    "admin",
                    "admin",
                    dataDiNascita,
                    "Città",
                    "petrov@pigrecoservizi.com",
                    password,
                    "numero di telefono"
            );

            try {
                adminService.saveAdmin(initialAdmin);
                System.out.println("Admin creato con successo!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
