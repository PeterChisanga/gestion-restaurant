package com.pcm.gestionRestaurant.services;

import com.pcm.gestionRestaurant.models.Plat;
import com.pcm.gestionRestaurant.models.PlatDto;
import com.pcm.gestionRestaurant.services.PlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PlatService {

    @Autowired
    private PlatRepository platRepository;

    public Plat savePlat(PlatDto platDto, MultipartFile imageFile) throws IOException {

        if (platDto.getNom() == null || platDto.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (platDto.getPrix() == null || platDto.getPrix() <= 0) {
            throw new IllegalArgumentException("Le prix est obligatoire et doit être supérieur à 0.");
        }

        String imageUrl = saveImage(imageFile);

        Plat plat = new Plat();
        plat.setNom(platDto.getNom());
        plat.setPrix(platDto.getPrix());
        plat.setDescription(platDto.getDescription());
        plat.setImage(imageUrl);

        return platRepository.save(plat);
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
