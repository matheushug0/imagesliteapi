package br.com.mh.imageliteapi.domain.service;

import br.com.mh.imageliteapi.domain.entity.Image;
import br.com.mh.imageliteapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {
    Image save(Image image);
    Optional<Image> findById(UUID id);
    List<Image> search(ImageExtension imageExtension, String query);
}
