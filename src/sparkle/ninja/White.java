package sparkle.ninja;

import ass2_library.BaseOfWhite;
import ass2_library.AI_assignment2;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class White extends BaseOfWhite {

  public int white_rook_table[][] = { {0,  0,  0,  0,  0,  0,  0,  0}, 
                                      {5, 10, 10, 10, 10, 10, 10,  5},
                                      {-5,  0,  0,  0,  0,  0,  0, -5}, 
                                      {-5,  0,  0,  0,  0,  0,  0, -5}, 
                                      {-5,  0,  0,  0,  0,  0,  0, -5}, 
                                      {-5,  0,  0,  0,  0,  0,  0, -5}, 
                                      {-5,  0,  0,  0,  0,  0,  0, -5}, 
                                      {0,  0,  0,  5,  5,  0,  0,  0} };

  public int white_king_table[][] = { {-50, -40, -30, -20, -20, -30, -40, -50}, 
                                      {-30, -20, -10,   0,   0, -10, -20, -30},
                                      {-30, -10,  20,  30,  30,  20, -10, -30}, 
                                      {-30, -10,  30,  40,  40,  30, -10, -30}, 
                                      {-30, -10,  30,  40,  40,  30, -10, -30}, 
                                      {-30, -10,  20,  30,  30,  20, -10, -30}, 
                                      {-30, -30,   0,   0,   0,   0, -30, -30}, 
                                      {-50, -30, -30, -30, -30, -30, -30, -50} };
  
  @Override
  public void initState(String[] state) {
  }

  @Override
  public String whiteMove(String blackMove) {
    return null;
  }

  public static void main(String[] args) {
    White white = new White();
    AI_assignment2.setWhite(white);
    try {
        AI_assignment2.run();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        System.out.print(white.white_rook_table[i][j] + " ");
        
      }
      System.out.println("");
    }
  }
}