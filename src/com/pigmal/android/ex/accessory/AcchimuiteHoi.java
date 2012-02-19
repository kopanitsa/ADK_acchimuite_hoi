package com.pigmal.android.ex.accessory;

import com.pigmal.android.ex.accessory.Game.AcchimuiteHoiListener;

public class AcchimuiteHoi {
    private AcchimuiteHoiListener mListener;
    private ADKCommandSender mSender;
    
    public enum Direction {
        right,
        left,
        center,
    }
    
    public AcchimuiteHoi(ADKCommandSender sender){
        mSender = sender;
    }
    
    // 0: move face (User wins)
    // 1: move finger (Enemy wins)
    public void setEnemyDirection(int status, Direction direction){
        switch (direction){
        case right:
            mSender.sendServoCommand(status, 0);
            break;
        case left:
            mSender.sendServoCommand(status, 180);
            break;
        case center:
            mSender.sendServoCommand(status, 90);
            break;
        }
    }
    
    public void setAcchimuiteHoiListener(AcchimuiteHoiListener listener){
        mListener = listener;
    }
    
    public void setSwitchStateChanged(byte sw, boolean b) {
        if (b){
            if (sw == 0){
                mListener.onAcchimuiteHoiSet(Direction.left);
            } else if (sw == 2){
                mListener.onAcchimuiteHoiSet(Direction.right);
            }
        }
    }

    public void clear() {
        mSender.sendServoCommand(0, 90);
        mSender.sendServoCommand(1, 90);
    }
}
