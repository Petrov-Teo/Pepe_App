package PetrovTodor.PepeMedicalKids.services.finanza;

import PetrovTodor.PepeMedicalKids.repositorys.finanza.PagamentoFornitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoFornitoreService {
    @Autowired
    private PagamentoFornitoreRepository pagamentoFornitoreRepository;
}
