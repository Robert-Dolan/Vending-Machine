package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//class that confirms wether a user is a client by cheking the Client.dat file
public class Client
{
    public Client(){}

//check to see if the user name and password matches up in the admin.dat file if so boolean yes and admin user is confirmed.
    public boolean isClient(String user, String password) throws IOException {
        boolean isClient = false;
        String balance = "";
//        System.out.println("Success"); used for debugging, confirming isClient is running
        BufferedReader reader = new BufferedReader(new FileReader("Clients.dat"));

        String line = "";
        while((line = reader.readLine())!=null){
            String [] clients = line.trim().split(",");

            if (clients[0].equals(user) && clients[1].equals(password)){

                isClient = true;
            }
        }
        return isClient;
    }
    //This method is for checking the balance of a clients account in Clients.dat--serves two purposes, for letting the client know how much
    //money they have. Second is for checking whether they have enough money in their account in the "payNow" event in the main class
    public String getBalance(String user, String password) throws IOException {

        String balance = "";
        BufferedReader reader = new BufferedReader(new FileReader("Clients.dat"));

        String line = "";
        while((line = reader.readLine())!=null){
            String [] clients = line.trim().split(",");

            if (clients[0].equals(user) && clients[1].equals(password)){

                balance = clients[2];
            }
        }
        return balance;
    }



}