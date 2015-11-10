package Utility;

import Pages.Page;

public class Wait {

    private long time;
    
    public Wait(){
        
    }
    
    public void waitSecs(int sec){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < sec * 1000);
    }
    
    public void waitMilliSecs(int millisecs){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < millisecs);
    }
    
    public void sync(Page page, int timeoutSecs){
        time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < timeoutSecs && !page.elementExists());
    }
    
}
