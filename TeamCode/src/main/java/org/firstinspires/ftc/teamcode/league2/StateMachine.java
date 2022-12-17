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
        STACKIN,
        STACKDE,
        STACK,
        PANIC
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
            case STACKIN:
                stackIncrease();
                break;
            case STACKDE:
                stackDecrease();
                break;
            case STACK:
                stackoffi();
                break;
            case PANIC:
                panic();
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
        moveToTarget(2300, 0.9);
    }
    public void medium(){
        moveToTarget(4000,.9);
    }
    public void high(){
        moveToTarget(5700,0.9);
    }
    public void stackoffi(){moveToTarget(900,0.9);}
    public void stackDecrease(){
        moveToTarget(lift.getCurrentPosition() - 150,1);
    }
    public void stackIncrease(){
        moveToTarget(lift.getCurrentPosition() + 100,1);
    }
    public void panic(){
        lift.setPower(0);
    }
}