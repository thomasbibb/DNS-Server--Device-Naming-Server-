/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;



import dnsconsoleserver.*;
import java.net.*;
import java.io.*;
import java.sql.SQLException;


public class protocol {

private static final int WAITING = 0;
private static final int SENTHEADER = 1;
private int state = WAITING;
private String action[];
String ipCheck[];
private int idleCount = 0;
private String resolve;

    public String respond(String theInput, Socket theSocket) throws SQLException {
    bindObject bind = new bindObject();


       String theOutput = null;


       if (state == WAITING) {
           // send header to client, ONCE
            theOutput = "oxideDNS 1.0 Server (oDNS) ["+theSocket.getLocalAddress()+":"+theSocket.getLocalPort()+"]\n"
                + "Hello ["+theSocket.getInetAddress()+":"+theSocket.getPort()+"]\n"
                + "200: Connection Sucsesfully Established";

            //set state to SENTHEADER so it does not get sent again
           state = SENTHEADER;
           //if we have sent the header continue with I/O
       } else if (state == SENTHEADER) {
           System.out.println(theInput);
            action = theInput.split(" ");
            String strCommand = action[0];
           if (strCommand.equals("BIND")) {
            if (action.length == 1) {
                //IP Address missing, alert client
                theOutput = "201: No IP Address specified";
                System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
            } else if (action.length == 2) {
                //Hostname missing, alert client
                theOutput = "202: No Hostname specified";
                System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
            } else if (action.length == 3) {

                if (bind.bind(action[1], action[2])) {
                    //alert the user of sucsessful bind
                    theOutput = "250: Sucsesful Bind "+action[1]+" with "+action[2];
                    System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
                } else {
                    //Duplicate Entry found
                    theOutput = "254: Unsucsesful Bind "+action[1]+" Exsists";
                    System.out.println(theOutput + " -" +theSocket.getInetAddress()+":"+theSocket.getPort());

                }
                    
            } else {
                //more than 2 values input, alert client
                theOutput = "203: Too many parameters";
            }


           } else if (strCommand.equals("UNBIND")) {
               if (action.length == 1) {
                theOutput = "201: No IP Address specified";
                   System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
               } else if (action.length == 2) {

                   if (bind.unbind(action[1])) {
                        theOutput = "251: Sucsesful Unbind";
                   } else {
                       theOutput = "252: Unsucsesful Unbind";
                   }
                      System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
               }


           } else if (strCommand.equals("LOOKUP")){
               if (action.length == 1) {
                theOutput = "202: No Hostname specified";
                System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
               } else if (action.length == 2) {

                   resolve = bind.lookup(action[1]);

                   if (resolve == null) {
                       theOutput = "209: "+action[1]+" not found!!";
                       System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
                   } else {
                       theOutput = "202: "+action[1]+" "+resolve;
                       System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
                   }
               }


           } else if (strCommand.equals("EXIT")){
               theOutput = "Goodbye ["+theSocket.getInetAddress()+":"+theSocket.getPort()+"]";
           } else if (strCommand.equals("HELP")) {
               theOutput = "--- HELP ---\n"
                       +"COMMANDS\n"
                       +"BIND <IPADDRESS> <HOSTNAME>\n"
                       +"UNBIND <HOSTNAME>\n"
                       +"LOOKUP <HOSTNAME>\n"
                       +"HELP <Shows This Information>\n"
                       +"EXIT <Kills the Thread>\n";
           } else {
                idleCount++;
                theOutput = "200: Command not Implamented - Attempt "+idleCount;
                System.out.println(theOutput + " - " +theSocket.getInetAddress()+":"+theSocket.getPort());
                if(idleCount == 8) {
                    theOutput = "Sorry, too many invalid requests\n"
                            +"Goodbye ["+theSocket.getInetAddress()+":"+theSocket.getPort()+"]";
           }
           }
       }
                return theOutput;
    }
}

