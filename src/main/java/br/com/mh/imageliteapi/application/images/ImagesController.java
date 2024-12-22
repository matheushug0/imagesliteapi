package br.com.mh.imageliteapi.application.images;

import br.com.mh.imageliteapi.domain.entity.Image;
import br.com.mh.imageliteapi.domain.enums.ImageExtension;
import br.com.mh.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;
    private final ImagesMapper imagesMapper;

    @PostMapping
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
    ) throws IOException {
        Image image = imagesMapper.mapToImage(file, name, tags);
        Image savedImage = imageService.save(image);
        URI imageUri = buildImageURL(savedImage);
        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageId") UUID imageId) {
        Optional<Image> OpImage = imageService.findById(imageId);
        if (OpImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Image image = OpImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImagesDTO>> search(
            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
            @RequestParam(value = "query", required = false) String query) {
        List<Image> images = imageService.search(ImageExtension.ofName(extension), query);

        List<ImagesDTO> imagesDTO = images.stream().map(
                image -> imagesMapper.imageToDTO(image, buildImageURL(image)
                        .toString()))
                        .toList();
        return ResponseEntity.ok(imagesDTO);
    }

    private URI buildImageURL(Image image) {
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(imagePath)
                .build().toUri();
    }
}
