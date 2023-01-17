import java.util.Comparator;
import tester.*;

interface IABST<T> {

  //inserts an item into a binary search tree, and the item is placed in the approproate spot
  ABST<T> insert(T data); 

  // is the given item present in a binary search tree?
  boolean present(T item);

  // returns the left most item in a binary search tree?
  T getLeftmost();

  // helper for get left most, returns the left most item in a abst
  T getLeftmostHelp(T node);

  // returns an ABST with everything BUT the leftmost item
  ABST<T> getRight();

  // makes a new ABST without the given
  ABST<T> getRightHelp(T leftItem);

  // is this tree the same as that tree?
  boolean sameTree(ABST<T> tree);

  // is this tree the same as that leaf?
  boolean sameLeaf(Leaf<T> leaf);

  // is this tree the same as that node?
  boolean sameNode(Node<T> node);

  // is this tree's data the same as that tree's data?
  boolean sameData(ABST<T> othert);

  // is this tree's data the same as that node's data?
  boolean sameDataHelp(ABST<T> node);

  // creates a list out of the data of a given ABST
  IList<T> buildList();

  // creates a list out of the data of a given ABST and that list
  IList<T> buildListHelper(IList<T> list);
}

abstract class ABST<T> implements IABST<T> {
  Comparator<T> order;

  ABST(Comparator<T> order) {
    this.order = order;
  }

  /*
   * Fields:
   *  this.order    ... Comparator<T>
   *  
   * Methods:
   * N/A
   * 
   * Methods for Fields:
   *  this.order.compare(Book, Book)    ... int
   */

}

class Leaf<T> extends ABST<T> {
  Leaf(Comparator<T> order) {
    super(order);
  }

  /*
   * Fields:
   * N/A
   * 
   * Methods:
   *  this.insert(T)   ... ABST<T>
   *  this.present(T)   ... boolean
   *  this.getLeftmost()   ... T
   *  this.getLeftmostHelp(T)   ... T
   *  this.getRight()   ... ABST<T>
   *  this.getRightHelp(T)   ... ABST<T>
   *  this.sameTree(ABST<T>)   ... boolean
   *  this.sameLeaf(Leaf<T>)   ... boolean
   *  this.sameNode(Node<T>)   ... boolean
   *  this.sameData(ABST<T>)   ... boolean
   *  this.sameDataHelp(ABST<T>)   ... boolean
   *  this.buildList()   ... IList<T>
   *  this.buildListHelper(IList<T>)   ... IList<T>
   *  
   * Methods for Fields:
   * N/A
   */

  // inserts an item into a binary search tree, and the item is placed in the approproate spot
  public ABST<T> insert(T data) {
    return new Node<T>(this.order, data, new Leaf<T>(this.order), new Leaf<T>(this.order));
  }

  //is the given item present in a binary search tree?
  public boolean present(T item) {
    return false;
  }

  //returns the left most item in a binary search tree?
  public T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // helper for get left most, returns the left most item in a abst
  public T getLeftmostHelp(T node) {
    return node;
  }

  // returns an ABST with everything BUT the leftmost item
  public ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // makes a new ABST without the given item
  public ABST<T> getRightHelp(T leftItem) {
    return this;
  }

  // is this tree the same as that tree?
  public boolean sameTree(ABST<T> tree) {
    return tree.sameLeaf(this);
  }

  //is this node the same as that tree?
  public boolean sameLeaf(Leaf<T> leaf) {
    return true;
  }

  // is this tree's data the same as that tree's data?
  public boolean sameData(ABST<T> othert) {
    return othert.sameDataHelp(this)
        && this.sameDataHelp(othert);
  }

  // is this tree's data the same as that node's data?
  public boolean sameDataHelp(ABST<T> node) {
    return true;
  }

  // creates a list out of the data of a given ABST
  public IList<T> buildList() {
    return new MtList<T>();
  }

  // creates a list out of the data of a given ABST and that list
  public IList<T> buildListHelper(IList<T> list) {
    return list;
  }

