import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

class MapIter<T, U> implements Iterator<U> {
  Iterator<T> iter;
  Function<T, U> fun;

  MapIter(Iterator<T> iter, Function<T, U> fun) {
    this.iter = iter;
    this.fun = fun;
  }

  // are there any more items to produce?
  public boolean hasNext() {
    return this.iter.hasNext();
    // if the lower order iter has another item, this iter higher order iter does too
    }

  // produce the next item after applying the fucntion to it
  // EFFECT: iter is advanced because it invokes its next method
  public U next() {
    // check to see if we have another itme
    if (!this.hasNext()) {
      throw new NoSuchElementException("nope");
      
  }
    return this.fun.apply(this.iter.next());

}


// to make examples, 
  /*
   * what is that notation s->s.length()?
   * MapIter<String, Int> mapiter = new MapIter(new ArrayList<String>(Array.asList("a")).iterator(), s->s.lengh()));
   */




}