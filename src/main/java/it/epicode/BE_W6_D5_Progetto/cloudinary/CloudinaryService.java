package it.epicode.BE_W6_D5_Progetto.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            Map result = cloudinary.uploader()
                    .upload(file.getBytes(), Cloudinary.asMap("folder", "BE-Cloudinary", "public_id", file.getOriginalFilename()));
            String url = result.get("secure_url").toString();

            return url;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } }
