package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
//class used to write new information to .dat/.txt files used by VendingJavaFX
public class Writer {

     StringBuffer stringBufferOfData = new StringBuffer();
     Scanner sc = new Scanner(System.in);//initiliaze scanner to get user input

    public void writer(String filename, String lineToEdit, String replacementText) {
        boolean fileRead = false;
        Scanner File = null;
        try {
            File = new Scanner(new File(filename)); //point the scanner method to a file
            //check if there is a next line and it is not null and then read it in
            for (String line; File.hasNextLine() && (line = File.nextLine()) != null; ) {

                stringBufferOfData.append(line).append("\r\n");//this small line here is to append all text read in from the file to a string buffer which will be used to edit the contents of the file
            }

            File.close();//this is used to release the scanner from file
            fileRead = true;
        } catch (FileNotFoundException ex) {//if the file cannot be found an exception will be thrown
            System.out.println("The file " + filename + " could not be found! " + ex.getMessage());
            fileRead = false;
        } finally {//if an error occurs now we close the file to exit gracefully
            assert File != null;
            File.close();
            fileRead = true;
        }
        //call the method to read the file with the files name
        if (fileRead) {//if the read file was successful


            //replacement
            //System.out.println(sb);//used for debugging to check that my stringbuffer has correct contents and spacing
            int startIndex = stringBufferOfData.indexOf(lineToEdit);//now we get the starting point of the text we want to edit
            int endIndex = startIndex + lineToEdit.length();//now we add the staring index of the text with text length to get the end index
            stringBufferOfData.replace(startIndex, endIndex, replacementText);//this is where the actual replacement of the text happens

            //write to file
            try {
                BufferedWriter bufwriter = new BufferedWriter(new FileWriter(filename));
                bufwriter.write(stringBufferOfData.toString());//writes the edited string buffer to the new file
                stringBufferOfData.delete(0, stringBufferOfData.length());
                bufwriter.flush();
                bufwriter.close();//closes the file
            } catch (Exception e) {//if an exception occurs
                System.out.println("Error occured while attempting to write to file: " + e.getMessage());
            }
        }
    }
}
