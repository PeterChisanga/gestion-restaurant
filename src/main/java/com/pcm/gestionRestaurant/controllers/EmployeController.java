package com.pcm.gestionRestaurant.controllers;

import com.pcm.gestionRestaurant.models.Employe;
import com.pcm.gestionRestaurant.models.EmployeDto;
import com.pcm.gestionRestaurant.models.Restaurant;
import com.pcm.gestionRestaurant.services.EmployeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository repo;

    @GetMapping({"","/"})
    public String showEmployesList(Model model, HttpSession session) {
        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        if (restaurantId == null) {
            return "redirect:/employes/login";
        }

        List<Employe> employes = repo.findByRestaurantId(restaurantId);
        model.addAttribute("employes", employes);
        return "employes/index";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        EmployeDto loginDto = new EmployeDto();
        model.addAttribute("loginDto", loginDto);
        return "employes/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute EmployeDto loginDto, BindingResult result, HttpSession session, Model model) {

        Employe loggedInEmploye = validateLogin(loginDto.getEmail(), loginDto.getPassword());

        if (loggedInEmploye == null) {
            result.addError(new FieldError("loginDto", "email", "Invalid email or password"));
            model.addAttribute("loginDto", loginDto);
            return "employes/login";
        }

        model.addAttribute("loggedInEmploye", loggedInEmploye);

        if (loggedInEmploye.getRestaurant() != null) {
            session.setAttribute("employeId", loggedInEmploye.getId());
            session.setAttribute("restaurantId", loggedInEmploye.getRestaurant().getId());
            return "redirect:/restaurants/dashboard";
        } else {
            return "redirect:/employes/login";
        }
    }

    private Employe validateLogin(String email, String password) {
        Employe employe = repo.findByEmail(email);

        if (employe != null && employe.getPassword().equals(password)) {
            return employe;
        }
        return null;
    }

    @GetMapping("/create")
    public String showCreatePage (Model model) {
        EmployeDto employeDto = new EmployeDto();
        model.addAttribute("employeDto", employeDto);
        return "employes/createEmploye";
    }

    @PostMapping("/create")
    public String createEmploye (@Valid @ModelAttribute EmployeDto employeDto,
                                 BindingResult result,HttpSession session) {

        Integer restaurantId = (Integer) session.getAttribute("restaurantId");
        if (restaurantId == null) {
            return "redirect:/employes/login";
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        Employe employe = new Employe();
        employe.setEmail(employeDto.getEmail());
        employe.setNom(employeDto.getNom());
        employe.setPassword(employeDto.getPassword());
        employe.setRestaurant(restaurant);

        repo.save(employe);
        return "redirect:/employes";
    }

    @GetMapping("/edit")
    public String showEditPage (Model model ,
                                @RequestParam int id) {
        try{
            Employe employe = repo.findById(id).orElseThrow(() -> new RuntimeException("Employe not found"));
            EmployeDto employeDto = new EmployeDto(employe.getNom(), employe.getEmail(), employe.getPassword());
            model.addAttribute("employe", employe);
            model.addAttribute("employeDto", employeDto);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/employes";
        }

        return "employes/editEmploye";
    }

    @PostMapping("/edit")
    public String updateProduct (Model model ,
                                @RequestParam int id,
                                @Valid @ModelAttribute EmployeDto employeDto,
                                 BindingResult result
    ) {
        try{
            Employe employe = repo.findById(id).get();
            model.addAttribute("employe", employe);

            if(result.hasErrors()){
                return "employes/editEmploye";
            }

            employe.setEmail(employeDto.getEmail());
            employe.setNom(employeDto.getNom());
            employe.setPassword(employeDto.getPassword());

            repo.save(employe);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/employes";
        }

        return "redirect:/employes";
    }

    @GetMapping("/delete")
    public String deleteEmploye(
            @RequestParam int id
    ) {
        try{
            Employe employe = repo.findById(id).get();
            repo.delete(employe);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/employes";
    }
}
