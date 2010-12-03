/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;
import java.sql.*;


/**
 *
 * @author Tom
 */
public class dbc {
    Connection mySQLi = null;

        public dbc () {
           try
           {
               String url = "jdbc:mysql://localhost/dns-server?user=root&password=skelitor[]8521";
               Class.forName ("com.mysql.jdbc.Driver").newInstance ();
               mySQLi = DriverManager.getConnection (url);
               System.out.println ("Querying Datastore");
           }
           catch (Exception e)
           {
               System.err.println ("Cannot connect to db server "+e.getMessage());
           }
           finally
           {
               if (mySQLi != null)
               {            
                       System.out.println ("Datastore process terminated");
               }
           }
  }
}