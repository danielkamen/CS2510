import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;


interface ILoString {

}

interface IList<T> {
  IList<T> filter(Predicate<T> pred);
  <U> IList<U> map(Function<T,U> converter);
  <U> U fold(BiFunction<T,U,U> converter,U initial);  
}

class MtList<T> implements IList<T> {

  MtList() {}

  @Override
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    return new MtList<T>();
  }

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }

  @Override
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return initial;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  @Override
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  @Override
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
}

class ExamplesLists{

  IList<String> mtstring =  new MtList<String>();
  IList<String> month = new ConsList<String>("January", new ConsList<String>("Feburary",
      new ConsList<String>("March",
          new ConsList<String>("April",
              new ConsList<String>("May",
                  new ConsList<String>("June",
                      new ConsList<String>("July",
                          new ConsList<String>("August",
                              new ConsList<String>("September",
                                  new ConsList<String>("October",
                                      new ConsList<String>("November",
                                          new ConsList<String>("December", mtstring))))))))))));







  boolean testFilter(Tester t) {
    return t.checkExpect(this.month.filter(s->s.startsWith("T")), this.mtstring)
        && t.checkExpect(this.month.filter(s->s.endsWith("er")),
            new ConsList<String>("September",
                new ConsList<String>("October",
                    new ConsList<String>("November",
                        new ConsList<String>("December", mtstring)))));      

  }

  boolean testMap(Tester t) {
    return t.checkExpect(this.mtstring.map(s->s.substring(0,3)), this.mtstring) &&
        t.checkExpect(this.month.map(s->s.substring(0,3)),  new ConsList<String>("Jan", 
            new ConsList<String>("Feb",
                new ConsList<String>("Mar",
                    new ConsList<String>("Apr",
                        new ConsList<String>("May",
                            new ConsList<String>("Jun",
                                new ConsList<String>("Jul",
                                    new ConsList<String>("Aug",
                                        new ConsList<String>("Sep",
                                            new ConsList<String>("Oct",
                                                new ConsList<String>("Nov",
                                                    new ConsList<String>("Dec", mtstring)))))))))))));
  }
}