  // is this leaf the same as a node?
  public boolean sameNode(Node<T> node) {
    return false;
  }


}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  /*
   * Fields:
   *  this.data   ... T
   *  this.left   ... ABST<T>
   *  this.right  ... ABST<T>
   *  
   * Methods:
   *  this.insert(T)   ... ABST<T>
   *  this.present(T)   ... boolean
   *  this.getLeftmost()  ... T
   *  this.getLeftmostHelp(T)   ... T
   *  this.getRight()   ... ABST<T>
   *  this.getrightHelp(T)   ... ABST<T>
   *  this.sameTree(ABST<T>)   ... boolean
   *  this.sameLeaf(Leaf<T>)   ... boolean
   *  this.sameNode(Node<T>)   ... boolean
   *  this.sameData(ABST<T>)   ... boolean
   *  this.sameDataHelp(ABST<T>)   ... boolean
   *  this.buildList()   ... IList<T>
   *  this.buildListHelper  (IList<T>)   ... IList<T>
   *  
   * Methods for Fields:
   *  this.left.insert(T)   ... ABST<T>
   *  this.left.present(T)   ... boolean
   *  this.left.getLeftmost()   ... T
   *  this.left.getLeftmostHelp(T)   ... T
   *  this.left.getRight()   ... ABST<T>
   *  this.left.getRightHelp(T)   ... ABST<T>
   *  this.left.sameTree(ABST<T>)   ... boolean
   *  this.left.sameLeaf(Leaf<T>)   ... boolean
   *  this.left.sameNode(Node<T>)   ... boolean
   *  this.left.sameData(ABST<T>)   ... boolean
   *  this.left.sameDataHelp(ABST<T>)   ... boolean
   *  this.left.buildList()   ... IList<T>
   *  this.left.buildListHelper(IList<T>)   ... IList<T>
   *  this.right.insert(T)   ... ABST<T>
   *  this.right.present(T)   ... boolean
   *  this.right.getLeftmost()   ... T
   *  this.right.getleftmostHelp(T)   ... T
   *  this.right.getRight()   ... ABST<T>
   *  this.right.getRightHelp(T)   ... ABST<T>
   *  this.right.sameTree(ABST<T>)   ... boolean
   *  this.right.sameLeaf(Leaf<T>)   ... boolean
   *  this.right.sameNode(Node<T>)   ... boolean
   *  this.right.sameData(ABST<T>)   ... boolean
   *  this.right.sameDataHelp(ABST<T>)   ... boolean
   *  this.right.buildList()   ... IList<T>
   *  this.right.buildListHelper(IList<T>)   ... IList<T>
   */

  // inserts an item into a binary search tree, and the item is placed in the approproate spot
  public ABST<T> insert(T data) {
    if (this.order.compare(this.data, data) == 0) { 

      return new Node<T>(this.order, this.data, this.left, 
          new Node<T>(this.order, data, new Leaf<T>(this.order), this.right));
    }

    else {
      if (this.order.compare(this.data, data) == 1) {
        return new Node<T>(this.order, this.data, this.left.insert(data), this.right);
      }
      else { 
        return new Node<T>(this.order, this.data, this.left, this.right.insert(data));
      }
    }
  }

  //is the given item present in a binary search tree?
  public boolean present(T item) {
    return (this.order.compare(this.data, item) == 0) 
        || this.left.present(item)
        || this.right.present(item);      
  }

  //returns the left most item in a binary search tree?
  public T getLeftmost() {
    return this.left.getLeftmostHelp(this.data);
  }

  // helper for get left most, returns the left most item in a abst
  public T getLeftmostHelp(T node) {
    return this.left.getLeftmostHelp(this.data);
  }

  // returns an ABST with everything BUT the leftmost item
  public ABST<T> getRight() {
    T leftItem = this.getLeftmost();

    return this.getRightHelp(leftItem);
  }


  // takes in the left most item, and if the first item is that item,
  // return left item, otherwise, make a node(order, data, this.left.getright, right)
  public ABST<T> getRightHelp(T leftItem) {
    if (this.order.compare(leftItem, this.data) == 0) {
      return this.right;
    }
    else {
      return new Node<T>(this.order, this.data, this.left.getRightHelp(leftItem), 
          this.right.getRightHelp(leftItem));
    }
  }

  // is this tree the same as that tree?
  public boolean sameTree(ABST<T> tree) {
    return tree.sameNode(this);
  }

  // is this node the same as that tree?  
  public boolean sameNode(Node<T> tree) {
    if (this.order.compare(tree.data, this.data) == 0) {
      return this.left.sameTree(tree.left) && this.right.sameTree(tree.right);
    }
    else {
      return false;
    }
  }

  // is the given tree's data the same as this trees data?
  public boolean sameData(ABST<T> othert) {
    return othert.sameDataHelp(this)
        && this.sameDataHelp(othert);
  }

  // is this tree's data the same as that node's data?
  public boolean sameDataHelp(ABST<T> node) {
    return node.present(this.data) 
        && this.left.sameDataHelp(node)
        && this.right.sameDataHelp(node);
  }

  // creates a list out of the data of a given ABST
  public IList<T> buildList() {
    return this.buildListHelper(new MtList<T>()).reverse();
  }

  // creates a list out of the data of a given ABST and that list
  public IList<T> buildListHelper(IList<T> list) {
    return this.right.buildListHelper(new ConsList<T>(this.data, this.left.buildListHelper(list)));
  }

  // is this node the same as that leaf?
  public boolean sameLeaf(Leaf<T> leaf) {
    return false;
  }
}


