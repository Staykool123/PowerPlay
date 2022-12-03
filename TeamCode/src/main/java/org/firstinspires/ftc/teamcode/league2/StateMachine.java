package org.firstinspires.ftc.teamcode.league2;
import android.os.SystemClock;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class StateMachine {
    protected HardwareMap hwMap;
    public StateMachine (HardwareMap hwMap){
        this.hwMap = hwMap;
    }
    public enum LiftState {
        GROUND,
        HIGH,
        MEDIUM,
        LOW,
        STACK,
        STACK2
    }
    public void setLiftState(LiftState state){
        this.liftState = state;
    }
    private LiftState liftState;
    public DcMotor lift;
    ElapsedTime liftTimer = new ElapsedTime();
    public void init(){
        liftTimer.reset();
        lift = hwMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftState = LiftState.GROUND;

    }
    public void runLiftState(){
        switch(liftState){
            case GROUND:
                ground();
                break;
            case LOW:
                low();
                break;
            case MEDIUM:
                medium();
                break;
            case HIGH:
                high();
                break;
            case STACK:
                stack();
                break;
            case STACK2:
                stackDecrease();
                break;
        }
    }
    public void moveToTarget(int target, double power){
        lift.setPower(power);
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void ground(){
        moveToTarget(1, 0.9);
    }
    public void low(){
        moveToTarget(2100, 0.9);
    }
    public void medium(){
        moveToTarget(4000,.9);
    }
    public void high(){
        moveToTarget(5900,0.9);
    }
    public void stackDecrease(){
        moveToTarget(lift.getCurrentPosition() - 200,1);
    }
    public void stack(){
        moveToTarget(1000, 0.9);
    }
}