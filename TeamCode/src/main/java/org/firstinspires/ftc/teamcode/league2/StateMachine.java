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
        STACK

    }
    public void setLiftState(LiftState state){
        this.liftState = state;
    }
    LiftState liftState = LiftState.GROUND;
    public DcMotor lift;
    ElapsedTime liftTimer = new ElapsedTime();
    public void init(){
        liftTimer.reset();
        lift = hwMap.get(DcMotorEx.class, "lift");
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        }
    }
    public void moveToTarget(int target, double power){
        lift.setPower(power);
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void ground(){
        moveToTarget(1, 0.7);
    }
    public void low(){
        moveToTarget(2100, 0.73);
    }
    public void medium(){
        moveToTarget(4000,0.73);
    }
    public void high(){
        moveToTarget(5900,0.76);
    }
    public void stack(){
        moveToTarget(lift.getCurrentPosition() - 200,0.3);
    }

}