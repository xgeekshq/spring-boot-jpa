package io.xgeeks.examples.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CarService {

    private final CarRepository repository;

    private final CarMapper mapper;

    @Autowired
    public CarService(CarRepository repository, CarMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CarDTO> findAll(Pageable page) {
        Stream<Car> stream = StreamSupport
                .stream(repository.findAll(page)
                        .spliterator(), false);
        return stream.map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CarDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public CarDTO insert(CarDTO dto) {
        Car car = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(car));
    }

    public CarDTO update(Long id, CarDTO dto) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car does not find with the id " + id));
        car.update(mapper.toEntity(dto));
        repository.save(car);
        return mapper.toDTO(car);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
