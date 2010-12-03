/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;

/**
 *
 * @author Tom
 */
import dnsconsoleserver.serverThread;
import java.net.*;
import java.io.*;

public class server {

    protected ServerSocket svrSocket = null;
    protected boolean      isRunning = true; /* true denotes running */
    private int            i;
    private serverThread[] st;

    /* get the Server Status */
    protected synchronized boolean isRunning() {
        return this.isRunning;
    }


    /* start the Server */  
    public void start(int svrPort) throws IOException  {
        svrSocket = new ServerSocket(svrPort);
        isRunning = true;
        System.out.println( "oxideDNS 1.0 Server (oDNS) ["+svrSocket.getLocalSocketAddress()+"]\n");
        
        while (isRunning()) {
            new serverThread(svrSocket.accept()).start();

        }

    }

    /* stop the Server */
    public synchronized void stop() throws IOException{
        isRunning = false;
        this.svrSocket.close();

    }
}