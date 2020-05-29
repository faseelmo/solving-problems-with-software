
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.FileResource;
import edu.duke.StorageResource;
import edu.duke.*;

public class Part1 {
    public int findStopCodon( String dna, int startIndex, String stopCodon ) {
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while(currIndex != -1) {
            int diff = currIndex - startIndex;
            if(diff % 3 == 0) {
                return currIndex;
            } else {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }

        return dna.length();
    
    }
    
    public void testFindStopCodon() {
        String dna = "ATGMTAAMMTAA";
        
        int dex = findStopCodon(dna, 0,"TAA");
        System.out.println(dex);
    }
        
    public String findGene (String dna, int where) {
        String dnaUP = dna.toUpperCase();
        int startIndex = dnaUP.indexOf("ATG", where);
        //System.out.println("where " + where);
        //System.out.println("startIndex " + startIndex);
        
        if (startIndex == -1 || where == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dnaUP, startIndex, "TAA");
        //System.out.println("taaIndex " + taaIndex);
        int tagIndex = findStopCodon(dnaUP, startIndex, "TAG");
        //System.out.println("tagIndex " + tagIndex);
        int tgaIndex = findStopCodon(dnaUP, startIndex, "TGA");
        //System.out.println("tgaIndex " + tgaIndex);
       

        
        int temp = Math.min(taaIndex, tagIndex); //temp = 185
        int minIndex = Math.min(temp, tgaIndex); 
        if (minIndex == dna.length()){
            return "";
           }
           
        //System.out.println("minIndex " + minIndex);        
           
        return dna.substring(startIndex, minIndex + 3);
    }
    
    
    public void testFindGene () {
        String dna1 = "";
        //System.out.println(dna1.toUpperCase());
        //System.out.println("For dna1 = " + dna1);
        System.out.println(findGene(dna1,0));
        //System.out.println(dna1.length());
        
    }
    
    public void printAllGenes() {
    String dna = "";  
    System.out.println(dna.length());
    int genenum = 1; 
    //System.out.println(dna);
    //System.out.println(dna.length());
  
    int currIndex = 0;
    
    
        while (true) {
            System.out.println("Current Index at Begining  " + currIndex);
            String gene = findGene(dna, currIndex);    
            if (gene.isEmpty()) {
                break;
            }
           
    
            System.out.println("Gene "+ genenum +" is "+ gene);
            currIndex = dna.indexOf(gene, currIndex) + gene.length();
            System.out.println("Current Index at Ending is " + currIndex);
            genenum = genenum + 1;
            
            
        }
        
         
    }
    
    public StorageResource getAllGenes(String dna) {
    //String dna = "MMMMATGMMMTAAMMMATGMMMTGAMMATGMMMTAGMM";  
    int currIndex = 0;
    
    StorageResource geneList = new StorageResource();
    
    while (true) {
        String gene = findGene(dna, currIndex);
        //System.out.println(gene);
        
        if (gene.isEmpty()) {
            break;
        }
        
        geneList.add(gene);
        currIndex = dna.indexOf(gene, currIndex) + gene.length();
        //System.out.println("Gene is "+ gene);        
        
       
    }
        
    return geneList; 
    
    }
    
    public void testAllGenes() {
        String dna = "";  
        StorageResource sr = getAllGenes(dna);
        System.out.println("Printing All genes from StorageResource; ");
        for ( String s : sr.data() ) {
            System.out.println(s);
        }
    }
    
    public float cgRatio(String dna) {
        int cnum = 0;
        int gnum = 0; 
        int prevIndex = 0;
        int indexC = 0;
        int indexG = 0;
        String dnaUP = dna.toUpperCase();
        
        while (true) {
            indexC = dnaUP.indexOf("C", prevIndex);
            //System.out.println("IndexC is " + indexC);
            prevIndex = indexC + 1;
            if (indexC == -1 ) {
                break;
            }
            cnum = cnum + 1;
        }
        
        prevIndex = 0;
        while (true) {
            indexG = dnaUP.indexOf("G", prevIndex);
            //System.out.println("IndexG is " + indexG);
            prevIndex = indexG + 1;
            if (indexG == -1 ) {
                break;
            }
            gnum = gnum + 1;
        }
        
        float length = dna.length();
 
        
        return (cnum+gnum) / length; 
        
    }
    
    public void testcgRation() {
        String dna = "ATGCCATAG";
        
        float cgnum = cgRatio(dna);
        System.out.println(cgnum);
    }
    
    public int countCTG(String dna) {
        int count = 0; 
        int prevIndex = 0;
        int indexC = 0;
        String dnaUP = dna.toUpperCase();
        while (true) {
            indexC = dnaUP.indexOf("CTG", prevIndex);
            //System.out.println("IndexC is " + indexC);
            prevIndex = indexC + 1;
            if (indexC == -1 ) {
                break;
            }
            count = count + 1;
        }
        
        return count; 
    }
    
    public void testcountCTG() {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        System.out.println(countCTG(dna));
    }
    
    public void processGenes(StorageResource sr){
     int countnine = 0;
     int countcg = 0;
     int countsixty = 0;
     int temp = 0;
     int largestSR = 0;
     int count = 0;
     for ( String s : sr.data()){
         count = count + 1;   
         // System.out.println(" For gene:  " + s);
         //System.out.println("CG Ration is " + cgRatio(s)); 
         
         if (s.length() > 9 ) {
             //System.out.println("Has Longer than 9 characters");
             
             countnine = countnine + 1;
            }
         
         
                
         if (cgRatio(s) > 0.35) {   
             //System.out.println("Sr with C-G ration > 0.35"); 
              
             countcg = countcg + 1;
         }
         
         

         if ( temp > s.length() ) { 
             largestSR = temp;
            } else {
             largestSR = s.length();
            }

         if ( s.length() > 60 ) { 
            //System.out.println("Is greater than 60 characters");
            countsixty = countsixty + 1;
        }
        
     }
     
     System.out.println("Total number of Genes : " + count);
     System.out.println("No.of genes longer than 9 characters: " + countnine);
     System.out.println("No.of genes with C-G ration > 0.35 : " + countcg);
     System.out.println("Largest Gene : " + largestSR);
     System.out.println("No.of genes greater than 60 : " + countsixty);
     
    }
     
    public void testProcessGenes() {
       
        FileResource fr = new FileResource();
        String dna = fr.asString();
        StorageResource sr = getAllGenes(dna);
        
        processGenes(sr);
     
    }
 }