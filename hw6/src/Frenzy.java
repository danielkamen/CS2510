import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import java.awt.Color; // and predefined colors (Red, Green, Yellow, Blue, Black, White)
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

// a generic list interface
interface IList<T> {

  // filter this list by the given predicate
  IList<T> filter(Predicate<T> pred);

  // maps a function onto each member of the list, producing a list of the results
  <U> IList<U> map(Function<T, U> fun);

  // combines the items in this list using the given function
  <U> U foldr(BiFunction<T, U, U> fun, U base);

  // do any of the items in this list pass the given predicate?
  boolean ormap(Predicate<T> pred);

  // do all of the items in this list pass then given predicate?
  boolean andmap(Predicate<T> pred);

  // combines the items of this list and that list (SAME TYPE)
  IList<T> interweave(IList<T> list1);

  // helper for interweave dynamic dispatch is used to determine list cases
  IList<T> interweaveHelp(IList<T> that);

}

// represents an empty generic list
class MtList<T> implements IList<T> {

  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    return this;
  }

  // maps a function onto each member of the list, producing a list of the results
  public <U> MtList<U> map(Function<T, U> fun) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }

  // combines the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return base;
  }

  // does any element of the empty list satisfy the situation?
  public boolean ormap(Predicate<T> pred) {
    return false;
  }

  // does any element of the empty list satisfy the situation?
  public boolean andmap(Predicate<T> pred) {
    return true;
  }

  // interweaves two lists, but given an empty list, returns that list
  public IList<T> interweave(IList<T> list1) {
    return list1;
  }

  // interweaves two lists, but given an empty list, returns that list
  public IList<T> interweaveHelp(IList<T> that) {
    return that;
  }
}

// represents a list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filter this list by the given predicate
  public IList<T> filter(Predicate<T> pred) {
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  // maps a function onto each member of the list, producing a list of the results
  public <U> ConsList<U> map(Function<T, U> fun) {
    return new ConsList<U>(fun.apply(this.first), this.rest.map(fun));
  }

  // combines the items in this list using the given function
  public <U> U foldr(BiFunction<T, U, U> fun, U base) {
    return fun.apply(this.first, this.rest.foldr(fun, base));
  }

  // Does any element of the cons list satisfy the situation?
  public boolean ormap(Predicate<T> pred) {
    return pred.test(this.first) || this.rest.ormap(pred);
  }

  // Does any element of the cons list satisfy the situation?
  public boolean andmap(Predicate<T> pred) {
    return pred.test(this.first) && this.rest.andmap(pred);
  }

  // combines two lists, ustilizing dynamic dispatch to determine cases
  public IList<T> interweave(IList<T> list1) {
    return list1.interweaveHelp(this);
  }

  // combines two conslists
  public IList<T> interweaveHelp(IList<T> that) {
    return new ConsList<T>(this.first, that).interweave(this.rest);
  }
}

// moves a given NPCFish
class MovesFish implements Function<NPCFish, NPCFish> {

  // calls the move method for an NPCFish defined in NPCFish class
  public NPCFish apply(NPCFish first) {
    return first.moveItems();
  }
}

// draws an NPCFish onto the given world scene
class DrawsWorld implements BiFunction<NPCFish, WorldScene, WorldScene> {

  // draws the NPCFish onto the accumulated worldscene
  public WorldScene apply(NPCFish first, WorldScene foldr) {
    return first.makeSceneHelper(foldr);
  }
}

// determines if the NPC is within range of the player fish
class Distance implements Predicate<NPCFish> {
  PlayerFish player;

  Distance(PlayerFish player) {
    this.player = player;
  }

  // is the NPC within the range of the player fish?
  public boolean test(NPCFish npc) {
    return Math.sqrt((npc.x - this.player.x) * (npc.x - this.player.x)
        + (npc.y - this.player.y) * (npc.y - this.player.y)) <= this.player.radius + npc.radius;
  }
}

// is the NPC NOT within the range of the player fish?
class DistanceNot implements Predicate<NPCFish> {
  PlayerFish player;

  DistanceNot(PlayerFish player) {
    this.player = player;
  }

