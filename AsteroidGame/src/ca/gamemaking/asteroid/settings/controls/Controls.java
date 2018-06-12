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
    
    private int FORWARD;
    private int TURN_RIGHT;
    private int TURN_LEFT;
    private int SHOOT;
    
    public Controls(){
        SetDefault();
    }
    
    public Controls(Controls clone){
        this.FORWARD = clone.FORWARD;
        this.TURN_LEFT = clone.TURN_LEFT;
        this.TURN_RIGHT = clone.TURN_RIGHT;
        this.SHOOT = clone.SHOOT;
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

    public boolean setFORWARD(int FORWARD) {
        boolean valid;
        if (valid = checkValidity(FORWARD))
            this.FORWARD = FORWARD;
        
        return valid;
    }

    public int getTURN_RIGHT() {
        return TURN_RIGHT;
    }

    public boolean setTURN_RIGHT(int TURN_RIGHT) {
        boolean valid;
        if (valid = checkValidity(TURN_RIGHT))
            this.TURN_RIGHT = TURN_RIGHT;
        
        return valid;
    }

    public int getTURN_LEFT() {
        return TURN_LEFT;
    }

    public boolean setTURN_LEFT(int TURN_LEFT) {
        boolean valid;
        if (valid = checkValidity(TURN_LEFT))
            this.TURN_LEFT = TURN_LEFT;
        
        return valid;
    }

    public int getSHOOT() {
        return SHOOT;
    }

    public boolean setSHOOT(int SHOOT) {
        boolean valid;
        if (valid = checkValidity(SHOOT))
            this.SHOOT = SHOOT;
        
        return valid;
    }
    
    public void SetDefault(){
        FORWARD = KeyEvent.VK_W;
        TURN_RIGHT = KeyEvent.VK_D;
        TURN_LEFT = KeyEvent.VK_A;
        SHOOT = KeyEvent.VK_SPACE;
    }
    
    public boolean SetControl(String controlName, int keyCode){
        switch (controlName){
            case "R":
                return setTURN_RIGHT(keyCode);
            case "L":
                return setTURN_LEFT(keyCode);
            case "F":
                return setFORWARD(keyCode);
            case "S":
                return setSHOOT(keyCode);
            default:
                System.out.println("That's no control name");
                return false;
        }
    }
    
    private boolean checkValidity(int keycode){
        
        return (keycode != FORWARD) && (keycode != TURN_RIGHT) && (keycode != TURN_LEFT) && (keycode != SHOOT);  
    }
    
    @Override
    public String toString(){
        return "FORWARD:" + FORWARD + ";"
              +"TURN_RIGHT:" + TURN_RIGHT + ";"
              +"TURN_LEFT:" + TURN_LEFT + ";"
              +"SHOOT:" + SHOOT;
    }
    
}
