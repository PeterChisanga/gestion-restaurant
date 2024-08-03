package com.pcm.gestionRestaurant.controllers;

import com.pcm.gestionRestaurant.models.*;
import com.pcm.gestionRestaurant.services.CommandeRepository;
import com.pcm.gestionRestaurant.services.EmployeRepository;
import com.pcm.gestionRestaurant.services.PlatRepository;
import com.pcm.gestionRestaurant.services.RestaurantRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private PlatRepository platRepository;

    @GetMapping("/dashboard")
    public String index( HttpSession session, Model model) {
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");

        if (restaurantId == null) {
            return "redirect:/employes/login";
        }

        List<Commande> commandes = commandeRepository.findByRestaurantId(restaurantId);
        List<Plat> plats = platRepository.findByRestaurantId(restaurantId);
        List<Employe> employes = employeRepository.findByRestaurantId(restaurantId);

        model.addAttribute("commandeCount", commandes.size());
        model.addAttribute("platCount", plats.size());
        model.addAttribute("employeCount", employes.size());

        return "restaurants/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("restaurantDto", new RestaurantDto());
        return "restaurants/createRestaurant";
    }

    @PostMapping("/register")
    public String registerRestaurant(@ModelAttribute RestaurantDto restaurantDto, Model model) {
        if (restaurantDto.getNomEmploye() == null || restaurantDto.getNomEmploye().isEmpty()) {
            model.addAttribute("error", "Le nom de l'employé est obligatoire.");
            return "restaurants/createRestaurant";
        }

        Employe employe = new Employe(
                restaurantDto.getNomEmploye(),
                restaurantDto.getEmailEmploye(),
                restaurantDto.getPasswordEmploye()
        );

        Restaurant restaurant = new Restaurant(
                restaurantDto.getNom(),
                restaurantDto.getAddress()
        );

        restaurantRepository.save(restaurant);
        employe.setRestaurant(restaurant);
        employeRepository.save(employe);

        return "redirect:/employes/login";
    }
}
