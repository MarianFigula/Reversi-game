package sk.stuba.fei.uim.oop.window;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.components.*;
import sk.stuba.fei.uim.oop.game.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyWindow implements ItemListener, ActionListener, KeyListener {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final int X = 300;
    private static final int Y = 50;
    private static final String[] SIZES = new String[]{"6x6","8x8","10x10","12x12"};
    public static final Color PRE_COLOR_TILE = new Color(222, 222, 222,222);
    public static final Color PRE_COLOR_STONE = new Color(201, 182, 30);
    public static final Color TILE_COLOR_LIGHT = new Color(101, 184, 207);
    public static final Color TILE_COLOR_DARK = new Color(18, 130, 162);
    private Tile[][] tiles;
    private final JFrame frame = new JFrame("Reversi");
    private JPanel panelCenter;
    private JLabelTurn labelTurn;
    private JLabelSize labelSize;
    private JLabelScore labelScore;
    @Getter @Setter
    private int areaSize;

    public MyWindow(){
        JPanel panelEast = new JPanel();
        JPanel panelNorth = new JPanel();
        setAreaSize(6);

        frame.setBounds(X,Y,WINDOW_WIDTH,WINDOW_HEIGHT);
        createJPanelEast(panelEast);
        createJPanelNorth(panelNorth);
        createPlayingArea(getAreaSize());
        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(panelEast, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setVisible(true);
        new Game(tiles,labelTurn, labelScore);
    }

    private void resetGrid(){
        frame.getContentPane().remove(panelCenter);
        labelScore.setText("2:2");
        createPlayingArea(getAreaSize());
        frame.repaint();
        frame.revalidate();
        frame.setFocusable(true);
    }

    private void resetGame(){
        resetGrid();
        new Game(tiles,labelTurn, labelScore);
    }

    private void paintTile(int i, int j, Tile tile){
        if ((i % 2 == 0 && j % 2 == 0)||i % 2 == 1 && j % 2 == 1){
            tile.setBackground(TILE_COLOR_DARK);
            tile.setName("Dark");
        } else {
            tile.setBackground(TILE_COLOR_LIGHT);
            tile.setName("Light");
        }
    }

    private void setMiddleStones(int i, int j, Tile tile){
        if (i == (getAreaSize() / 2) - 1 && j == (getAreaSize() / 2) - 1 || i == (getAreaSize() / 2) && j == (getAreaSize() / 2)){
            tile.setStoneColor(Color.WHITE);
        } else if (i == (getAreaSize() / 2) - 1 && j == (getAreaSize() / 2) || i == (getAreaSize() / 2) && j == (getAreaSize() / 2 - 1)){
            tile.setStoneColor(Color.BLACK);
        }
    }

    private void createPlayingArea(int size){
        panelCenter = new JPanel(new GridLayout(size,size));
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile(null, false);
                paintTile(i,j,tile);
                setMiddleStones(i,j, tile);
                tiles[i][j] = tile;
                panelCenter.add(tile);
            }
        }
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.repaint();
        frame.revalidate();
    }

    private void createJPanelNorth(JPanel panelNorth){
        GridBagConstraints gbcN = new GridBagConstraints();

        panelNorth.setBackground(new Color(224, 224, 224));
        panelNorth.setLayout(new GridBagLayout());

        JLabelVersus labelVersus = new JLabelVersus(gbcN);
        panelNorth.add(labelVersus,gbcN);

        labelSize = new JLabelSize(getAreaSize(), gbcN);
        panelNorth.add(labelSize, gbcN);

        labelTurn = new JLabelTurn(gbcN);
        panelNorth.add(labelTurn, gbcN);

        labelScore = new JLabelScore(gbcN);
        panelNorth.add(labelScore, gbcN);
    }

    private void createJPanelEast(JPanel panelEast){
        GridBagConstraints gbcE = new GridBagConstraints();

        panelEast.setLayout(new GridBagLayout());
        panelEast.setBackground(Color.white);

        ComboLabel comboLabel = new ComboLabel(gbcE);
        panelEast.add(comboLabel,gbcE);

        JComboBox<String> sizeComboBox = new JComboBox<>(SIZES);
        sizeComboBox.addItemListener(this);
        sizeComboBox.setFocusable(false);
        gbcE.gridx = 1;
        gbcE.gridy = 0;
        gbcE.insets = new Insets(0,30,0,30);
        panelEast.add(sizeComboBox,gbcE);

        ResetButton resetButton = new ResetButton(gbcE);
        resetButton.addActionListener(this);
        panelEast.add(resetButton,gbcE);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String item = e.getItem().toString();

            try {
                setAreaSize(Integer.parseInt((item.charAt(0) + "" + item.charAt(1))));
            }catch (NumberFormatException exception){
                setAreaSize(Integer.parseInt(item.charAt(0) + ""));
            }
            resetGame();
            labelSize.setText("Size: " + comboBox.getSelectedItem());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (String.valueOf(e.getKeyChar()).equalsIgnoreCase("r")){
            resetGame();
        } else if (e.getKeyChar() == 27){
            frame.dispose();
            System.exit(1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}