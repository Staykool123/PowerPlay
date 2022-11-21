package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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
        LOW
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
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void runLiftState() {
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
        }
    }
    public void moveToTarget(int target, double power) {
        lift.setPower(power);
        lift.setTargetPosition(target);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void ground(){
        moveToTarget(0, 1);
    }
    public void low(){
        moveToTarget(100, 1);
    }
    public void medium(){
        moveToTarget(200,1);
    }
    public void high(){
        moveToTarget(300,1);
    }
}