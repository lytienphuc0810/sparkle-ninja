// remember to change.
package sparkle.ninja;

import ass2_library.AI_assignment2;
import ass2_library.BaseOfWhite;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class White extends BaseOfWhite {

  public int white_king = 20000;  
  public int white_rook = 500;
  public int black_king = 20000;
  public boolean white = true;
  public boolean black = false;
  
  public int moveset[][] = {{0, 1},   
                            {1, 0},   
                            {1, 1},   
                            {0, -1},                            
                            {-1, 0},                               
                            {0, -1},                            
                            {-1, 1},                            
                            {1, 1}};

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
  public int init_white_king_x = -1;  
  public int init_white_king_y = -1;
  public int init_white_rook_x = -1;
  public int init_white_rook_y = -1;
  public int init_black_king_x = -1;
  public int init_black_king_y = -1;

  public int white_king_x = -1;  
  public int white_king_y = -1;
  public int white_rook_x = -1;
  public int white_rook_y = -1;
  public int black_king_x = -1;
  public int black_king_y = -1;

  public int depth = 3;
  
  public list pre_state = new list();

  public class state{
    public int white_king_x = -1;  
    public int white_king_y = -1;
    public int white_rook_x = -1;
    public int white_rook_y = -1;
    public int black_king_x = -1;
    public int black_king_y = -1;

    public state(int x1, int y1, int x2, int y2, int x3, int y3){
      white_king_x = x1;  
      white_king_y = y1;
      white_rook_x = x2;
      white_rook_y = y2;
      black_king_x = x3;
      black_king_y = y3;
    }

    public boolean equal(int x1, int y1, int x2, int y2, int x3, int y3){
      return (
              white_king_x == x1 &&  
              white_king_y == y1 &&
              white_rook_x == x2 &&
              white_rook_y == y2 &&
              black_king_x == x3 &&
              black_king_y == y3);
    }
  }

  public class node{
    public state st;
    public node next;

    public node(state st1){
      st = st1;
      next = null;
    }
  }

  public class list{
    public node head;
    public node tail;

    public list(){
      head = null;
      tail = null;
    }

    public void add(state st1){
      if(head == null){
        head = new node(st1);
        tail = head;
      }
      else{
        tail.next = new node(st1);
        tail = tail.next;
      }
    }

    public void save_state(){
      add(new state(white_king_x, white_king_y, white_rook_x, white_rook_y, black_king_x, black_king_y));
    }
    
    public void remove_state(){
      if(head == tail){
        head = null;
        tail = null;
      }
      else{
        node temp = head;
        while(temp.next != tail){
          temp = temp.next;
        }
        tail = temp;
        tail.next = null;
      }
    }

    public boolean contains_current(){
      node temp = head;
      while(temp != null){
        if(temp.st.equal(white_king_x, white_king_y, white_rook_x, white_rook_y, black_king_x, black_king_y)){
          return true;
        }
        temp = temp.next;
      }
      return false;
    }
  }

  public boolean valid_position(int x, int y){
    return (x >= 0 && x < 8 && y >= 0 && y < 8);
  }

  public int evaluation(){
    int result = 0;
    result += white_rook + white_rook_table[7 - white_rook_y][white_rook_x];
    result += white_king + white_king_table[7 - white_king_y][white_king_x];
    result -= black_king + white_king_table[7 - black_king_y][black_king_x];
    result += in_check_rook() + in_check_king(white) + in_check_king(black);
    return result;
  }
  
  public int in_check_rook(){
    if(black_king_x == white_rook_x){
      if(!(white_king_x == white_rook_x && ( (white_king_y > white_rook_y && white_king_y < black_king_y) || (white_king_y < white_rook_y && white_king_y > black_king_y) ))){
        for(int i = 0; i <= 7; i++){
          if(i ==  black_king_y){
            return 1000;
          }
        }
      }
    }
    if(black_king_y == white_rook_y){
      if(!(white_king_y == white_rook_y && ( (white_king_x > white_rook_x && white_king_x < black_king_x) || (white_king_x < white_rook_x && white_king_x > black_king_x) ))){
        for(int i = 0; i <= 7; i++){
          if(i ==  black_king_x){
            return 1000;
          }
        }
      }
    }
    return 0;
  }
  
  public int in_check_king(boolean white){
    if(white){
      if(Math.abs(white_king_x - black_king_x) <= 1 && Math.abs(white_king_y - black_king_y) <= 1){
        return 1000;
      }
      else{
        return 0;
      }
    }
    else{
      if(Math.abs(white_king_x - black_king_x) <= 1 && Math.abs(white_king_y - black_king_y) <= 1){
        if(Math.abs(white_rook_x - black_king_x) <= 1 && Math.abs(white_rook_y - black_king_y) <= 1){
          return -1500;
        }
        else{
          return -1000;
        }
      }
      else{
         if(Math.abs(white_rook_x - black_king_x) <= 1 && Math.abs(white_rook_y - black_king_y) <= 1){
          return -500;
        }
        else{
          return 0;
        }
      }
    }
  }
  
  public boolean invalid_move_rook(int x, int y){
    if(!valid_position(x, y) || (x == white_king_x && y == white_king_y)){
      return true;
    }
    return false;
  }
  
  public boolean invalid_move_king(int x, int y){
    if(!valid_position(x, y) || (x == white_rook_x && y == white_rook_y)){
      return true;
    }
    return false;
  }

  public int max(int current_depth){
  // rook 16 moves
  // king 8 moves
  // repeated states?
  // chi toi luot cua minh thi moi ko tinh chuyen ko di lai

    if(current_depth > depth){
      return evaluation();
    }

    int temp, temp1, step_x, step_y;
    int max = -1;

    boolean rook = false;
    boolean unset = true;

    temp = white_rook_x;
    for(int i = 0; i < 8; i++){
      if(pre_state.contains_current() || invalid_move_rook(i, white_rook_y)){
        continue;
      }
      else{
        white_rook_x = i;
        pre_state.save_state();
        temp1 = min(current_depth + 1);
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
          step_x = white_rook_x;
          step_y = white_rook_y;
        }
        pre_state.remove_state();
      }
    }
    white_rook_x = temp;

    temp = white_rook_y;
    for(int i = 0; i < 8; i++){
      if(pre_state.contains_current() || invalid_move_rook(white_rook_x, i)){
        continue;
      }
      else{
        white_rook_y = i;
        temp1 = min(current_depth + 1);
        pre_state.save_state();
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
          step_x = white_rook_x;
          step_y = white_rook_y;
        }
        pre_state.remove_state();
      }
    }
    white_rook_y = temp;

    int temp_x = white_king_x;    
    int temp_y = white_king_y;

    for(int i = 0; i < 8; i++){
      if(pre_state.contains_current() || invalid_move_king(white_king_x + moveset[i][0], white_king_y + moveset[i][1])){
        continue;
      }
      else{
        white_king_x += moveset[i][0];        
        white_king_y += moveset[i][1];
        pre_state.save_state();
        temp1 = min(current_depth + 1);
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
          step_x = white_king_x;
          step_y = white_king_y;
        }
        pre_state.remove_state();
      }
      white_king_x = temp_x;
      white_king_y = temp_y;
    }
    
    return max;
  }
  
  public int min(int current_depth){
    if(current_depth > depth){
      return evaluation();
    }

    int temp1, step_x, step_y;
    int min = -1;

    boolean unset = true;

    int temp_x = black_king_x;   
    int temp_y = black_king_y;

    for(int i = 0; i < 8; i++){
      if(pre_state.contains_current() || invalid_move_king(white_king_x + moveset[i][0], white_king_y + moveset[i][1])){
        continue;
      }
      else{
        black_king_x += moveset[i][0];        
        black_king_y += moveset[i][1];
        pre_state.save_state();
        temp1 = min(current_depth + 1);
        if(unset || temp1 < min){
          min = temp1;
          unset = false;
          step_x = black_king_x;
          step_y = black_king_y;
        }
        pre_state.remove_state();
      }
      black_king_x = temp_x;
      black_king_y = temp_y;
    }
    
    return min;
  }
  
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
  }
}