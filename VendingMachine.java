package sample;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
   A vending machine.
*/
public class VendingMachine
{  

   /**
      Constructs a VendingMachine object.
   */
   public VendingMachine()
   { }
   

//creates an array of product objects by checking the contents of Products.txt
   public Product[] getProductTypes() throws IOException
   {
      int linecount = 0;
      BufferedReader count = new BufferedReader(new FileReader("Products.txt"));
      String lineCount = "";
      while((lineCount = count.readLine())!=null) {
         linecount++;
      }
//         System.out.println(linecount); used for debugging, confirming initial count on the amount of products

      Product[] r = new Product[linecount+1]; //put this is because i was having errors with out of bounds when populating products array below
      BufferedReader reader = new BufferedReader(new FileReader("Products.txt"));

      String line = "";
      int i = 0;
      while((line = reader.readLine())!=null){

         String [] products = line.trim().split(",");

         if(products[0] != null) {
            r[i] = new Product(products[0], products[1]);

            i++;
         }
      }

      return r;
   }

//gets the balance of the machine, this method is used in the the admin menu. this is a new method that is used
   //as part of the replacement system of the old coin system
   public String getMachineBalance() throws IOException {

      String machineBalance = "";
      BufferedReader reader = new BufferedReader(new FileReader("Machine.dat"));

      String line = "";
      while((line = reader.readLine())!=null){
         String [] Balance = line.trim().split(",");


            machineBalance = Balance[0];
         }

      return machineBalance ;

   }

}
