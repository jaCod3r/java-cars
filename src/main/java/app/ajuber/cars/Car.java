package app.ajuber.cars;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Car {

  private @Id @GeneratedValue Long id;
  private String model;
  private String brand;
  private int year;


  public Car() {
    // default constructor
  }

  public Car(String model, String brand, int year) {
    this.model = model;
    this.brand = brand;
    this.year = year;
  }


}