class BooksByTitle implements Comparator<Book> {

  /*
   * Fields:
   * N/A
   * 
   * Methods:
   *  --- compare(Book, Book) ... int ---
   *  
   * Methods for Fields:
   * N/A
   */

  public int compare(Book o1, Book o2) {
    return o1.title.compareTo(o2.title);
  }
}

class BooksByAuthor implements Comparator<Book> {
  /*
   * Fields:
   * N/A
   * 
   * Methods:
   *  --- compare(Book, Book) ... int ---
   *  
   * Methods for Fields:
   * N/A
   */
  public int compare(Book o1, Book o2) {
    return o1.author.compareTo(o2.author);
  }
}

class BooksByPrice implements Comparator<Book> {
  /*
   * Fields:
   * N/A
   * 
   * Methods:
   *  --- compare(Book, Book) ... int ---
   *  
   * Methods for Fields:
   * N/A
   */

  public int compare(Book o1, Book o2) {
    return o1.price - o2.price;
  }
}

class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  /*
   * Fields:
   *  --- this.title ... String ---
   *  --- this.author ... String ---
   *  --- this.price ... int ---
   *  
   * Methods:
   * N/A
   * 
   * Methods for Fields:
   * N/A
   */
}

interface IList<T> {

  // reverses the order of items in this list, keeping mtList at the end
  IList<T> reverse();

  // reverses the order of items in this list on top of that list
  IList<T> reverseHelp(IList<T> listData);
}

class MtList<T> implements IList<T> {
  MtList() {}

  /*
   * Fields:
   * N/A
   * 
   * Methods:
   *  --- this.reverse() ... IList<T> ---
   *  --- this.reverseHelper(IList<T> item) ... IList<T> ---
   *  
   * Methods for Fields:
   * N/A
   */

  // reverses the order of items in this list, keeping mtList at the end
  public IList<T> reverse() {
    return this;
  }

  // reverses the order of items in this list on top of that list
  public IList<T> reverseHelp(IList<T> listData) {
    return listData;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * Fields:
   *  --- this.first ... T ---
   *  --- this.rest ... IList<T> ---
   *  
   * Methods:
   *  --- this.reverse() ... IList<T> ---
   *  --- this.reverseHelper(IList<T>) ... IList<T> ---
   *  
   * Methods for Fields:
   * N/A
   */

  // reverses the order of items in this list, keeping mtList at the end
  public IList<T> reverse() {
    return this.rest.reverseHelp(new ConsList<T>(this.first, new MtList<T>()));
  }

  // reverses the order of items in this list on top of that list
  public IList<T> reverseHelp(IList<T> listData) {
    return this.rest.reverseHelp(new ConsList<T>(this.first, listData));
  }
}

class ExampleBook {

