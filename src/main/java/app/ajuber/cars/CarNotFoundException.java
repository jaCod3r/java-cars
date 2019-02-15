package app.ajuber.cars;

public class CarNotFoundException extends RuntimeException {
  public CarNotFoundException(Long id) {
    super("Could not find the car you requested.");
  }
}
