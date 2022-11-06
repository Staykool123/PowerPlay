package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.testing.Testinglift;

@TeleOp
public class Teleoptesting extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight, liftDrive;
    private Servo leftclaw, rightclaw;
    public static final double open1 = 0.45;
    public static final double open2 = 0.45;
    public static final double close1 = 0.15;
    public static final double close2 = 0.15;

    Testinglift testinglift;


    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        testinglift = new Testinglift(hardwareMap);
        testinglift.init();

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
        if (gamepad2.x){
            leftclaw.setPosition(open1);
            rightclaw.setPosition(open2);
        }
        if (gamepad2.y) {
            leftclaw.setPosition(close1);
            rightclaw.setPosition(close2);
        }
        if(gamepad2.dpad_up){
            testinglift.firstPos();
        }
        if(gamepad2.dpad_down){
            testinglift.moveByInchTele(-2, 0.5);
        }


        telemetry.addData("Motors", "frontLeft (%.2f), frontRight (%.2f), backLeft (%.2f), backRight(%.2f)",
                frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.update();

    }
}
