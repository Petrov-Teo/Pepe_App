package PetrovTodor.PepeMedicalKids.security;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.entities.users.Medico;
import PetrovTodor.PepeMedicalKids.services.users.AdminService;
import PetrovTodor.PepeMedicalKids.services.users.MedicoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTChekFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MedicoService medicoService; // Aggiungi MedicoService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = authHeader.replace("Bearer ", "");

        try {
            jwtTools.verifyToken(accessToken);

            String id = jwtTools.extractDipendenteFromToken(accessToken);
            String ruolo = jwtTools.extractRuoloFromToken(accessToken);

            // Dichiarazione dell'oggetto utente
            Object user = null;

            switch (ruolo) {
                case "ADMIN":

                    Optional<Admin> adminOptional = Optional.ofNullable(adminService.findById(UUID.fromString(id)));
                    if (adminOptional.isPresent()) {
                        user = adminOptional.get();
                        Authentication adminAuth = new UsernamePasswordAuthenticationToken(user, null, ((Admin) user).getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(adminAuth);
                    }
                    break;

                case "MEDICO":
                   
                    Optional<Medico> medicoOptional = Optional.ofNullable(medicoService.findMedicoByIdMedico(UUID.fromString(id))); // Assumendo che MedicoService abbia il metodo findById
                    if (medicoOptional.isPresent()) {
                        user = medicoOptional.get();
                        Authentication medicoAuth = new UsernamePasswordAuthenticationToken(user, null, ((Medico) user).getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(medicoAuth);
                    }
                    break;

                case "RECEPTIONIST":
                    // Implementa la logica per Receptionist se necessario
                    break;

                default:
                    throw new ServletException("Ruolo non valido");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Token non valido");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}