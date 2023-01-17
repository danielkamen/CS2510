import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = 
      new ArrayList<Character>(Arrays.asList(
          'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
          'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
          't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code 
  // can assume this will always be a proper permutation of the alphabet field
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code 
  // for testing methods that encode and decode messages
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> alpcopy = new ArrayList<Character>(Arrays.asList(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
        't', 'u', 'v', 'w', 'x', 'y', 'z'));
    return this.initGrand(alpcopy);
  }

  // produce an encoded String from the given String
  String encode(String source) {
    return this.encodeHelp(source, "");
  }

  // produces an encoded string from the given string
  // ACCUMULATOR: and accumulates it into the encrypted message
  String encodeHelp(String source, String acc) {
    if (source.equals("")) {
      return acc;
    }
    else {
      Character first = source.charAt(0);
      String rest = source.substring(1); 
      /*
       * 
       * how do i write this:
       * need to use this code to assign alphabet letters to new value
       * must do that for each one
       * start with first letter, assign it its corresponding encoded char
       * and create new string w encoded char (concat prob)
       * 
       * keeping track of alphabet, permutated alphabet, and 'source'
       * given "abcde", "bcdea", and encode "cebad"
       * index of c = 2, -> "d"
       * index of e = 4 -> "a"
       * index of b = 1 -> c
       * ind of a = 0 -> b
       * ind of d = 3 -> e
       * new message = "dacbe"
       */
      Character encodedChar = this.code.get(this.alphabet.indexOf(first));
      acc = acc.concat(encodedChar.toString());
      return this.encodeHelp(rest, acc);
    }
  }

  // produce a decoded String from the given String
  String decode(String code) {
    return this.decodeHelp(code, "");
  }

  // ACCUMULATOR: produces a decoded string from the given
  // string and accumulates the message
  String decodeHelp(String word, String acc) {
    if (word.equals("")) {
      return acc;
    }
    else {
      Character first = word.charAt(0);
      String rest = word.substring(1);
      // just flips this.code and alphabet to reverse what was done in EncodedHelp
      Character decodedChar = this.alphabet.get(this.code.indexOf(first));
      acc = acc.concat(decodedChar.toString());
      return this.decodeHelp(rest, acc);
    }
  }

  // helps initEncoder by...
  // WISH LIST:
  // if checking base case for empty copy then ... WHAT
  // else assign a random character from copy to local var,
  // and then add it to the code, and remove it from copy,
  // and then recurse
  
  // tried using a forloop but got stack overflow
  // check permutation test file for final exam practice
  ArrayList<Character> initGrand(ArrayList<Character> alpcopy) {
    if (alpcopy.size() == 0) {
      return this.code;
    }
    else {
      int i = this.rand.nextInt(alpcopy.size());
      this.code.add(alpcopy.get(i));
      alpcopy.remove(i);
      return this.initGrand(alpcopy);
    }
  } 
}




class Examples {

  // example alphabets:
  // "can assume this will always be a proper permutation of the alphabet field"
  // for my sanity some alphabets will only include "abcdefg"
  ArrayList<Character> a1 = new ArrayList<Character>(Arrays.asList(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
      't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> a2 = new ArrayList<Character>(Arrays.asList(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',  
      't', 'u', 'v', 'w', 'x', 'y', 'z', 'k', 'l',
      'm', 'n', 'o', 'p', 'q', 'r', 's'));

  ArrayList<Character> a3 = new ArrayList<Character>(Arrays.asList(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));

  ArrayList<Character> a4 = new ArrayList<Character>(Arrays.asList(
      'f', 'g', 'h', 'a', 'b', 'c', 'd', 'e'));


  String normal1 = "simple";
  String normal2 = "cheffed";
  String normal3 = "";

  // different permutations assigned 1:1
  PermutationCode p1 = new PermutationCode(this.a1); 
  // normal alphabet


  // simple -> simple
  String encrypt1 = "simple";

  PermutationCode p2 = new PermutationCode(this.a2); 
  // wonky alphabet

  // simple -> livyue -> simple
  String encrypt2 = "livyue";

  PermutationCode p3 = new PermutationCode(this.a3); 
  // short and sweet 1

  // cheffed -> cheffed
  String encrypt3 = "cheffed";

  PermutationCode p4 = new PermutationCode(this.a4); 
  // short and sweet 2

  //cheffed -> hebccba -> cheffed
  String encrypt4 = "hebccba";



  boolean testEncode(Tester t) {
    return t.checkExpect(this.p2.encode(this.normal1), this.encrypt2)
        && t.checkExpect(this.p4.encode(this.normal2), this.encrypt4)
        && t.checkExpect(this.p3.encode(this.normal3), "");
  }

  boolean testEncodeHelp(Tester t) {
    return t.checkExpect(this.p2.encodeHelp(this.normal1, ""), this.encrypt2)
        && t.checkExpect(this.p4.encodeHelp(this.normal2, ""), this.encrypt4)
        && t.checkExpect(this.p3.encodeHelp(this.normal3, ""), "")
        && t.checkExpect(this.p2.encodeHelp(this.normal1, "m"), "mlivyue");

  }

  boolean testDecode(Tester t) {
    return t.checkExpect(this.p2.decode(this.encrypt2), this.normal1)
        && t.checkExpect(this.p4.decode(this.encrypt4), this.normal2)
        && t.checkExpect(this.p3.decode(this.normal3), "");

  }

  boolean testDecodeHelp(Tester t) {
    return t.checkExpect(this.p2.decodeHelp(this.encrypt2, ""), this.normal1)
        && t.checkExpect(this.p4.decodeHelp(this.encrypt4, ""), this.normal2)
        && t.checkExpect(this.p3.decodeHelp("", ""), "")
        && t.checkExpect(this.p2.decodeHelp(this.encrypt2, "m"), "msimple");

  }


  // how can i test init encoder??\
  // future me figure out the right way to run these
  boolean testInitEncoder(Tester t) {
    return t.checkExpect(this.p1.initEncoder(), this.p1.code)
        && t.checkExpect(this.p2.initEncoder(), this.p2.code);
  }
  
  // does the
  boolean testInitGrand(Tester t) {
    return t.checkExpect(this.p1.initEncoder(), this.p1.code)
        && t.checkExpect(this.p2.initEncoder(), this.p2.code);
  }
}