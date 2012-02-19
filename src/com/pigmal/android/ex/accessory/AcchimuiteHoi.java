package com.pigmal.android.ex.accessory;

public class AcchimuiteHoi {
    enum Direction {
        right,
        left,
        center,
    }
    
    public void setEnemyDirection(Direction direction){
        
    }
    
    public void setAcchimuiteHoiListener(){
        
    }
    
    public interface AcchimuiteHoiListener {
        public void onAcchimuiteHoiSet(Direction direction);
    }
}
