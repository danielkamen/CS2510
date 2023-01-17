import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//represents a cell
class Cell {
  int x; // coordinates
  int y; // coordinates
  Color color;

  // the cells neighbors
  Cell right;
  Cell left;
  Cell up;
  Cell down;
  boolean used;

  // the constructor
  Cell(Color color, int x, int y) {
    this.color = color;
    this.x = x;
    this.y = y;
    if (this.color == Color.white) {
      this.used = false;
    }
    else {
      this.used = true;
    }
  }

  // draws this cell
  WorldImage drawCell() {
    return new RectangleImage(55, 55, "solid", this.color);
  }

  // draws this cell onto scene
  WorldScene drawOnScene(int xcoord, int ycoord, WorldScene scene) {
    scene.placeImageXY(this.drawCell(), xcoord * 55 + 27, ycoord * 55 + 27);
    return scene;
  }

  // has a square been used?
  public boolean used() {
    return this.used;
  }

  // EFFECT: changes the cells color to a new one
  // presumably when it was clicked on
  public void update(Color color2) {
    this.color = color2;
  }

  // Effect: when a playable cell has been used, update its tracker
  public void updateUsed() {
    this.used = true;
  }
}


// represents bridgit game
class BridgitWorld extends World {
  int size;
  ArrayList<ArrayList<Cell>> cells;
  int sizecopy;
  int moveCount;

  // the constructor
  BridgitWorld(int size) {
    this.moveCount = 0;
    if (size <= 3) {
      this.size = 3;
      this.sizecopy = 3;
    }
    else if (size % 2 == 0) {
      this.size = size + 1;
      this.sizecopy = size + 1;
    }
    else { 
      this.size = size;
      this.sizecopy = size;
    }

    this.cells = new ArrayList<ArrayList<Cell>>();
    Boolean flag = true;

    // initilizes the squares into the corrext spaces
    for (int i = 0; i < this.size; i ++) {
      ArrayList<Cell> row = new ArrayList<Cell>();
      for (int j = 0; j < this.sizecopy; j ++) {
        if (flag) {
          // true flag case
          row.add(new Cell(Color.white, j, i));
          // flip flag
          flag = !flag;

        }
        // false flag case and even rows
        else if (!flag && i % 2 == 0) {
          row.add(new Cell(Color.magenta, j, i));
          // flip flag
          flag = !flag;
        }
        // false flag and odd rows
        else if (!flag && i % 2 == 1) {
          row.add(new Cell(Color.pink, j, i));
          flag = !flag;
        }
      }
      this.cells.add(row);
    }


    // double for loop that goes into cell and adds the top, left, right, and bottom cells
    for (int row = 0; row < this.size; row ++) {
      for (int col = 0; col < this.sizecopy; col ++) {
        Cell tempcell = this.cells.get(row).get(col);

        /*Imagine the smallest size, a 3x3 grid. 
         *        _|_|_
         *        _|_|_
         *         | |
         * Theres 9 unqiue cases for when a square has no
         * left, right, up or down neighbor
         */
        // top left, case 1
        if (col == 0 && row == 0) {
          tempcell.left = null;
          tempcell.right = this.cells.get(row).get(col + 1);
          tempcell.up = null;
          tempcell.down = this.cells.get(row + 1).get(col);
        }
        // middle of top row, case 2
        else if (col > 0 && col < this.size - 1 && row == 0) {
          tempcell.left = this.cells.get(row).get(col - 1);
          tempcell.right = this.cells.get(row).get(col + 1);
          tempcell.up = null;
          tempcell.down = this.cells.get(row + 1).get(col);
        }
        // top right, case 3
        else if (col == this.size - 1 && row == 0) {
          tempcell.left = this.cells.get(row).get(col - 1);
          tempcell.right = null;
          tempcell.up = null;
          tempcell.down = this.cells.get(row + 1).get(col);
        }
        // middle of left, case 4
        else if (col == 0 && row > 0 && row < this.size - 1) {
          tempcell.left = null;
          tempcell.right = this.cells.get(row).get(col + 1);
          tempcell.up = this.cells.get(row - 1).get(col);
          tempcell.down = this.cells.get(row + 1).get(col);
        }
        // middle of right, case 5
        else if (row < this.size - 1 && row > 0 && col == this.size - 1) {
          tempcell.left = this.cells.get(row).get(col - 1);
          tempcell.right = null;
          tempcell.up = this.cells.get(row - 1).get(col);
          tempcell.down = this.cells.get(row + 1).get(col);
        }
        // middle of bottom, case 6
        else if (row == this.size - 1 && col < this.size - 1 && col > 0) {
          tempcell.left = this.cells.get(row).get(col - 1);
          tempcell.right = this.cells.get(row).get(col + 1);
          tempcell.up = this.cells.get(row - 1).get(col);
          tempcell.down = null;
        }
        // bottom left, case 7
        else if (col == 0 && row == this.size - 1) {
          tempcell.left = null;
          tempcell.right = this.cells.get(row).get(col + 1);
          tempcell.up = this.cells.get(row - 1).get(col);
          tempcell.down = null;
        }
        // bottom right, case 8
        else if (col == this.size - 1 && row == this.size - 1) {
          tempcell.left = this.cells.get(row).get(col - 1);
          tempcell.right = null;
          tempcell.up = this.cells.get(row - 1).get(col);
          tempcell.down = null;
        }
        // middle square(s), case 9
        else {
          tempcell.left = this.cells.get(row - 1).get(col);
          tempcell.right = this.cells.get(row + 1).get(col);
          tempcell.up = this.cells.get(row).get(col - 1);
          tempcell.down = this.cells.get(row).get(col + 1);
        }
      }
    }
  }

