import tester.*;

import java.util.function.BiFunction;
import java.util.function.Function;

// represents an Arith tree
interface IArith {
  // accepts a function to be applied to this shape
  <R> R accept(IArithVisitor<R> fun);
}

// represents a constant
class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  // will return the result of the visitor applied to this
  public <R> R accept(IArithVisitor<R> fun) {
    return fun.visitConst(this);
  }
}

// represents a unary formula within an IArith
class UnaryFormula implements IArith {
  Function<Double, Double> func;
  String name;
  IArith child;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.func = func;
    this.name = name;
    this.child = child;
  }

  // will return the result of the visitor applied to this
  public <R> R accept(IArithVisitor<R> fun) {
    return fun.visitUnary(this);
  }

}

// represents a binary formula within an IArith
class BinaryFormula implements IArith {
  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;

  BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  // will return the result of the visitor applied to this
  public <R> R accept(IArithVisitor<R> fun) {
    return fun.visitBinary(this);
  }

}

// visitor interface which will be applied to IArith returning generic R
interface IArithVisitor<R> {


  R visitBinary(BinaryFormula b);

  R visitUnary(UnaryFormula u);

  R visitConst(Const c);
}


// the visitor class that evaluates the IArith with its given functions
class EvalVisitor implements IArithVisitor<Double> {

  // Double is the object class that wraps all doubles in it
  // double is a primitive data type within the Double class

  // visits the binaryformula class and evaluates the tree
  public Double visitBinary(BinaryFormula b) {
    return b.func.apply(b.left.accept(new EvalVisitor()), b.right.accept(new EvalVisitor()));
  }

  // visits the UnaryFormula class and evaluates the tree
  public Double visitUnary(UnaryFormula u) {
    return u.func.apply(u.child.accept(new EvalVisitor()));
  }

  // visits the Const class and returns the num
  public Double visitConst(Const c) {
    return c.num;
  }

  // applies the IArith to new EvalVisitor
  public Double apply(IArith c) {
    return c.accept(this);
  }
}

// visits an IArith and ret7urns the Dr Racket (gross) expression of the evaluation
class PrintVisitor implements IArithVisitor<String> {

  // visits the BinaryFormula class and evalutes the tree into a string expression
  public String visitBinary(BinaryFormula b) {
    return "(" + b.name + " " + b.left.accept(new PrintVisitor())
      + " " + b.right.accept(new PrintVisitor()) + ")";
  }

  // visits the UnaryFormula class and evalutes the tree into a string expression
  public String visitUnary(UnaryFormula u) {
    return "(" + u.name + " " + u.child.accept(new PrintVisitor()) + ")";
  }

  // visits the Const class and evalutes the constant into a string expression
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  // applies the IArith to new EvalVisitor
  public String apply(IArith c) {
    return c.accept(this);
  }


}

//visits an IArith and doubles every value in the tree
class DoublerVisitor implements IArithVisitor<IArith> {

  //visits the BinaryFormula class and returns a new tree with every value doubled
  public IArith visitBinary(BinaryFormula b) {
    return 
        new BinaryFormula(b.func, b.name, b.left.accept(new DoublerVisitor()),
            b.right.accept(new DoublerVisitor()));
  }

  //visits the UnaryFormula class and returns a new tree with every value doubled
  public IArith visitUnary(UnaryFormula u) {
    return new UnaryFormula(u.func, u.name, u.child.accept(new DoublerVisitor()));
  }

  // doubles a constant when visiting the Const class
  public IArith visitConst(Const c) {
    return new Const(c.num * 2);
  }

  // applies the IArith to new EvalVisitor
  public IArith apply(IArith c) {
    return c.accept(this);
  }
}

// visitor class which answers: is every value in this IArith >= 0?
// and is every value evaluated in an IArith >= 0?
class NoNegativeResults implements IArithVisitor<Boolean> {

