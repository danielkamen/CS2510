import java.util.ArrayList;
import java.util.Iterator;
import tester.*;
/*
interface Iterator<T> {

  // Does this sequence have at least one more value?
  boolean hasNext();

  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  T next();

  // Returns an iterator over this collection
  Iterator<T> iterator();
}
 */

class ListOfLists<T> implements Iterable<T> {
  ArrayList<ArrayList<T>> lol;

  ListOfLists(ArrayList<ArrayList<T>> lol) {
    this.lol = lol;
  }

  public ListOfLists() {
    this.lol = new ArrayList<ArrayList<T>>();
  }

  void addNewList() {
    this.lol.add(new ArrayList<T>());
  }

  @Override 
  public Iterator<T> iterator() {
    return new ArrayListIterator<T>(this);
  }

  int size() {
    return this.lol.size();
  }

  void add(int index,T object) {
    if (this.lol.size() - 1 < index) {
      throw new IndexOutOfBoundsException("RRAAAAAHHHHHHHHH YOU MESSED UP");
    }
    else {
      ArrayList<T> whoopty = this.lol.get(index);
      whoopty.add(object);
    }
  }

  ArrayList<T> get(int index) {
    if (this.lol.size() - 1 < index) {
      throw new IndexOutOfBoundsException("come on we talked ab this");
    }
    else {
      return this.lol.get(index);
    }
  }

}

class ArrayListIterator<T> implements Iterator<T> {
  ListOfLists<T> items;
  int currentIndex;
  Iterator<T> it;

  ArrayListIterator(ListOfLists<T> items) {
    this.items = items;
    this.currentIndex = 0;

    if (this.currentIndex < this.items.size()) {
      this.it = this.items.get(currentIndex).iterator();
    }
    else {
      this.it = new ArrayList<T>().iterator();
    }
  }

  // are there any more items to produce
  public boolean hasNext() {
    return this.it.hasNext() || this.currentIndex < this.items.size() - 1;
  }

  // produces the next item from the current iterator
  // EFFECT: 
  public T next() {
    if (!this.hasNext()) {
      this.currentIndex += 1;
      if (this.currentIndex < this.items.size()) {
        this.it = this.items.get(currentIndex).iterator();
      }
      else {
        this.it = new ArrayList<T>().iterator();
      }
    }

    return this.it.next();
  }
}



class Examples {


  void testListOfLists(Tester t) {
    ListOfLists<Integer> lol = new ListOfLists<Integer>();
    //add 3 lists
    lol.addNewList();
    lol.addNewList();
    lol.addNewList();

    //add elements 1,2,3 in first list
    lol.add(0,1);
    lol.add(0,2);
    lol.add(0,3);

    //add elements 4,5,6 in second list
    lol.add(1,4);
    lol.add(1,5);
    lol.add(1,6);

    //add elements 7,8,9 in third list
    lol.add(2,7);
    lol.add(2,8);
    lol.add(2,9);

    //iterator should return elements in order 1,2,3,4,5,6,7,8,9
    int number = 1;
    for (Integer num:lol) {
      t.checkExpect(num,number);
      number = number + 1;
    }
  }
}

