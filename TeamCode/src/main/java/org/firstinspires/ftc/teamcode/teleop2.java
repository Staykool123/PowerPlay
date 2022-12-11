package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class teleop2 extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight, liftDrive;
    private Servo claw, rotator;
    boolean aButtonPreviousState = false;
    boolean slowModeActive = false;
    public static final double open = 0.1;
    public static final double close = 0.265;
    public static final double normal = 1;
    public static final double mid = 0.7;
    public static final double back = 0.38;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        liftDrive = hardwareMap.get(DcMotorEx.class, "lift");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        claw = hardwareMap.get(Servo.class,"claw");
        rotator = hardwareMap.get(Servo.class,"rotator");

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

            double liftUp = gamepad2.right_trigger;
            double liftDown = gamepad2.left_trigger;

            double frontLeftPower = Range.clip(drive + strafe + turn, -0.7, 0.7);
            double frontRightPower = Range.clip(drive - strafe - turn, -0.7, 0.7);
            double backLeftPower = Range.clip(drive - strafe + turn, -0.7, 0.7);
            double backRightPower = Range.clip(drive + strafe - turn, -0.7, 0.7);
            double liftDrivePower = Range.clip(-liftUp + liftDown, -1.0, 1.0);

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
            liftDrive.setPower(liftDrivePower);
        }
        else{
            double drive = gamepad1.left_stick_y * .7;
            double strafe = -gamepad1.left_stick_x * .7;
            double turn = -gamepad1.right_stick_x * .5;

            double liftUp = gamepad2.right_trigger;
            double liftDown = gamepad2.left_trigger;

            double frontLeftPower = Range.clip(drive + strafe + turn, -0.7, 0.7);
            double frontRightPower = Range.clip(drive - strafe - turn, -0.7, 0.7);
            double backLeftPower = Range.clip(drive - strafe + turn, -0.7, 0.7);
            double backRightPower = Range.clip(drive + strafe - turn, -0.7, 0.7);
            double liftDrivePower = Range.clip(-liftUp + liftDown, -1.0, 1.0);

            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);
            liftDrive.setPower(liftDrivePower);
        }
        aButtonPreviousState = gamepad1.a;

        if (gamepad2.dpad_left){
            rotator.setPosition(mid);
        }
        if (gamepad2.dpad_down){
            rotator.setPosition(back);
        }
        if (gamepad2.dpad_up){
            rotator.setPosition(normal);
        }
        if (gamepad2.b){
            claw.setPosition(open);
        }
        if (gamepad2.x){
            claw.setPosition(close);
        }
        if (gamepad2.right_bumper) {
            rotator.setPosition(rotator.getPosition() + 0.0005);
        }
        if(gamepad2.left_bumper) {
            rotator.setPosition(rotator.getPosition()-0.0005);
        }

    }
}