  // is every value in this IArith >= 0
  public Boolean visitBinary(BinaryFormula b) {
    return b.left.accept(new NoNegativeResults())
        && b.right.accept(new NoNegativeResults())
        && new Const(b.left.accept(new EvalVisitor())).accept(new NoNegativeResults())
        && new Const(b.right.accept(new EvalVisitor())).accept(new NoNegativeResults())
        && new Const(b.func.apply(b.left.accept(new EvalVisitor()),
            b.right.accept(new EvalVisitor()))).accept(new NoNegativeResults());
  }

  // is every value in this IArith >= 0
  public Boolean visitUnary(UnaryFormula u) {
    return u.child.accept(new NoNegativeResults())
        && new Const(u.child.accept(new EvalVisitor())).accept(new NoNegativeResults());
  }

  // is every value in this IArith >= 0
  public Boolean visitConst(Const c) {
    return c.num >= 0;
  }

  // applies the IArith to new EvalVisitor
  public Boolean apply(IArith c) {
    return c.accept(this);
  }
}

class Minus implements BiFunction<Double, Double, Double> {

  // subtracts the two values
  public Double apply(Double t, Double u) {
    return t - u;
  }
}

class Plus implements BiFunction<Double, Double, Double> {

  // adds the two values
  public Double apply(Double t, Double u) {
    return t + u;
  }
}

class Mult implements BiFunction<Double, Double, Double> {

  // multiples two doubles
  public Double apply(Double t, Double u) {
    return t * u;
  }
}

class Div implements BiFunction<Double, Double, Double> {

  // divides two doubles
  public Double apply(Double t, Double u) {
    return t / u;
  }
}

class Neg implements Function<Double, Double> {

  // negates the given double
  public Double apply(Double t) {
    return t * -1;
  }
}

class Sqr implements Function<Double, Double> {

  // squares the given value
  public Double apply(Double t) {
    return t * t;
  }
}






class Examples {
  // const
  Const c1 = new Const(-1.0);
  Const c2 = new Const(3.0);
  Const c3 = new Const(6.0);
  Const c4 = new Const(9.0);

  // bf
  BinaryFormula b1 = new BinaryFormula(new Mult(), "mult", this.c1, this.c3);
  BinaryFormula b2 = new BinaryFormula(new Div(), "div", this.c3, this.c2);
  BinaryFormula b3 = new BinaryFormula(new Plus(), "plus", this.c3, this.c2);
  BinaryFormula b4 = new BinaryFormula(new Minus(), "minus", this.c3, this.c2);


  // doubled b1 and b2
  BinaryFormula b1double = new BinaryFormula(new Mult(), "mult",  new Const(-2.0), new Const(12.0));
  BinaryFormula b2double = new BinaryFormula(new Div(), "div",  new Const(12.0), new Const(6.0));
  
  
  // doubled u1 and u2
  UnaryFormula u1double = new UnaryFormula(new Sqr(), "sqr", new Const(6.0));
  UnaryFormula u2double = new UnaryFormula(new Neg(), "neg", new Const(18.0));
  
  // nested case
  BinaryFormula b5 = new BinaryFormula(new Minus(), "minus", this.c3, this.b3);

  // uf
  UnaryFormula u1 = new UnaryFormula(new Sqr(), "sqr", this.c2);
  UnaryFormula u2 = new UnaryFormula(new Neg(), "neg", this.c4);
  UnaryFormula u3 = new UnaryFormula(new Neg(), "neg", this.c1);


  boolean testApply(Tester t) {
    return t.checkExpect(new Mult().apply(7.0, 3.0), 21.0)
        && t.checkExpect(new Minus().apply(6.0, 3.0), 3.0)
        && t.checkExpect(new Plus().apply(6.0, 3.0), 9.0)
        && t.checkExpect(new Div().apply(6.0, 3.0), 2.0);
  }

