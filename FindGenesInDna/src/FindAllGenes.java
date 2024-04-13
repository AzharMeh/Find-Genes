/*This Program helps to find the genes in Dna strings. dna code is made of letter A, C, G, and T. 
These letters are abbreviated as Adenine (A), Cytosine (C), Guanine (G), and Thymine (T).
Dna codons are mede of three letters for example ATG, TAA, TCA, TGA and their combination makes the Dna String.
gene in Dna starts with start codon and end with stop codon a string can have many genes in it.
this program take dna string find ATG in String as start codon and return all the genes inside the string which end with the three stop codon (gene lenth is multiple of three) TAA, TGA, TAG*/

import java.util.*;
import edu.duke.*;
public class FindAllGenes {
    //This method Take dna string, start codon, and stop codon as a parameter to find stop codon's index value in dna string
    public static int findStopCodon(String dna,int startCodon, String stopCodon){
    // Find the stopCodon in dna string after the index number of startCodon + 3 and return the index number
    //it returns -1 if not stopCodon find in da string 
        int currIndex = dna.indexOf(stopCodon, startCodon+3);
    // run a loop if currIndex not equals -1 
        while(currIndex != -1){
            //Check if gene legnth is multiple of 3
            if((currIndex - startCodon)%3 == 0){
                // if so return index number of stop codon (currIndex)
                return currIndex;
            }
            else {
                //if not Start the search again after index value of currIndex + 1
                currIndex =  dna.indexOf(stopCodon, currIndex+1);
            }
        }
        //return -1 if no stop codon found
        return -1;
    }
// This function find the gene which ends with all stop codon and take the nearest to start codon and return it.
// search starts with the Start index (where) 
    public static String findGenes(String dna, int where){
        //Check if String is not empty
        if(dna.isEmpty()){return "";}
        String startATG = "ATG";
        String StopTAA = "TAA";
        String StopTGA = "TGA";
        String StopTAG = "TAG";
        String initial = dna.substring(0,3);
        //Check if dna string is in lower case or upper case if It's in lower case change the case of start and stop codon
        if(initial.equals(initial.toLowerCase())){
            startATG = "atg";
            StopTAA = "taa";
            StopTGA = "tga";
            StopTAG = "tag";            
        }
        //find the Start codon from (where) in String and store it's index value to startCodon
        int startCodon = dna.indexOf(startATG, where);
        //return empty string if no start codon found
        if(startCodon == -1){return "";}
        //find the stop codon's index value using function findStopCodon
        int TaaCodon = findStopCodon(dna, startCodon, StopTAA);    
        int TgaCodon = findStopCodon(dna, startCodon, StopTGA);
        int TagCodon = findStopCodon(dna, startCodon, StopTAG);
        //int tmp = Math.min(TaaCodon, TgaCodon);
        //Initialize minIndex with 0
        int minIndex = 0;//Math.min(tmp, TagCodon);
        //Check the nearest stop codon from start codon and store it's index value to minIndex(if a stop codon is not found in string it stores -1)
        if(TaaCodon == -1 || (TgaCodon != -1 && TgaCodon < TaaCodon)){
        minIndex = TgaCodon;
        }
        else{
        minIndex = TaaCodon;
        }      
        if (minIndex == -1 || (TagCodon != -1 && TagCodon < minIndex)){
            minIndex = TagCodon;
        }
        //if no stop codon found return empty string
        if (minIndex == -1){
        return "";
        }
        // create a substring of dna from start codon to the nearest stop codon (minindex) (codon is of 3 letter add +3 in the endIndex)
        return dna.substring(startCodon, minIndex+3);
    }
   // this method count the number of Genes in given Dna string
    public int countGenes(String dna){
    // Start with initializing startIndex and count to 0
    int startIndex = 0;
    int count = 0;
    // run loop until breaks
    while(true){
    // get gene using findGenes()method
     String currString = findGenes(dna,startIndex);
     // Check if it returns a gene or empty String
     if(currString.isEmpty()){
        //if currString is empty break through loop
        break;
        }
        //If it currString has gene increase count with one 
        count = count + 1;
        // initialize startIndex with the index of next letter right after the end of currString
        startIndex = dna.indexOf(currString,startIndex) + currString.length();
    }
    // return count
    return count;
    }

    //this method store all the genes find in dna string inside a String array
    public String[] storeGenes(String dna){
        //Create an array list (stringList)
        List<String> stringList = new ArrayList<>();   
        //initialize startIndex with 0     
        int startIndex = 0;
        // run loop until break
            while(true){
                // find gene using findGenes() method and store in currGene
                String currGene = findGenes(dna, startIndex);
                //break if currGene is empty
                   if(currGene.isEmpty()) {       
                    break;                       
                    }
                // add currGene in stringList 
                stringList.add(currGene); 
                // initialize startIndex with the index of next letter right after the end of currString           
                startIndex = dna.indexOf(currGene, startIndex) + currGene.length();
            
            }   
        // Convert List<String> stringList to String Array of the appropriate size and fill it with the elements from the list
        String[] geneList = stringList.toArray(new String[0]);  
        //return String array       
        return geneList;
        }
//Main method
public static void main(String[] args) throws Exception {
    // Create a variable dnaString of type FindAllGenes and initializing it with a new instance of FindAllGenes.
        FindAllGenes dnaString = new FindAllGenes();
        // Create a new FileResource class object fr
        FileResource fr = new FileResource();   
        int i = 1;    
        //Iterate over each line in fr
        for(String dna : fr.lines()){ 
            // create a String array  String[] geneList and store the result of dnaString.storeGenes(dna) method
            String[] geneList = dnaString.storeGenes(dna);
            //Print gene count using countGenes(dna) method
            System.out.println("Total genes in line "+i+" : "+dnaString.countGenes(dna));
            //if geneList is empty print message no gene found
            if(geneList.length == 0){
                System.out.println("No Gene Found");
            }
            // Print each string in geneList
            for(String gene : geneList){                
                System.out.println("Gene Found in line "+i+" : "+gene);                
            }
            //increase i with 1 after every line
            i += 1;

         }
        
}
}