interface IDoc {
  // makes a bib out of just books
  ILoString sources();
}

class Wiki implements IDoc {
  String author;
  String title;
  String url;
  ILoDoc bibliography;
  
  
  Wiki(String author, String title, String url, ILoDoc bibliography) {
    this.author = author;
    this.title = title;
    this.url = url;
    this.bibliography = bibliography;
  }


  @Override
  public ILoString sources() {
    return this.bibliography.sourcesFromList();
    
  }
}

class Book implements IDoc {
  String author;
  String title;
  String publisher;
  ILoDoc bibliography;
  
  Book(String author, String title, String publisher, ILoDoc bibliography) {
    this.author = author;
    this.title = title;
    this.publisher = publisher;
    this.bibliography = bibliography;
  }

  @Override
  public ILoString sources() {
    return new ConsLoString(this.author.format() + this.title, this.bibliography.sourcesFromList());
    
  }
}

interface ILoString {
  
}


class MtLoDocs implements ILoDoc{
  MtLoDocs() {}
  
}

class ConsLoDocs implements ILoDoc {
  
}