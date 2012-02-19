package com.pigmal.android.ex.accessory;

import com.pigmal.android.ex.accessory.Game.ZhankenListener;

public class Zhanken {
    private ZhankenListener mListener;
    private ADKCommandSender mSender;
    
    enum Command {
        gu,
        choki,
        par
    }
    
    public Zhanken(ADKCommandSender sender){
        mSender = sender;
    }
    
    public void setEnemyCommand (Command command){
        switch (command){
        case gu:
            mSender.sendLEDcommand(1, 1, 255);
            mSender.sendLEDcommand(1, 2, 128);
            mSender.sendLEDcommand(1, 3, 128);
            break;
        case choki:
            mSender.sendLEDcommand(2, 1, 128);
            mSender.sendLEDcommand(2, 2, 255);
            mSender.sendLEDcommand(2, 3, 128);
            break;
        case par:
            mSender.sendLEDcommand(3, 1, 128);
            mSender.sendLEDcommand(3, 2, 128);
            mSender.sendLEDcommand(3, 3, 255);
            break;
        }
    }
    
    public void setZhankenListener(ZhankenListener listener){
        mListener = listener;
    }
    
    public void setSwitchStateChanged(byte sw, boolean b) {
        if (b) {
            switch (sw){
            case 1:
                mListener.onCommandSet(Command.gu);
                break;
            case 2:
                mListener.onCommandSet(Command.choki);
                break;
            case 3:
                mListener.onCommandSet(Command.par);
                break;
            }
        }
    }
}
