import java.awt.Color;
import java.util.Random;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.WorldImage;


// represents the non-player characters
class NPCFish {
  int radius;
  int x;
  int y;
  Color color;
  int speed;


  // instantiates the NPC fish, for spawning one too  
  NPCFish(Color color) {
    Random rand = new Random();
    Random rand1 = new Random();
    this.radius = rand.nextInt(30) + 5;
    this.x = 1;
    this.y = rand.nextInt(799);
    this.color = color;
    this.speed = rand1.nextInt(6) - 3;
  } 

  // CONVIENENCE CONTRSUCTOR: for when the npc fish needs to be moved
  NPCFish(int radius, int x, int y, Color color, int speed) {
    this.radius = radius;
    this.x = x;
    this.y = y;
    this.color = color;
    this.speed = speed;
  }

  // draws the NPC
  WorldImage draw() {
    return new CircleImage(this.radius, "solid", this.color);
  }

  // moves a npc for every tick && checks if it is in range
  // prob could of called a helper method to clean up this code to do one of 2 tasks 
  NPCFish moveItems() {
    if (this.x > 1200)  {
      return new NPCFish(this.radius, 0, this.y, this.color, this.speed);
    }
    else if (this.x < 0) {
      return new NPCFish(this.radius, 1200, this.y, this.color, this.speed);
    }
    else if (y > 800) { 
      return new NPCFish(this.radius, this.x, 0, this.color, this.speed);
    }
    else if (y < 0) { 
      return new NPCFish(this.radius, this.x, 800, this.color, this.speed);
    }
    else if (this.speed == 0) {
      return new NPCFish(this.radius, this.x + this.speed,
          this.y, this.color, 1);
    }
    else {
      return new NPCFish(this.radius, this.x + this.speed,
          this.y, this.color, this.speed);
    }
  }

  // places the NPC onto the world scene
  public WorldScene makeSceneHelper(WorldScene acc) {
    return acc.placeImageXY(this.draw(), this.x, this.y);
  }

  // is this NPC bigger than the player?
  public boolean biggerThan(PlayerFish player) {
    return (this.radius > player.radius);
  }

  // is this NPC smaller than the player?
  public boolean smallerThan(PlayerFish player) {
    return (this.radius <= player.radius);
  }

  // makes an NPC green (only in the case when is smaller than player)
  public NPCFish makeGreen() {
    return new NPCFish(this.radius, this.x, this.y, Color.green, this.speed);
  }

}