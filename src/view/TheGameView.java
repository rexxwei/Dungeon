package view;

import control.Controller;
import dungeon.Cave;
import dungeon.MonsterStatus;
import model.ViewOnlyModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import static helper.HelperClass.randomNumber;
import static helper.HelperClass.randomOneZero;


/**
 * the class implements the View interface.
 */
public class TheGameView extends JFrame implements View {

  ViewOnlyModel model;
  JFrame frame;

  // create menuItems
  JMenuBar menuBar;
  JMenu menu1;
  JMenuItem m1_1;
  JMenuItem m1_2;
  JMenuItem m1_3;

  JPanel eastPanel;
  JPanel westPanel;
  JPanel centralPanel;
  JPanel buttonPanel;
  JButton[][] buttons;
  JScrollPane scrollPane;
  JPanel shootPanel;

  //text on westPanel
  JLabel playerInfo;
  JLabel spotInfo;
  JLabel gameInfo;

  //button on eastPanel
  JButton northButton;
  JButton southButton;
  JButton westButton;
  JButton eastButton;
  JButton pickButton;
  JButton shootButton;

  //shooting parameters
  JLabel shootResult = new JLabel();

  /**
   * Constructor.
   * @param m the model to get info
   */
  public TheGameView(ViewOnlyModel m) {
    //build window to display game state
    super("Dungeon Adventure");
    this.model = m;

    String title;
    if (this.model == null) {
      title = "Null";
    }
    else {
      title = "Dungeon Adventure";
    }

    frame = new JFrame(title);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // --- create JMenu component ---
    menuBar = new JMenuBar();
    // create 1st menu
    menu1 = new JMenu("The Game");
    // create menuItems
    m1_1 = new JMenuItem("Restart new game");
    m1_2 = new JMenuItem("Resume the game");
    m1_3 = new JMenuItem("Quit");
    // add menu items to menu
    menu1.add(m1_1);
    menu1.add(m1_2);
    menu1.add(m1_3);
    // create 2nd menu
    JMenu menu2 = new JMenu("About Dungeon");
    // create menuItems
    JMenuItem m2_1 = new JMenuItem("Size: "
            + m.getDungeonSize());
    JMenuItem m2_2 = new JMenuItem("InterConnectivity: "
            + m.getInterConnectivity());
    JMenuItem m2_3 = new JMenuItem("Treasure(%): "
            + m.getDungeonPercentage());
    JMenuItem m2_4 = new JMenuItem("Monster Qty: "
            + m.getMonsterQuantity());
    JMenuItem m2_5 = new JMenuItem("Wrapping: "
            + m.getWrappingState());
    // add menu items to menu
    menu2.add(m2_1);
    menu2.add(m2_2);
    menu2.add(m2_3);
    menu2.add(m2_4);
    menu2.add(m2_5);
    // add menu to menuBar
    menuBar.add(menu1);
    menuBar.add(menu2);
    // add menuBar to frame
    frame.setJMenuBar(menuBar);

    // --- create East Panel ---->
    JPanel dirPanel = new JPanel();
    dirPanel.setLayout(new GridLayout(2, 2));
    dirPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    dirPanel.setBackground(Color.LIGHT_GRAY);

    // build direction panel
    northButton = new JButton("North ↑");
    northButton.setFont(new Font("Arial", Font.BOLD, 25));
    northButton.setPreferredSize(new Dimension(80,25));
    northButton.setFocusable(false);
    dirPanel.add(northButton);

    southButton = new JButton("South ↓");
    southButton.setPreferredSize(new Dimension(80,25));
    southButton.setFont(new Font("Arial", Font.BOLD, 25));
    southButton.setFocusable(false);
    dirPanel.add(southButton);

    westButton = new JButton("← West");
    westButton.setPreferredSize(new Dimension(80,25));
    westButton.setFont(new Font("Arial", Font.BOLD, 25));
    westButton.setFocusable(false);
    dirPanel.add(westButton);

    eastButton = new JButton("East →");
    eastButton.setPreferredSize(new Dimension(80,25));
    eastButton.setFont(new Font("Arial", Font.BOLD, 25));
    eastButton.setFocusable(false);
    dirPanel.add(eastButton);

    //build actionPanel
    JPanel actionPanel = new JPanel();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBackground(Color.LIGHT_GRAY);

    // add pick button
    pickButton = new JButton("Pick(p)");
    pickButton.setPreferredSize(new Dimension(150,40));
    pickButton.setFont(new Font("Arial", Font.BOLD, 20));
    pickButton.setFocusable(false);
    actionPanel.add(pickButton);

    // add shooting button
    shootButton = new JButton("Shoot(s)");
    shootButton.setPreferredSize(new Dimension(150,40));
    shootButton.setFont(new Font("Arial", Font.BOLD, 20));
    shootButton.setFocusable(false);

    //add keyListener
    actionPanel.add(shootButton);

    // build shootPanel
    shootPanel = new JPanel();
    shootPanel.setLayout(new GridLayout(3,3));
    shootPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    shootPanel.setBackground(Color.LIGHT_GRAY);
    paintShootPanel("");

    // build eastCentral panel
    JPanel eastCentralPanel = new JPanel();
    eastCentralPanel.setLayout(new GridLayout(3, 1));
    eastCentralPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    eastCentralPanel.setBackground(Color.LIGHT_GRAY);
    eastCentralPanel.setSize(new Dimension(800, 600));

    eastCentralPanel.add(dirPanel);
    eastCentralPanel.add(actionPanel);
    eastCentralPanel.add(shootPanel);

    //build east panel
    eastPanel = new JPanel();
    eastPanel.setBackground(Color.LIGHT_GRAY);
    eastPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    eastPanel.setLayout(new BorderLayout());
    eastPanel.add(eastCentralPanel, BorderLayout.CENTER);
    // end of East panel --->

    // <--- create West Panel ---
    westPanel = new JPanel();
    westPanel.setBackground(Color.LIGHT_GRAY);
    westPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
    westPanel.setLayout(new BorderLayout());

    playerInfo = new JLabel(); //setText in paintWestPanel() method

    spotInfo = new JLabel(); //setText in paintWestPanel() method
    spotInfo.setFont(new Font("Verdana", Font.PLAIN, 18));
    westPanel.add(spotInfo, BorderLayout.CENTER);

    gameInfo = new JLabel(); //setText in paintWestPanel() method

    paintWestPanel();
    // <--- end of west panel

    // ---create ->O<- Central(Game) Panel---
    int rowNum = this.model.getDungeonNumberOfRows();
    int colNum = this.model.getDungeonNumberOfCols();

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(rowNum, colNum));
    buttonPanel.setSize(500,500);

