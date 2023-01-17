import tester.Tester;
import java.awt.Color;


// interface represents blobs of paint
interface IPaint {
  // computes the final color of this paint
  Color getFinalColor();

  // computes the number of solid paints involved in producing the final color
  int countPaints();

  // counts how many times a paint was mixed
  int countMixes();

  //counts the depth of the total number of mixtures of a paint
  int formulaDepth();

  // inverts darken and brighten methods
  IPaint invert();

  // produces a string represetning the contents of this paint
  String mixingFormula(int depth);





}

class Solid implements IPaint {
  String name;
  Color color;

  Solid(String name, Color color) {
    this.name = name;
    this.color = color;
  }
  /* TEMPLATE:
   * fields:
   * this.name  ... String
   * this.color ... Color
   * 
   * methods:
   * this.getFinalColor() .... Color
   * this.countPaints() .... int
   * this.countMixes() ... int
   * this.formulaDepth() ... int
   * this.invert() ... IPaint
   * this.mixingFormula() ... String
   * 
   * Methods for fields:
   * this.color.getFinalColor() ... Color
   * 
   */



  // returns the final color of a solid paint
  public Color getFinalColor() {
    return this.color;
  }


  // counts the amount of whole colors are added to a combo 
  // for ex, the most can be 5. RGB + black and white
  public int countPaints() {
    return 1; 
  }


  // counts how many times a paint was mixed
  public int countMixes() {
    return 0;
  }



  // depth of a solid color
  public int formulaDepth() {
    return 0;
  }


  //inverts the colors mixture
  public IPaint invert() {
    return new Solid(this.name, this.color);
  }


  // returns the name of a paint at depth 0
  public String mixingFormula(int depth) {
    return this.name;
  }

}

class Combo implements IPaint {
  String name;
  IMixture operation;

  Combo(String name, IMixture operation) {
    this.name = name;
    this.operation = operation;

  }
  /* TEMPLATE:
   * fields:
   * this.name  ... String
   * this.operation .... IMixture
   * 
   * methods:
   * this.getFinalColor() .... Color
   * this.countPaints() ... int
   * this.countMixes() ... int
   * this.formulaDepth() ... int
   * this.invert() ... IMixture
   * this.mixingFormula(int depth) ... String
   * 
   * Methods for fields:
   * this.operation.getFinalColorHelp() ... Color
   * this.operation.countPaintsHelp() .... int
   * this.operation.countMixesHelp() ... int
   * this.operation.formulaDepthHelp() ... int
   * this.operation.invertHelp() ... IMixutre
   * this.operation.mixingFormulaHelp(int depth) ... String
   */


  // returns the final color of a combo
  public Color getFinalColor() {
    return this.operation.getFinalColorHelp();
  }


  // counts number of paints used
  public int countPaints() {
    return this.operation.countPaintsHelp();
  }


  // counts how many times a paint was mixed
  public int countMixes() {
    return this.operation.countMixesHelp();
  }

  // gets the depth of a mixtures mixture
  public int formulaDepth() {
    return this.operation.formulaDepthHelp();
  }

  // inverts the paints brighten and darken mixtures
  public IPaint invert() {
    return new Combo(this.name, this.operation.invertHelp());
  }

  // produces a string representing the contents of a paint
  public String mixingFormula(int depth) {
    if (depth <= 0) {
      return this.name;
    } else {
      return this.operation.mixingFormulaHelp(depth);
    }
  }
}




// interface represents mixtures of paint colors
interface IMixture {
  // will compute the operation needed to change the color
  Color getFinalColorHelp();

  // will count how many paints are used
  int countPaintsHelp();

  // will count how many times a paint was mixed
  int countMixesHelp();

  // counts the depth of a paints formula
  int formulaDepthHelp();

  // inverts the darken and brighten formulas
  IMixture invertHelp();

  // collects the mixture types and returns them as a string
  String mixingFormulaHelp(int depth);

}

class Darken implements IMixture {
  IPaint paint;

  Darken(IPaint paint) {
    this.paint = paint;  
  }
  /*
   * template:
   * Fields:
   * this.paint1 ... IPaint
   * 
   * methods:
   * this.getFinalColorHelp() ... Color
   * this.countPaintsHelp() ... int
   * this.countMixesHelp() ... int
   * this.formulaDepth() ... int
   * this.invertHelp() ... IMixture
   * 
   * methods for fields:
   * this.paint.getFinalColor() ... Color
   * this.paint.countPaints()  ....  int
   * this.paint.countMixes() .... int
   * this.paint.formulaDepth() ... int
   * this.paint.invert() ... IMixture
   * this.paint.mixingFormula(int depth) ... String
   * 
   */


  // will add half the RGB values of this.color with black
  public Color getFinalColorHelp() {
    return this.paint.getFinalColor().darker();
  }


