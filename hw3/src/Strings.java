import tester.*;

// to represent a list of Strings
interface ILoString {

  // combine all Strings in this list into one
  String combine();

  // produces a ILoString where any instance of the first string is replaced with the second string
  ILoString findAndReplace(String fst, String snd);

  // is any item repeated more than once?
  boolean anyDupes();

  // collects the first item of a ILoString and then compares it to the second item of the list
  boolean anyDupesAcc(String acc);

  // returns a sorted list
  ILoString sort();

  // compares first item of ILoString to rest of ILoString
  ILoString sortAcc(String acc);

  //determines whether a list is alphabetically sorted
  boolean isSorted();

  // accumulator function for isSorted
  boolean isSortedAcc(String acc);

  // interleaves the items in ILoString1 with items in ILoString2
  ILoString interleave(ILoString givenList);

  // determines if this list contains pairs of idential strings (1+2, 3+4, 5+6 etc)
  boolean isDoubledList();

  // accumulates the first string of a list
  boolean isDoubledListAcc(String acc);

  // concatenates every item in a list, but in REVERSE (WOAHHHH)
  String reverseConcat();

  // accumulator for the reverse concat function (WILD)
  String reverseConcatAcc(String acc);

  // merges two already sorted lists
  ILoString merge(ILoString givenList);

  //determines if list is a Palindrome
  boolean isPalindromeList();

  //concatenates every item of list
  String concatList();

  PairOfLists unzip();

  PairOfLists addToFirst1(String first);
}

class PairOfLists{
  ILoString first, second;
  PairOfLists(ILoString first, ILoString second) {
    this.first = first;
    this.second = second;
  }
  // Produces a new pair of lists, with the given String added to
  // the front of the first list of this pair
  PairOfLists addToFirst(String first) {
    return new PairOfLists(new ConsLoString(first, this.first), this.second);
  }
  // Produces a new pair of lists, with the given String added to
  // the front of the second list of this pair
  PairOfLists addToSecond(String second) {
    return new PairOfLists(this.first, new ConsLoString(second, this.second));
  }
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString(){}

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // produces a ILoString where any Instance of the first string is replaces with the second string
  public ILoString findAndReplace(String fst, String snd) {
    return new MtLoString();

  }

  // is any item in an ILoString repeated  more than once?
  public boolean anyDupes() {
    return false;

  }

  // accumulates the first item of a ILoString and then compares it to the second item of the list
  public boolean anyDupesAcc(String acc) {
    return false;
  }

  // sorts a list alphabetically
  public ILoString sort() {
    return this;
  }

  // accumulator: for the mt case, this returns nothing
  public ILoString sortAcc(String acc) {
    return new ConsLoString(acc, this);
  }

  // interleaves the items between the given and this ILoString
  public ILoString interleave(ILoString givenList) {
    return givenList;
  }

  // are the 1+2, 3+4, 5+6th items of a list identical? 
  //(for ex, a ILoString containing "Apple" "WOOOOOOOOOOOO" "p" "p" would retrun true)
  public boolean isDoubledList() {
    return false;
  }

  // accumulates the first and second item of an ILoString
  public boolean isDoubledListAcc(String acc) {
    return false;
  }

  // reveres an ILoString, and concatenates all the items together
  public String reverseConcat() {
    return "";
  }

  // accumulator for reverseConcat. will collect all the items in an ILoString from back to front,
  // concatelizing them as it runs
  public String reverseConcatAcc(String acc) {
    return acc;

  }


  public boolean isSorted() {
    return true;
  }

  //
  public boolean isSortedAcc(String acc) {
    return true;
  }

  // merges a given list with this list
  public ILoString merge(ILoString givenList) {
    return givenList;
  }


  public boolean isPalindromeList() {
    return true;
  }

  public String concatList() {
    return "";
  }

  @Override
  public PairOfLists unzip() {
    return new PairOfLists(this, this);
  }

  @Override
  public PairOfLists addToFirst1(String first) {
    // TODO Auto-generated method stub
    return null;
  }


}



// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;  
  }

  /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString

     METHODS
     ... this.combine() ...     -- String

     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareTo(String) ...     -- int
     ... this.rest.combine() ...              -- String

   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  //produces a ILoString where any Instance of the first string is replaces with the second string
  public ILoString findAndReplace(String fst, String snd) {
    if (this.first.equals(fst)) {
      return new ConsLoString(snd, this.rest.findAndReplace(fst, snd)); 
    }
    else 
    { return new ConsLoString(this.first, this.rest.findAndReplace(fst, snd)); 
    }

  }


  //is any item in an ILoString repeated  more than once?
  public boolean anyDupes() {
    { return this.rest.anyDupesAcc(this.first) || this.rest.anyDupes(); }
  }

  // accumulates the first item of a ILoString and then compares it to the second item of the list
  public boolean anyDupesAcc(String acc) {
    if (this.first.equals(acc)) {
      return true;
    }
    else { 
      return this.rest.anyDupesAcc(acc); }
  }

  //sorts a list alphabetically
  public ILoString sort() {
    return this.rest.sort().sortAcc(this.first);
  }

  //accumulator: will compare the order of the given item to the first item, 
  // and then puts ONLY the items that were compared
  // in correct order
  public ILoString sortAcc(String acc) {
    if (this.first.toLowerCase().compareTo(acc.toLowerCase()) < 0) {
      { return new ConsLoString(this.first, this.rest.sortAcc(acc)); }
    }
    else { 
      return new ConsLoString(acc, this); }
  }

  // interleaves the items between the given and this ILoString
  public ILoString interleave(ILoString givenList) {
    return new ConsLoString(this.first, givenList.interleave(this.rest));
  }

  // are the 1+2, 3+4, 5+6th items of a list identical? (for ex, a ILoString containing "Apple" "WOOOOOOOOOOOO" "p" "p" would retrun true, since the 3rd and 4th items are identical)
  public boolean isDoubledList() {
    return this.rest.isDoubledListAcc(this.first); 
  }

  // accumulates the first and second item of an ILoString, and checks to see if theyre the same
  public boolean isDoubledListAcc(String acc) {
    if (this.first.equals(acc)) { return true; 
    } 
    else  {return this.rest.isDoubledList(); }
  }


  // concatenate the strings in this non - empty list in reverse order
  public String reverseConcat() {
    return this.reverseConcatAcc("");
  }

  // helps to reverse concatenate the strings in this list
  // accumulator : keeps track of the reversed concatenated string so far
  public String reverseConcatAcc(String acc) {
    return this.rest.reverseConcatAcc(this.first + acc );
  }



  public ILoString merge(ILoString givenList) {
    return this.interleave(givenList).sort();
  }


  public boolean isSorted() {
    return this.rest.isSortedAcc(this.first) && this.rest.isSorted();
  }


  public boolean isSortedAcc(String acc) {
    if (this.first.toLowerCase().compareTo(acc.toLowerCase()) > 0) {
      { return this.rest.isSortedAcc(acc); }
    }
    else { return false; }
  }


  public boolean isPalindromeList() {
    return this.reverseConcat().equals(this.concatList());
  }


  public String concatList() {
    return this.first + this.rest.concatList();
  }

  public PairOfLists unzip() {
    return this.rest.addToFirst1(this.first).addToSecond1().unzip();
  }

  @Override
  public PairOfLists addToFirst1(String first) {
    return new PairOfLists(new ConsLoString(first, new MtLoString()), new MtLoString());
  }
  
  public PairOfLists addToSecond1() {
    return new ConsLoString(this.first, new MtLoString());
  }

}



// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mt = new MtLoString();
  ILoString mary = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("little ",
                  new ConsLoString("lamb. ", new MtLoString())))));
  ILoString maryduplicate = new ConsLoString("Mary ", this.mary);
  ILoString littleduplicate = new ConsLoString("little ", this.mary);
  ILoString littlethrice = new ConsLoString("little ", this.littleduplicate);
  ILoString maryandlittle = new ConsLoString("Mary ", this.littleduplicate);
  ILoString marysorted = new ConsLoString("a ",
      new ConsLoString("had ", 
          new ConsLoString("lamb. ",
              new ConsLoString("little ",
                  new ConsLoString("Mary ", this.mt)))));
  ILoString littlesunsorted = new ConsLoString("Mary ", 
      new ConsLoString("had ", 
          new ConsLoString( "littles ",
              new ConsLoString("a ",
                  new ConsLoString("little ",
                      new ConsLoString("lamb. ", this.mt))))));

  ILoString littlessorted = new ConsLoString("a ", 
      new ConsLoString("had ", 
          new ConsLoString( "lamb. ",
              new ConsLoString("little ",
                  new ConsLoString("littles ",
                      new ConsLoString("Mary ", this.mt))))));

  ILoString firsttwounsort = new ConsLoString("a ", 
      new ConsLoString("had ", 
          new ConsLoString( "Mary ",
              new ConsLoString("littles ",
                  new ConsLoString("little ",
                      new ConsLoString("lamb. ", this.mt))))));

  ILoString alphabet1 = new ConsLoString("a",
      new ConsLoString("b", 
          new ConsLoString("c",
              new ConsLoString("d", this.mt))));
  ILoString alphabet2 = new ConsLoString("b",
      new ConsLoString("e",
          new ConsLoString("f",
              new ConsLoString("i", this.mt))));
  ILoString alphabet3 = new ConsLoString("a",
      new ConsLoString("d",
          new ConsLoString("k", this.mt)));

  ILoString alphabet23sorted = 
      new ConsLoString("a",
          new ConsLoString("b",
              new ConsLoString("d",
                  new ConsLoString("e",
                      new ConsLoString("f",
                          new ConsLoString("i", 
                              new ConsLoString("k",
                                  this.mt)))))));
  ILoString alphabet4 = new ConsLoString("a",
      new ConsLoString("b", 
          new ConsLoString("b",
              new ConsLoString("e",
                  new ConsLoString("c",
                      new ConsLoString("f",
                          new ConsLoString("d",
                              new ConsLoString("i", this.mt))))))));
  ILoString alphabet4sorted = new ConsLoString("a",
      new ConsLoString("b", 
          new ConsLoString("b",
              new ConsLoString("c",
                  new ConsLoString("d",
                      new ConsLoString("e",
                          new ConsLoString("f",
                              new ConsLoString("i", this.mt))))))));
  ILoString alphabet5 = new ConsLoString("a",
      new ConsLoString("a", 
          new ConsLoString("b",
              new ConsLoString("d",
                  new ConsLoString("c",
                      new ConsLoString("k",
                          new ConsLoString("d", this.mt)))))));
  ILoString alphabet6 = new ConsLoString("a",
      new ConsLoString("a", 
          new ConsLoString("d",
              new ConsLoString("b",
                  new ConsLoString("k",
                      new ConsLoString("c",
                          new ConsLoString("d", this.mt)))))));
  ILoString alphabet7 = new ConsLoString("a",
      new ConsLoString("n", 
          new ConsLoString("b",
              new ConsLoString("b",
                  new ConsLoString("k",
                      new ConsLoString("c",
                          new ConsLoString("d", this.mt)))))));

  ILoString palindrome1 = new ConsLoString("a",
      new ConsLoString("b",
          new ConsLoString("b",
              new ConsLoString("a", this.mt))));

  ILoString palindrome2 = new ConsLoString("a",
      new ConsLoString("b",
          new ConsLoString("c",
              new ConsLoString("b",
                  new ConsLoString("a", this.mt)))));

  ILoString palindrome3 = new ConsLoString("mary ",
      new ConsLoString("little ",
          new ConsLoString("mary ", this.mt)));

  ILoString notpalindrome = new ConsLoString("a",
      new ConsLoString("b",
          new ConsLoString("c",
              new ConsLoString("b",
                  new ConsLoString("f", this.mt)))));

  String maryreverse = new String("lamb. little a had Mary ");
  String littleduplicatereverse = new String("lamb. little a had Mary little ");
  String alphabet1reverse = new String("dcba");



  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return 
        t.checkExpect(this.mary.combine(), "Mary had a little lamb. ");
  }

  boolean testFindAndReplace(Tester t) {
    return t.checkExpect(this.mt.findAndReplace("little ", "gigantic "), this.mt)
        && t.checkExpect(this.mary.findAndReplace("a ", "fifteen "),
            new ConsLoString("Mary ",
                new ConsLoString("had ",
                    new ConsLoString("fifteen ",
                        new ConsLoString("little ",
                            new ConsLoString("lamb. ", new MtLoString()))))))
        && t.checkExpect(this.littlethrice.findAndReplace("little ", "big "),
            new ConsLoString("big ",
                new ConsLoString("big ",
                    new ConsLoString("Mary ",
                        new ConsLoString("had ",
                            new ConsLoString("a ",
                                new ConsLoString("big ",
                                    new ConsLoString("lamb. ", new MtLoString()))))))));
  } 

  boolean testAnyDupes(Tester t) {
    return t.checkExpect(this.mt.anyDupes(), false)
        && t.checkExpect(this.maryduplicate.anyDupes(), true)
        && t.checkExpect(this.littleduplicate.anyDupes(), true)
        && t.checkExpect(this.mary.anyDupes(), false)
        && t.checkExpect(this.littlethrice.anyDupes(), true)
        && t.checkExpect(this.maryandlittle.anyDupes(), true);

  }

  boolean testSort(Tester t) {
    return t.checkExpect(this.mt.sort(), this.mt)
        && t.checkExpect(this.mary.sort(), this.marysorted)
        && t.checkExpect(this.littlesunsorted.sort(), this.littlessorted)
        && t.checkExpect(this.firsttwounsort.sort(), this.littlessorted);
  }

  boolean testInterleave(Tester t) {
    return t.checkExpect(this.alphabet1.interleave(this.alphabet2), this.alphabet4)
        && t.checkExpect(this.mt.interleave(this.mt), this.mt)
        && t.checkExpect(this.mt.interleave(this.alphabet4), this.alphabet4)
        && t.checkExpect(this.alphabet1.interleave(this.alphabet3), this.alphabet5)
        && t.checkExpect(this.alphabet3.interleave(this.alphabet1), this.alphabet6);
  }

  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.maryandlittle.isDoubledList(), false)
        && t.checkExpect(this.littleduplicate.isDoubledList(), false)
        && t.checkExpect(this.alphabet7.isDoubledList(), true)
        && t.checkExpect(this.maryduplicate.isDoubledList(), true)
        && t.checkExpect(this.mt.isDoubledList(), false);

  }

  boolean testisSorted(Tester t) {
    return t.checkExpect(this.mt.isSorted(), true)
        && t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.marysorted.isSorted(), true)
        && t.checkExpect(this.littlesunsorted.isSorted(), false)
        && t.checkExpect(this.littlessorted.isSorted(), true);
  }

  boolean testMerge(Tester t) {
    return t.checkExpect(this.mt.merge(this.mary), this.mary)
        && t.checkExpect(this.alphabet1.merge(this.alphabet2),alphabet4sorted)
        && t.checkExpect(this.alphabet2.merge(this.alphabet3), alphabet23sorted);
  }

  boolean testReverseConcat(Tester t) {
    return t.checkExpect(this.mt.reverseConcat(), "")
        && t.checkExpect(this.mary.reverseConcat(), this.maryreverse)
        && t.checkExpect(this.littleduplicate.reverseConcat(), this.littleduplicatereverse)
        && t.checkExpect(this.alphabet1.reverseConcat(), this.alphabet1reverse);
  }

  boolean testisPalindromeList(Tester t) {
    return t.checkExpect(this.mt.isPalindromeList(), true)
        && t.checkExpect(this.palindrome1.isPalindromeList(), true)
        && t.checkExpect(this.palindrome2.isPalindromeList(), true)
        && t.checkExpect(this.palindrome3.isPalindromeList(), true)
        && t.checkExpect(this.notpalindrome.isPalindromeList(), false);
  }

  boolean testConcatList(Tester t) {
    return t.checkExpect(this.mt.concatList(), "")
        && t.checkExpect(this.notpalindrome.concatList(), "abcbf");
  }

  boolean testAnyDupesAcc(Tester t) {
    return t.checkExpect(this.mt.anyDupesAcc("apple"),false)
        && t.checkExpect(this.mary.anyDupesAcc("Mary "), true)
        && t.checkExpect(this.mary.anyDupesAcc("apple "), false);
  }

  boolean testSortAcc(Tester t) {
    return t.checkExpect(this.mt.sortAcc("apple"), new ConsLoString("apple", this.mt))
        && t.checkExpect(this.mary.sortAcc("aa"), new ConsLoString("aa", this.mary));

  }

  boolean testIsDoubledAcc(Tester t) {
    return t.checkExpect(this.mt.isDoubledListAcc("hello"), false)
        && t.checkExpect(this.mary.isDoubledListAcc("Mary "), true)
        && t.checkExpect(this.mary.isDoubledListAcc("apple "), false);

  }

  boolean testReverseConcatAcc(Tester t) {
    return t.checkExpect(this.mt.reverseConcatAcc("this"), "this")
        && t.checkExpect(this.mary.reverseConcatAcc("little "), littleduplicatereverse);

  }

  boolean testIsSortedAcc(Tester t) {
    return t.checkExpect(this.mt.isSortedAcc("apple"), true)
        && t.checkExpect(this.mary.isSortedAcc("apple"), false)
        && t.checkExpect(this.marysorted.isSortedAcc(""), true);
  }

}
