package br.com.mh.imageliteapi.infra.repository.specs;

import br.com.mh.imageliteapi.domain.entity.Image;
import br.com.mh.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;


//Design Pattern - Singleton

public class ImageSpecs {
    private ImageSpecs() {}
    public static Specification<Image> extensionEqual(ImageExtension extension) {
        Specification<Image> extensionEqual = (root, q, criteriaBuilder) -> criteriaBuilder.equal(root.get("extension"), extension);
        return extensionEqual;
    }

    public static Specification<Image> nameLike(String query) {
        Specification<Image> nameLike = (root, q, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + query.toUpperCase() + "%");
        return nameLike;
    }
    public static Specification<Image> tagsLike(String query) {
        Specification<Image> tagsLike = (root, q, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("tags")), "%" + query.toUpperCase() + "%");
        return tagsLike;
    }
}