    //draw the buttons according to dungeon size
    buttons = new JButton[rowNum][colNum];
    paintCentralPanel();

    // ---add all panels to frame---
    frame.add(eastPanel, BorderLayout.EAST);
    frame.add(westPanel, BorderLayout.WEST);
    frame.add(centralPanel, BorderLayout.CENTER);
    frame.setSize(1450, 900);
    frame.setResizable(false);
    frame.setVisible(false);

  } //end of constructor

  @Override
  public void turnOnGameView() {
    frame.setVisible(true);
  }


  /**
   * Add the listener(controller) for all the needed methods.
   *
   * @param listener the listener to use
   */
  @Override
  public void addGameListener(Controller listener) {
    addClickListener(listener);
    addPickListener(listener);
    addShootListener(listener);
    addDirectionListener(listener);
    addMenuListener(listener);
    addKeyBinding(listener);
  }

  /**
   * Add listener to Menu bar item.
   *
   * @param listener the controller to use
   */
  public void addMenuListener(Controller listener) {
    // create the MouseAdapter
    ActionListener restartListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.startNewGame();
        refreshTheFrame();
      }
    };
    m1_1.addActionListener(restartListener);

    ActionListener resumeListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.resumeSameGame();
        paintEastPanel();
        paintWestPanel();
        refreshGameBoard();
      }
    };
    m1_2.addActionListener(resumeListener);

    ActionListener quitListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    };
    m1_3.addActionListener(quitListener);

  }


  /**
   * Add listener to direction buttons on East panel.
   *
   * @param listener the controller to use
   */
  public void addDirectionListener(Controller listener) {
    // create the MouseAdapter
    MouseAdapter dirAdapter = new MouseAdapter() {

          @Override
          public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.getSource() == northButton) {
              try {
                listener.handleMove("N");
              } catch (IllegalArgumentException exc) {
                JOptionPane.showMessageDialog(frame, "Invalid move!");
              }
            }

            if (e.getSource() == southButton) {
              try {
                listener.handleMove("S");
              } catch (IllegalArgumentException exc) {
                JOptionPane.showMessageDialog(frame, "Invalid move!");
              }
            }

            if (e.getSource() == eastButton) {
              try {
                listener.handleMove("E");
              } catch (IllegalArgumentException exc) {
                JOptionPane.showMessageDialog(frame, "Invalid move!");
              }
            }

            if (e.getSource() == westButton) {
              try {
                listener.handleMove("W");
              } catch (IllegalArgumentException exc) {
                JOptionPane.showMessageDialog(frame, "Invalid move!");
              }
            }

            // update West panel content
            paintWestPanel();

            // update East panel content
            paintEastPanel();

            //display type of current position
            if (model.inCaveNow()) {
              buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setBackground(Color.ORANGE);
            }
            else {
              buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setBackground(Color.GRAY);
            }
            buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setText("<html>"
                    + spotInfoText().replaceAll("\\n", "<br>") + "</html>");

            //repaint game board
            updateGameBoard();

            checkMoveResult(listener);

            if (!model.isGameOn()) {
              gameOver();
            }
          } // end of make a move

    }; //end of MouseAdapterImpl class

    northButton.addMouseListener(dirAdapter);
    southButton.addMouseListener(dirAdapter);
    eastButton.addMouseListener(dirAdapter);
    westButton.addMouseListener(dirAdapter);
  }
  //~~~~~~~~~ end of [Direction Listener] ~~~~~~~~~~~

  /**
   * Add the listener to Pickup button.
   *
   * @param listener the controller to use
   */
  public void addPickListener(Controller listener) {
    // create the MouseAdapter

    MouseAdapter pickHandler = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        Object[] option = {"Arrow", "Treasure"};
        int n = JOptionPane.showOptionDialog(frame, "Pickup what? \n","Choose stuff",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, option, option[0]);
        if (n == 0) {
          try {
            listener.handlePickup("arrow");
            JOptionPane.showMessageDialog(frame, "You pickup arrow");
          }
          catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, "No arrow here");
          }
        }
        else {
          String treasure = "";
          if (model.currentPosition().getGemList().toString().toLowerCase().contains("ruby")) {
            treasure = "Ruby";
          }
          if (model.currentPosition().getGemList().toString().toLowerCase().contains("diamond")) {
            treasure = "Diamond";
          }
          if (model.currentPosition().getGemList().toString().toLowerCase().contains("sapphire")) {
            treasure = "Sapphire";
          }

          try {
            listener.handlePickup(treasure);
            JOptionPane.showMessageDialog(frame, "You pickup a " + treasure);
          }
          catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(frame, "No stuff here");
          }

        }
        paintWestPanel();
        updateGameBoard();

      }
    };
    pickButton.addMouseListener(pickHandler);

  }
  //======== end of [Pick Listener] ===========


  /**
   * Add the listener to SHOOT button.
   *
   * @param listener the controller to use
   */
  public void addShootListener(Controller listener) {
    // create the MouseAdapter
    MouseAdapter shootHandler = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int shootDis = 0;
        while (shootDis < 1 || shootDis > 5) {
          try {
            shootDis = Integer.parseInt(JOptionPane.showInputDialog("Distance to shoot [1-5]:"));
          }
          catch (Exception ex) {
            System.out.println("Incorrect input format");
          }
        }
        System.out.println("Type in " + shootDis);

        String shootDir = "";
        while ( !shootDir.equalsIgnoreCase("n")
              && !shootDir.equalsIgnoreCase("e")
              && !shootDir.equalsIgnoreCase("s")
              && !shootDir.equalsIgnoreCase("w")) {
          try {
            shootDir = JOptionPane.showInputDialog("Direction to shoot [n/e/s/w]:");
          }
          catch (Exception ex) {
            System.out.println("Incorrect input format");
          }
        }
        System.out.println("Type in " + shootDir);
        // make the shot
        String feedback = String.valueOf(listener.handleShoot(shootDis,shootDir));
        String res = "";
        if (feedback.contains("[hit]")) {
          res += "\nYou hear great howl from distance";
        }
        if (feedback.contains("[miss]")) {
          res += "\nYou shoot arrow into darkness";
        }
        paintWestPanel();
        paintShootPanel(res);
        shootPanel.setVisible(true);

      }
    };
    shootButton.addMouseListener(shootHandler);
  }
  //++++++++ end of [Shoot Listener] +++++++++


  /**
   * Add the listener to buttons on the GAME Board.
   *
   * @param listener the controller to use
   */
  public void addClickListener(Controller listener) {

    // create the MouseAdapter
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int row = -1;
        int col = -1;
        for (int i = 0; i < 10; i++) {
          for (int j = 0; j < 10; j++) {
            if (e.getSource() == buttons[i][j]) {
              row = i;
              col = j;
            }
          }
        }
        //display the selected button
        //check spot valid or not
        if (Math.abs(model.getPlayerPos()[0] - row) > 1
                || Math.abs(model.getPlayerPos()[1] - col) > 1
                || (Math.abs(model.getPlayerPos()[0] - row) == 1
                && Math.abs(model.getPlayerPos()[1] - col) == 1)) {
          System.out.println("Illegal move!");
        }
        else { //make legal move
          // move north
          if ((model.getPlayerPos()[0] - row == 1) && (model.getPlayerPos()[1] == col)) {
            try {
              listener.handleMove("N");
            }
            catch (IllegalArgumentException exc) {
              JOptionPane.showMessageDialog(frame, "Invalid move!");
            }
          }
          // move to south
          if ((model.getPlayerPos()[0] - row == -1) && (model.getPlayerPos()[1] == col)) {
            try {
              listener.handleMove("S");
            }
            catch (IllegalArgumentException exc) {
              JOptionPane.showMessageDialog(frame, "Invalid move!");
            }
          }
          // move to east
          if ((model.getPlayerPos()[0] == row) && (model.getPlayerPos()[1] - col == -1)) {
            try {
              listener.handleMove("E");
            }
            catch (IllegalArgumentException exc) {
              JOptionPane.showMessageDialog(frame, "Invalid move!");
            }
          }
          // move to west
          if ((model.getPlayerPos()[0] == row) && (model.getPlayerPos()[1] - col == 1)) {
            try {
              listener.handleMove("W");
            }
            catch (IllegalArgumentException exc) {
              JOptionPane.showMessageDialog(frame, "Invalid move!");
            }
          }

          //display type of current position
          if (model.inCaveNow()) {
            buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setBackground(Color.ORANGE);
          }
          else {
            buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setBackground(Color.GRAY);
          }
          buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].setText("<html>"
                  + spotInfoText().replaceAll("\\n", "<br>") + "</html>");

          //highlight current spot
          buttons[model.getPlayerPos()[0]][model.getPlayerPos()[1]].requestFocus();

          // update West panel content
          refreshTheFrame();

          checkMoveResult(listener);

          //reaching the end or eaten by monster
          if (!model.isGameOn()) {
            gameOver();
          }
        } // end of make a move
      }
    }; //end of MouseAdapterImpl class

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        buttons[i][j].addMouseListener(clickAdapter);
      }
    }
  }
  //~~~~~~~~~ end of [Click Listener] ~~~~~~~~~~~~


  public void checkMoveResult(Controller listener) {
    if (model.checkMonsterState() == MonsterStatus.Normal
            || model.checkMonsterState() == MonsterStatus.Injured) {
      Object[] option = {"Yes", "No"};
      int n = JOptionPane.showOptionDialog(frame, "Do you want to exchange "
                      + "4 treasure for a weapon? \n","Use Treasure for Weapon",
              JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
              null, option, option[0]);
      if (n == 0) { //choose to buy weapon

        if (model.getGemNumber() >= 4) {
          try {
            listener.exchangeWeapon();
            System.out.println("monster is dead");
            JOptionPane.showMessageDialog(frame, "Monster is dead");
            Icon icon = new ImageIcon("C:\\Users\\Rexx\\Downloads\\patrick.gif");
            JOptionPane.showMessageDialog(null, "", "Monster Dead",
                    JOptionPane.INFORMATION_MESSAGE, icon);
            refreshTheFrame();
          }
          catch (IllegalStateException ex) {
            System.out.println("not enough gem");
            JOptionPane.showMessageDialog(frame, "No enough treasure");
            listener.terminateGame();
          }
        }
        else { //no enough gems
          System.out.println("No 4 gems");
          JOptionPane.showMessageDialog(frame, "No 4 gems");
          listener.terminateGame();
        }
      }
      if (n == 1) { //not to buy weapon
        //if monster is normal or (injured and player NO change to escape)
        if (model.checkMonsterState() == MonsterStatus.Normal
                || (model.checkMonsterState() == MonsterStatus.Injured
                && randomOneZero() == 0)) {
          System.out.println("No weapon, eaten by monster");
          JOptionPane.showMessageDialog(frame, "No weapon, eaten by monster");
          listener.terminateGame();
        }
        else { //monster injured and player get change to escape
          JOptionPane.showMessageDialog(frame, "Run! There is Otyugh here");
        }
      }
    }
  }

  @Override
  public void paintEastPanel() {
    if (!model.curAvailableDir().contains("N")) {
      northButton.setBackground(Color.GRAY);
    }
    else {
      northButton.setBackground(Color.WHITE);
    }

    if (!model.curAvailableDir().contains("S")) {
      southButton.setBackground(Color.GRAY);
    }
    else {
      southButton.setBackground(Color.WHITE);
    }

    if (!model.curAvailableDir().contains("E")) {
      eastButton.setBackground(Color.GRAY);
    }
    else {
      eastButton.setBackground(Color.WHITE);
    }

    if (!model.curAvailableDir().contains("W")) {
      westButton.setBackground(Color.GRAY);
    }
    else {
      westButton.setBackground(Color.WHITE);
    }
  }

  @Override
  public void blackTheBoard() {
    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        buttons[i][j].setText("");
        buttons[i][j].setBackground(Color.BLACK);
      }
    }
  }

  @Override
  public void updateGameBoard() {
    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        //current spot info
        if ( i * 10 + j == this.model.currentPosition().getIndex() ) {
          //display type of position
          buttons[i][j].setFocusable(true);
          if (this.model.currentPosition() instanceof Cave) {
            buttons[i][j].setBackground(Color.ORANGE);
          }
          else {
            buttons[i][j].setBackground(Color.LIGHT_GRAY);
          }
          buttons[i][j].setBorder(new LineBorder(Color.RED));
          //buttons[i][j].setForeground(Color.CYAN);
          buttons[i][j].setText("<html>"
                  + spotInfoText().replaceAll("\\n", "<br>") + "</html>");
          paintEastPanel();
        }
        // place in visit history
        else if (this.model.getPlayerHistory().contains(i * 10 + j)) {
          if (this.model.getDungeonSpot(i * 10 + j) instanceof Cave) {
            buttons[i][j].setBackground(Color.ORANGE);
            buttons[i][j].setBorder(new LineBorder(Color.GRAY));
          }
          else {
            buttons[i][j].setBackground(Color.LIGHT_GRAY);
            buttons[i][j].setBorder(new LineBorder(Color.GRAY));
          }
        }
        // place not in history
        else {
          buttons[i][j].setBackground(Color.BLACK);
          buttons[i][j].setBorder(new LineBorder(Color.GRAY));
        }

      }
    }
  }


  @Override
  public void refreshGameBoard() {
    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        //current spot info
        if ( i * 10 + j == this.model.currentPosition().getIndex() ) {
          //display type of position
          buttons[i][j].setFocusable(true);
          if (this.model.currentPosition() instanceof Cave) {
            buttons[i][j].setBackground(Color.ORANGE);
          }
          else {
            buttons[i][j].setBackground(Color.LIGHT_GRAY);
          }
          buttons[i][j].setBorder(new LineBorder(Color.RED));
          //buttons[i][j].setForeground(Color.CYAN);
          buttons[i][j].setText("<html>"
                  + spotInfoText().replaceAll("\\n", "<br>") + "</html>");
          paintEastPanel();
        }
        else {
          buttons[i][j].setBackground(Color.BLACK);
          buttons[i][j].setBorder(new LineBorder(Color.GRAY));
          buttons[i][j].setText("");
        }
      }
    }
  }

  @Override
  public void paintCentralPanel() {
    //System.out.println("painting central panel");
    int rowNum = this.model.getDungeonNumberOfRows();
    int colNum = this.model.getDungeonNumberOfCols();

    for (int i = 0; i < rowNum; i++) {
      for (int j = 0; j < colNum; j++) {
        buttons[i][j] = new JButton();
        buttons[i][j].setPreferredSize(new Dimension(120, 120));
        buttons[i][j].setFocusable(false);
        buttons[i][j].setFont(new Font("Verdana", Font.BOLD, 12));
        //current spot info
        if (i * 10 + j == model.currentPosition().getIndex()
                || this.model.getPlayerHistory().contains(i * 10 + j)) {
          //display type of position
          buttons[i][j].setFocusable(true);
          if (this.model.getDungeonSpot(i * 10 + j) instanceof Cave) {
            buttons[i][j].setBackground(Color.ORANGE);
          }
          else {
            buttons[i][j].setBackground(Color.GRAY);
          }
          buttons[i][j].setText("<html>"
              + spotInfoText().replaceAll("\\n", "<br>") + "</html>");
          paintEastPanel();
        }
        else {
          buttons[i][j].setBackground(Color.BLACK);
        }

        //add button to panel
        buttonPanel.add(buttons[i][j]);
      }
    }

    scrollPane = new JScrollPane(buttonPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBounds(0, 0, 800, 800);
    scrollPane.setFocusable(false);

    centralPanel = new JPanel(null);
    centralPanel.setBackground(Color.LIGHT_GRAY);
    centralPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    // add scrollPane to central panel
    centralPanel.add(scrollPane);

    //add key binding
    centralPanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
    centralPanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
    centralPanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
    centralPanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");

  }
  //end of paintCentralPanel()


  /**
   * Add the listener to handle the keyboard input.
   *
   * @param listener the controller to use
   */
  public void addKeyBinding(Controller listener) {
    System.out.println("adding Key Binding...");
    centralPanel.getActionMap().put("upAction", new UpAction(listener));
    centralPanel.getActionMap().put("downAction", new DownAction(listener));
    centralPanel.getActionMap().put("leftAction", new LeftAction(listener));
    centralPanel.getActionMap().put("rightAction", new RightAction(listener));
  }

  class UpAction extends AbstractAction {
    private Controller listener;
    UpAction(Controller l) {
      listener = l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      listener.handleMove("N");
      checkMoveResult(listener);
      refreshTheFrame();
      System.out.println("Pressed UP");
      if (!model.isGameOn()) {
        gameOver();
      }
    }
  }

  class DownAction extends AbstractAction {
    private Controller listener;
    DownAction(Controller l) {
      listener = l;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      listener.handleMove("S");
      refreshTheFrame();
      checkMoveResult(listener);
      System.out.println("Pressed Down");
      if (!model.isGameOn()) {
        gameOver();
      }
    }
  }

  class LeftAction extends AbstractAction {
    private Controller listener;
    LeftAction(Controller l) {
      listener = l;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      listener.handleMove("W");
      refreshTheFrame();
      checkMoveResult(listener);
      System.out.println("Pressed Left");
      if (!model.isGameOn()) {
        gameOver();
      }
    }
  }

  class RightAction extends AbstractAction {
    private Controller listener;
    RightAction(Controller l) {
      listener = l;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      listener.handleMove("E");
      refreshTheFrame();
      checkMoveResult(listener);
      System.out.println("Pressed Right");
      if (!model.isGameOn()) {
        gameOver();
      }
    }
  }


  @Override
  public void paintWestPanel() {

    playerInfo.setText("" + model.getThePlayerState());

    String curInfo = model.getThePlayerState();
    curInfo += "\n\n\n Current " + model.currentPosition().toString();
    int[] des = model.indexToCoor(model.getGameEndPoint());
    curInfo += "\n\nDestination (" + des[0] + ", " + des[1] + ")";

    spotInfo.setText("<html>"
            + curInfo.replaceAll("\\n", "<br>")
            + "</html>");
  }


  @Override
  public void gameOver() {

    String reason;
    if (model.checkArriveEnd()) {
      reason = "Arrived End";
    }
    else {
      reason = "Killed by monster";
    }

    Object[] option = {"Exit"};
    int n = JOptionPane.showOptionDialog(frame, "Game is over!\n" + reason,
            "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, option, option[0]);
    if (n == 0) {
      frame.dispose();
      System.exit(0);
    }
  }

  @Override
  public String spotInfoText() {
    String text = "";

    //get available directions
    if (model.currentPosition().getWest() == 1) {
      text += "←";
    } else {
      text += " ";
    }
    if (model.currentPosition().getNorth() == 1) {
      text += "↑";
    } else {
      text += " ";
    }
    if (model.currentPosition().getSouth() == 1) {
      text += "↓";
    } else {
      text += " ";
    }
    if (model.currentPosition().getEast() == 1) {
      text += "→\n";
    } else {
      text += " \n";
    }

    //get smell info
    text += model.currentPosition().getSmell();

    //get arrow number
    if (model.currentPosition().getArrow() > 0) {
      text += "\nArrow: " + model.currentPosition().getArrow();
    }

    if (model.inCaveNow()) {
      //get treasure info
      if (model.currentPosition().getGemList().size() > 0) {
        text += "\n" + model.currentPosition().getGemList().toString();
      }
      if (model.currentPosition().getSmell().equalsIgnoreCase("stinky")) {
        if (Objects.equals(model.currentPosition().getMonsterStatus(),
                MonsterStatus.Dead)) {
          text += "\n[Dead Otyguh] ";
        }
        else {
          text += "\nOtyguh ";
        }
      }
    }
    return text;
  }

  @Override
  public void paintShootPanel(String res) {
    //hide the panel
    shootPanel.setVisible(false);
    int rNumber = randomNumber(1,4);
    System.out.println("Color: " + rNumber);
    Color fontColor;

    switch (rNumber) {
      case 1:
        fontColor = Color.RED;
        break;
      case 2:
        fontColor = Color.YELLOW;
        break;
      case 3:
        fontColor = Color.MAGENTA;
        break;
      default:
        fontColor = Color.BLUE;
    }

    shootResult.setFont(new Font("Verdana", Font.BOLD, 16));
    shootResult.setText("<html>" + res.replaceAll("\\n", "<br>") + "</html>");
    shootResult.setForeground(fontColor);
    shootPanel.add(shootResult);
    shootResult.setVisible(true);
  }

  public void refreshTheFrame() {
    paintWestPanel();
    paintEastPanel();
    //refreshGameBoard();
    updateGameBoard();
  }

}
