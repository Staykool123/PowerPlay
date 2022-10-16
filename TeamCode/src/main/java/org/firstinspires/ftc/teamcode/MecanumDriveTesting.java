package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;


@TeleOp
public class MecanumDriveTesting extends OpMode {


    private DcMotorEx frontLeft, frontRight, backLeft, backRight;


    @Override


    public void init() {
        telemetry.addData("Status", "Initializing...");


        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    @Override
    public void loop() {
        double drive = gamepad1.left_stick_x * .75;
        double strafe = -gamepad1.right_stick_x * 1.1;
        double turn = -gamepad1.right_stick_y;

        double frontLeftPower = Range.clip(drive - strafe + turn, -0.7, 0.7);
        double frontRightPower = Range.clip(drive - strafe - turn, -0.7, 0.7);
        double backLeftPower = Range.clip(drive + strafe + turn, -0.7, 0.7);
        double backRightPower = Range.clip(-drive - strafe + turn, -0.7, 0.7);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

    }

}