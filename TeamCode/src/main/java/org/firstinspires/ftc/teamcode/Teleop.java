package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.subsystems.EncoderSlider;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
        private DcMotorEx frontLeft, frontRight, backLeft, backRight, liftDrive;
        private Servo leftclaw, rightclaw;
        public static final double open1 = 0.45;
        public static final double open2 = 0.45;
        public static final double close1 = 0.15;
        public static final double close2 = 0.15;

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

        leftclaw = hardwareMap.get(Servo.class, "leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");

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

        double liftUp = gamepad2.right_trigger;
        double liftDown = gamepad2.left_trigger;

        double frontLeftPower = Range.clip(turnRight - turnLeft - drive + strafe - driveSlow + strafeSlow, -0.7, 0.7);
        double frontRightPower = Range.clip(turnRight - turnLeft - drive - strafe - driveSlow - strafeSlow, -0.7, 0.7);
        double backLeftPower = Range.clip(turnRight - turnLeft + drive + strafe + driveSlow + strafeSlow, -0.7, 0.7);
        double backRightPower = Range.clip(-turnRight + turnLeft - drive + strafe -driveSlow + strafeSlow, -0.7, 0.7);
        double liftDrivePower = Range.clip(liftUp - liftDown, -1.0, 1.0);


        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
        liftDrive.setPower(liftDrivePower);

        if (gamepad2.x){
            leftclaw.setPosition(open1);
            rightclaw.setPosition(open2);
        }
        if (gamepad2.y) {
            leftclaw.setPosition(close1);
            rightclaw.setPosition(close2);
        }


            telemetry.addData("Motors", "frontLeft (%.2f), frontRight (%.2f), backLeft (%.2f), backRight(%.2f)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.update();

        }
    }
