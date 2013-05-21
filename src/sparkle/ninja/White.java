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
  public int king_check = 10000;  
  public int rook_check = 5000;
  
  public list pre_state = new list();

  private boolean check(boolean white) {
    if(white){
      return (in_check_rook() + in_check_king(true) > 0);
    }
    else{
      return (in_check_king(false) < 0);
    }
  }

  public class state{
    public int state_white_king_x = -1;  
    public int state_white_king_y = -1;
    public int state_white_rook_x = -1;
    public int state_white_rook_y = -1;
    public int state_black_king_x = -1;
    public int state_black_king_y = -1;

    public state(int x1, int y1, int x2, int y2, int x3, int y3){
      state_white_king_x = x1;  
      state_white_king_y = y1;
      state_white_rook_x = x2;
      state_white_rook_y = y2;
      state_black_king_x = x3;
      state_black_king_y = y3;
    }

    public boolean equal(int x1, int y1, int x2, int y2, int x3, int y3){
      return (
              state_white_king_x == x1 &&  
              state_white_king_y == y1 &&
              state_white_rook_x == x2 &&
              state_white_rook_y == y2 &&
              state_black_king_x == x3 &&
              state_black_king_y == y3);
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
          System.out.println("state thot");
          return true;
        }
        temp = temp.next;
      }
      return false;
    }
  }
  
  public char get_char(int n){
    switch(n){
      case 0:
        return 'a';
      case 1:
        return 'b';
      case 2:
        return 'c';
      case 3:
        return 'd';
      case 4:
        return 'e';
      case 5:
        return 'f';
      case 6:
        return 'g';
      case 7:
        return 'h';
      default:
        return 'p';
    }
  }
  
  public int get_int(char chr){
    switch(chr){
      case 'a':
        return 0;
      case 'b':
        return 1;
      case 'c':
        return 2;
      case 'd':
        return 3;
      case 'e':
        return 4;
      case 'f':
        return 5;
      case 'g':
        return 6;
      case 'h':
        return 7;
      default:
        return -1;
    }
  }

  public boolean valid_position(int x, int y){
    return (x >= 0 && x < 8 && y >= 0 && y < 8);
  }

  public int evaluation(){
    int result = 0;
    result += white_rook;// + white_rook_table[7 - white_rook_y][white_rook_x];
    result += white_king;// + white_king_table[7 - white_king_y][white_king_x];
    result -= black_king;// + white_king_table[7 - black_king_y][black_king_x];
    result += in_check_rook() + in_check_king(white) + in_check_king(black);
    return result;
  }
  
  public int in_check_rook(){
    for(int i = 1; i < 8; i++){
      if(white_rook_x + i > 7 || (white_rook_x + i == white_king_x && white_rook_y == white_king_y)){
        break;
      }
      if(white_rook_x + i == black_king_x && white_rook_y == black_king_y){
        return king_check; 
      }
    }
    
    for(int i = 1; i < 8; i++){
      if(white_rook_x - i < 0 || (white_rook_x - i == white_king_x && white_rook_y == white_king_y)){
        break;
      }
      if(white_rook_x - i == black_king_x && white_rook_y == black_king_y){
        return king_check; 
      }
    }
    
    for(int i = 1; i < 8; i++){
      if(white_rook_y + i > 7 || (white_rook_x == white_king_x && white_rook_y + i == white_king_y)){
        break;
      }
      if(white_rook_x == black_king_x && white_rook_y + i == black_king_y){
        return king_check; 
      }
    }
    
    for(int i = 1; i < 8; i++){
      if(white_rook_y - i < 0 || (white_rook_x == white_king_x && white_rook_y - i == white_king_y)){
        break;
      }
      if(white_rook_x == black_king_x && white_rook_y - i == black_king_y){
        return king_check; 
      }
    }
    return 0;
  }
  
  public int in_check_king(boolean white){
    int result = 0;
    if(white){
      if(Math.abs(white_king_x - black_king_x) <= 1 && Math.abs(white_king_y - black_king_y) <= 1){
        result += king_check;
      }
    }
    else{
      if(Math.abs(white_king_x - black_king_x) <= 1 && Math.abs(white_king_y - black_king_y) <= 1){
        result -= king_check;
      }
      if(Math.abs(white_rook_x - black_king_x) <= 1 && Math.abs(white_rook_y - black_king_y) <= 1){
        result -= rook_check;
      }
    }
    return result;
  }
  
  public boolean invalid_move_rook(int x, int y){
    if(!valid_position(x, y) || (x == white_king_x && y == white_king_y) || (x == black_king_x && y == black_king_y)){
      System.out.println("rook thot");
      return true;
    }
    return false;
  }
  
  public boolean invalid_move_king(int x, int y){
    if(!valid_position(x, y) || (x == white_rook_x && y == white_rook_y) || (white_king_x == black_king_x && white_king_y == black_king_y)){
      System.out.println("king thot");
      return true;
    }
    return false;
  }

  public int max(int current_depth, String step){
    System.out.println("max " + current_depth);
    if(current_depth > depth || check(true)){
      System.out.println(step + " " + evaluation());
      return evaluation();
    }
    
    String str;
    int temp, temp1;
    int max = -1;
    boolean unset = true;

    temp = white_rook_x;
    for(int i = 0; i < 8; i++){

      white_rook_x = i;
      
      if(pre_state.contains_current() || invalid_move_rook(i, white_rook_y)){
        continue;
      }
      else{
        pre_state.save_state();
        
        str = step + " R" + get_char(white_rook_x) + white_rook_y;
        temp1 = min(current_depth + 1, str);
        
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
        }
        pre_state.remove_state();
      }
    }
    white_rook_x = temp;

    temp = white_rook_y;
    for(int i = 0; i < 8; i++){
      
      white_rook_y = i;
  
      if(pre_state.contains_current() || invalid_move_rook(white_rook_x, i)){
        continue;
      }
      else{
        str = step + " R" + get_char(white_rook_x) + white_rook_y;
        temp1 = min(current_depth + 1, str);
        pre_state.save_state();
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
        }
        pre_state.remove_state();
      }
    }
    white_rook_y = temp;

    int temp_x = white_king_x;    
    int temp_y = white_king_y;

    for(int i = 0; i < 8; i++){
      white_king_x += moveset[i][0];        
      white_king_y += moveset[i][1];
      
      if(pre_state.contains_current() || invalid_move_king(white_king_x + moveset[i][0], white_king_y + moveset[i][1])){
        continue;
      }
      else{
        pre_state.save_state();
        str = step + " K" + get_char(white_king_x) + white_king_y;
        temp1 = min(current_depth + 1, str);
        if(unset || temp1 > max){
          max = temp1;
          unset = false;
        }
        pre_state.remove_state();
      }
      white_king_x = temp_x;
      white_king_y = temp_y;
    }
    
    return max;
  }
  
  public int min(int current_depth, String step){
    System.out.println("min" + current_depth);
    if(current_depth > depth || check(false)){
      System.out.println(step);
      return evaluation();
    }

    int temp1;
    int min = -1;
    String str;
    boolean unset = true;
    int temp_x = black_king_x;   
    int temp_y = black_king_y;

    for(int i = 0; i < 8; i++){
      black_king_x += moveset[i][0];        
      black_king_y += moveset[i][1];
      
      if(pre_state.contains_current() || invalid_move_king(black_king_x + moveset[i][0], black_king_y + moveset[i][1])){
        continue;
      }
      else{
        pre_state.save_state();
        str = step + " BK" + get_char(black_king_x) + black_king_y; 
        temp1 = max(current_depth + 1, str);
        if(unset || temp1 < min){
          min = temp1;
          unset = false;
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
    white_king_x = get_int(state[0].charAt(1));    
    white_king_y = Integer.parseInt(state[0].substring(2, 3)) - 1;
    
    white_rook_x = get_int(state[1].charAt(1));    
    white_rook_y = Integer.parseInt(state[1].substring(2, 3)) - 1;
    
    black_king_x = get_int(state[2].charAt(1));    
    black_king_y = Integer.parseInt(state[2].substring(2, 3)) - 1;
    System.out.println(white_king_x + " " + white_king_y);    
    System.out.println(white_rook_x + " " + white_rook_y);    
    System.out.println(black_king_x + " " + black_king_y);    
  }

  @Override
  public String whiteMove(String blackMove) {
    black_king_x = get_int(blackMove.charAt(1));    
    black_king_y = Integer.parseInt(blackMove.substring(2, 3)) - 1;

    max(0, "");
    return ("123");
  }

  public static void main(String[] args) {
    White white = new White();
//    AI_assignment2.setWhite(white);
//    try {
//        AI_assignment2.run();
//    } catch (IOException ex) {
//        ex.printStackTrace();
//    }
    String[] asd = {"Kd3","Ra1","Kg3"};
    white.initState(asd);
    white.whiteMove("Kg2");
  }
}