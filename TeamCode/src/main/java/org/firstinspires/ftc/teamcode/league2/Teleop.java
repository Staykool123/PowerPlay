package org.firstinspires.ftc.teamcode.league2;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class Teleop extends OpMode {
    StateMachine stateMachine;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private Servo claw, rotator;
    boolean aButtonPreviousState = false;
    boolean slowModeActive = false;
    public static final double open = 0.1;
    public static final double close = 0.265;
    public static final double normal = 1;
    //    public static final double mid = 0.7;
    public static final double back = 0.335;
    boolean call = true;
    boolean call1 = true;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        stateMachine = new StateMachine(hardwareMap);
        stateMachine.init();
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class,"claw");
        rotator = hardwareMap.get(Servo.class,"rotator");
        claw.setPosition(open);
        rotator.setPosition(normal);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        if(gamepad1.a && !aButtonPreviousState){
            slowModeActive = !slowModeActive;
        }
        if(slowModeActive){
            double drive = gamepad1.left_stick_y * .3;
            double strafe = -gamepad1.left_stick_x * .3;
            double turn = -gamepad1.right_stick_x * .3;

            double frontLeftPower = Range.clip(drive + strafe + turn, -0.7, 0.7);
            double frontRightPower = Range.clip(drive - strafe - turn, -0.7, 0.7);
            double backLeftPower = Range.clip(-drive - strafe + turn, -0.7, 0.7);
            double backRightPower = Range.clip(-drive + strafe - turn, -0.7, 0.7);

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
        }
        else{
            double drive = gamepad1.left_stick_y * .7;
            double strafe = -gamepad1.left_stick_x * .7;
            double turn = -gamepad1.right_stick_x;

            double frontLeftPower = Range.clip(drive + strafe + turn, -0.7, 0.7);
            double frontRightPower = Range.clip(drive - strafe - turn, -0.7, 0.7);
            double backLeftPower = Range.clip(-drive - strafe + turn, -0.7, 0.7);
            double backRightPower = Range.clip(-drive + strafe - turn, -0.7, 0.7);

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
        }
        aButtonPreviousState = gamepad1.a;

//        if (gamepad2.dpad_left){
//            rotator.setPosition(mid);
//        }
//        if (gamepad2.dpad_down){
//            rotator.setPosition(back);
//        }
//        if (gamepad2.dpad_up){
//            rotator.setPosition(normal);
//        }
//        if (gamepad2.right_bumper){
////            claw.setPosition(open);
////        }
////        if (gamepad2.left_bumper) {
////            claw.setPosition(close);
////        }

        if (gamepad2.left_bumper){
            while(gamepad2.left_bumper) {
            }
            if(call){
                rotator.setPosition(back);
                call = false;
                return;
            }
            else if(!call){
                rotator.setPosition(normal);
                call = true;
                return;
            }
        }
        if(gamepad2.right_bumper){
            while(gamepad2.right_bumper){

            }
            if(call1){
                claw.setPosition(close);
                call1 = false;
                return;
            }
            else if(!call1){
                claw.setPosition(open);
                call1 = true;
                return;
            }
        }
        telemetry.addData("rotator",rotator.getPosition());
        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("call", call);
        telemetry.addData("call1", call);
        telemetry.update();
        if(gamepad2.x){
            stateMachine.setLiftState(StateMachine.LiftState.GROUND);
            stateMachine.runLiftState();
        }
        if(gamepad2.y){
            stateMachine.setLiftState(StateMachine.LiftState.LOW);
            stateMachine.runLiftState();
        }
        if(gamepad2.b){
            stateMachine.setLiftState(StateMachine.LiftState.MEDIUM);
            stateMachine.runLiftState();
        }
        if(gamepad2.a){
            stateMachine.setLiftState(StateMachine.LiftState.HIGH);
            stateMachine.runLiftState();
        }

        if (gamepad2.dpad_down){
            stateMachine.setLiftState(StateMachine.LiftState.STACKDE);
            stateMachine.runLiftState();
        }
        if(gamepad2.dpad_up){
            stateMachine.setLiftState(StateMachine.LiftState.STACKIN);
            stateMachine.runLiftState();
        }
        if(gamepad2.dpad_left){
            stateMachine.setLiftState(StateMachine.LiftState.STACK);
            stateMachine.runLiftState();
        }
        if (gamepad1.left_bumper) {
            rotator.setPosition(rotator.getPosition() + 0.0005);
        }
        if(gamepad1.right_bumper) {
            rotator.setPosition(rotator.getPosition()-0.0005);
        }
    }
    public void stop(){
        stateMachine.init();
    }
}
