import java.util.function.Predicate;

import tester.Tester;


abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // counts how  many nodes are in the list
  // returns the size of an empty list
  int sizeHelp(int acc) {
    return acc;
  }

  // updates the links of the items in a list and returns the data of the item removed
  // abstracted and will do something if given a node
  //trys to removes a sentinel but isnt strong enough
  T removeFromListHelp() {
    throw new RuntimeException("RAHHH WHY U TRY TO REMOVE ME!?");
  }

  // finds the first node that passes the predicate,
  // if none pass ( and gets back to sentinel), returns sentinel
  ANode<T> findHelp(Predicate<T> pred) {
    return this;
  }

  // EFFECTL: removes the given node from the list, and if its a sentinel,
  // does nothing
  abstract void removeNodeHelp();

  void addAtAbs(ANode<T> prev, ANode<T> next) {
    this.prev = prev;
    this.next = next;
    prev.next = this;
    next.prev = this;
  }
}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  // convience constructor
  // is used in add head and add tail
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    this.next = next;
    this.prev = prev;
    if (this.next == null || this.prev == null) {
      throw new IllegalArgumentException("cant make an ANode = null");
    }
    else { 
      this.prev.next = this;
      this.next.prev = this;
    }
  }

  // counts the size of this node list
  int sizeHelp(int acc) {
    return this.next.sizeHelp(acc + 1);
  }

  // abstraction of ...HeadHelp and ...TailHelp
  // 'removes' the node by updating the links to go around it
  // would be removed and implemented under removeNodeHelp()
  // but since I abstracted it from two required methods i thought
  // itd be good to keep it
  T removeFromListHelp() {
    this.prev.next = this.next;
    this.next.prev = this.prev;
    return this.data;
  }

  // finds the node that passes the given predicate
  ANode<T> findHelp(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(pred);
    }
  }

  // EFFECT: removes the given node from the list
  void removeNodeHelp() {
    this.removeFromListHelp();
  }
   
}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  /*
  // EFFECT: places the given item into the front of the list
  // and updates the links - prev stays the same
  void addAtHead(T item) {
    // Utilizies the convience constructor to update the links
    this.next = new Node<T>(item, this.next, this);
  }

  // EFFECT: places the given item into the end of the list
  // and updates the links - next stays the same
  void addAtTail(T item) {
    this.prev = new Node<T>(item, this, this.prev);
  }
  */

  // redundant code and no effect is done, but since I want this method
  // to do nothing if given an empty list, I made the method do something that
  // wont change the code
  void removeNodeHelp() {
    this.next = next;

  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  // convienence constructor
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // returns the size of a Deque, excluding the header
  int size() {
    return this.header.next.sizeHelp(0);
  }

  // EFFECT: places the given item into the front of this list
  // must go through header
  void addAtHead(T item) {
    Node<T> HH = new Node<T>(item);
    HH.addAtAbs(this.header, this.header.next);
  }

  // EFFECT: places the given item into the end of this list
  void addAtTail(T item) {
    Node<T> HH = new Node<T>(item);
    HH.addAtAbs(this.header.prev, this.header);
  }

  // EFFECT: removes the head of the list
  T removeFromHead() {
    return this.header.next.removeFromListHelp();
  }

  //EFFECT: removes the tail of the list
  T removeFromTail() {
    return this.header.prev.removeFromListHelp();
  }

  // finds the first node that passes the given predicate
  ANode<T> find(Predicate<T> pred) {
    return this.header.next.findHelp(pred);
  }

  // EFFECT: removes the given node from the deque
  void removeNode(ANode<T> anode) {
    anode.removeNodeHelp();
  }
  
  // appends a given Deque to the end of this Deque
  void appendDeque(Deque<T> that) {
    int size = that.size();
    for (int i = 0; i < size; i ++) {
      this.addAtTail(that.removeFromHead());
    }
  }
}

// represents the predicate passed into find
class ABCNode<T> implements Predicate<T> {