  // draws the board
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.size * 56, this.size * 56);
    for (int row = 0; row < this.size; row++) {
      for (int col = 0; col < this.sizecopy ; col++) {
        cells.get(row).get(col).drawOnScene(col, row, scene);
      }
    }
    scene.placeImageXY(new TextImage("Player Move:" + this.player(),
        this.size * 3, Color.black), this.size * 20,
        (this.size * 55) + 40);
    return scene;
  }

  // determines the string output of whoevers turn it is
  public String player() {
    String player = "";
    if (moveCount % 2 == 0) {
      player = " Magenta";
      return player;
    }
    else {
      player = " Pink";
      return player;
    }
  }

  // EFFECT: changes an empty piece upon click
  // EFFECT: checks if the player has won the game at their turn
  // and triggers the final display screen if either have won
  public void onMouseClicked(Posn pos) {
    int col = pos.x / 55;
    int row = pos.y / 55;

    Color color = null;
    if (this.moveCount % 2 == 0) {
      color = Color.magenta; 
    } else {
      color = Color.pink;
    }

    if (!this.cells.get(row).get(col).used()) {
      if (row != 0 && row != this.size - 1 && col != 0 && col != this.size - 1) {
        this.cells.get(row).get(col).update(color);
        this.moveCount ++;
        this.cells.get(row).get(col).updateUsed();
        // has the player won off of this click?
        if (color == Color.magenta) {
          for (int i = 0; i < this.size - 1; i++) {
            for (int j = 0; j < this.size - 1; j++) {
              boolean p1won = this.hasWon(this.cells.get(i).get(0),
                  this.cells.get(j).get(this.size - 1), Color.MAGENTA);

              if (p1won) {
                this.endOfWorld("Player 1");
              }
            }
          }
        }
        else if (color == Color.pink) {
          for (int i = 0; i < this.size - 1; i++) {
            for (int j = 0; j < this.size - 1; j++) {
              boolean p2won = this.hasWon(this.cells.get(i).get(0),
                  this.cells.get(j).get(this.size - 1), Color.pink);

              if (p2won) {
                this.endOfWorld("Player 2");
              }
            }
          }
        }
      }
    }
  }

  // EFFECT: resets the game state whenver the players decide to press r
  public void onKeyEvent(String k) {
    if (k.equals("r")) {
      BridgitWorld newWorld = new BridgitWorld(this.size);
      this.cells = newWorld.cells;
    }
  }

  // finds the path via BFS search
  public boolean hasWon(Cell start, Cell end, Color color) {
    return false;
  }

  // makes the win screen for each player
  public WorldScene lastScene() {
    WorldScene scene = new WorldScene(this.size * 55 + 27, this.size * 55 + 27);

    if (moveCount % 2 == 0) {
      scene.placeImageXY(new TextImage("Magenta wins!",
          50.0, Color.MAGENTA), (this.size * 55) + 27,
          (this.size * 55) + 27);
    }
    else if (moveCount % 2 == 1) {
      scene.placeImageXY(new TextImage("Pink wins!",
          50.0, Color.PINK), (this.size * 55) + 27,
          (this.size * 55) + 27);
    }
    return scene;
  }

}


