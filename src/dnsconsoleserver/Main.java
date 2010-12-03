/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;

import java.io.IOException;

/**
 *
 * @author Tom
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        server s = new server();
        s.start(6567);
    }

}