  // does this data equal abc?
  public boolean test(T t) {
    return t.equals("abc");
  }

}

//represents the predicate passed into find
class LMNNode<T> implements Predicate<T> {

  // does this data equal abc?
  public boolean test(T t) {
    return t.equals("lmn");
  }

}

//represents the predicate passed into find
class XXXNode<T> implements Predicate<T> {

  //does this data equal abc?
  public boolean test(T t) {
    return t.equals("xxx");
  }

}

class ExamplesDeque {

  Node<String> n1;
  Node<String> n2;
  Node<String> n3;
  Node<String> n4;
  Node<String> n5;
  Node<String> n6;
  Node<String> n7;
  Node<String> n8;

  Node<String> n9;
  
  Sentinel<String> s1;
  Sentinel<String> s2; 
  Sentinel<String> s3;

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<String> deque3;


  void initData() {

    // use first constructor to initialize this.data
    this.n1 = new Node<String>("abc");
    this.n2 = new Node<String>("bcd");
    this.n3 = new Node<String>("cde");
    this.n4 = new Node<String>("def");
    this.n5 = new Node<String>("lmn");
    this.n6 = new Node<String>("hij");
    this.n7 = new Node<String>("sqr");
    this.n8 = new Node<String>("tuv");


    // adds new nodes and connects them with the convienence constructor
    // first example
    this.s1 = new Sentinel<String>();

    this.n1 = new Node<String>("abc", this.n2, this.s1);
    this.n2 = new Node<String>("bcd", this.n3, this.n1);
    this.n3 = new Node<String>("cde", this.n4, this.n2);
    this.n4 = new Node<String>("def", this.s1, this.n3);

    this.deque1 = new Deque<String>(this.s1);


    // second example
    this.s2 = new Sentinel<String>();

    this.n5 = new Node<String>("lmn", this.n6, this.s2);
    this.n6 = new Node<String>("hij", this.n7, this.n5);
    this.n7 = new Node<String>("qrs", this.n8, this.n6);
    this.n8 = new Node<String>("tuv", this.s2, this.n7);

    this.deque2 = new Deque<String>(this.s2);

    // third example && also the empty list
    this.s3 = new Sentinel<String>();

    this.deque3 = new Deque<String>();
    
  }

  
  void testAppend(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.size(), 4); //1
    this.deque1.appendDeque(this.deque2); //2
    t.checkExpect(this.deque1.size(), 8); //3
    this.initData();
    this.deque1.appendDeque(this.deque1);
    t.checkExpect(this.deque1.size(), 4);
    this.initData();
    this.deque1.appendDeque(this.deque3);
    t.checkExpect(this.deque1.size(), 4);
  }
  void testSize(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.size(), 4);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 0); 
  }


  boolean testSizeHelp(Tester t) {
    this.initData();
    return t.checkExpect(this.s1.sizeHelp(0), 0)
        && t.checkExpect(this.n1.sizeHelp(0), 4)
        && t.checkExpect(this.n2.sizeHelp(1), 4)
        && t.checkExpect(this.s3.sizeHelp(0), 0);
  }

  boolean testRemoveFromListHelp(Tester t) {
    this.initData();
    return t.checkException(new RuntimeException("RAHHH WHY U TRY TO REMOVE ME!?"),
        this.s1, "removeFromListHelp")
        && t.checkException(new RuntimeException("RAHHH WHY U TRY TO REMOVE ME!?"),
            this.s3, "removeFromListHelp")
        && t.checkExpect(this.n3.removeFromListHelp(), "cde")
        && t.checkExpect(this.n1.removeFromListHelp(), "abc");
  }

  // uses the class ABC LNN and XXXNode for these tests
  boolean testFind(Tester t) {
    this.initData();
    return t.checkExpect(this.deque1.find(new ABCNode<String>()), this.n1)
        && t.checkExpect(this.deque1.find(new XXXNode<String>()), this.s1)
        && t.checkExpect(this.deque2.find(new LMNNode<String>()), this.n5)
        && t.checkExpect(this.deque3.find(new ABCNode<String>()), this.s3);
  }

  boolean testFindHelp(Tester t) {
    this.initData();
    return t.checkExpect(this.deque1.find(new ABCNode<String>()), this.n1)
        && t.checkExpect(this.deque2.find(new LMNNode<String>()), this.n5)
        && t.checkExpect(this.deque3.find(new ABCNode<String>()), this.s3)
        && t.checkExpect(this.deque2.find(new ABCNode<String>()), this.s2);
  }

  void testRemoveNode(Tester t) {
    this.initData();
    this.deque1.removeNode(this.n1);
    t.checkExpect(this.deque1.header.next, this.n2);

    this.initData();
    this.deque1.removeNode(this.n4);
    t.checkExpect(this.deque1.header.prev, this.n3);

    this.initData();
    this.deque1.removeNode(this.s1);
    t.checkExpect(this.deque1.header, this.s1);
    this.deque2.removeNode(this.s2);
    t.checkExpect(this.deque2.header, this.s2);
  }

  void testRemoveNodeHelp(Tester t) {
    this.initData();
    this.n1.removeNodeHelp();
    t.checkExpect(this.deque1.header.next, this.n2);

    this.initData();
    this.n4.removeNodeHelp();
    t.checkExpect(this.deque1.header.prev, this.n3);

    this.initData();
    this.s1.removeNodeHelp();
    t.checkExpect(this.deque1.header, this.s1);
    this.s2.removeNodeHelp();
    t.checkExpect(this.deque2.header, this.s2);
  }


  void testAddAtHead(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.header.next, this.n1);
    this.deque1.addAtHead("xyz");
    t.checkExpect(this.deque1.header.next, new Node<String>("xyz", this.n1, this.s1));
    t.checkExpect(this.deque2.header.next, this.n5);
    this.deque2.addAtHead("hey");
    t.checkExpect(this.deque2.header.next, new Node<String>("hey", this.n5, this.s2));
  }

  void testAddAtTail(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.header.prev, this.n4);
    this.deque1.addAtTail("xyz");
    t.checkExpect(this.deque1.header.prev, new Node<String>("xyz", this.s1, this.n4));
    t.checkExpect(this.deque2.header.prev, this.n8);
    this.deque2.addAtTail("hey");
    t.checkExpect(this.deque2.header.prev, new Node<String>("hey", this.s2, this.n8));
  }

  void testRemoveFromHead(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.header.next, this.n1);
    this.deque1.removeFromHead();
    t.checkExpect(this.deque1.header.next, this.n2);
    t.checkExpect(this.deque2.header.next, this.n5);
    this.deque2.removeFromHead();
    t.checkExpect(this.deque2.header.next, this.n6);

    t.checkExpect(this.deque3.header.next, this.s3);   
    t.checkException(new RuntimeException("RAHHH WHY U TRY TO REMOVE ME!?"),
        this.deque3, "removeFromHead");
  }

  void testRemoveFromTail(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.header.prev, this.n4);
    this.deque1.removeFromTail();
    t.checkExpect(this.deque1.header.prev, this.n3);
    t.checkExpect(this.deque2.header.prev, this.n8);
    this.deque2.removeFromTail();
    t.checkExpect(this.deque2.header.prev, this.n7);
    t.checkExpect(this.deque3.header.prev, this.s3);   
    t.checkException(new RuntimeException("RAHHH WHY U TRY TO REMOVE ME!?"),
        this.deque3, "removeFromTail");
  }


  boolean testTest(Tester t) {
    return t.checkExpect(new ABCNode<String>().test("abc"), true)
        && t.checkExpect(new ABCNode<String>().test("ttt"), false)
        && t.checkExpect(new LMNNode<String>().test("abc"), false)
        && t.checkExpect(new LMNNode<String>().test("lmn"), true)
        && t.checkExpect(new XXXNode<String>().test("xxx"), true)
        && t.checkExpect(new XXXNode<String>().test("abc"), false);
  }
}