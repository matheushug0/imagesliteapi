package br.com.mh.imageliteapi.infra.repository.specs;

import br.com.mh.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
    private GenericSpecs() {}

    public static <T> Specification<T> conjunction(){
        Specification<T> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
        return conjunction;
    }
}
