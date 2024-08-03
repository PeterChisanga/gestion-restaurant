package com.pcm.gestionRestaurant.controllers;

import com.pcm.gestionRestaurant.models.*;
import com.pcm.gestionRestaurant.services.CommandeRepository;
import com.pcm.gestionRestaurant.services.PlatRepository;
import com.pcm.gestionRestaurant.services.PlatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("/plats")
public class PlatController {

    @Autowired
    private PlatService platService;

    @Autowired
    private PlatRepository repo;

    @Autowired
    private CommandeRepository repoCommande;

    @GetMapping({"", "/"})
    public String showPlatsList(Model model, HttpSession session) {
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        if (restaurantId == null) {
            return "redirect:/employes/login"; // Redirect to login if session attribute is missing
        }

        List<Plat> plats = repo.findByRestaurantId(restaurantId);
        model.addAttribute("plats", plats);
        return "plats/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        PlatDto platDto = new PlatDto();
        model.addAttribute("platDto", platDto);
        return "plats/createPlat";
    }

    @PostMapping("/create")
    public String createPlat(PlatDto platDto, @RequestParam("image") MultipartFile imageFile,HttpSession session, Model model) {
        try {
            platService.savePlat(platDto, imageFile,session);
            return "redirect:/plats";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "plats/createPlat";
        }
    }

    @GetMapping("/delete")
    public String deletePlat(@RequestParam int id) {
        try {
            Plat plat = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid plat Id:" + id));

            List<Commande> commandes = repoCommande.findByPlat(plat);

            for (Commande commande : commandes) {
                System.out.println("Commande ID: " + commande.getId() + ", Client: " + commande.getClient().getNom() + ", Plat: " + commande.getPlat().getNom() + ", Montant Total: " + commande.getMontantTotal());
            }

            repo.delete(plat);

        } catch (Exception e) {
            throw new RuntimeException("Error deleting plat: " + e.getMessage(), e);
        }
        return "redirect:/plats";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Plat plat = repo.findById(id).orElseThrow(() -> new RuntimeException("Plat pas trouve"));
            PlatDto platDto = new PlatDto(plat.getNom(), plat.getPrix(), plat.getDescription());
            model.addAttribute("plat", plat);
            model.addAttribute("platDto", platDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/plats";
        }
        return "plats/editPlat";
    }

    @PostMapping("/edit")
    public String updatePlat(@RequestParam int id,
                             @RequestParam("image") MultipartFile imageFile,
                             @Valid @ModelAttribute PlatDto platDto,
                             BindingResult result,
                             Model model) {
        try {
            Plat plat = repo.findById(id).orElseThrow(() -> new RuntimeException("Plat n'existe pas"));

//            if (result.hasErrors()) {
//                return "plats/editPlat";
//            }

            plat.setNom(platDto.getNom());
            plat.setPrix(platDto.getPrix());
            plat.setDescription(platDto.getDescription());

            if (!imageFile.isEmpty()) {
                String imageUrl = saveImage(imageFile);
                plat.setImage(imageUrl);
            } else {
                plat.setImage(plat.getImage());
            }

            repo.save(plat);
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred: " + e.getMessage());
            return "plats/editPlat";
        }

        return "redirect:/plats";
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }

        String imageDirectory = "public/images/";
        String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(imageDirectory + imageName);

        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, imageFile.getBytes());

        return "/images/" + imageName;
    }
}