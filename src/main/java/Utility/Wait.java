package Utility;

/*
 * @author Jonathan Doll
 */

public class Wait {

    private long time;
    
    public Wait(){
        
    }
    
    public void waitSecs(double sec){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < sec * 1000);
    }
    
    public void waitMilliSecs(int millisecs){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < millisecs);
    }
    
    public void animation(){
        waitMilliSecs(500);
    }
    
}
