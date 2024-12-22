package br.com.mh.imageliteapi.application.images;

import br.com.mh.imageliteapi.domain.entity.Image;
import br.com.mh.imageliteapi.domain.enums.ImageExtension;
import br.com.mh.imageliteapi.domain.service.ImageService;
import br.com.mh.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findById(UUID id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension imageExtension, String query) {
        return imageRepository.findByExtensionAndNameOrTagsLike(imageExtension, query);
    }
}
