package sample;
//class that deals with admin log in
import java.io.*;

@SuppressWarnings("ALL")
public class Admin
{

    public Admin(){}

//check to see if the admin name and password matches up in the admin.dat file if so boolean yes and admin user is confirmed.
    //this method is used in the main VendingMachineJavaFX class to confirm an admin user and send them to scene 3 where they have admin privilages
    public boolean isAdmin(String user, String password) throws IOException {
        boolean isAdmin = false;
        String balance = "";
        BufferedReader reader = new BufferedReader(new FileReader("Admin.dat"));

        String line = "";
        while((line = reader.readLine())!=null){
            String [] clients = line.trim().split(",");

            if (clients[0].equals(user) && clients[1].equals(password)){

                isAdmin = true;
            }
        }
        return isAdmin;
    }


}
