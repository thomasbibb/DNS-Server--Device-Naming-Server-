/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;

/**
 *
 * @author Tom
 */
import dnsconsoleserver.*;
import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class serverThread extends Thread {
    private Socket socket = null;

    public serverThread(Socket socket) {
	super("serverThread");
	this.socket = socket;
    }

    private  protocol ps = new protocol();

    public void run() {

	try {
            //writ out in the console that the client new client has connected
            System.out.println("New Connection from " + socket.getInetAddress());
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    String input, output;
	    
	    output = ps.respond(null, socket);
	    out.println(output);

	    while ((input = in.readLine()) != null) {
		output = ps.respond(input, socket);
		out.println(output);
		if (output.contains("Goodbye"))
		    break;
	    }
	    out.close();
	    in.close();
            //write out in console that the client has disconnected
            System.out.println(socket.getInetAddress()+":"+socket.getPort() + " Has disconnected");
	    socket.close();

	} catch (SQLException ex) {
            Logger.getLogger(serverThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
	    e.printStackTrace();
	}
    }
   }