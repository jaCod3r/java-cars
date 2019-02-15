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

  @GetMapping("/cars/year/{year}")
  public List<Car> findByYear(@PathVariable int year) {
    return carRepository.findAll().stream().filter(car -> car.getYear() == year).collect(Collectors.toList());
  }

  @PostMapping("/cars/upload")
  public List<Car> uploadData(@RequestBody List<Car> newCars) {
    CarLog message = new CarLog("Data Loaded Successfully");
    rabbitTemplate.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
    return carRepository.saveAll(newCars);
  }

  @DeleteMapping("/cars/delete/{id}")
  public Car deleteCar(@PathVariable Long id) {
    Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    carRepository.delete(car);
    CarLog message = new CarLog("{" + id + "} Data Deleted");
    rabbitTemplate.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
    return car;
  }
}
