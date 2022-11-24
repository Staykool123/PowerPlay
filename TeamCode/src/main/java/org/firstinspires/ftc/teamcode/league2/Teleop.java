package org.firstinspires.ftc.teamcode.league2;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class Teleop extends OpMode {
    StateMachine stateMachine;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private Servo claw, rotator;
    public static final double open = 0.2;
    public static final double close = 0.35;
    public static final double normal = 1;
    public static final double mid = 0.7;
    public static final double back = 0.38;
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


        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        double turnRight = gamepad1.right_trigger * .75;
        double turnLeft = gamepad1.left_trigger * .75;
        double drive = -gamepad1.right_stick_x * .75;
        double strafe = -gamepad1.right_stick_y;

        double driveSlow = -gamepad1.left_stick_x * .3;
        double strafeSlow = -gamepad1.left_stick_y * .3;

        double frontLeftPower = Range.clip(turnRight - turnLeft - drive + strafe - driveSlow + strafeSlow, -0.7, 0.7);
        double frontRightPower = Range.clip(turnRight - turnLeft - drive - strafe - driveSlow - strafeSlow, -0.7, 0.7);
        double backLeftPower = Range.clip(turnRight - turnLeft + drive + strafe + driveSlow + strafeSlow, -0.7, 0.7);
        double backRightPower = Range.clip(-turnRight + turnLeft - drive + strafe -driveSlow + strafeSlow, -0.7, 0.7);


        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        if (gamepad2.dpad_left){
            rotator.setPosition(mid);
        }
        if (gamepad2.dpad_down){
            rotator.setPosition(back);
        }
        if (gamepad2.dpad_up){
            rotator.setPosition(normal);
        }
        if (gamepad2.right_bumper){
            claw.setPosition(open);
        }
        if (gamepad2.left_bumper) {
            claw.setPosition(close);
        }
        if(gamepad2.x){
            stateMachine.setLiftState(StateMachine.LiftState.GROUND);
            stateMachine.runLiftState();
        }
        if(gamepad2.y){
            stateMachine.setLiftState(StateMachine.LiftState.LOW);
            stateMachine.runLiftState();
        }
        if(gamepad2.a){
            stateMachine.setLiftState(StateMachine.LiftState.MEDIUM);
            stateMachine.runLiftState();
        }
        if(gamepad2.b){
            stateMachine.setLiftState(StateMachine.LiftState.HIGH);
            stateMachine.runLiftState();
        }
        telemetry.addData("Motors", "frontLeft (%.2f), frontRight (%.2f), backLeft (%.2f), backRight(%.2f)",
                frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.update();

    }
}