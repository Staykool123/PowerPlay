package org.firstinspires.ftc.teamcode.league1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous
@Disabled
public class RedRight1 extends LinearOpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight, liftDrive;
    private Servo leftclaw, rightclaw;
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     FORWARD_SPEED = 0.3;
    static final double     BACKWARD_SPEED = -0.3;
    public static final double open = 0.45;
    @Override
    public void runOpMode() {
        // Initialize the drive system variables.
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        leftclaw = hardwareMap.get(Servo.class, "leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
        liftDrive = hardwareMap.get(DcMotorEx.class,"lift");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        waitForStart();
        frontLeft.setPower(BACKWARD_SPEED);
        frontRight.setPower(FORWARD_SPEED);
        backLeft.setPower(FORWARD_SPEED);
        backRight.setPower(BACKWARD_SPEED); // strafe
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 1050)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(FORWARD_SPEED);
        frontRight.setPower(FORWARD_SPEED);
        backLeft.setPower(FORWARD_SPEED);
        backRight.setPower(FORWARD_SPEED); //move forward
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 325)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        liftDrive.setPower(FORWARD_SPEED); //stop driving and move lift
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 8)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(BACKWARD_SPEED);
        frontRight.setPower(FORWARD_SPEED);
        backLeft.setPower(FORWARD_SPEED);
        backRight.setPower(BACKWARD_SPEED);
        liftDrive.setPower(FORWARD_SPEED); //stop driving and move lift
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .3)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        liftDrive.setPower(FORWARD_SPEED); //stop driving and move lift
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .5)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        liftDrive.setPower(0); //stop lift and open claw
        leftclaw.setPosition(open);
        rightclaw.setPosition(open);
        while (opModeIsActive() && (runtime.seconds() < 2.5)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(BACKWARD_SPEED); //go backwards
        frontRight.setPower(BACKWARD_SPEED);
        backLeft.setPower(BACKWARD_SPEED);
        backRight.setPower(BACKWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 500)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        frontLeft.setPower(BACKWARD_SPEED); //strafe
        frontRight.setPower(FORWARD_SPEED);
        backLeft.setPower(FORWARD_SPEED);
        backRight.setPower(BACKWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 2000)) {
            telemetry.addData("Path", "Leg 1: %4.1f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        backLeft.setPower(0); // stop
        backRight.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
        telemetry.addData("Path",  "Complete");
        telemetry.update();
        sleep(1000);
    }
}