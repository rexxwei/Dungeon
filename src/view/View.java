package view;

import control.Controller;

/**
 * The interface to specified method for a view class.
 */
public interface View {

  void turnOnGameView();

  /**
   * Add the listener(controller) for the component.
   *
   * @param listener the listener to use
   */
  void addGameListener(Controller listener);

  /**
   * display arrow, pickup and shoot buttons in east side panel.
   */
  void paintEastPanel();

  void blackTheBoard();

  /**
   * Update the state of game in central panel.
   *
   */
  void updateGameBoard();

  /**
   * Update the state of game board.
   */
  void refreshGameBoard();

  /**
   * draw the board of game in central panel.
   */
  void paintCentralPanel();

  /**
   * display info about player and position in west side panel.
   */
  void paintWestPanel();

  /**
   * Show the message of ending a game to player.
   *
   */
  void gameOver();

  /**
   * Get the info about a position.
   *
   * @return the info as string
   */
  String spotInfoText();

  /**
   * Update the info in shooting result area.
   *
   * @param res the result of shooting
   */
  void paintShootPanel(String res);

}