  Book b1 = new Book("Alphabet soup", "DiVinci", 15);
  Book b2 = new Book("Belarusian Breakfast", "Alfredo", 19);
  Book b3 = new Book("Coconuts", "Rick", 92);
  Book b4 = new Book("Dandelions", "Don Que Hote", 6);
  Book b5 = new Book("Eggs and Ham of the Green variety", "Dr Sues", 1000);
  Book b6 = new Book("Farquad: an Autobiography", "Lord Farquad", 123456789);



  // A NOTE ABOUT VARIABLE NAMING FOR SANITY:
  // the first character indicates it is a leaf, l, 
  //second character indicates what number leaf it is
  // and the third character indicates what binary search method it is.
  // 1 == Title, 2 == Author, 3 == Price

  // example leaf nodes for the BooksByTtile search pattern
  Comparator<Book> BooksByTitleExample = new BooksByTitle();
  Leaf<Book> l11 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l21 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l31 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l41 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l51 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l61 = new Leaf<Book>(BooksByTitleExample);
  Leaf<Book> l71 = new Leaf<Book>(BooksByTitleExample);

  //example node nodes for the BooksByTitle search pattern
  // this is in the form of:
  //             B3("C")
  //        /             \
  //       B2("B")      B4 ("D")
  //      / \            /   \
  //    B1("A") l11    L41    B6"F"
  //    /\                     /\
  // l21 L31                   l61 l71


  Node<Book> bookByTitleTree1 = new Node<Book>(BooksByTitleExample, b3, 
      new Node<Book>(BooksByTitleExample, b2, 
          new Node<Book>(BooksByTitleExample, b1, l21, l31), l11), 
      new Node<Book>(BooksByTitleExample, b4, l41,
          new Node<Book>(BooksByTitleExample, b5, l61, l71)));



  // example leaf nodes for the BooksByAuthor search pattern
  Comparator<Book> BooksByAuthorExample = new BooksByAuthor();
  Leaf<Book> l12 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l22 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l32 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l42 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l52 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l62 = new Leaf<Book>(BooksByAuthorExample);
  Leaf<Book> l72 = new Leaf<Book>(BooksByAuthorExample);

  //example node nodes for the BooksByAuthor search pattern
  // this is in the form of:
  //             B5 "DR"
  //        /             \
  //       B1"Divinci"       B6 "LORD"
  //      / \            /   \
  //    B2A   B4DonQ   l52      B3 "RICK"
  //    /\   /\                 /\
  // l12 L22  l32 l42       l62   l72


  Node<Book> bookByAuthorTree1 = new Node<Book>(BooksByAuthorExample, b5, 
      new Node<Book>(BooksByAuthorExample, b1, 
          new Node<Book>(BooksByAuthorExample, b2, l12, l22), 
          new Node<Book>(BooksByAuthorExample, b4, l32, l42)),
      new Node<Book>(BooksByAuthorExample, b6, l52,
          new Node<Book>(BooksByAuthorExample, b3, l62, l72)));

  Node<Book> bookByAuthorTree2 = new Node<Book>(BooksByAuthorExample, b5, 
      new Node<Book>(BooksByAuthorExample, b1, 
          new Node<Book>(BooksByAuthorExample, b2, l12, l22), 
          new Node<Book>(BooksByAuthorExample, b4, l32, l42)),
      new Node<Book>(BooksByAuthorExample, b6,
          new Node<Book>(BooksByAuthorExample, b3, l62, l72), l52));

  Node<Book> bookByAuthorTree3 = new Node<Book>(BooksByAuthorExample, b5, 
      new Node<Book>(BooksByAuthorExample, b1, 
          new Node<Book>(BooksByAuthorExample, b2, l12, l22), 
          new Node<Book>(BooksByAuthorExample, b4, l32, l42)),
      new Node<Book>(BooksByAuthorExample, b6,
          new Node<Book>(BooksByAuthorExample, new Book("a", "ahvjhvjuy", 1), l62, l72), l52));


