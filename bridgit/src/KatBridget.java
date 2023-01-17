import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
/*
// represents a cell
class Cell {
  int x; // coordinates
  int y; // coordinates
  Color color;
  
  // the cells neighbors
  Cell right;
  Cell left;
  Cell up;
  Cell down;
  
  // the constructor
  Cell(int x, int y) {
    this.x = x;
    this.y = y;
    this.color = Color.green;
    
  }
  
  WorldImage drawCell() {
    return new RectangleImage(20, 20, "solid", this.color);
  }
  
  void drawOnScene(WorldScene scene) {
    scene.placeImageXY(this.drawCell(), x, y);
  }
  
  // EFFECT: changes the cell color to magenta for player 2
  void makeMagenta() {
    this.color = Color.magenta;
  }
  
  //EFFECT: changes the cell color to pink for player 1
  void makePink() {
    this.color = Color.pink;
  }
}


// represents the game
class Bridgit extends World {
  int size;
  ArrayList<ArrayList<Cell>> cells;
  
  // the constructor
  Bridgit(int size) {
    this.size = size;
    this.cells = new ArrayList<ArrayList<Cell>>();

    for (int i = 0; i < size; i++) {
      ArrayList<Cell> row = new ArrayList<Cell>();
      for (int j = 0; j < size; j++) {
        row.add(new Cell(i, j));
      }
      this.cells.add(row);
    }    
  }

  // draws the board
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(500,500);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        cells.get(i).get(j).drawOnScene(scene);
      }
    }
    return scene;
}

  // probably add if statements to the board so that we can change the color
}


class ExamplesGame {
  Cell c1;
  
  void initCond() {
    this.c1 = new Cell(10,10);
  }
  
  
  void testGame(Tester t) {
    initCond();
    Bridgit g = new Bridgit(12);
    g.bigBang(500,500);
  }
}
*/