  // will count how many paints are used 
  public int countPaintsHelp() {
    return 1 + this.paint.countPaints();
  }


  // counts how many times a paint was mixed
  public int countMixesHelp() {
    return 1 + this.paint.countMixes();
  }


  // will count the depth of the paints formula
  public int formulaDepthHelp() {
    return 1 + this.paint.formulaDepth();

  }

  // inverts the methods of a darker mixture to brighter
  public IMixture invertHelp() {
    return new Brighten(this.paint);

  }

  // concatenates the methods of a mixture together
  public String mixingFormulaHelp(int depth) {
    depth = depth - 1;
    return "darken(" + this.paint.mixingFormula(depth) + ")";

  }
}


class Brighten implements IMixture {
  IPaint paint;

  Brighten(IPaint paint) {
    this.paint = paint;
  }
  /*
   * template:
   * Fields:
   * this.paint1 ... IPaint
   * 
   * methods:
   * this.getFinalColorHelp() ... Color
   * this.countPaintsHelp() ... int
   * this.countMixesHelp() ... int
   * this.formulaDepth() ... int
   * this.invertHelp() ... IMixture
   * 
   * methods for fields:
   * this.paint.getFinalColor() ... Color
   * this.paint.countPaints()  ....  int
   * this.paint.countMixes() .... int
   * this.paint.formulaDepth() ... int
   * this.paint.invert() ... IMixture
   * this.paint.mixingFormula(int depth) ... String
   * 
   */


  // brightens the color 
  public Color getFinalColorHelp() {
    return this.paint.getFinalColor().brighter();  
  }


  // counts how many paints are used
  public int countPaintsHelp() {
    return 1 + this.paint.countPaints();
  }


  // counts how many times a paint was mixed
  public int countMixesHelp() {
    return 1 + this.paint.countMixes();
  }


  // counts the depth of the paints formula
  public int formulaDepthHelp() {
    return 1 + this.paint.formulaDepth();
  }

  // inverts a brighter method to darker
  public IMixture invertHelp() {
    return new Darken(this.paint);
  }

  // concatenates the methods together
  public String mixingFormulaHelp(int depth) {
    depth = depth - 1;
    return "brighten(" + this.paint.mixingFormula(depth) + ")";
  }
}


class Blend implements IMixture {
  IPaint paint1;
  IPaint paint2;

  Blend(IPaint color1, IPaint color2) {
    this.paint1 = color1;
    this.paint2 = color2;
  }
  /* 
   * template:
   * fields:
   * this.paint1 ... IPaint
   * this.paint2 ... IPaint
   * 
   * methods:
   * this.getFinalColorHelp() ... Color
   * this.countPaintsHelp() ... int
   * this.countMixesHelp() ... int
   * this.formulaDepthHelp() ... int
   * this.invertHelp() ... IMixture
   * this.mixingFormulaHelp(int depth) ... String
   * 
   * methods for fields:
   * this.paint1.getFinalColor() ... color
   * this.paint1.countPaints() ... int
   * this.paint1.countMixes() ... int
   * this.paint1.formulaDepth() ... int
   * this.paint1.invert() ... IMixture
   * this.paint1.mixingFormula(int depth) ... String 
   * this.paint1.getFinalColor() ... color
   * this.paint2.countPaints() ... int
   * this.paint2.countMixes() ... int
   * this.paint2.formulaDepth() ... int
   * this.paint2.invert() ... IMixture
   * this.paint2.mixingFormula(int depth) ... String*/

  // combines the two colors 
  public Color getFinalColorHelp() {
    return new Color(Math.round(.5 * this.paint1.getFinalColor().getRed()
        + .5 * this.paint2.getFinalColor().getRed()),
        Math.round(.5 * this.paint1.getFinalColor().getGreen()
            + .5 * this.paint2.getFinalColor().getGreen()),
        Math.round(.5 * this.paint1.getFinalColor().getBlue()
            + .5 * this.paint2.getFinalColor().getBlue()));

  }


  // counts the number of paints used
  public int countPaintsHelp() {
    return this.paint1.countPaints() + this.paint2.countPaints();
  }

  // // counts how many times a paint was mixed
  public int countMixesHelp() {
    return 1 + this.paint1.countMixes() + this.paint2.countMixes();
  }

  // finds the max formula depth within a paint
  public int formulaDepthHelp() {
    return Math.max((1 + this.paint1.formulaDepth()), (1 + this.paint2.formulaDepth()));
  }

  // will invert the color 
  public IMixture invertHelp() {
    return new Blend(this.paint1, this.paint2);
  }

  // concatinates the strings of a combo together
  public String mixingFormulaHelp(int depth) {
    depth = depth - 1;
    return "blend(" + this.paint1.mixingFormula(depth)
      + ", " + this.paint2.mixingFormula(depth) + ")";
  }
}


