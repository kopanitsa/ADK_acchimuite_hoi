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
    
    public void setEnemyDirection(Direction direction){
        switch (direction){
        // TODO confirm direction is right
        case right:
            mSender.sendServoCommand(0, 180);
            break;
        case left:
            mSender.sendServoCommand(0, 0);
            break;
        case center:
            mSender.sendServoCommand(0, 90);
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
}
