package PetrovTodor.PepeMedicalKids.controllers;

import PetrovTodor.PepeMedicalKids.entities.users.Admin;
import PetrovTodor.PepeMedicalKids.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/amministratori")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    public Admin save(@RequestBody Admin admin, BindingResult validationResult,
                      @AuthenticationPrincipal UserDetails userDetails) {
        return this.adminService.creaNuovoAdmin(admin);
    }
}
