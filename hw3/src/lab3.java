interface IBook {
  // consumes the current day and produces # of days a book is overdue
  int daysOverdue(int that);
  
  // produces a bool that indicates whether a book is overdue
  boolean isOverdue(int givenDay);
}

abstract class AABook implements IBook {
  String title;
  int dayTaken;

  AABook(String title, int dayTaken) {
    this.title = title;
    this.dayTaken = dayTaken;
    

  }
  public boolean isOverdue(int givenDay) {
    return (daysOverdue(givenDay) > 0);
  }
}
class Book extends AABook {
  String author;

  Book(String title, int dayTaken, String author) {
    super(title, dayTaken);
    this.author = author;

  }

//consumes the current day and produces # of days a book is overdue
  public int daysOverdue(int that) {
    return (that - dayTaken) - 14;
  }


}

class RefBook extends AABook {
  
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }

//consumes the current day and produces # of days a book is overdue
  public int daysOverdue(int that) {
    return (that - dayTaken) - 2;
  }


}

class AudioBook extends AABook {
  String author;

  AudioBook(String title, int dayTaken, String author) {
    super(title, dayTaken);
    this.author = author;

  }

//consumes the current day and produces # of days a book is overdue
  public int daysOverdue(int that) {
    return (that - dayTaken) - 14;
  }


}
