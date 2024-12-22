package br.com.mh.imageliteapi.infra.repository;

import br.com.mh.imageliteapi.domain.entity.Image;
import br.com.mh.imageliteapi.domain.enums.ImageExtension;
import br.com.mh.imageliteapi.infra.repository.specs.GenericSpecs;
import br.com.mh.imageliteapi.infra.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {

        // WHERE 1 = 1
        Specification<Image> specification = Specification.where(GenericSpecs.conjunction());

        if (extension != null) {
            // AND EXTENSION = 'EXTENSION'
            specification = specification.and(ImageSpecs.extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            // AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
            Specification<Image> nameOrTagsLike = Specification.anyOf(ImageSpecs.nameLike(query), ImageSpecs.tagsLike(query));
            specification = specification.and(nameOrTagsLike);
        }

        return findAll(specification);
    }
}
