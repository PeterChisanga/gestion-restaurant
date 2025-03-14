package com.pcm.gestionRestaurant.controllers;

import com.pcm.gestionRestaurant.models.*;
import com.pcm.gestionRestaurant.services.ClientRepository;
import com.pcm.gestionRestaurant.services.CommandeRepository;
import com.pcm.gestionRestaurant.services.PlatRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping({"", "/"})
    public String listCommandes(Model model, HttpSession session) {
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        if(restaurantId == null)
            return "redirect:/employes/login";

        List<Commande> commandes = commandeRepository.findByRestaurantId(restaurantId);
        model.addAttribute("commandes", commandes);
        return "commandes/index";
    }

    @GetMapping("/create")
    public String showCreateCommandeForm(Model model) {
        model.addAttribute("plats", platRepository.findAll());
        model.addAttribute("commandeDto", new CommandeDto());
        return "commandes/createCommande";
    }

    @PostMapping("/save")
    public String saveCommande(@ModelAttribute CommandeDto commandeDto, Model model,HttpSession session) {
        if (commandeDto.getNomClient() == null || commandeDto.getNomClient().isEmpty()) {
            model.addAttribute("error", "Le nom du client est obligatoire.");
            model.addAttribute("plats", platRepository.findAll());
            return "commandes/createCommande";
        }

        Integer restaurantId = (Integer) session.getAttribute("restaurantId");

        if(restaurantId == null){
            return "redirect:/employes/login";
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        Plat plat = platRepository.findById(commandeDto.getPlatId()).orElseThrow(() -> new IllegalArgumentException("Plat non trouvé."));
        Client client = new Client(commandeDto.getNomClient());
        client = clientRepository.save(client);

        Commande commande = new Commande(client, plat, plat.getPrix(),restaurant);
        commandeRepository.save(commande);

        return "redirect:/commandes";
    }
}
