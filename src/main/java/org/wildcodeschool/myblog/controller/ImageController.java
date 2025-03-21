package org.wildcodeschool.myblog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.ImageDTO;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.repository.ImageRepository;
import org.wildcodeschool.myblog.repository.ArticleRepository;
import org.wildcodeschool.myblog.service.ImageService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // on autorise que les admins ici, ok
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> images = imageService.getAllImages();
        return images.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO image = imageService.getImageById(id);
        return image == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(image);
    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        return ResponseEntity.status(201).body(imageService.createImage(imageDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        ImageDTO updatedImage = imageService.updateImage(id, imageDTO);
        return updatedImage == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}