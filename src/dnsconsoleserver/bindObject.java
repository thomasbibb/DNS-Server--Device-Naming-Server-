/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dnsconsoleserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tom
 */
public class bindObject {

//create db connection
dbc dbc = new dbc();

public boolean bind (String ipAddress, String Hostname) throws SQLException {

   if (dupeCheck(ipAddress, "ipAddress") == false) {
        Statement db = dbc.mySQLi.createStatement();
        db.executeUpdate("INSERT INTO tbl_objects (ipAddress, Hostname) VALUES ('"+ipAddress+"', '"+Hostname+"')");
        db.close();
        return true;
   } else {
       return false;
    }
}

public boolean unbind (String Hostname) throws SQLException {
   if (dupeCheck(Hostname, "Hostname") == true) {
        Statement db = dbc.mySQLi.createStatement();
        db.executeUpdate("DELETE FROM tbl_objects WHERE Hostname = '"+Hostname+"'");
        db.close();
        return true;
   } else {
       return false;
    }
}

public String lookup (String Hostname) throws SQLException {
 //create db Statement
    Statement db = dbc.mySQLi.createStatement();
    String ipAddress = null;
    //execute SQL Query
    db.executeQuery("SELECT ipAddress FROM tbl_objects WHERE Hostname = '"+Hostname+"'");
    //get the result set
    ResultSet rs = db.getResultSet();
    while (rs.next()) {
       ipAddress = rs.getString("ipAddress");
    }
    rs.close();
    db.close();
    return ipAddress;
}

private boolean dupeCheck (String ipAddress, String strCriteria) throws SQLException {
    //create db Statement
    Statement db = dbc.mySQLi.createStatement();
    //create a count
    int i = 0;
    //execute SQL Query
    db.executeQuery("SELECT * from tbl_objects WHERE "+strCriteria+" = '"+ipAddress+"'");
    //get the result set
    ResultSet rs = db.getResultSet();

    while (rs.next()) {
        i++;
    }
    rs.close();
    db.close();

    if (i >0) {
        //object exsists
        return true;
    } else {
        //no object found
        return false;
    }
}
}
