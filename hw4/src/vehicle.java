import tester.*;

interface IVehicle {
  // computes the total revenue of this Vehicle
  double totalRevenue();

  // computes the cost of fully fueling this Vehicle
  double fuelCost();

  // computes the per-passenger profit of this Vehicle
  double perPassengerProfit();

  // produce a String that shows the name and passenger capacity of this Vehicle
  String format();

  // is this IVehicle the same as that one?
  boolean sameVehicle(IVehicle that);

  // is this airplane the same as that one?
  boolean sameAirplane(Airplane that);

  // is this train the same as that train?
  boolean sameTrain(Train that);

  // is this bus the same as that bus?
  boolean sameBus(Bus that);
}

abstract class AVehicle implements IVehicle {
  String name;
  int passengerCapacity;
  double fare; // per passenger fare
  int fuelCapacity; // gallons of fuel (kerosene @ 1.94/gallon)


  AVehicle(String name, int passengerCapacity, double fare, int fuelCapacity) {
    this.name = name;
    this.passengerCapacity = passengerCapacity;
    this.fare = fare;
    this.fuelCapacity = fuelCapacity;
  }

  // computes the total revenue of operating this AVehicle
  public double totalRevenue() {
    return this.passengerCapacity * this.fare;
  }

  // computes the cost of fully fueling this AVehcile
  public double fuelCost() {
    return this.fuelCapacity * 2.55;
  }

  // computes the per-passenger profit of this AVehicle
  public double perPassengerProfit() {
    return (this.totalRevenue() - this.fuelCost()) / this.passengerCapacity;
  }

  // produce a String that shows the name and passenger capacity of this
  // AVehicle
  public String format() {
    return this.name + ", " + String.valueOf(this.passengerCapacity) + ".";
  }

  // the default case for comparing two airplanes, and is over riden in each subclass as needed
  public boolean sameAirplane(Airplane that) {
    return false;
  }

  // the default case for comparing two buses, and is over riden in each subclass as needed
  public boolean sameBus(Bus that) {
    return false;
  }

  // the default case for comparing two trains, and is over riden in each subclass as needed
  public boolean sameTrain(Train that) {
    return false;
  }
}

class Airplane extends AVehicle {
  String code; // ICAO type designator
  boolean isWideBody; // twin-aisle aircraft

  Airplane(String name, int passengerCapacity, double fare, int fuelCapacity, String code,
      boolean isWideBody) {
    super(name, passengerCapacity, fare, fuelCapacity);
    this.code = code;
    this.isWideBody = isWideBody;
  }

  // computes the cost of fully fueling this AVehcile
  public double fuelCost() {
    return this.fuelCapacity * 1.94;
  }


  // is this Airplane the same as that IVehicle?
  public boolean sameVehicle(IVehicle that) {
    return that.sameAirplane(this);
  }

  // is this airplane the same as that airplane?
  public boolean sameAirplane(Airplane that) {
    return this.name == that.name &&
        this.passengerCapacity == that.passengerCapacity &&
        this.fare == that.fare &&
        this.fuelCapacity == that.fuelCapacity &&
        this.code == that.code &&
        this.isWideBody == that.isWideBody;

  }
}

class Train extends AVehicle {
  int numberOfCars; // cars per trainset
  int gauge; // track gauge in millimeters

  Train(String name, int passengerCapacity, double fare, int fuelCapacity, int numberOfCars,
      int gauge) {
    super(name, passengerCapacity, fare, fuelCapacity);
    this.numberOfCars = numberOfCars;
    this.gauge = gauge;
  }


  // is this Train the same as that IVehicle?
  public boolean sameVehicle(IVehicle that) {
    return that.sameTrain(this);
  }

  // is this train the same as that train?
  public boolean sameTrain(Train that) {
    return this.name == that.name &&
        this.passengerCapacity == that.passengerCapacity &&
        this.fare == that.fare &&
        this.fuelCapacity == that.fuelCapacity &&
        this.numberOfCars == that.numberOfCars &&
        this.gauge == that.gauge;

  }
}

class Bus extends AVehicle {
  int length; // length in feet

  Bus(String name, int passengerCapacity, double fare, int fuelCapacity, int length) {
    super(name, passengerCapacity, fare, fuelCapacity);
    this.length = length;
  }


  // is this Bus the same as that IVehicle?
  public boolean sameVehicle(IVehicle that) {
    return that.sameBus(this);
  }

  // is this bus the same as that bus?
  public boolean sameBus(Bus that) {
    return this.name == that.name &&
        this.passengerCapacity == that.passengerCapacity &&
        this.fare == that.fare &&
        this.fuelCapacity == that.fuelCapacity &&
        this.length == that.length;

  }
}

class ExamplesVehicle {
  IVehicle dreamliner = new Airplane("Boeing 787", 242, 835.0, 33340, "B788", false);
  IVehicle commuterRail = new Train("MPI HSP46", 500, 11.50, 2000, 6, 1435);
  IVehicle silverLine = new Bus("Neoplan AN460LF", 77, 1.70, 100, 60);

  // testing total revenue method
  boolean testTotalRevenue(Tester t) {
    return t.checkInexact(this.dreamliner.totalRevenue(), 242 * 835.0, .0001)
        && t.checkInexact(this.commuterRail.totalRevenue(), 500 * 11.5, .0001)
        && t.checkInexact(this.silverLine.totalRevenue(), 77 * 1.7, 0.001);
  }
  
  boolean testSameVehicle(Tester t) {
    return t.checkExpect(this.dreamliner.sameVehicle(this.commuterRail), false);
  }
}