package net.codejava.io;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.FileWriter;
 

public class quiz {
 
    public static void main(String[] args) {
    	int counter = 0;
        try {
            FileReader reader = new FileReader("C:\\Users\\MSI DRAGON\\Documents\\JCreator LE\\MyProjects\\quiz\\src\\MyFile.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);         
            String line = "";

while ((line = bufferedReader.readLine()) != null) {

    counter ++;
    System.out.println(counter);
    String tokens[] = line.split(";;");
    int dlugosc = tokens.length;

    int poprawna = 0;
    if(!tokens[dlugosc-1].equals("0")){
    	poprawna = (Integer.parseInt(tokens[dlugosc-1].trim()))-1;
    }

    		try {
            FileWriter writer = new FileWriter("C:\\Users\\MSI DRAGON\\Documents\\JCreator LE\\MyProjects\\quiz\\src\\MyFile2.txt", true);
            writer.write("{");
            writer.write("\r\n");
            writer.write("question: " + "\"" + tokens[0] + "\"" + ",");
            writer.write("\r\n");
            writer.write("choices: [");
            for(int i=1; i<dlugosc-1; i++){
            	writer.write("'" + tokens[i] + "'");
            	if(i!=dlugosc-2)writer.write(",");
            	else {
            	writer.write("],");}
            }
            writer.write("\r\n"); 
            writer.write("correctAnswer: " + poprawna);
            writer.write("\r\n"); 
            writer.write("},");
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    		
}

 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
         
    }
 
}