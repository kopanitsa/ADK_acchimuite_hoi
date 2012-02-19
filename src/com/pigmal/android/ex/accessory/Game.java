package com.pigmal.android.ex.accessory;

import com.pigmal.android.ex.accessory.AcchimuiteHoi.Direction;
import com.pigmal.android.ex.accessory.Zhanken.Command;

public class Game {
    GameMode mGameMode;
    Zhanken mZhanken;
    AcchimuiteHoi mAcchi;

    enum GameMode {
        start,
        zyanken,
        acchimuite_hoi,
        celemony, // win or lose
    };
    
    public Game(ADKCommandSender sender, ADKCommandReceiver receiver){
        mGameMode = GameMode.start;
        mZhanken = new Zhanken(sender);
        mAcchi = new AcchimuiteHoi(sender);
        receiver.setGame(this);
    }
    
    public void setGameMode(GameMode mode){
        mGameMode = mode;
        switch (mode) {
        case start:
            // TODO flash LED
            break;
        case acchimuite_hoi:
            // nothing to do
            break;
        case zyanken:
            // nothing to do
            break;
        case celemony:
            // TODO flash LED
            break;
        }
    }

    public void switchStateChanged(byte sw, boolean b) {
        switch (mGameMode){
        case zyanken:
            mZhanken.setSwitchStateChanged(sw, b);
            break;
        case acchimuite_hoi:
            mAcchi.setSwitchStateChanged(sw, b);
            break;
        }
    }
    
    public void setEnemyZyankenCommand(Zhanken.Command command){
        mZhanken.setEnemyCommand(command);
    }
    
    public void setEnemyAcchimuiteHoiDirection(AcchimuiteHoi.Direction direction){
        mAcchi.setEnemyDirection(direction);
    }
    
    public void setAcchimuiteHoiListener(AcchimuiteHoiListener listener){
        mAcchi.setAcchimuiteHoiListener(listener);
    }
    
    public void setZhankenListener(ZhankenListener listener){
        mZhanken.setZhankenListener(listener);
    }

    public interface AcchimuiteHoiListener {
        public void onAcchimuiteHoiSet(Direction direction);
    }

    public interface ZhankenListener {
        public Command onCommandSet(Command command);
    }

}
