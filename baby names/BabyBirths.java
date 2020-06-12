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
        int countBoys = 0;
        int countGirls = 0;
        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                countBoys = countBoys + 1; 
            }
            else {
                totalGirls += numBorn;
                countGirls = countGirls + 1;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("Total Girl Names  = " + countGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("Total Boys Names  = " + countBoys);
    }
    

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        System.out.println("Yob1900");
        FileResource fr = new FileResource("data/us_babynames_by_year/yob1900.csv");
        totalBirths(fr);
        
        System.out.println("Yob1905");
        FileResource fr1 = new FileResource("data/us_babynames_by_year/yob1905.csv");
        totalBirths(fr1);
    }
    
    public int getRank (int year, String name, String gender) {
        String str1 = Integer.toString(year); 
        String filename = "data/us_babynames_by_year/yob" + year + ".csv";
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
        int emily = getRank(1960, "Emily", "F");
        System.out.println("Emilys Rank is " + emily);
        
        int Frank = getRank(1971, "Frank", "M");
        System.out.println("Franks Rank is " + Frank);
    }
    
    
    public String getName (int year, int rank, String gender) {
        
        String str1 = Integer.toString(year); 
       String filename = "data/us_babynames_by_year/yob" + year + ".csv";
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
        String gname = getName(1980, 350 ,"F");
        System.out.println("Girls name in 1980 is " + gname);
        
        String bname = getName(1982, 450 ,"M");
        System.out.println("Boys name in 1982 is " + bname);
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {

        int rank = getRank(year, name, gender);
        //System.out.println(rank);
        
        String GETname = getName(newYear, rank , gender);
        //System.out.println(GETname);
        
        System.out.println(name + " born in "+ year + " would be " + GETname + " if born in " + newYear);
    }
    
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
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
          
          if (rank != 0) {
              if (highestRank == 0 ) {
              highestRank = rank;  
              highestRankYear = year; 
            } else {
                if (rank < highestRank ) {
                 highestRank = rank;  
                 highestRankYear = year; 
            }
            }
              
              
            }
          
          //System.out.println("Rank is " + rank + " in the year " + year);
          
          
          
        }
        
       System.out.println("Highest Rank is " + highestRank + " in " + highestRankYear);
       
        
    }
    
    public void testyearOfHighestRank() {
        //yearOfHighestRank ( "Genevieve",  "F");
        yearOfHighestRank ( "Mich",  "M");
    }
    
    public double getAverageRank (String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double totalRank = 0; 
        String filename = ""; 
        String yearString = "";
        int highestRankYear = 0;
        int count = 0;
       
       for (File f : dr.selectedFiles()) {
          count = count + 1; 
          filename = f.getName();
          yearString = filename.substring(3,7);
          int year = Integer.parseInt(yearString);
          //System.out.println("Name is " + name);
          int rank = getRank(year, name, gender);
          
          if ( rank != -1 ) {
              totalRank = totalRank + rank;
            } 
        }
       
       double avgRank = 0;
       
       if (totalRank != 0) {
           avgRank = totalRank/count;
        } else {
            avgRank = -1;
        }
        
       return avgRank;
    }
    
    public void testgetAverageRank() {
        double avgRank = getAverageRank ("Susan","F");
        System.out.println(avgRank);
        
        double avgRankR = getAverageRank ("Robert","M");
        System.out.println(avgRankR);
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        String str1 = Integer.toString(year); 
        String filename = "data/us_babynames_by_year/yob" + year + ".csv";
        FileResource fr = new FileResource(filename);
        int birthNum = 0; 

        for (CSVRecord rec : fr.getCSVParser(false)) {

            if (gender.equals("F") && rec.get(1).equals("F")) {
 
                if (rec.get(0).equals(name)) {
                    break;
                 
                } else {
                    birthNum = birthNum + Integer.parseInt(rec.get(2));
                }
                     
            }
            
            if (gender.equals("M") && rec.get(1).equals("M")) {
 
                if (rec.get(0).equals(name)) {
                    break;
                 
                } else {
                    birthNum = birthNum + Integer.parseInt(rec.get(2));
                }
                     
            }
            
        }
         
        return birthNum; 
          
    }      
    
    public void testgetTotalBirthsRankedHigher () {
        
        System.out.println(getTotalBirthsRankedHigher(1990, "Emily", "F"));
        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
        
    }
    
    
}