  boolean testAccept(Tester t) {
    return t.checkExpect(this.c4.accept(new EvalVisitor()), 9.0)
        && t.checkExpect(this.c4.accept(new PrintVisitor()), "9.0")
        && t.checkExpect(this.c4.accept(new DoublerVisitor()), new Const(18.0))
        && t.checkExpect(this.c4.accept(new NoNegativeResults()), true)
        && t.checkExpect(this.c1.accept(new NoNegativeResults()), false)
        && t.checkExpect(this.u1.accept(new EvalVisitor()), 9.0)
        && t.checkExpect(this.u2.accept(new PrintVisitor()), "(neg 9.0)")
        && t.checkExpect(this.u2.accept(new DoublerVisitor()),
            new UnaryFormula(new Neg(), "neg", new Const(18.0)))
        && t.checkExpect(this.u2.accept(new NoNegativeResults()), true)
        && t.checkExpect(this.u3.accept(new NoNegativeResults()), false)
        && t.checkExpect(this.b1.accept(new EvalVisitor()), -6.0)
        && t.checkExpect(this.b2.accept(new PrintVisitor()), "(div 6.0 3.0)")
        && t.checkExpect(this.b3.accept(new DoublerVisitor()), 
            new BinaryFormula(new Plus(), "plus", new Const(12.0), new Const(6.0)))
        && t.checkExpect(this.b4.accept(new NoNegativeResults()), true)
        && t.checkExpect(this.b1.accept(new NoNegativeResults()), false);
  }


  boolean testVisitBinary(Tester t) {
    return t.checkExpect(new EvalVisitor().visitBinary(this.b1), -6.0)
        && t.checkExpect(new EvalVisitor().visitBinary(this.b2), 2.0)
        && t.checkExpect(new PrintVisitor().visitBinary(this.b1), "(mult -1.0 6.0)")
        && t.checkExpect(new PrintVisitor().visitBinary(this.b2), "(div 6.0 3.0)")
        && t.checkExpect(new DoublerVisitor().visitBinary(this.b1), this.b1double)
        && t.checkExpect(new DoublerVisitor().visitBinary(this.b2), this.b2double)
        && t.checkExpect(new NoNegativeResults().visitBinary(this.b1), false)
        && t.checkExpect(new NoNegativeResults().visitBinary(this.b2), true);
  }

  boolean testVisitUnary(Tester t) {
    return t.checkExpect(new EvalVisitor().visitUnary(this.u1), 9.0)
        && t.checkExpect(new EvalVisitor().visitUnary(this.u2), -9.0)
        && t.checkExpect(new PrintVisitor().visitUnary(this.u1), "(sqr 3.0)")
        && t.checkExpect(new PrintVisitor().visitUnary(this.u2), "(neg 9.0)")
        && t.checkExpect(new DoublerVisitor().visitUnary(this.u1), this.u1double)
        && t.checkExpect(new DoublerVisitor().visitUnary(this.u2), this.u2double)
        && t.checkExpect(new NoNegativeResults().visitUnary(this.u1), true)
        && t.checkExpect(new NoNegativeResults().visitUnary(this.u2), true)
        && t.checkExpect(new NoNegativeResults().visitUnary(this.u3), false);
  }
  
  boolean testVisitConst(Tester t) {
    return t.checkExpect(new EvalVisitor().visitConst(this.c1), -1.0)
        && t.checkExpect(new EvalVisitor().visitConst(this.c2), 3.0)
        && t.checkExpect(new PrintVisitor().visitConst(this.c1), "-1.0")
        && t.checkExpect(new PrintVisitor().visitConst(this.c2), "3.0")
        && t.checkExpect(new DoublerVisitor().visitConst(this.c1), new Const(-2.0))
        && t.checkExpect(new DoublerVisitor().visitConst(this.c2), new Const(6.0))
        && t.checkExpect(new NoNegativeResults().visitConst(this.c1), false)
        && t.checkExpect(new NoNegativeResults().visitConst(this.c2), true);
  }
}


