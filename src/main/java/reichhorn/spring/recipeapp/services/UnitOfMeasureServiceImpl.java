package reichhorn.spring.recipeapp.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reichhorn.spring.recipeapp.commands.UnitOfMeasureCommand;
import reichhorn.spring.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import reichhorn.spring.recipeapp.repositories.UnitOfMeasureRepository;
import reichhorn.spring.recipeapp.repositories.reactive.UnitOfMeasureReactiveRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return unitOfMeasureReactiveRepository
                .findAll()
                .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