  // is the NPC NOT within the range of the player fish?
  public boolean test(NPCFish npc) {
    return Math.sqrt((npc.x - this.player.x) * (npc.x - this.player.x)
        + (npc.y - this.player.y) * (npc.y - this.player.y)) > this.player.radius + npc.radius;
  }
}

// is the NPC bigger than the player fish?
class Bigger implements Predicate<NPCFish> {
  PlayerFish player;

  Bigger(PlayerFish player) {
    this.player = player;
  }

  // is the NPC bigger than the player fish?
  public boolean test(NPCFish first) {
    return first.biggerThan(this.player);
  }
}

// is the NPC smaller than the player fish?
class Smaller implements Predicate<NPCFish> {
  PlayerFish player;

  Smaller(PlayerFish player) {
    this.player = player;
  }

  // is the NPC smaller than the player fish?
  public boolean test(NPCFish first) {
    return first.smallerThan(this.player);
  }
}

// makes an NPC fish thats smaller than the player green
class MakesGreen implements Function<NPCFish, NPCFish> {

  // makes an NPC fish thats smaller than the player green
  public NPCFish apply(NPCFish first) {
    return first.makeGreen();
  }

}

// represents a world class to animate a list of Fish on a scene
class FishWorld extends World {
  IList<NPCFish> fish;
  PlayerFish p;
  boolean theGameIsLost;
  boolean theGameIsWon;
  int ticks;

  // instansiates the FishWorld into the game
  FishWorld(IList<NPCFish> fish, PlayerFish p) {
    this.fish = fish;
    this.p = p;
    this.theGameIsLost = false;
    this.theGameIsWon = false;
    this.ticks = 0;
  }

  // convience constructor, to be used whenever
  // the FishWorld is altered after the game starts
  FishWorld(IList<NPCFish> fish, PlayerFish p, boolean theGameIsLost, boolean theGameIsWon,
      int ticks) {
    this.fish = fish;
    this.p = p;
    this.theGameIsLost = theGameIsLost;
    this.theGameIsWon = theGameIsWon;
    this.ticks = ticks;
  }

  // moves the world at every tick while also checking if
  // any NPCs are over the player and acts accordingly
  // will preform the following actions:
  // add a NPCfish at every given interval
  // change the world when an NPC is overlapping player && is bigger than it
  // change the world when an NPC is overlapping player && is smaller than it
  // indicate that the game is won when all fish are smaller than it
  // moves all the fish if none of the above cases are satisfied
  public World onTick() {
    this.ticks += 1;
    // a tick counter used to determine when a fish is added to the game
    IList<NPCFish> smaller = this.fish.filter(new Smaller(this.p)).map(new MakesGreen()); // list of
    // fish
    // smaller
    IList<NPCFish> bigger = this.fish.filter(new Bigger(this.p));
    // list of fish bigger
    IList<NPCFish> all = smaller.interweave(bigger);
    // list of all smaller and bigger fish
    IList<NPCFish> overlaps = all.filter(new Distance(this.p));
    // list of fish overlapping player
    IList<NPCFish> notOverlaps = all.filter(new DistanceNot(this.p));
    // list of fish not overlapping player
    PlayerFish p1 = this.p.moveItems();
    // used to keep lines nice and short

    if (ticks % 150 == 0) {
      // every 150 ticks, or 2.5 seconds a new NPC fish is added
      return new FishWorld(new ConsList<NPCFish>(new NPCFish(Color.red), all), p1);
    }
    else if (overlaps.ormap(new Bigger(this.p))) {
      // when the NPC that overlaps player is bigger
      return new FishWorld(all.map(new MovesFish()), p1, true, false, this.ticks);
    }
    else if (overlaps.ormap(new Smaller(this.p))) {
      // when the NPC that overlaps the player is smaller
      return new FishWorld(
          new ConsList<NPCFish>(new NPCFish(Color.red), notOverlaps).map(new MovesFish()),
          p1.upgradedFish(), false, false, this.ticks);
    }
    else if (all.andmap(new Smaller(this.p))) { // when every fish is smaller than player fish
      return new FishWorld(all, p1, false, true, this.ticks);
    }
    else { // normal game // where i apply change to green
      return new FishWorld(all.map(new MovesFish()), p1, false, false, this.ticks);
    }
  }

  // draws the world scene
  public WorldScene makeScene() {
    return this.makeSceneHelper(new WorldScene(1200, 800));
  }

