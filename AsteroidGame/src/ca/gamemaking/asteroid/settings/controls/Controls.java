/*
 * Copyright 2018 © Maxime Lajoie - Tous droits réservés
 */
package ca.gamemaking.asteroid.settings.controls;

import java.awt.event.KeyEvent;

/**
 *
 * @author maxla
 */
public class Controls {
    
    int FORWARD;
    int TURN_RIGHT;
    int TURN_LEFT;
    int SHOOT;
    
    public Controls(){
        SetDefault();
    }
    
    public Controls(int FORWARD, int TURN_RIGHT, int TURN_LEFT, int SHOOT){
        this.FORWARD = FORWARD;
        this.TURN_RIGHT = TURN_RIGHT;
        this.TURN_LEFT = TURN_LEFT;
        this.SHOOT = SHOOT;
    }
    
    public Controls(String controls){
        String[] subControls = controls.split(";");
        
        if(subControls.length == 4){
            for(String control : subControls){
                String[] subControl = control.split(":");
                if (subControl.length == 2){
                    switch (subControl[0])
                    {
                        case "FORWARD":
                            FORWARD = Integer.parseInt(subControl[1]);
                            break;
                        case "TURN_RIGHT":
                            TURN_RIGHT = Integer.parseInt(subControl[1]);
                            break;
                        case "TURN_LEFT":
                            TURN_LEFT = Integer.parseInt(subControl[1]);
                            break;
                        case "SHOOT":
                            SHOOT = Integer.parseInt(subControl[1]);
                            break;
                        default:
                            System.out.println("Not a valid control name");
                    }
                }
            }
        }
        else{  
            SetDefault();
        }
    }

    public int getFORWARD() {
        return FORWARD;
    }

    public void setFORWARD(int FORWARD) {
        this.FORWARD = FORWARD;
    }

    public int getTURN_RIGHT() {
        return TURN_RIGHT;
    }

    public void setTURN_RIGHT(int TURN_RIGHT) {
        this.TURN_RIGHT = TURN_RIGHT;
    }

    public int getTURN_LEFT() {
        return TURN_LEFT;
    }

    public void setTURN_LEFT(int TURN_LEFT) {
        this.TURN_LEFT = TURN_LEFT;
    }

    public int getSHOOT() {
        return SHOOT;
    }

    public void setSHOOT(int SHOOT) {
        this.SHOOT = SHOOT;
    }
    
    public void SetDefault(){
        FORWARD = KeyEvent.VK_W;
        TURN_RIGHT = KeyEvent.VK_D;
        TURN_LEFT = KeyEvent.VK_A;
        SHOOT = KeyEvent.VK_SPACE;
    }
    
    @Override
    public String toString(){
        return "FORWARD:" + FORWARD + ";"
              +"TURN_RIGHT:" + TURN_RIGHT + ";"
              +"TURN_LEFT:" + TURN_LEFT + ";"
              +"SHOOT:" + SHOOT;
    }
    
}
