package reichhorn.spring.recipeapp.services;

import reactor.core.publisher.Flux;
import reichhorn.spring.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
