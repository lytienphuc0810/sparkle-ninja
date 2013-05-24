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
  
  public White(){
    pre_state = new list();
  }
  
  public int moveset[][] = {{0, 1},   
                            {0, -1},   
                            {1, 0},   
                            {1, 1},                            
                            {1, -1},                               
                            {-1, 0},                            
                            {-1, 1},                            
                            {-1, -1}};

  public int extra_rook[] = {100,50,25,15,5,-5,-10,-20};
  public int extra_king[] = {30,25,20,15,5,-20,-50,-100};
  
  public int white_rook_table[][] = { {10,  20,  20,  20,  20,  20,  20,  10}, 
                                      {20, 10, 10, 10, 10, 10, 10,  20},
                                      {20,  0,  0,  0,  0,  0,  0, 20}, 
                                      {20,  0,  0,  0,  0,  0,  0, 20}, 
                                      {20,  0,  0,  0,  0,  0,  0, 20}, 
                                      {20,  0,  0,  0,  0,  0,  0, 20}, 
                                      {20,  10,  10,  10,  10,  10,  10, 20}, 
                                      {10,  20,  20,  20,  20,  20,  20,  10} };

  public int white_king_table[][] = { {-50, -40, -30, -20, -20, -30, -40, -50}, 
                                      {-30, -20, -10,   0,   0, -10, -20, -30},
                                      {-30, -10,  40,  30,  30,  40, -10, -30}, 
                                      {-30, -10,  30,  20,  20,  30, -10, -30}, 
                                      {-30, -10,  30,  20,  20,  30, -10, -30}, 
                                      {-30, -10,  40,  30,  30,  40, -10, -30}, 
                                      {-30, -30,   0,   0,   0,   0, -30, -30}, 
                                      {-50, -30, -30, -30, -30, -30, -30, -50} };
  
  public int white_king_x = -1;  
  public int white_king_y = -1;
  public int white_rook_x = -1;
  public int white_rook_y = -1;
  public int black_king_x = -1;
  public int black_king_y = -1;

  public int depth = 3;
  
  public boolean king_global = true;
  public boolean check_confirm = false;
  public String first_move = " ";;
  public int king_check = 10000;  
  public int rook_check = 5000;
  
  public list pre_state;
  
  public int distance(boolean king){
    if(king){
      return(extra_king[Math.abs(white_king_x - black_king_x)] + extra_king[Math.abs(white_king_x - black_king_x)]);
    }
    else{
      return(extra_rook[Math.abs(white_rook_x - black_king_x)] + extra_rook[Math.abs(white_rook_x - black_king_x)]);
    }
  }
  
  public void print_state_num(String str){
    System.out.print(str + white_king_x + " " + white_king_y + ",");    
    System.out.print(white_rook_x + " " + white_rook_y + ",");    
    System.out.println(black_king_x + " " + black_king_y);  
  }
  
  public String result_string(String str,int x, int y){
    int Y = y + 1;
    return str + get_char(x) + Y;
  }
  
  public void print_state(){
    //my_println(white_king_x + "-" + white_king_y + " " + white_rook_x + "-" + white_rook_y + " " + black_king_x + "-" + black_king_y + " ");
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

    public boolean save_state(int x1, int y1, int x2, int y2, int x3, int y3){
      state st = new state(x1, y1, x2, y2, x3, y3);
      
      node temp = head;
      while(temp != null){
        if(temp.st.equal(x1, y1, x2, y2, x3, y3)){
          return false;
        }
        temp = temp.next;
      }
      
      add(st);
      return true;
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
  
  public boolean valid_move(boolean king, boolean white, int x, int y) {
    
    if(!valid_position(x, y)){
      return false;
    }
    
    if(king){
      if(white){
        if(invalid_move_king(x, y)){
          return false;
        }
      }
    }
    else{
      if(invalid_move_rook(x, y)){
        return false;
      }
    }
    
    if(white){
      for(int i = 0; i < 8; i++){
        if(x == black_king_x + moveset[i][0] && y == black_king_y + moveset[i][1]){
          return false;
        }
      }
    }
    else{
      for(int i = 0; i < 8; i++){
        if(x == white_king_x + moveset[i][0] && y == white_king_y + moveset[i][1]){
          return false;
        }
      }

      // rook
      for(int i = 1; i < 8; i++){
        if(white_rook_x + i > 7 || (white_rook_x + i == white_king_x && white_rook_y == white_king_y)){
          break;
        }
        if(white_rook_x + i == x && white_rook_y == y){
          return false; 
        }
      }

      for(int i = 1; i < 8; i++){
        if(white_rook_x - i < 0 || (white_rook_x - i == white_king_x && white_rook_y == white_king_y)){
          break;
        }
        if(white_rook_x - i == x && white_rook_y == y){
          return false; 
        }
      }

      for(int i = 1; i < 8; i++){
        if(white_rook_y + i > 7 || (white_rook_x == white_king_x && white_rook_y + i == white_king_y)){
          break;
        }
        if(white_rook_x == x && white_rook_y + i == y){
          return false; 
        }
      }

      for(int i = 1; i < 8; i++){
        if(white_rook_y - i < 0 || (white_rook_x == white_king_x && white_rook_y - i == white_king_y)){
          break;
        }
        if(white_rook_x == x && white_rook_y - i == y){
          return false; 
        }
      }
    }
    return true;
  }

  public boolean checkmate() {
    for(int i = 0; i < 8; i++){
      if(black_king_x == white_king_x + moveset[i][0] && black_king_y == white_king_y + moveset[i][1]){
        return true;
      }
    }

    // rook
    for(int i = 1; i < 8; i++){
      if(white_rook_x + i > 7 || (white_rook_x + i == white_king_x && white_rook_y == white_king_y)){
        break;
      }
      if(white_rook_x + i == black_king_x && white_rook_y == black_king_y){
        return true; 
      }
    }

    for(int i = 1; i < 8; i++){
      if(white_rook_x - i < 0 || (white_rook_x - i == white_king_x && white_rook_y == white_king_y)){
        break;
      }
      if(white_rook_x - i == black_king_x && white_rook_y == black_king_y){
        return true; 
      }
    }

    for(int i = 1; i < 8; i++){
      if(white_rook_y + i > 7 || (white_rook_x == white_king_x && white_rook_y + i == white_king_y)){
        break;
      }
      if(white_rook_x == black_king_x && white_rook_y + i == black_king_y){
        return true; 
      }
    }

    for(int i = 1; i < 8; i++){
      if(white_rook_y - i < 0 || (white_rook_x == white_king_x && white_rook_y - i == white_king_y)){
        break;
      }
      if(white_rook_x == black_king_x && white_rook_y - i == black_king_y){
        return true; 
      }
    }
    return false;
  }
  
  public boolean pseudo_checkmate(String str){
    int temp_x, temp_y;
    char piece = str.charAt(0);
    int x = get_int(str.charAt(1));    
    int y = Integer.parseInt(str.substring(2, 3)) - 1;
    boolean result;
    if('K' == piece){
      temp_x = white_king_x;
      temp_y = white_king_y;
      white_king_x = x;
      white_king_y = y;
      result = checkmate();
      white_king_x = temp_x;      
      white_king_y = temp_y;
    }
    else{
      temp_x = white_rook_x;
      temp_y = white_rook_y;
      white_rook_x = x;
      white_rook_y = y;
      result = checkmate();
      white_rook_x = temp_x;      
      white_rook_y = temp_y;
    }
    return result;
  }
  
  public int evaluation(){
    print_state();
    int result = 0;
//    if(checkmate()){
//      result += 1000;
//    }

    result += in_check_black_king();
    result += white_rook_table[7 - white_rook_y][white_rook_x] + distance(false);
    result += white_king_table[7 - white_king_y][white_king_x] + distance(true);
    result -= white_king_table[7 - black_king_y][black_king_x];
    return result;
  }
  
  public int in_check_black_king(){
    int result = 0;
    if((Math.abs(white_king_x - black_king_x) <= 1 && Math.abs(white_king_y - black_king_y) <= 1) || (Math.abs(white_rook_x - black_king_x) <= 1 && Math.abs(white_rook_y - black_king_y) <= 1)){
      result = -100000;
    }
    return result;
  }
  
  public boolean invalid_move_rook(int x, int y){
    if((x == white_king_x && y >= white_king_y && white_rook_y < white_king_y)){
      return true;
    }
    if((x == white_king_x && y <= white_king_y && white_rook_y > white_king_y)){
      return true;
    }
    if((y == white_king_y && x >= white_king_x && white_rook_x < white_king_x)){
      return true;
    }
    if((y == white_king_y && x <= white_king_x && white_rook_x > white_king_x)){
      return true;
    }
    
    return false;
  }
  
  public boolean invalid_move_king(int x, int y){
    if((x == white_rook_x && y == white_rook_y)){
      return true;
    }
    return false;
  }

  public int max(int current_depth, String step){
//    my_println("max " + current_depth);

    if(current_depth > depth || evaluation() < -1000){                          // mat piece
//      my_println(step + " " + evaluation());
      return evaluation();
    }
    
    String str;
    
    int temp, temp1;
    int max = -1;
    boolean unset = true;

    temp = white_rook_x;
    for(int i = 0; i < 8; i++){
      if(!valid_move(false, true, i, white_rook_y) || i == temp){
        continue;
      }
     
      white_rook_x = i;
      
      str = step + result_string("R", white_rook_x, white_rook_y);
      temp1 = min(current_depth + 1, str, false);
      if(unset || temp1 > max){
        if(current_depth == 0){
          first_move = result_string("R", white_rook_x, white_rook_y);
        }
        max = temp1;
        unset = false;
      }
    }
    white_rook_x = temp;

    temp = white_rook_y;
    for(int i = 0; i < 8; i++){
      if(!valid_move(false, true, white_rook_x, i) || i == temp){
        continue;
      }
      
      white_rook_y = i;
  
      str = step + result_string("R", white_rook_x, white_rook_y);
      temp1 = min(current_depth + 1, str, false);
      if(unset || temp1 > max){
        if(current_depth == 0){
          first_move = result_string("R", white_rook_x, white_rook_y);
        }
        max = temp1;
        unset = false;
      }
    }
    white_rook_y = temp;

    int temp_x = white_king_x;    
    int temp_y = white_king_y;

    for(int i = 0; i < 8; i++){
      if(!valid_move(true, true, white_king_x + moveset[i][0], white_king_y + moveset[i][1])){
        continue;
      }
      
      white_king_x += moveset[i][0];        
      white_king_y += moveset[i][1];
      
      str = step + result_string("K", white_king_x, white_king_y);
      temp1 = min(current_depth + 1, str, true);
      if(unset || temp1 > max){
        if(current_depth == 0){
          first_move = result_string("K", white_king_x, white_king_y);
        }
        max = temp1;
        unset = false;
      }
        
      white_king_x = temp_x;
      white_king_y = temp_y;
    }

    
    if(current_depth == 0){
      if(pseudo_checkmate(first_move)){
        first_move = first_move + "+";
      }
//      my_println("**** " + first_move + " ****");
    }
    

    return max;
  }
  
  public int min(int current_depth, String step, boolean king){
//    my_println("min" + current_depth);
    
    if(current_depth > depth || evaluation() < -1000){
//      my_println(step + " " + evaluation());
      return evaluation();
    }

    int temp1;
    int min = -1;
    int temp_x = black_king_x;   
    int temp_y = black_king_y;
    String str;
    boolean unset = true;

    for(int i = 0; i < 8; i++){
      if(!valid_move(true, false, black_king_x + moveset[i][0], black_king_y + moveset[i][1])){
        continue;
      }
      
      black_king_x += moveset[i][0];        
      black_king_y += moveset[i][1];

      ///////////////////////
      boolean good_state = true;
      
      if(king){
        good_state = pre_state.save_state(white_king_x, white_king_y, -1, -1, black_king_x, black_king_y);
      }
      else{
        good_state = pre_state.save_state( -1, -1, white_rook_x, white_rook_y, black_king_x, black_king_y);
      }
      
      if(!good_state){
        black_king_x = temp_x;
        black_king_y = temp_y;
        return -100000;
      }

      str = step + " " + result_string("BK", black_king_x, black_king_y);
      temp1 = max(current_depth + 1, str);
      if(unset || temp1 < min){
        min = temp1;
        unset = false;
      }
      
      pre_state.remove_state();
      ////////////////////////////////
      
      black_king_x = temp_x;
      black_king_y = temp_y;
    }
    
    
    if(min == -1){
      if(checkmate()){
        check_confirm = true;
        return 100000; // sua sau, checkmate
      }
      else{
        return -100000; // sua sau, checkmate
      }
    }
    return min;
  }
  
  public void reset_var(){
    first_move = "";
    check_confirm = false;
  }
  
  @Override
  public void initState(String[] state) {
    white_king_x = get_int(state[0].charAt(1));    
    white_king_y = Integer.parseInt(state[0].substring(2, 3)) - 1;
    
    white_rook_x = get_int(state[1].charAt(1));    
    white_rook_y = Integer.parseInt(state[1].substring(2, 3)) - 1;
    
    black_king_x = get_int(state[2].charAt(1));    
    black_king_y = Integer.parseInt(state[2].substring(2, 3)) - 1;
  }

  @Override
  public String whiteMove(String blackMove) {
    if(!"".equals(blackMove)){
      black_king_x = get_int(blackMove.charAt(1));    
      black_king_y = Integer.parseInt(blackMove.substring(2, 3)) - 1;
      if(king_global){
        pre_state.save_state(white_king_x, white_king_y, -1, -1, black_king_x, black_king_y);
      }
      else{
        pre_state.save_state(-1, -1, white_rook_x, white_rook_y, black_king_x, black_king_y);
      }
    }
   
    max(0, "");

    if(!"".equals(first_move)){
      if(first_move.charAt(0) == 'K'){
        king_global = true;
        white_king_x = get_int(first_move.charAt(1));    
        white_king_y = Integer.parseInt(first_move.substring(2, 3)) - 1;
      }
      else{
        king_global = false;
        white_rook_x = get_int(first_move.charAt(1));    
        white_rook_y = Integer.parseInt(first_move.substring(2, 3)) - 1;
      }
    }
    
    String result = first_move;
    reset_var();
    return (result);
  }

  public static void main(String[] args) {
      White white = new White();
      AI_assignment2.setWhite(white);
      try {
          AI_assignment2.run();
      } catch (IOException ex) {
          ex.printStackTrace();
      }
//    String[] asd = {"Ka1","Ra5","Ke4"};
//    white.initState(asd);
//    white.whiteMove("Ke5");
  }
}