class ExamplesGame {
  BridgitWorld b1;
  // USE THIS ONE FOR BIGBANG
  BridgitWorld b2;
  // testing purposes
  Cell c1;
  Cell c2;

  void initData() {
    this.b1 = new BridgitWorld(7);
    this.b2 = new BridgitWorld(11);

    // testing purposes
    this.c1 = new Cell(Color.white, 10, 10);
    this.c2 = new Cell(Color.magenta, 10, 10);
  }

  void testGame(Tester t) {
    this.initData();
    this.b2.bigBang(this.b2.size * 55 + 100, this.b2.size * 55 + 100);
  }

  void testDrawCell(Tester t) {
    this.initData();
    t.checkExpect(this.c1.drawCell(), new RectangleImage(55, 55, "solid", Color.white));
    t.checkExpect(this.c2.drawCell(), new RectangleImage(55, 55, "solid", Color.magenta));
  }

  void testDrawOnScene(Tester t) {
    this.initData();
    WorldScene scene = new WorldScene(500,500);
    scene.placeImageXY(c1.drawCell(), 10, 10);
    t.checkExpect(this.c1.drawOnScene(10, 10, scene), null);
    scene.placeImageXY(c2.drawCell(), 20, 20);
    t.checkExpect(this.c2.drawOnScene(20, 20, scene), null);
  }

  // not neccesary for assignment, but wanted to verify
  void testAssignNeighbors(Tester t) {
    this.initData();
    t.checkExpect(this.b1.cells.get(0).get(0).left, null);
    t.checkExpect(this.b1.cells.get(0).get(0).right, this.b1.cells.get(0).get(1));
    t.checkExpect(this.b1.cells.get(6).get(6).right, null);
    t.checkExpect(this.b1.cells.get(6).get(6).left, this.b1.cells.get(6).get(5));
    t.checkExpect(this.b1.cells.get(6).get(6).up, this.b1.cells.get(5).get(6));
    t.checkExpect(this.b1.cells.get(6).get(6).down, null);
  }

  // tests for used and updateUsed, needed to test each other
  void testUsed(Tester t) {
    this.initData();
    t.checkExpect(this.c1.used, false);
    this.c1.updateUsed();
    t.checkExpect(this.c1.used, true);
    t.checkExpect(this.c2.used, true);
    this.c2.updateUsed();
    t.checkExpect(this.c2.used, false);
  }

  void testUpdate(Tester t) {
    this.initData();
    t.checkExpect(this.c1.color, Color.white);
    this.c1.update(Color.green);
    t.checkExpect(this.c1.color, Color.green);
    t.checkExpect(this.c2.color, Color.magenta);
    this.c1.update(Color.green);
    t.checkExpect(this.c1.color, Color.green);
  }

  boolean testPlayer(Tester t) {
    this.initData();
    this.b2.moveCount ++;
    return t.checkExpect(this.b1.player(), " Magenta")
        && t.checkExpect(this.b2.player(), " Pink");
  }

  void testOnKeyEvent(Tester t) {
    this.initData();
    this.b1.cells.get(0).get(0).update(Color.green);
    t.checkExpect(this.b1.cells.get(0).get(0).color, Color.green);
    this.b1.onKeyEvent("r");
    t.checkExpect(this.b1.cells.get(0).get(0).color, Color.white);
    this.b1.cells.get(1).get(1).update(Color.green);
    t.checkExpect(this.b1.cells.get(1).get(1).color, Color.green);
    this.b1.onKeyEvent("q");
    t.checkExpect(this.b1.cells.get(1).get(1).color, Color.green); 
  }

  void testLastScene(Tester t) {
    this.initData();
    WorldScene pinkWin = new WorldScene(this.b1.size * 50, this.b1.size * 50);
    pinkWin.placeImageXY(new TextImage("Pink wons!", 50.0, Color.PINK), (this.b1.size * 50) + 27,
        (this.b1.size * 55) + 27);
    WorldScene magWin = new WorldScene(this.b1.size * 50, this.b1.size * 50);
    magWin.placeImageXY(new TextImage("Magenta won!", 50.0, Color.MAGENTA), (this.b1.size * 50) / 3,
        (this.b1.size * 55) / 3);
    t.checkExpect(this.b1.lastScene("magenta"), magWin);
    t.checkExpect(this.b1.lastScene("pink"), pinkWin);
  }
}
