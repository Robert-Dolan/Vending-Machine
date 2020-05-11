package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
   A product in a vending machine.
*/
public class Product
{
   private Scanner n;
   private String description;
   private String price;

   /**
      Constructs a Product object
      @param aDescription the description of the product
      @param aPrice the price of the product
   */
   public Product(){}
   public Product(String aDescription, String aPrice)
   {  
      description = aDescription;
      price = aPrice;
   }
   
   /**
      Gets the description.
      @return the description
   */
   public String getDescription()
   { 
      return description;
   }
   
   /**
      Gets the price.
      @return the price
   */
   public String getPrice()
   {  
      return price;
   }

   /**
      Determines of this product is the same as the other product.
      @param other the other product
      @return true if the products are equal, false otherwise
   */
   public boolean equals(Object other)
   { 
      if (other == null) return false;
      Product b = (Product) other;
      return description.equals(b.description) && price == b.price;
   }
   
   /**
      Formats the product's description and price.
   */
   public String toString()
   { 
      return description + " @ €" + price;
   }

//gets the amount of a product--used by the admin to check the levels of a product in a machine so they can adjust
   public String getQuantity(String description) throws IOException {

      String quantity = "";
      BufferedReader reader = new BufferedReader(new FileReader("Products.txt"));

      String line = "";
      while((line = reader.readLine())!=null){
         String [] products = line.trim().split(",");

         if (products[0].equals(description)){

            quantity = products[2];
         }
      }
      return quantity;
   }

}
