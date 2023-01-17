import tester.*;

interface ILoDirections{}

interface ILoRoadTripChunk{}

class Direction{
  String description;
  int miles;

  //constructor
  Direction(String description, int miles){
    this.description = description;
    this.miles = miles;
  }
}

class MtLoDirections implements ILoDirections{
  MtLoDirections(){}
}

class ConsLoDirections implements ILoDirections{
  Direction first;
  ILoDirections rest;

  //constructor
  ConsLoDirections(Direction first, ILoDirections rest){
    this.first = first;
    this.rest = rest;
  }
}

class RoadTrip{
  String driver1;
  String driver2;
  ILoDirections directions;

  //constructor
  RoadTrip(String driver1, String driver2, ILoDirections directions){
    this.driver1 = driver1;
    this.driver2 = driver2;
    this.directions = directions;
  }

  ILoRoadTripChunk splitUpTrip(int givenmiles) {
    return null;
  }
}

class RoadTripChunk{
  String driver;
  ILoDirections directions;

  //constructor
  RoadTripChunk(String driver, ILoDirections directions){
    this.driver = driver;
    this.directions = directions;
  }
}

class MtLoRoadTripChunk implements ILoRoadTripChunk{
  MtLoRoadTripChunk(){}
}

class ConsLoRoadTripChunk implements ILoRoadTripChunk{
  RoadTripChunk first;
  ILoRoadTripChunk rest;

  //constructor
  ConsLoRoadTripChunk(RoadTripChunk first, ILoRoadTripChunk rest){
    this.first = first;
    this.rest = rest;
  }
}

class ExamplesRoadTrip{
  Direction direction1 = new Direction("Left at Alberquerque", 13);
  Direction direction2 = new Direction("Right at fork", 2);
  Direction direction3 = new Direction("Left at fork", 3);
  Direction direction4 = new Direction("Take the overpass", 45);
  Direction direction5 = new Direction("Destination on Left", 12);

  Direction direction2a = new Direction ("Switch with Henry", 0);
  Direction direction4a = new Direction("Switch with Hazel", 12);
  Direction direction4b = new Direction("Switch with Henry", 15);
  Direction direction4c = new Direction("Switch with Hazel", 15);
  Direction direction4d = new Direction("Take the overpass", 3);

  Direction direction6 = new Direction("Swicth with Henry", 12);
  Direction direction7 = new Direction("Switch with Hazel", 30);
  Direction direction8 = new Direction("Take the overpass", 3);

  Direction direction9 = new Direction("Switch with Henry", 2);
  Direction direction10 = new Direction("Switch with Hazel", 20);
  Direction direction11 = new Direction("Switch with Henry", 20);
  Direction direction12 = new Direction("Take the overpass", 3);

  int totaldistance = 77;

  ILoDirections dempty = new MtLoDirections();
  ILoDirections dlist1 = new ConsLoDirections(this.direction1, this.dempty);
  ILoDirections dlist2 = new ConsLoDirections(this.direction1, 
      new ConsLoDirections(this.direction2,
          new ConsLoDirections(this.direction3,
              new ConsLoDirections(this.direction4, 
                  new ConsLoDirections(this.direction4, this.dempty)))));

  RoadTrip roadtrip1 = new RoadTrip("bob", "angel", this.dempty);
  RoadTrip roadtrip2 = new RoadTrip("cortex", "rynn", this.dlist1);
  RoadTrip roadtrip3 = new RoadTrip("Hazel", "Henry", this.dlist2);

  RoadTripChunk rtc1 = new RoadTripChunk("bob", this.dempty);
  RoadTripChunk rtctotal = new RoadTripChunk("Hazel", new ConsLoDirections (this.direction1,
      new ConsLoDirections (this.direction2a, 
          new ConsLoDirections (this.direction3,
              new ConsLoDirections (this.direction4a,
                  new ConsLoDirections(this.direction4b,
                      new ConsLoDirections(this.direction4c,
                          new ConsLoDirections(this.direction4d,
                              new ConsLoDirections(this.direction5, this.dempty)))))))));
  RoadTripChunk rtchazel15a = new RoadTripChunk ("Hazel", new ConsLoDirections(this.direction1,
      new ConsLoDirections(this.direction2, 
          new ConsLoDirections (this.direction2a, this.dempty))));

  RoadTripChunk rtchenry15a = new RoadTripChunk ("Henry", new ConsLoDirections(this.direction3,
      new ConsLoDirections(this.direction4a, this.dempty)));

  RoadTripChunk rtchazel15b = new RoadTripChunk ("Hazel", new ConsLoDirections(this.direction4b, this.dempty));

  RoadTripChunk rtchenry15b = new RoadTripChunk ("Henry", new ConsLoDirections(this.direction4c, this.dempty));

  RoadTripChunk rtchazel15c = new RoadTripChunk ("Hazel", new ConsLoDirections(this.direction4d,
      new ConsLoDirections (this.direction5, this.dempty)));

  RoadTripChunk rtchazeltotal = new RoadTripChunk("Hazel", this.dlist2);

  RoadTripChunk rtchazel30a = new RoadTripChunk("Hazel", new ConsLoDirections(this.direction1,
      new ConsLoDirections(this.direction2, 
          new ConsLoDirections(this.direction3, 
              new ConsLoDirections(this.direction6,this.dempty)))));

  RoadTripChunk rtchenry30a = new RoadTripChunk("Henry", new ConsLoDirections(this.direction7, this.dempty));

  RoadTripChunk rtchazel30b = new RoadTripChunk("Hazel", new ConsLoDirections(this.direction8,
      new ConsLoDirections(this.direction5, this.dempty)));

  RoadTripChunk rtchazel20a = new RoadTripChunk("Hazel", new ConsLoDirections(this.direction1,
      new ConsLoDirections(this.direction2,
          new ConsLoDirections(this.direction9, this.dempty))));

  RoadTripChunk rtchenry20a = new RoadTripChunk("Henry",new ConsLoDirections (this.direction10, this.dempty));

  RoadTripChunk rtchazel20b = new RoadTripChunk("Hazel", new ConsLoDirections (this.direction11, this.dempty));

  RoadTripChunk rtchenry20b = new RoadTripChunk("Henry", new ConsLoDirections(this.direction12, 
      new ConsLoDirections(this.direction5, this.dempty)));

  ILoRoadTripChunk mtrtc = new MtLoRoadTripChunk();

  ILoRoadTripChunk rtclist1 = new ConsLoRoadTripChunk(rtchazel15a, 
      new ConsLoRoadTripChunk(rtchenry15a, 
          new ConsLoRoadTripChunk(rtchazel15b, 
              new ConsLoRoadTripChunk(rtchenry15b, 
                  new ConsLoRoadTripChunk(rtchazel15c, this.mtrtc)))));

  ILoRoadTripChunk rtclist2 = new ConsLoRoadTripChunk(this.rtchazeltotal, this.mtrtc);
  ILoRoadTripChunk rtclist3 = new ConsLoRoadTripChunk(rtchazel30a, 
      new ConsLoRoadTripChunk(rtchenry30a, 
          new ConsLoRoadTripChunk(rtchazel30b, this.mtrtc)));

  ILoRoadTripChunk rtclist4 = new ConsLoRoadTripChunk(rtchazel20a, 
      new ConsLoRoadTripChunk(rtchenry20a, 
          new ConsLoRoadTripChunk(rtchazel20b, 
              new ConsLoRoadTripChunk(rtchenry20b,this.mtrtc))));

}