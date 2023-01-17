import tester.*;     
import java.util.Random;           
import javalib.worldimages.*;   
import javalib.funworld.*;      
import java.awt.Color;     

//a class to represent a dot with a radius, color and location
class Dot {
  int radius;
  Color c;
  int x;
  int y;

  Dot(int radius, Color c) {
    Random rand = new Random();
    this.radius = radius;
    this.c = c;
    this.x = rand.nextInt(600);
    this.y = rand.nextInt(400);
  }

  Dot(int radius, Color c, int x, int y) {
    this.radius = radius;
    this.c = c;
    this.x = x;
    this.y = y;
  }

  //draw this Dot as CircleImage with its size and color
  WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.c);
  }

  //create a new dot that is like this Dot but is shifted on the x-axis
  Dot move() {
    return new Dot(this.radius, this.c, this.x + 5, this.y + 3);
  }

  // changes a dot's color to green
  Dot changeToGreen() {
    return new Dot(this.radius, Color.green, this.x, this.y);
  }

}

//represents a list of Dots
interface ILoDot {
  //draws Dots from this list onto the given scene
  WorldScene draw(WorldScene acc);

  //moves this list of Dots
  ILoDot move();

  // changes every dot to green
  ILoDot changeGreen();
}

//represents an empty list of Dots
class MtLoDot implements ILoDot {

  //draws Dots from this empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return acc;
  }

  //move the Dots in this empty list
  public ILoDot move() {
    return this;
  }

  // changes a ILoDot to green
  public ILoDot changeGreen() {
    return this;
  }
}

//represents a non-empty list of Dots
class ConsLoDot implements ILoDot {
  Dot first;
  ILoDot rest;

  ConsLoDot(Dot first, ILoDot rest) {
    this.first = first;
    this.rest = rest;
  }

  //draws dots from this non-empty list onto the accumulated
  //image of the scene so far
  public WorldScene draw(WorldScene acc) {
    return this.rest.draw(acc.placeImageXY(this.first.draw(), this.first.x, this.first.y));
  }

  //move the dots in this non-empty list
  public ILoDot move() {
    return new ConsLoDot(this.first.move(), this.rest.move());
  }

  // changes every dot's color to green
  public ILoDot changeGreen() {
    return new ConsLoDot(this.first.changeToGreen(), this.rest.changeGreen());
  } 
}

//represents a world class to animate a list of Dots on a scene
class DotsWorld extends World {
  ILoDot dots;

  DotsWorld(ILoDot dots) {
    this.dots = dots;
  }

  //draws the dots onto the background
  public WorldScene makeScene() {
    return this.dots.draw(new WorldScene(600, 400));
  }

  //move the Dots on the scene. Adds a new Dot at a random location at every tick of the clock
  public World onTick() {
    ILoDot add = new ConsLoDot(new Dot(10, Color.magenta), this.dots);
    return new DotsWorld(add.move());
  }

  // add a key event to change the colors of all of
  //the existing Dots in this World to green when the "g" key is pressed
  public World onKeyEvent(String g) {
    if (g.equals("g")) {
      ILoDot green = new ConsLoDot(new Dot(10, Color.green), this.dots.changeGreen());
      return new DotsWorld(green.move()); }
    else { 
      return this;
    }
  }
}

class ExamplesAnimation {
  Dot d1 = new Dot(10, Color.magenta);
  Dot d2 = new Dot(10, Color.magenta);
  Dot d3 = new Dot(10, Color.magenta);

  ILoDot mt = new MtLoDot();
  ILoDot lod1 = new ConsLoDot(this.d1, this.mt);
  ILoDot lod2 = new ConsLoDot(this.d2, this.lod1);
  ILoDot lod3 = new ConsLoDot(this.d3, this.lod2);

  //add test for all methods above and any that you add

  boolean testBigBang(Tester t) {
    DotsWorld world = new DotsWorld(this.mt);
    int worldWidth = 600;
    int worldHeight = 400;
    double tickRate = .1;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

}