package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class EncoderSlider {
    //don't care about this it's totally useless atm
    public DcMotorEx liftMotor; //for better PID of run to position

    protected HardwareMap hwMap;
    //protected TeleBug teleBug;

    private static final double TICKS_PER_MOTOR_REV     = 1120; //Andymark motor on rev 20 gear box
    private static final double WHEEL_DIAMETER_INCHES   = 1.5;     // For figuring circumference
    public static final double TICKS_PER_INCH = TICKS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    private static final int POSITIONING_TOLERANCE = 30; //The amount of ticks the DriveByInch method should be allowed to deviate by
    public static int BOTTOM = 0; //position at the bottom
    private static final int TOP = 6000; //position at the top
    public int targetPos;

    public EncoderSlider (HardwareMap hwMap){
        this.hwMap = hwMap;
    }

    public void init() {
        liftMotor = hwMap.get(DcMotorEx.class, "lifthmm");
        liftMotor.setTargetPositionTolerance(POSITIONING_TOLERANCE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveByInch(double inches, double power){
        targetPos = (int)(inches * TICKS_PER_INCH);

//        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (liftMotor.isBusy() ){}
        liftMotor.setPower(0);
    }

    public void moveByInchTele(double inches, double power){
        targetPos = (int)(inches * TICKS_PER_INCH);

//        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        //prevent going past 0?
        if (liftMotor.getCurrentPosition() + targetPos < BOTTOM-1)
            liftMotor.setTargetPosition(BOTTOM-1);
        else
            liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void moveByInchTeleBeyond(double inches, double power){
        int targetPos = (int)(inches * TICKS_PER_INCH);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        //prevent going past 0?
        liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stop() {liftMotor.setPower(0.0);}

    public int positionTicks() {return liftMotor.getCurrentPosition(); }

    public void firstPos() {
        moveByInch(7, 1.);
    }
    public void bottom() { //move to the bottom position
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(-1.0);

        liftMotor.setTargetPosition(BOTTOM-1);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void release() { //move to the bottome position
        moveByInch(-9, 1.);
    }

    public void pull() {
        moveByInch(9, 1.);
    }
}