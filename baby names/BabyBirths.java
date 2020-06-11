/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/us_babynames_by_year/yob2014.csv");
        totalBirths(fr);
    }
    
    public int getRank (int year, String name, String gender) {
        String str1 = Integer.toString(year); 
        String filename = "data/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(filename);
        //System.out.println("Name of file is : " + filename);
        
        int rank = 0; 
        int finalRank = 0;
        int f_count = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
         rank = rank + 1; 

         if (gender.equals("F")) {
             
          if (rec.get(0).equals(name) ) {
             
             if (rec.get(1).equals(gender)){
                 finalRank = rank; 
                 //System.out.println("Match Found");
                 break;
                }
                
             else {
                 finalRank = -1;
                }
          }
          
         }
         
         if (gender.equals("M")) {
             
          if (rec.get(1).equals("F")) {
            f_count = f_count + 1;
            //System.out.println("f_count is " + f_count);
            }
            
          if (rec.get(0).equals(name) ) {
             
             if (rec.get(1).equals(gender)){
                 finalRank = rank - f_count ; 
                 //System.out.println("Match Found");
                 //System.out.println("Fcount is " + f_count);
                 break;
                }
                
             else {
                 finalRank = -1;
                }
           }   

         }
         
        }
        return finalRank; 
        
    }
    
    public void testgetRank () {
        int rank = getRank(2012, "Mason", "M");
        System.out.println(rank);
    }
    
    
    public String getName (int year, int rank, String gender) {
        
        String str1 = Integer.toString(year); 
        String filename = "data/us_babynames_test/yob" + year + "short.csv";
        FileResource fr = new FileResource(filename);

        String name = "started";
        int tempRank = 0;
        int f_count = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            tempRank = tempRank + 1;
            if (gender.equals("F")) {
                if (tempRank == rank && rec.get(1).equals("F") ) {
                    name = rec.get(0);
                    System.out.println("MatchFound");
                    break;
                }
                
                else {
                    name = "NO NAME";
                }
            }
            
            if (gender.equals("M")) {
                
                if (rec.get(1).equals("F")) {
                    f_count = f_count + 1;
                    //System.out.println("f_count is " + f_count);
                }
                
                if ( (tempRank - f_count) == rank && rec.get(1).equals("M") ) {
                    name = rec.get(0);
                    System.out.println("MatchFound");
                    break;
                }
                
                else {
                    name = "NO NAME";
                }
            }
        }
        
        
        
        return name; 
    }
    
    public void testgetName () {
        String name = getName(2012, 10 ,"M");
        System.out.println(name);
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {

        int rank = getRank(year, name, gender);
        //System.out.println(rank);
        
        String GETname = getName(newYear, rank , gender);
        //System.out.println(GETname);
        
        System.out.println(name + " born in "+ year + " would be " + GETname + " if born in " + newYear);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }
    
    public void yearOfHighestRank (String name, String gender) {
       DirectoryResource dr = new DirectoryResource();
       int highestRank = 0; 
       String filename = ""; 
       String yearString = "";
       int highestRankYear = 0;
       
       for (File f : dr.selectedFiles()) {
          filename = f.getName();
          yearString = filename.substring(3,7);
          int year = Integer.parseInt(yearString);
          //System.out.println("Name is " + name);
          int rank = getRank(year, name, gender);
          
          System.out.println("Rank is " + rank + " in the year " + year);
          if (highestRank == 0) {
              highestRank = rank;  
              highestRankYear = year; 
            } else {
                if (rank < highestRank) {
                 highestRank = rank;  
                 highestRankYear = year; 
            }
            }
          
          
        }
        
       System.out.println("Highest Rank is " + highestRank + " in " + highestRankYear);
       
        
    }
    
    public void testyearOfHighestRank() {
        yearOfHighestRank ( "Mason",  "M");
    }
    
}
