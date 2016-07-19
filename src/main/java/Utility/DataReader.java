package Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author John
 */

public class DataReader {
    
    BufferedReader file;
    
    public DataReader(String filepath) throws FileNotFoundException{
        file = new BufferedReader(new FileReader(new File(filepath)));
    }
    
    public String[][] getData() throws IOException{
        String r;
        ArrayList dataRows = new ArrayList();
        while((r = file.readLine()) != null){
            dataRows.add(r);
        }
        
        String[][] data = new String[dataRows.size()][];
        for(int i = 0; i< dataRows.size(); i++){
            data[i] = dataRows.get(i).toString().split("\t");
        }
        
        return data;
    }
    
}
