package com.pigmal.android.ex.accessory;

public class Zhanken {
    enum Command {
        gu,
        choki,
        Par
    }
    
    public void setEnemyCommand (Command command){
        
    }
    
    public void setZhankenListener(ZhankenListener listener){
        
    }
    
    public interface ZhankenListener {
        public Command onCommandSet();
    }
}
