package sk.stuba.fei.uim.oop.game;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.components.JLabelScore;
import sk.stuba.fei.uim.oop.components.JLabelTurn;
import sk.stuba.fei.uim.oop.components.Tile;
import sk.stuba.fei.uim.oop.window.MyWindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Getter @Setter
public class Game implements MouseListener {
    private Tile[][] tiles;
    private JLabelTurn labelTurn;
    private JLabelScore labelScore;
    private Color tileColorOld;
    private int playerScore = 2;
    private int pcScore = 2;
    private int preMovesCounter;
    private int upCount = 0;
    private int downCount = 0;
    private int leftCount = 0;
    private int rightCount = 0;
    private int upLeftCount = 0;
    private int downLeftCount = 0;
    private int upRightCount = 0;
    private int downRightCount = 0;
    private boolean passBot = false;
    private boolean passPlayer = false;

    public Game(Tile[][] tiles,  JLabelTurn labelTurn, JLabelScore labelScore){
        this.tiles = tiles;
        this.labelTurn = labelTurn;
        this.labelScore = labelScore;
        labelTurn.setText("Player's turn - White");
        findPreMoves(tiles, Color.WHITE, Color.BLACK);
        setPassPlayer(false);
        setPassBot(false);
    }

    // counting black, white and pre moves stones
    private void countAllStones(Tile[][] tile){
        int playerScore = 0;
        int pcScore = 0;
        int preMovesCounter = 0;
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile.length; j++) {
                if(tile[i][j].getStoneColor() == Color.WHITE) {
                    playerScore++;
                } else if(tile[i][j].getStoneColor() == Color.BLACK) {
                    pcScore++;
                } else if(tile[i][j].getStoneColor() == MyWindow.PRE_COLOR_STONE) {
                    preMovesCounter++;
                }
            }
        }
        setPlayerScore(playerScore);
        setPcScore(pcScore);
        setPreMovesCounter(preMovesCounter);
    }

    private void rewriteLabelScore(JLabelScore labelScore){
        labelScore.setText(getPlayerScore() + ":" + getPcScore() + " ");
    }

    private void checkWinner(){
        if (getPlayerScore() + getPcScore() == (tiles.length*tiles.length) || (isPassPlayer() && isPassBot())){
            writeWinner(getPlayerScore(), getPcScore());
        }
    }

    private void writeWinner(int playerScore, int pcScore){
        if (playerScore > pcScore){
            labelTurn.setText("Player Wins!");
        } else if (pcScore > playerScore){
            labelTurn.setText("Pc Wins!");
        } else {
            labelTurn.setText("It's a tie!");
        }
    }

    private void makePreMovesClickable(Tile[][] tiles){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++){
                if (tiles[i][j].isClickable())
                    tiles[i][j].addMouseListener(this);
            }
        }
    }

    private void activateTile(Tile tile){
        tile.setStoneColor(MyWindow.PRE_COLOR_STONE);
        tile.setClickable(true);
    }

    // pre moves directions (8)
    private void preMoveUp(Tile[][] tiles, int i, int j, Color color, Color oppositeColor){
        if (i-1 >= 0 && tiles[i-1][j].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            while (tmpIndex >= 0 && tiles[tmpIndex][j].getStoneColor() == oppositeColor){
                tmpIndex--;
            }
            if (tmpIndex >= 0 && tiles[tmpIndex][j].getStoneColor() != color){
                activateTile(tiles[tmpIndex][j]);
            }
        }
    }

    private void preMoveDown(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (i+1 < tiles.length && tiles[i+1][j].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            while (tmpIndex < tiles.length && tiles[tmpIndex][j].getStoneColor() == oppositeColor){
                tmpIndex++;
            }
            if (tmpIndex < tiles.length && tiles[tmpIndex][j].getStoneColor() != color){
                activateTile(tiles[tmpIndex][j]);
            }
        }
    }

    private void premMoveLeft(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (j-1 >= 0 && tiles[i][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = j-1;
            while (tmpIndex >=0 && tiles[i][tmpIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
            }
            if (tmpIndex >=0 && tiles[i][tmpIndex].getStoneColor() != color){
                activateTile(tiles[i][tmpIndex]);
            }
        }
    }

    private void preMoveRight(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (j+1 < tiles.length && tiles[i][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = j+1;
            while (tmpIndex < tiles.length && tiles[i][tmpIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
            }
            if (tmpIndex < tiles.length && tiles[i][tmpIndex].getStoneColor() != color){
                activateTile(tiles[i][tmpIndex]);
            }
        }
    }

    private void preMoveUpLeft(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (i-1 >= 0 && j-1 >= 0 && tiles[i-1][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            int tmpJIndex = j-1;
            while (tmpIndex >= 0 && tmpJIndex >= 0 && tiles[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
                tmpJIndex--;
            }
            if (tmpIndex >= 0 && tmpJIndex >= 0 && tiles[tmpIndex][tmpJIndex].getStoneColor() != color){
                activateTile(tiles[tmpIndex][tmpJIndex]);
            }
        }
    }

    private void preMoveDownLeft(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (i+1 < tiles.length && j-1 >= 0 && tiles[i+1][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            int tmpJIndex = j-1;
            while (tmpIndex < tiles.length && tmpJIndex >= 0 && tiles[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
                tmpJIndex--;
            }
            if (tmpIndex < tiles.length && tmpJIndex >= 0 && tiles[tmpIndex][tmpJIndex].getStoneColor() != color){
                activateTile(tiles[tmpIndex][tmpJIndex]);
            }
        }
    }

    private void preMoveUpRight(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (i-1 >= 0 && j+1 < tiles.length && tiles[i-1][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            int tmpJIndex = j+1;
            while (tmpIndex >= 0 && tmpJIndex < tiles.length && tiles[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
                tmpJIndex++;
            }
            if (tmpIndex >= 0 && tmpJIndex < tiles.length && tiles[tmpIndex][tmpJIndex].getStoneColor() != color){
                activateTile(tiles[tmpIndex][tmpJIndex]);
            }
        }
    }

    private void preMoveDownRight(Tile[][] tiles, int i, int j,Color color, Color oppositeColor){
        if (i+1 < tiles.length && j+1 < tiles.length && tiles[i+1][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            int tmpJIndex = j+1;
            while (tmpIndex < tiles.length && tmpJIndex < tiles.length && tiles[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
                tmpJIndex++;
            }
            if (tmpIndex < tiles.length && tmpJIndex < tiles.length && tiles[tmpIndex][tmpJIndex].getStoneColor() != color) {
                activateTile(tiles[tmpIndex][tmpJIndex]);
            }
        }
    }

    private void findPreMoves(Tile[][] tiles, Color color, Color oppositeColor){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getStoneColor() == color) {
                    preMoveDown(tiles,i,j,color,oppositeColor);
                    preMoveUp(tiles,i,j,color,oppositeColor);
                    preMoveRight(tiles,i,j,color,oppositeColor);
                    premMoveLeft(tiles,i,j,color,oppositeColor);
                    preMoveDownRight(tiles,i,j,color,oppositeColor);
                    preMoveUpRight(tiles,i,j,color,oppositeColor);
                    preMoveDownLeft(tiles,i,j,color,oppositeColor);
                    preMoveUpLeft(tiles,i,j,color,oppositeColor);
                }
            }
        }
        makePreMovesClickable(this.tiles);
    }

    // taking stones after finding pre moves
    private void repaintUp(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i--;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintDown(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i++;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintLeft(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            j--;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintRight(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            j++;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintUpRight(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i--;
            j++;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintUpDownRight(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i++;
            j++;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintUpLeft(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i--;
            j--;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void repaintDownLeft(Tile[][] tiles, int i, int j, Color color, int counter){
        for (int k = 0; k < counter; k++) {
            i++;
            j--;
            tiles[i][j].setStoneColor(color);
        }
    }

    private void moveUp(Tile[][] tile, int i, int j, Color color, Color oppositeColor){
        int counter = 0;
        if (i-1 >= 0 && tile[i-1][j].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            while (tmpIndex > 0 && tile[tmpIndex][j].getStoneColor() == oppositeColor){
                tmpIndex--;
                counter++;
            }
            if (tile[tmpIndex][j].getStoneColor() == color){
                setUpCount(counter);
            }
        }
    }

    private void moveDown(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (i+1 < tile.length && tile[i+1][j].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            while (tmpIndex < tile.length-1 && tile[tmpIndex][j].getStoneColor() == oppositeColor){
                tmpIndex++;
                counter++;
            }
            if (tile[tmpIndex][j].getStoneColor() == color) {
                setDownCount(counter);
            }
        }
    }

    private void moveLeft(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (j-1 >= 0 && tile[i][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = j-1;
            while (tmpIndex > 0 && tile[i][tmpIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
                counter++;
            }
            if (tile[i][tmpIndex].getStoneColor() == color){
                setLeftCount(counter);
            }
        }
    }

    private void moveRight(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (j+1 < tile.length && tile[i][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = j+1;
            while (tmpIndex < tile.length-1 && tile[i][tmpIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
                counter++;
            }
            if (tile[i][tmpIndex].getStoneColor() == color){
                setRightCount(counter);
            }
        }
    }

    private void moveUpLeft(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (i-1 >= 0 && j-1 >= 0 && tile[i-1][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            int tmpJIndex = j-1;
            while (tmpIndex > 0 && tmpJIndex > 0 && tile[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
                tmpJIndex--;
                counter++;
            }
            if (tile[tmpIndex][tmpJIndex].getStoneColor() == color) {
                setUpLeftCount(counter);
            }
        }
    }

    private void moveDownLeft(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (i+1 < tile.length && j-1 >= 0 && tile[i+1][j-1].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            int tmpJIndex = j-1;
            while (tmpIndex < tile.length-1 && tmpJIndex > 0 && tile[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
                tmpJIndex--;
                counter++;
            }
            if (tile[tmpIndex][tmpJIndex].getStoneColor() == color) {
                setDownLeftCount(counter);
            }
        }
    }

    private void moveUpRight(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (i-1 >= 0 && j+1 < tile.length && tile[i-1][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = i-1;
            int tmpJIndex = j+1;
            while (tmpIndex > 0 && tmpJIndex < tile.length-1 && tile[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex--;
                tmpJIndex++;
                counter++;
            }
            if (tile[tmpIndex][tmpJIndex].getStoneColor() == color){
                setUpRightCount(counter);
            }
        }
    }

    private void moveDownRight(Tile[][] tile, int i, int j,Color color, Color oppositeColor){
        int counter = 0;
        if (i+1 < tile.length && j+1 < tile.length && tile[i+1][j+1].getStoneColor() == oppositeColor){
            int tmpIndex = i+1;
            int tmpJIndex = j+1;
            while (tmpIndex < tile.length-1 && tmpJIndex < tile.length-1 && tile[tmpIndex][tmpJIndex].getStoneColor() == oppositeColor){
                tmpIndex++;
                tmpJIndex++;
                counter++;
            }
            if (tile[tmpIndex][tmpJIndex].getStoneColor() == color){
                setDownRightCount(counter);
            }
        }
    }

    // counting how many steps from sourceButton can program go in all directions
    private void checkPlayableStones(Tile[][] tile, int i, int j, Color color, Color oppositeColor){
        moveDown(tile,i,j,color, oppositeColor);
        moveUp(tile,i,j,color, oppositeColor);
        moveLeft(tile,i,j,color,oppositeColor);
        moveRight(tile,i,j,color,oppositeColor);
        moveUpLeft(tile,i,j,color,oppositeColor);
        moveDownLeft(tile,i,j,color,oppositeColor);
        moveUpRight(tile,i,j,color,oppositeColor);
        moveDownRight(tile,i,j,color,oppositeColor);
    }

    private void resetCounters(){
        setUpCount(0);
        setDownCount(0);
        setLeftCount(0);
        setRightCount(0);
        setUpLeftCount(0);
        setDownLeftCount(0);
        setUpRightCount(0);
        setDownRightCount(0);
    }

    private void takeStones(Tile[][] tiles, int i, int j, Color color){
        tiles[i][j].setStoneColor(color);
        repaintDown(tiles, i, j, color, getDownCount());
        repaintUp(tiles, i, j, color, getUpCount());
        repaintLeft(tiles, i, j, color, getLeftCount());
        repaintRight(tiles, i, j, color, getRightCount());
        repaintUpLeft(tiles, i, j, color, getUpLeftCount());
        repaintDownLeft(tiles, i, j, color, getDownLeftCount());
        repaintUpRight(tiles, i, j, color, getUpRightCount());
        repaintUpDownRight(tiles, i, j, color, getDownRightCount());
        repaintTileBack(i,j);
        removeClickFromTile(tiles[i][j]);
    }

    private void removeClickFromTile(Tile tile){
        tile.removeMouseListener(this);
        tile.setClickable(false);
    }

    private void repaintTileBack(int i, int j){
        if ((i % 2 == 0 && j % 2 == 0)||i % 2 == 1 && j % 2 == 1){
            tiles[i][j].setBackground(MyWindow.TILE_COLOR_DARK);
        } else {
            tiles[i][j].setBackground(MyWindow.TILE_COLOR_LIGHT);
        }
    }

    private void removePreMoves(Tile[][] tiles){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getStoneColor() == MyWindow.PRE_COLOR_STONE) {
                    tiles[i][j].setStoneColor(null);
                    removeClickFromTile(tiles[i][j]);
                }
            }
        }
    }

    private int sumDirections(){
        return getDownCount() + getUpCount() + getLeftCount() + getRightCount() + getDownLeftCount() + getUpLeftCount()
                + getDownRightCount() + getUpRightCount();
    }

    private void move(Tile[][] tiles, int i, int j, Color color, Color oppositeColor){
        removePreMoves(tiles);
        checkPlayableStones(tiles,i,j,color, oppositeColor);
        takeStones(tiles,i,j,color);
        resetCounters();
    }

    public void playerMove(MouseEvent e){
        Tile tile = (Tile) e.getSource();
        int tmpI = 0;
        int tmpJ = 0;
        loop:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tile == tiles[i][j]) {
                    tmpI = i;
                    tmpJ = j;
                    break loop;
                }
            }
        }
        move(tiles, tmpI, tmpJ, Color.WHITE, Color.BLACK);
    }

    public void botMove(Color color, Color oppositeColor){
        int sum = 0;
        Integer tmpI = null;
        Integer tmpJ = null;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getStoneColor() == MyWindow.PRE_COLOR_STONE){
                    resetCounters();
                    checkPlayableStones(tiles,i,j,color, oppositeColor);
                    int tmpSum = sumDirections();
                    if (tmpSum > sum){
                        sum = tmpSum;
                        tmpI = i;
                        tmpJ = j;
                    }
                }
            }
        }
        resetCounters();
        if (tmpI == null){
            setPassBot(true);
        } else {
            setPassBot(false);
            move(tiles, tmpI,tmpJ, Color.BLACK, Color.WHITE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playerMove(e);
        // if bot can play but player can't play multiple times
        do{
            findPreMoves(tiles, Color.BLACK,Color.WHITE);
            botMove(Color.BLACK, Color.WHITE);
            findPreMoves(tiles, Color.WHITE,Color.BLACK);
            countAllStones(tiles);
            setPassPlayer(getPreMovesCounter() == 0);
        } while (isPassPlayer() && !isPassBot());

        checkWinner();
        rewriteLabelScore(labelScore);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Tile tile = (Tile) e.getComponent();
        tile.setBackground(MyWindow.PRE_COLOR_TILE);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Tile tile = (Tile) e.getComponent();
        if (tile.getName().equals("Dark")){
            tile.setBackground(MyWindow.TILE_COLOR_DARK);
        } else {
            tile.setBackground(MyWindow.TILE_COLOR_LIGHT);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
}