class ExamplesPaint {
  IPaint black11 = new Solid("black", new Color(0, 0, 0));
  IPaint white11 = new Solid("white", new Color(255, 255, 255));
  IPaint red = new Solid("red", new Color(255, 0, 0));
  IPaint green = new Solid("green", new Color(0, 255, 0));
  IPaint blue = new Solid("blue", new Color(0, 0, 255));
  IPaint purple = new Combo("purple", new Blend(red, blue)); 
  IPaint darkPurple = new Combo("dark purple", new Darken(purple));
  IPaint khaki = new Combo("khaki", new Blend(red, green));
  IPaint yellow = new Combo("yellow", new Brighten(khaki));
  IPaint mauve = new Combo("mauve", new Blend(purple, khaki));
  IPaint pink = new Combo("pink", new Brighten(mauve));
  IPaint coral = new Combo("coral", new Blend(pink, khaki));
  IPaint brown = new Combo("brown", new Blend(yellow, darkPurple));
  IPaint darkRed = new Combo("dark red", new Darken(red));
  IPaint brightPurple = new Combo("bright purple", new Brighten(darkPurple));
  IPaint darkBlue = new Combo("dark blue", new Darken(this.blue));
  IPaint darkbrightPurple = new Combo("dark bright purple", 
      new Blend(this.brightPurple, this.darkBlue));

  boolean testGetFinalColor(Tester t) {
    return t.checkExpect(this.red.getFinalColor(), new Color(255, 0, 0))
        && t.checkExpect(this.purple.getFinalColor(), new Color(127, 0, 127))
        && t.checkExpect(this.darkBlue.getFinalColor(), new Color(0, 0, 178))
        && t.checkExpect(this.yellow.getFinalColor(), new Brighten(this.khaki));
  }

  boolean testGetFinalColorHelp(Tester t) {
    return t.checkExpect(this.purple.getFinalColor().darker(), new Darken(this.purple))
        && t.checkExpect(this.purple.getFinalColor().brighter(), new Brighten(this.purple))
        && t.checkExpect(this.purple, new Color(127, 0, 127));

  }

  boolean testCountPaints(Tester t) {
    return t.checkExpect(this.red.countPaints(), 1)
        && t.checkExpect(this.brightPurple.countPaints(), 4)
        && t.checkExpect(this.coral.countPaints(), 7);  
  }

  boolean testCountPaintsHelp(Tester t) {
    return t.checkExpect(this.darkPurple, 4)
        && t.checkExpect(this.yellow, 4)
        && t.checkExpect(this.brown, 8);

  }

  boolean testCountMixes(Tester t) {
    return t.checkExpect(this.red, 0)
        && t.checkExpect(this.coral, 9)
        && t.checkExpect(this.yellow, 3);
  }

  boolean testCountMixesHelp(Tester t) {
    return t.checkExpect(this.khaki, 1)
        && t.checkExpect(this.coral, 9)
        && t.checkExpect(this.yellow, 3);

  }

  boolean testFormulaDepth(Tester t) {
    return t.checkExpect(this.red.formulaDepth(), 0)
        && t.checkExpect(this.darkPurple.formulaDepth(), 2)
        && t.checkExpect(this.yellow.formulaDepth(), 3);
  }

  boolean testFormulaDepthHelp(Tester t) {
    return t.checkExpect(this.darkPurple, 2)
        && t.checkExpect(this.yellow, 3)
        && t.checkExpect(this.mauve, 4);

  }

  boolean testInvert(Tester t) {
    return t.checkExpect(this.brightPurple.invert(), new Darken(darkPurple))
        && t.checkExpect(this.darkRed.invert(), new Brighten(red))
        && t.checkExpect(this.red.invert(), this.red);
  }

  boolean testInvertHelp(Tester t) {
    return t.checkExpect(this.darkPurple, new Brighten(this.darkPurple))
        && t.checkExpect(this.yellow, new Darken(this.yellow))
        && t.checkExpect(this.brown, new Blend(this.yellow, this.darkPurple));

  }

  boolean testMixingFormula(Tester t) {
    return t.checkExpect(this.pink.mixingFormula(0), "pink")
        && t.checkExpect(this.pink.mixingFormula(3), 
            "brighten(blend(blend(red, blue), blend(red, green)))")
        && t.checkExpect(this.red.mixingFormula(6), "red");

  }

  boolean testMixingFormulaHelp(Tester t) {
    return t.checkExpect(this.pink.mixingFormula(1), "brighten(mauve)")
        && t.checkExpect(this.khaki.mixingFormula(2), "blend(red, green)")
        && t.checkExpect(this.darkPurple.mixingFormula(1), "darken(purple)");
  }


}