  Comparator<Book> BooksByPriceExample = new BooksByPrice();
  Leaf<Book> l13 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l23 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l33 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l43 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l53 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l63 = new Leaf<Book>(BooksByPriceExample);
  Leaf<Book> l73 = new Leaf<Book>(BooksByPriceExample);

  //example node nodes for the BooksByPrice search pattern
  // this is in the form of:
  //              B2
  //        /             \
  //       B1             B5 1000
  //      / \            /   \
  //    B4   L33        B3    B6
  //    /\             /\       /\
  // l31 L32        L34 L35  l63   l73


  Node<Book> bookByPriceTree1 =  new Node<Book>(BooksByPriceExample, b2, 
      new Node<Book>(BooksByPriceExample, b1, 
          new Node<Book>(BooksByPriceExample, b4, l13, l23), l33), 
      new Node<Book>(BooksByPriceExample, b5,
          new Node<Book>(BooksByPriceExample, b3, l43, l53),
          new Node<Book>(BooksByPriceExample, b6, l63, l73)));

  Node<Book> bookByPriceTree2 =  new Node<Book>(BooksByPriceExample, b2, 
      new Leaf<Book>(BooksByPriceExample), 
      new Node<Book>(BooksByPriceExample, b5,
          new Node<Book>(BooksByPriceExample, b3, l43, l53),
          new Node<Book>(BooksByPriceExample, b6, l63, l73)));



  MtList<Book> mtBook = new MtList<Book>();
  ConsList<Book> bookListPrice1 = new ConsList<Book>(
      this.b4, new ConsList<Book>(this.b1,
          new ConsList<Book>(this.b2,
              new ConsList<Book>(this.b3,
                  new ConsList<Book>(this.b5,
                      new ConsList<Book>(this.b6, this.mtBook))))));

  ConsList<Book> bookListAuthor1 = new ConsList<Book>(
      this.b2, new ConsList<Book>(this.b1,
          new ConsList<Book>(this.b4,
              new ConsList<Book>(this.b5,
                  new ConsList<Book>(this.b6,
                      new ConsList<Book>(this.b3, this.mtBook))))));

  boolean testInsert(Tester t) {
    return t.checkExpect(this.bookByPriceTree1.insert(new Book("a", "a", 999)), 
        new Node<Book>(BooksByPriceExample, b2, 
            new Node<Book>(BooksByPriceExample, b1, 
                new Node<Book>(BooksByPriceExample, b4, l13, l23), l33), 
            new Node<Book>(BooksByPriceExample, b5,
                new Node<Book>(BooksByPriceExample, b3, l43, 
                    new Node<Book>(BooksByPriceExample, new Book("a", "a", 999),
                        new Leaf<Book>(BooksByPriceExample), new Leaf<Book>(BooksByPriceExample))),
                new Node<Book>(BooksByPriceExample, b6, l63, l73))))
        && t.checkExpect(this.bookByAuthorTree1.insert(new Book("a", "Dr Sues", 999)), 
            new Node<Book>(BooksByAuthorExample, b5, 
                new Node<Book>(BooksByAuthorExample, b1, 
                    new Node<Book>(BooksByAuthorExample, b2, l12, l22), 
                    new Node<Book>(BooksByAuthorExample, b4, l32, l42)),
                new Node<Book>(BooksByAuthorExample, new Book("a", "Dr Sues", 999),
                    new Leaf<Book>(BooksByAuthorExample),
                    new Node<Book>(BooksByAuthorExample, b6, l52,
                        new Node<Book>(BooksByAuthorExample, b3, l62, l72)))))
        && t.checkExpect(this.l63.insert(new Book("a", "a", 9)), 
            new Node<Book>(BooksByPriceExample, new Book("a", "a", 9),
                new Leaf<Book>(BooksByPriceExample), new Leaf<Book>(BooksByPriceExample)))
        && t.checkExpect(this.l13.insert(this.b1), 
            new Node<Book>(BooksByPriceExample, this.b1, new Leaf<Book>(BooksByPriceExample), 
                new Leaf<Book>(BooksByPriceExample)));
  }

