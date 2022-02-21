package control;

import model.Model;

/**
 * The interface of command controller.
 */
public interface CommandController {
  /**
   * Starting point for the controller.
   *
   * @param m the model to use
   */
  void execute(Model m);

}
