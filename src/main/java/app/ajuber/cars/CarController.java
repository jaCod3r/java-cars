package app.ajuber.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CarController {
  private final CarRepository carRepository;
  private final RabbitTemplate rabbitTemplate;

  public CarController(CarRepository carRepository, RabbitTemplate rabbitTemplate) {
    this.carRepository = carRepository;
    this.rabbitTemplate = rabbitTemplate;
  }

  @GetMapping("/cars")
  public List<Car> all () {
    return carRepository.findAll();
  }

  @GetMapping("/cars/id/{id}")
  public Car findOne(@PathVariable Long id) {
    return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
  }

  @GetMapping("/car/year/{year}")
  public List<Car> findByYear(@PathVariable int year) {
    return carRepository.findAll().stream().filter(car -> car.getYear() == year).collect(Collectors.toList());
  }

}
