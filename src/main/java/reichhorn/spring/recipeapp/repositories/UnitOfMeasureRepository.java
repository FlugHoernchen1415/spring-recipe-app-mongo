package reichhorn.spring.recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import reichhorn.spring.recipeapp.model.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
