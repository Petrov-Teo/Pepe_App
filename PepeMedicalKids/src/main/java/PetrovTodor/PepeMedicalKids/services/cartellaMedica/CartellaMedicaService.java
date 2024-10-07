package PetrovTodor.PepeMedicalKids.services.cartellaMedica;

import PetrovTodor.PepeMedicalKids.repositorys.cartellaMedica.CartellaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartellaMedicaService {
    @Autowired
    private CartellaMedicaRepository cartellaMedicaRepository;
}