  boolean testPresent(Tester t) {
    return t.checkExpect(this.bookByPriceTree1.present(new Book("a", "a", 999)), false)
        && t.checkExpect(this.bookByPriceTree1.present(b3), true)
        && t.checkExpect(this.bookByPriceTree1.present(b4), true)
        && t.checkExpect(this.l13.present(b3), false);
  }

  boolean testGetLeftmost(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.getLeftmost(), this.b2)
        && t.checkExpect(this.bookByPriceTree1.getLeftmost(), this.b4)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.l31, "getLeftmost")
        && t.checkExpect(this.bookByPriceTree2.getLeftmost(),this.b2);
  }

  boolean testGetLeftmostHelp(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.getLeftmostHelp(b4), this.b2)
        && t.checkExpect(this.l31.getLeftmostHelp(this.b2), this.b2)
        && t.checkExpect(this.bookByPriceTree1.getLeftmostHelp(this.b6), this.b4)
        && t.checkExpect(this.bookByTitleTree1.getLeftmostHelp(new Book("a", "a", 1)), this.b1);
  }

  boolean testGetRight(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.getRight(), 
        new Node<Book>(BooksByAuthorExample, b5, 
            new Node<Book>(BooksByAuthorExample, b1, new Leaf<Book>(BooksByAuthorExample), 
                new Node<Book>(BooksByAuthorExample, b4, l32, l42)),
            new Node<Book>(BooksByAuthorExample, b6, l52,
                new Node<Book>(BooksByAuthorExample, b3, l62, l72))))
        && t.checkExpect(this.bookByPriceTree1.getRight(),
            new Node<Book>(BooksByPriceExample, b2, 
                new Node<Book>(BooksByPriceExample, b1, 
                    new Leaf<Book>(BooksByPriceExample), l33), 
                new Node<Book>(BooksByPriceExample, b5,
                    new Node<Book>(BooksByPriceExample, b3, l43, l53),
                    new Node<Book>(BooksByPriceExample, b6, l63, l73))))
        && t.checkException(new RuntimeException("No right of an empty tree"), this.l13, "getRight")
        &&  t.checkException(new RuntimeException("No right of an empty tree"), 
            this.l62, "getRight");
  }

  boolean testGetRightHelp(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.getRightHelp(this.b5), 
        new Node<Book>(BooksByAuthorExample, b6, l52,
            new Node<Book>(BooksByAuthorExample, b3, l62, l72)))
        && t.checkExpect(this.bookByPriceTree1.getRightHelp(this.b1), 
            new Node<Book>(BooksByPriceExample, b2, l33, 
                new Node<Book>(BooksByPriceExample, b5,
                    new Node<Book>(BooksByPriceExample, b3, l43, l53),
                    new Node<Book>(BooksByPriceExample, b6, l63, l73))))
        && t.checkExpect(this.l13.getRightHelp(this.b1), this.l13)
        && t.checkExpect(this.l62.getRightHelp(this.b6), this.l62);
  }

  boolean testSametree(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.sameTree(this.bookByAuthorTree1), true)
        && t.checkExpect(this.bookByPriceTree1.sameTree(this.bookByPriceTree2), false)
        && t.checkExpect(this.l31.sameTree(bookByTitleTree1), false)
        && t.checkExpect(this.bookByPriceTree1.sameTree(this.l33), false)
        && t.checkExpect(this.l31.sameTree(this.l31), true);
  }

  boolean testSameNode(Tester t) {
    return t.checkExpect(this.bookByPriceTree1.sameNode(this.bookByPriceTree2), false)
        && t.checkExpect(this.bookByTitleTree1.sameNode(this.bookByTitleTree1), true);
  }

  boolean testSameLeaf(Tester t) {
    return t.checkExpect(this.l31.sameLeaf(this.l31), true)
        && t.checkExpect(this.l31.sameLeaf(this.l42), true);
  }

  boolean testSameData(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.sameData(this.bookByAuthorTree1), true)
        && t.checkExpect(this.bookByAuthorTree1.sameData(this.bookByAuthorTree3), false)
        && t.checkExpect(this.l31.sameData(bookByAuthorTree3), false)
        && t.checkExpect(this.l31.sameData(this.l31), true);
  }

  boolean testSameDataHelp(Tester t) {
    return t.checkExpect(this.bookByAuthorTree2.sameDataHelp(
        new Node<Book>(BooksByAuthorExample, new Book("A", "A,", 1),
            new Leaf<Book>(BooksByAuthorExample),  new Leaf<Book>(BooksByAuthorExample))), false)
        && t.checkExpect(this.bookByAuthorTree1.sameDataHelp(this.bookByAuthorTree1), true)
        && t.checkExpect(this.bookByAuthorTree1.sameDataHelp(this.bookByAuthorTree2), true)
        && t.checkExpect(this.l31.sameDataHelp(this.bookByAuthorTree1), true)
        && t.checkExpect(this.l61.sameDataHelp(this.bookByAuthorTree3), true);
  }

  boolean testBuildList(Tester t) {
    return t.checkExpect(this.bookByAuthorTree1.buildList(),
        this.bookListAuthor1)
        && t.checkExpect(this.bookByPriceTree1.buildList(), this.bookListPrice1)
        && t.checkExpect(this.l31.buildList(), new MtList<Book>()); 
  }

  boolean testBuildListHelper(Tester t) {
    return t.checkExpect(this.l31.buildListHelper(this.bookListPrice1), this.bookListPrice1)
        && t.checkExpect(new Node<Book>(BooksByPriceExample, 
            this.b1, l12, l13).buildListHelper(this.bookListPrice1),
            new ConsList<Book>(this.b1, this.bookListPrice1));
  }

  boolean testReverse(Tester t) {
    return t.checkExpect(this.bookListPrice1.reverse(), 
        new ConsList<Book>(
            this.b6, new ConsList<Book>(this.b5,
                new ConsList<Book>(this.b3,
                    new ConsList<Book>(this.b2,
                        new ConsList<Book>(this.b1,
                            new ConsList<Book>(this.b4, this.mtBook)))))))
        && t.checkExpect(this.bookListAuthor1.reverse(),
            new ConsList<Book>(
                this.b3, new ConsList<Book>(this.b6,
                    new ConsList<Book>(this.b5,
                        new ConsList<Book>(this.b4,
                            new ConsList<Book>(this.b1,
                                new ConsList<Book>(this.b2, this.mtBook)))))))
        && t.checkExpect(this.mtBook.reverse(), this.mtBook);
  }


  boolean testReverseHelp(Tester t) {
    return t.checkExpect(this.bookListPrice1.reverseHelp(new ConsList<Book>(this.b1, this.mtBook)),
        new ConsList<Book>(
            this.b6, new ConsList<Book>(this.b5,
                new ConsList<Book>(this.b3,
                    new ConsList<Book>(this.b2,
                        new ConsList<Book>(this.b1,
                            new ConsList<Book>(this.b4, 
                                new ConsList<Book>(this.b1, this.mtBook))))))))
        && t.checkExpect(this.mtBook.reverseHelp(this.bookListAuthor1), this.bookListAuthor1);

  }

  boolean testCompare(Tester t) {
    return t.checkExpect(this.BooksByAuthorExample.compare(this.b2, this.b1), -3)
        && t.checkExpect(this.BooksByAuthorExample.compare(this.b1, this.b2), 3)
        && t.checkExpect(this.BooksByAuthorExample.compare(this.b2, this.b2), 0)
        && t.checkExpect(this.BooksByPriceExample.compare(this.b2, this.b1), 4)
        && t.checkExpect(this.BooksByPriceExample.compare(this.b1, this.b2), -4)
        && t.checkExpect(this.BooksByPriceExample.compare(this.b2, this.b2), 0)
        && t.checkExpect(this.BooksByTitleExample.compare(this.b2, this.b1), 1)
        && t.checkExpect(this.BooksByTitleExample.compare(this.b1, this.b2), -1)
        && t.checkExpect(this.BooksByTitleExample.compare(this.b2, this.b2), 0);
  }
}
