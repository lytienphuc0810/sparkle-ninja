package ass2_source;

import ass2_library.BaseOfWhite;
import ass2_library.AI_assignment2;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class White extends BaseOfWhite {

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