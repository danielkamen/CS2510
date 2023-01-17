import java.awt.Color;
import javalib.funworld.WorldScene;
import javalib.worldimages.AlignModeX;
import javalib.worldimages.AlignModeY;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EllipseImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetAlign;
import javalib.worldimages.WorldImage;

// represents player fish
class PlayerFish {
  int radius;
  int x;
  int y;
  Color color;
  int speed;

  PlayerFish(int radius, int x, int y, Color color, int speed) {
    this.radius = radius;
    this.x = x;
    this.y = y;
    this.color = color;
    this.speed = speed;
  }


  // updates the player Fish's size after eating a new fish
  PlayerFish upgradedFish() {
    int newRad = this.radius + 1;

    return new PlayerFish(newRad, this.x, this.y, this.color, speed);
  }

  // verifies the player fish is within the range of the world scene
  public PlayerFish moveItems() {
    if (this.x > 1200)  {
      return new PlayerFish(this.radius, 0, this.y, this.color,speed);
    }
    else if (this.x < 0) {
      return new PlayerFish(this.radius, 1200, this.y, this.color, speed);
    }
    else if (y > 800) { 
      return new PlayerFish(this.radius, this.x, 0, this.color, speed);
    }
    else if (y < 0) { 
      return new PlayerFish(this.radius, this.x, 800, this.color, speed);
    }
    else {
      return this;
    }
  }

  // places the playerfish onto the worldscene
  public WorldScene makeSceneHelper(WorldScene acc) {
    return acc.placeImageXY(this.draw(), this.x, this.y);
  }

  // moves the player UP
  PlayerFish moveByUpKey() {
    return new PlayerFish(this.radius, this.x, (this.y - this.speed), this.color, this.speed);
  }

  // moves the player DOWN
  public PlayerFish moveByDownKey() {
    return new PlayerFish(this.radius, this.x, (this.y + this.speed), this.color, this.speed);
  }

  // moves the player LEFT
  public PlayerFish moveByLeftKey() {
    return new PlayerFish(this.radius, (this.x - this.speed), this.y, this.color, this.speed);
  }

  // moves the player RIGHT
  public PlayerFish moveByRightKey() {
    return new PlayerFish(this.radius, (this.x + this.speed), this.y, this.color, this.speed);
  }

  // draws the player at current size (FROG!)
  WorldImage draw() {
    CircleImage mikeBody = new CircleImage(this.radius, OutlineMode.SOLID, Color.green);
    CircleImage mikeEye = new CircleImage(this.radius / 4, OutlineMode.SOLID, Color.black);
    EllipseImage frogMouth = new EllipseImage(this.radius,
        this.radius / 2, OutlineMode.SOLID, Color.BLACK);
    OverlayOffsetAlign mike = 
        new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.TOP, frogMouth, 7.5, -20,
            new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.TOP, mikeEye, 5.5, -7,
                new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.TOP, mikeEye,
                    -7, -7, mikeBody)));
    return mike;
  }

}