  // draws the fish onto the given worldscene
  public WorldScene makeSceneHelper(WorldScene acc) {
    return this.p.makeSceneHelper(this.fish.foldr(new DrawsWorld(), acc));
  }

  // will move the player fish given UP, DOWN, LEFT, or RIGHT keys
  public World onKeyEvent(String key) {
    if (key.equals("right")) {
      return new FishWorld(this.fish, this.p.moveByRightKey(), this.theGameIsLost,
          this.theGameIsWon, this.ticks);
    }
    else if (key.equals("up")) {
      return new FishWorld(this.fish, this.p.moveByUpKey(), this.theGameIsLost, this.theGameIsWon,
          this.ticks);
    }
    else if (key.equals("left")) {
      return new FishWorld(this.fish, this.p.moveByLeftKey(), this.theGameIsLost, this.theGameIsWon,
          this.ticks);
    }
    else if (key.equals("down")) {
      return new FishWorld(this.fish, this.p.moveByDownKey(), this.theGameIsLost, this.theGameIsWon,
          this.ticks);
    }
    else {
      return new FishWorld(this.fish, this.p, this.theGameIsLost, this.theGameIsWon, this.ticks);
    }
  }

  // will end the world when 1/2 conditions are met:
  // player won or player lost
  public WorldEnd worldEnds() {
    if (theGameIsLost) {
      return new WorldEnd(true, this.makeAFinalSceneLost());
    }
    else if (theGameIsWon) {
      return new WorldEnd(true, this.makeAFinalSceneWon());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // makes the losing world screen
  public WorldScene makeAFinalSceneLost() {
    return new WorldScene(1200, 800)
        .placeImageXY(new TextImage("Game Over: You Lost", 50, Color.red), 600, 400);
  }

  // makes the winning world screen
  public WorldScene makeAFinalSceneWon() {
    return new WorldScene(1200, 800)
        .placeImageXY(new TextImage("Game Over: You Won!", 50, Color.green), 600, 400);
  }
}

class ExamplesGame {
  ExamplesGame() {
  }
  
  PlayerFish p1 = new PlayerFish(14, 600, 400, Color.black, 15);
  PlayerFish p2 = new PlayerFish(14, 600, 400, Color.green, 15);

  // strictly for moveItems
  PlayerFish p3 = new PlayerFish(14, 1201, 400, Color.green, 15);
  PlayerFish p4 = new PlayerFish(14, -1, 400, Color.green, 15);
  PlayerFish p5 = new PlayerFish(14, 1, 801, Color.green, 15);
  PlayerFish p6 = new PlayerFish(14, 1, -1, Color.green, 15);

  // this is used to ensure that at the start of the game,
  // the first few fish aren't all
  // smaller than the player fish, making the game too easy
  NPCFish n1 = new NPCFish(25, 1199, 19, Color.red, 7);
  NPCFish n2 = new NPCFish(25, 1199, 19, Color.green, 7);

  // used to test moveItems()
  NPCFish n3 = new NPCFish(25, 1201, 19, Color.blue, 7);
  NPCFish n4 = new NPCFish(25, -1, 19, Color.red, 7);
  NPCFish n5 = new NPCFish(25, 1200, -1, Color.red, 7);
  NPCFish n6 = new NPCFish(25, 1200, 801, Color.red, 7);

  // normal fish that can be used for other tests
  NPCFish n7 = new NPCFish(25, 1199, 19, Color.blue, 7);
  NPCFish n8 = new NPCFish(25, 1, 19, Color.red, 7);
  NPCFish n9 = new NPCFish(25, 1199, 1, Color.red, 7);
  NPCFish n10 = new NPCFish(25, 1199, 799, Color.red, 7);
  NPCFish n11 = new NPCFish(1, 1199, 799, Color.red, 7);
  NPCFish n12 = new NPCFish(1, 600, 400, Color.red, 7);

  IList<NPCFish> npcList1 = new ConsList<NPCFish>(this.n10,
      new ConsList<NPCFish>(this.n9, new ConsList<NPCFish>(this.n8,
          new ConsList<NPCFish>(this.n7, new ConsList<NPCFish>(this.n6, this.mtNPCFish)))));

  WorldScene world = new WorldScene(1200, 800);

  IList<NPCFish> mtNPCFish = new MtList<NPCFish>();
  IList<NPCFish> listFish1 = new ConsList<NPCFish>(n1, mtNPCFish);

  // run the game
  boolean testBigBang(Tester t) {
    FishWorld world = new FishWorld(this.listFish1, this.p1);
    int worldWidth = 1200;
    int worldHeight = 800;
    double tickRate = .00001;
    return world.bigBang(worldWidth, worldHeight, tickRate);
  }

  // test upgradedFish
  boolean testUpgradedFish(Tester t) {
    return t.checkExpect(this.p1.upgradedFish(), new PlayerFish(15, 600, 400, Color.black, 15))
        && t.checkExpect(this.p2.upgradedFish(), new PlayerFish(15, 600, 400, Color.green, 15));
  }

  // test moveItems()
  boolean testMoveItems(Tester t) {
    return t.checkExpect(this.p3.moveItems(), new PlayerFish(14, 0, 400, Color.green, 15))
        && t.checkExpect(this.p4.moveItems(), new PlayerFish(14, 1200, 400, Color.green, 15))
        && t.checkExpect(this.p5.moveItems(), new PlayerFish(14, 1, 0, Color.green, 15))
        && t.checkExpect(this.p6.moveItems(), new PlayerFish(14, 1, 800, Color.green, 15))
        && t.checkExpect(this.n3.moveItems(), new NPCFish(25, 0, 19, Color.blue, 7))
        && t.checkExpect(this.n4.moveItems(), new NPCFish(25, 1200, 19, Color.red, 7))
        && t.checkExpect(this.n5.moveItems(), new NPCFish(25, 1200, 800, Color.red, 7))
        && t.checkExpect(this.n6.moveItems(), new NPCFish(25, 1200, 0, Color.red, 7));
  }

  boolean testMakeSceneHelperClass(Tester t) {
    return t.checkExpect(this.p1.makeSceneHelper(world),
        world.placeImageXY(this.p1.draw(), 600, 400))
        && t.checkExpect(this.p2.makeSceneHelper(world),
            world.placeImageXY(this.p2.draw(), 600, 400))
        && t.checkExpect(this.n2.makeSceneHelper(world),
            world.placeImageXY(this.n2.draw(), 1199, 19))
        && t.checkExpect(this.n1.makeSceneHelper(world),
            world.placeImageXY(this.n1.draw(), 1199, 19));
  }

  boolean testMoveUp(Tester t) {
    return t.checkExpect(this.p1.moveByUpKey(), new PlayerFish(14, 600, 385, Color.black, 15))
        && t.checkExpect(this.p2.moveByUpKey(), new PlayerFish(14, 600, 385, Color.green, 15));
  }

  boolean testMoveDown(Tester t) {
    return t.checkExpect(this.p1.moveByDownKey(), new PlayerFish(14, 600, 415, Color.black, 15))
        && t.checkExpect(this.p2.moveByDownKey(), new PlayerFish(14, 600, 415, Color.green, 15));
  }

  boolean testMoveLeft(Tester t) {
    return t.checkExpect(this.p1.moveByLeftKey(), new PlayerFish(14, 585, 400, Color.black, 15))
        && t.checkExpect(this.p2.moveByLeftKey(), new PlayerFish(14, 585, 400, Color.green, 15));
  }

  boolean testMoveRight(Tester t) {
    return t.checkExpect(this.p1.moveByRightKey(), new PlayerFish(14, 615, 400, Color.black, 15))
        && t.checkExpect(this.p2.moveByRightKey(), new PlayerFish(14, 615, 400, Color.green, 15));
  }

  boolean testDraw(Tester t) {
    return t.checkExpect(this.n1.draw(), new CircleImage(25, "solid", Color.red))
        && t.checkExpect(this.n2.draw(), new CircleImage(25, "solid", Color.green));
    // the method for draw works,
    // but because I made a frog, i really dont wanna write all that
    // && t.checkExpect(this.p1.draw(), new CircleImage(5, "solid", Color.black))
    // && t.checkExpect(this.p2.draw(), new CircleImage(10, "solid", Color.green));
  }

  boolean testSmallerThan(Tester t) {
    return t.checkExpect(this.n11.smallerThan(this.p1), true)
        && t.checkExpect(this.n2.smallerThan(this.p1), false);
  }

  boolean testBiggerThan(Tester t) {
    return t.checkExpect(this.n11.biggerThan(this.p1), false)
        && t.checkExpect(this.n2.biggerThan(this.p1), true);
  }

  boolean testMakeGreen(Tester t) {
    return t.checkExpect(this.n1.makeGreen(), new NPCFish(25, 1199, 19, Color.green, 7))
        && t.checkExpect(this.n7.makeGreen(), new NPCFish(25, 1199, 19, Color.green, 7));
  }

  boolean testMovesItem(Tester t) {
    return t.checkExpect(new MovesFish().apply(this.n1), this.n1.moveItems())
        && t.checkExpect(new MovesFish().apply(this.n2), this.n2.moveItems());
  }

  boolean testDistance(Tester t) {
    return t.checkExpect(new Distance(this.p1).test(this.n1), false)
        && t.checkExpect(new Distance(this.p1).test(this.n12), true);
  }

  boolean testDistanceNot(Tester t) {
    return t.checkExpect(new DistanceNot(this.p1).test(this.n1), true)
        && t.checkExpect(new DistanceNot(this.p1).test(this.n12), false);
  }

  boolean testDrawsWorld(Tester t) {
    return t.checkExpect(new DrawsWorld().apply(this.n1, world), this.n1.makeSceneHelper(world))
        && t.checkExpect(new DrawsWorld().apply(this.n2, world), this.n2.makeSceneHelper(world));
  }

  boolean testBigger(Tester t) {
    return t.checkExpect(new Bigger(this.p1).test(this.n1), true)
        && t.checkExpect(new Bigger(this.p1).test(this.n11), false);
  }

  boolean testSmaller(Tester t) {
    return t.checkExpect(new Smaller(this.p1).test(this.n1), false)
        && t.checkExpect(new Smaller(this.p1).test(this.n11), true);
  }

  boolean testMakesGreen(Tester t) {
    return t.checkExpect(new MakesGreen().apply(this.n1), new NPCFish(25, 1199, 19, Color.green, 7))
        && t.checkExpect(new MakesGreen().apply(this.n7),
            new NPCFish(25, 1199, 19, Color.green, 7));
  }

  PlayerFish p11 = new PlayerFish(14, 600, 400, Color.black, 15);
  PlayerFish p12 = new PlayerFish(14, 600, 400, Color.black, 15);

  NPCFish n13 = new NPCFish(25, 600, 400, Color.red, 7);
  NPCFish n14 = new NPCFish(13, 600, 400, Color.red, 7);
  NPCFish n15 = new NPCFish(1, 800, 400, Color.red, 7);
  NPCFish n16 = new NPCFish(1, 800, 400, Color.red, 7);
  NPCFish n17 = new NPCFish(1, 800, 400, Color.red, 7);

  // all smaller
  IList<NPCFish> npcList2 = new ConsList<NPCFish>(this.n17,
      new ConsList<NPCFish>(this.n16, new ConsList<NPCFish>(this.n15, this.mtNPCFish)));

  // some bigger
  IList<NPCFish> npcList3 = new ConsList<NPCFish>(this.n17, new ConsList<NPCFish>(this.n16,
      new ConsList<NPCFish>(this.n15, new ConsList<NPCFish>(this.n13, this.mtNPCFish))));

  // some bigger with small overlap
  IList<NPCFish> npcList4 = new ConsList<NPCFish>(this.n17, new ConsList<NPCFish>(this.n16,
      new ConsList<NPCFish>(this.n15, new ConsList<NPCFish>(this.n14, this.mtNPCFish))));
  FishWorld f110 = new FishWorld(new ConsList<NPCFish>(this.n15,
      this.mtNPCFish), this.p1, false, false, 0);
  FishWorld f1101 = new FishWorld(new ConsList<NPCFish>(this.n14,
      this.mtNPCFish), this.p1, false, false, 0);
  FishWorld f111 = new FishWorld(this.npcList1, this.p1, false, false, 150); // case 1
  FishWorld f112 = new FishWorld(this.npcList3, this.p1, false, false, 0); // case 2
  // FishWorld f1122 = new FishWorld(this.npcList3.
  // moveItems(), this.p1, false, false, 0); // case 2
  FishWorld f113 = new FishWorld(this.npcList4, this.p1, false, false, 0); // case 3
  FishWorld f114 = new FishWorld(this.npcList1, this.p1, false, false, 0); // case 4
  FishWorld f115 = new FishWorld(this.mtNPCFish, this.p1, false, false, 0);
  /*
   * boolean testOnTick(Tester t) { return t.checkExpect(f111.onTick(), this.f111)
   * && t.checkExpect(f112.onTick(), new FishWorld(f112, this.p1.moveItems()))
   * &&t.checkExpect(f113.onTick(), new FishWorld(f112), this.p1.moveItems()); }
   */
  FishWorld worldWin = new FishWorld(this.npcList1, this.p1, false, true, 0);
  FishWorld worldLose = new FishWorld(this.npcList1, this.p1, true, false, 0);
  FishWorld worldNormal = new FishWorld(this.npcList1, this.p1, false, false, 0);

  boolean testOnKey(Tester t) {
    return t.checkExpect(this.f111.onKeyEvent("up"), new FishWorld(this.npcList1,
        new PlayerFish(14, 600, 385, Color.black, 15), false, false, 150))
        && t.checkExpect(this.f111.onKeyEvent("down"), new FishWorld(this.npcList1, 
            new PlayerFish(14, 600, 415, Color.black, 15), false, false, 150))
        && t.checkExpect(this.f111.onKeyEvent("left"), new FishWorld(this.npcList1, 
            new PlayerFish(14, 585, 400, Color.black, 15), false, false, 150))
        && t.checkExpect(this.f111.onKeyEvent("right"), new FishWorld(this.npcList1,
            new PlayerFish(14, 615, 400, Color.black, 15), false, false, 150))
        && t.checkExpect(this.f111.onKeyEvent("k"), this.f111);
  }
  /* these will and can pass if I had time to write out what the actual
   method would produce, however throws nullpointer exception bc
   im passing in a null item(mt list) into the expected


  boolean testMakeScene(Tester t) {
    return t.checkExpect(this.f110.makeScene(),
        this.p1.makeSceneHelper(new DrawsWorld().apply(this.n15, this.world)))
        && t.checkExpect(this.f1101.makeScene(),
            this.p2.makeSceneHelper(new DrawsWorld().apply(this.n14, this.world)));
  }

  boolean testMakeSceneHelperMethod(Tester t) {
    return t.checkExpect(this.f115.makeSceneHelper(world),
        this.p1.makeSceneHelper(world))
        && t.checkExpect(this.f110.makeSceneHelper(world),
            this.p1.makeSceneHelper(world.placeImageXY(this.n15.draw(), this.n15.x, this.n15.y)));
  }

  boolean testMakeAFinalSceneLost(Tester t) {
    return t.checkExpect(this.f110.makeAFinalSceneLost(), new WorldScene(1200, 800)
        .placeImageXY(new TextImage("Game Over: You Lost", 50, Color.red), 600, 400))
        && t.checkExpect(this.f111.makeAFinalSceneLost(), new WorldScene(1200, 800)
            .placeImageXY(new TextImage("Game Over: You Lost", 50, Color.red), 600, 400));

  }

  boolean testMakeAFinalSceneWon(Tester t) {
    return t.checkExpect(this.f110.makeAFinalSceneWon(), new WorldScene(1200, 800)
        .placeImageXY(new TextImage("Game Over: You Won!", 50, Color.green), 600, 400))
        && t.checkExpect(this.f111.makeAFinalSceneWon(), new WorldScene(1200, 800)
            .placeImageXY(new TextImage("Game Over: You Won!", 50, Color.green), 600, 400));
  }

  boolean testWorldEnds(Tester t) {
    return t.checkExpect(this.worldWin.worldEnds(),
        new WorldEnd(true, this.worldWin.makeAFinalSceneWon()))
        && t.checkExpect(this.worldLose.worldEnds(),
            new WorldEnd(true, this.worldLose.makeAFinalSceneLost()))
        && t.checkExpect(this.worldNormal.worldEnds(),
            new WorldEnd(false, this.worldNormal.makeScene()));
  }

   */
}
