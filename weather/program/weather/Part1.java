
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Part1 {
    public CSVRecord coldestHourFile(CSVParser parser, String Parameter) {
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if (smallestSoFar == null) {
                smallestSoFar = currentRow;
            } else {
                 double currentTemp = Double.parseDouble(currentRow.get(Parameter));
                 double smallestTemp = Double.parseDouble(smallestSoFar.get(Parameter));
                 
                 if ( currentTemp < smallestTemp && currentTemp != -9999) {
                     smallestSoFar = currentRow;
                    }
                 
                 
            }
            
        }
        
        return smallestSoFar; 
        
    }
    
    
    
    public void testSmallestSoFar() {
        FileResource fr = new FileResource();
        CSVRecord smallest = coldestHourFile(fr.getCSVParser(), "TemperatureF");
        System.out.println("Smallest Temperature is " + smallest.get("TemperatureF") +  " on (UTC) " + smallest.get("DateUTC")  );
    }
    
    
    public String fileWithColdestTemperature() {
         CSVRecord smallestSoFar = null;
         String filename = "";
         
         DirectoryResource dr = new DirectoryResource();
         for (File f : dr.selectedFiles()) {
             FileResource fr = new  FileResource(f); 

             CSVRecord currentFile = coldestHourFile(fr.getCSVParser(), "TemperatureF");
             
             if (smallestSoFar == null) {
                 smallestSoFar = currentFile;
                 filename = f.getName();
                 
                } else {
                 
                 double currentTemp = Double.parseDouble(currentFile.get("TemperatureF"));                    
                 double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                 
                 if ( currentTemp < smallestTemp) {
                     smallestSoFar = currentFile; 
                     filename = f.getName();
                    
                    
                    }

                }
       
         }


         return filename; 
    
    }
    
    public void testFileWithColdestTemperature() {
        String filename = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + filename);
        
        String filesource = "D:/Desktop/github clones/infinite/solving-problems-with-software/weather/data/nc_weather/2014/" + filename;  
        FileResource fr = new FileResource(filesource);
        CSVRecord smallest = coldestHourFile(fr.getCSVParser(), "TemperatureF");
        System.out.println("Smallest temp is " + smallest.get("TemperatureF"));
        
        System.out.println("All the temperature in this file are");
        for (CSVRecord currentRow : fr.getCSVParser()) {
           System.out.println(currentRow.get("DateUTC") + "  " +currentRow.get("TemperatureF"));
        }
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
         CSVRecord smallestSoFar = null;
         //String filename = "";
         
         DirectoryResource dr = new DirectoryResource();
         for (File f : dr.selectedFiles()) {
             FileResource fr = new  FileResource(f); 

             CSVRecord currentFile = coldestHourFile(fr.getCSVParser(), "Humidity");
             
             if (smallestSoFar == null) {
                 smallestSoFar = currentFile;
                 //filename = f.getName();
                 
                } else {
                 
                 double currentTemp = Double.parseDouble(currentFile.get("Humidity"));                    
                 double smallestTemp = Double.parseDouble(smallestSoFar.get("Humidity"));
                 
                 if ( currentTemp < smallestTemp) {
                     smallestSoFar = currentFile; 
                     //filename = f.getName()
                    }

                }
       
         }


         return smallestSoFar;         
        
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord smallestSoFar = lowestHumidityInManyFiles();
        String Humidity = smallestSoFar.get("Humidity");
        String Date = smallestSoFar.get("DateUTC");
        System.out.println("Lowest Humidity was " + Humidity + " at " + Date);
    }
    
}
