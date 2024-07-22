package com.pcm.gestionRestaurant.controllers;

import com.pcm.gestionRestaurant.models.Employe;
import com.pcm.gestionRestaurant.models.EmployeDto;
import com.pcm.gestionRestaurant.services.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository repo;

    @GetMapping({"","/"})
    public String showEmployesList (Model model) {
        List<Employe> employes = repo.findAll();
        model.addAttribute("employes", employes);
        return "employes/index";
    }

    @GetMapping("/create")
    public String showCreatePage (Model model) {
        EmployeDto employeDto = new EmployeDto();
        model.addAttribute("employeDto", employeDto);
        return "employes/createEmploye";
    }

    @PostMapping("/create")
    public String createEmploye (@Valid @ModelAttribute EmployeDto employeDto,
                                 BindingResult result) {

        Employe employe = new Employe();
        employe.setEmail(employeDto.getEmail());
        employe.setNom(employeDto.getNom());
        employe.setPassword(employeDto.getPassword());

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
