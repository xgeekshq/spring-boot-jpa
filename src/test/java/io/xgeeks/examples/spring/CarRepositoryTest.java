package io.xgeeks.examples.spring;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Autowired
    private JdbcTemplate template;

    @Test
    public void shouldFindById() {
        Assertions.assertNotNull(repository);
        Optional<Car> car = repository.findById(1L);
        Assertions.assertNotNull(car);
    }

    @Test
    public void shouldInsertCar() {
        Car car = Car.builder()
                .city("Salvador")
                .color("Red")
                .name("Fiat")
                .model("Model")
                .build();
        Car insert = repository.save(car);
        Assertions.assertNotNull(insert);
        Assertions.assertNotNull(insert.getId());
    }

    @Test
    public void shouldDelete() {
        Car car = Car.builder()
                .city("Salvador")
                .color("Red")
                .name("Fiat")
                .model("Model")
                .build();
        Car insert = repository.save(car);
        repository.deleteById(insert.getId());
        Optional<Car> empty = repository.findById(insert.getId());
        Assertions.assertTrue(empty.isEmpty());
    }

    @Test
    public void shouldUpdate() {
        Car car = Car.builder()
                .city("Salvador")
                .color("Red")
                .name("Fiat")
                .model("Model")
                .build();
        Car insert = repository.save(car);

        insert.update(Car.builder()
                .city("Salvador")
                .color("Red")
                .name("Fiat")
                .model("Update")
                .build());
        repository.save(insert);
    }

    @Test
    public void shouldFindAll() {
        template.execute("DELETE FROM CAR");
        List<Car> cars = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Car car = Car.builder()
                    .city("Salvador")
                    .color("Red")
                    .name("Fiat " + index)
                    .model("Model")
                    .build();
            cars.add(repository.save(car));
        }
        Pageable page = PageRequest.of(0, 2);
        List<Car> result = StreamSupport.stream(repository.findAll(page).spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(2, result.size());
        MatcherAssert.assertThat(result, Matchers.contains(cars.get(0), cars.get(1)));
        Pageable nextPage = page.next();
        result = StreamSupport.stream(repository.findAll(nextPage).spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(2, result.size());
        MatcherAssert.assertThat(result, Matchers.contains(cars.get(2), cars.get(3)));
    }
}