import tester.*;

class Time {
  int hour;
  int minute;
  int second;
  boolean isAm;


  Time(int hour, int minute, int second) {
    this.hour = new Utils().checkRange(hour, 0, 23, 
        "Invalid hour: " + Integer.toString(hour));
    this.minute = new Utils().checkRange(minute, 0, 59, 
        "Invalid minute: " + Integer.toString(minute));
    this.second = new Utils().checkRange(second, 0, 59, 
        "Invalid second: " + Integer.toString(second));
  }

  //  Constructor
  Time(int hour, int minute) {
    this(hour, minute, 0);
  }

  //  constructor given 1-12 hr
  Time(int hour, int minute, boolean isAm) {
    this(hour = new Utils().checkRange(hour, 1, 12, 
        "Invalid hour: " + Integer.toString(hour)), minute, 0);
    if (isAm && hour == 12) { // when given a 12:x AM time
      this.hour = 0;
    }
    else 
      {if (!isAm && hour < 12) { // when given a time in the afternoon that isnt 12 PM
          this.hour = this.hour + 12;
        }
        else {
          
            this.hour = this.hour + 0;
          }
    }
  }

  boolean sameTime(Time that) {
    return (this.hour == that.hour && this.minute == that.minute && this.second == that.second); // first constructor and first constructor
       

  }
}

class Utils {
  int checkRange(int val, int min, int max, String msg) {
    if (val >= min && val <= max) {
      return val;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }

}

class ExamplesTimes {

  Time am = new Time(12, 15, true);
  Time pm = new Time(12, 15, false);
  Time pm2 = new Time(12, 30, false);
  Time pm3 = new Time(4, 20, false);
  Time am2 = new Time(4, 20, true);
  Time example1applebottomidk1 = new Time(12, 12, 12);
  Time example1applebottomidk2 = new Time(12, 12, 12);
  Time example1applebottomidk24 = new Time(12, 15, 13);
  Time example1applebottomidk3 = new Time(12, 15);
  Time example1applebottomidk4 = new Time(12, 12);
  Time example1applebottomidk5 = new Time(12, 13);
  
  


  boolean testerCheckConstructorException(Tester t) {
    return
        t.checkConstructorException(new IllegalArgumentException("Invalid hour: 24"), ""
            + "Time", 24, 15, 19) 
        && t.checkConstructorException(new IllegalArgumentException("Invalid hour: 40"), ""
            + "Time", 40, 15) 
        && t.checkConstructorException(new IllegalArgumentException("Invalid hour: 0"), ""
            + "Time", 0, 15, true) 
        && t.checkException(
            new IllegalArgumentException("no"), new Utils(), "checkRange", 15, 0, 7, "no");
  }

  boolean testerSameTime(Tester t) {
    return t.checkExpect(this.pm.sameTime(am), false)
        && t.checkExpect(this.pm3.sameTime(this.am2), false)
        && t.checkExpect(this.pm.sameTime(this.example1applebottomidk24), false);
  }
}
