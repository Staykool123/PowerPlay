package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
<<<<<<< HEAD
=======
import com.qualcomm.robotcore.hardware.DcMotor;
>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
<<<<<<< HEAD
import org.firstinspires.ftc.teamcode.subsystems.EncoderSlider;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private Servo leftclaw, rightclaw;
    //    private Servo servo;
=======
@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight, liftDrive;
    private Servo leftclaw, rightclaw;
>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")
    public static final double open1 = 0;
    public static final double close1 = 0.45;
    public static final double open2 = 0.45;
    public static final double close2 = 0;

<<<<<<< HEAD
    EncoderSlider slider;

=======
>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

<<<<<<< HEAD
        // Connect to servo (Assume PushBot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
//        servo = hardwareMap.get(Servo.class, "left_hand");
        leftclaw = hardwareMap.get(Servo.class, "leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"wristclaw");
        slider = new EncoderSlider(hardwareMap);
        slider.init();
=======
//         Connect to servo (Assume PushBot Left Hand)
//         Change the text in quotes to match any servo name on your robot.
        leftclaw = hardwareMap.get(Servo.class, "leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
        liftDrive = hardwareMap.get(DcMotorEx.class, "lift");
>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
<<<<<<< HEAD
    public void loop() {
=======
       public void loop() {
>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")
        double drive;
        double strafe;
        double turn;
        drive = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        double frontLeftPower = Range.clip(drive + strafe + turn, -1.0, 1.0);
        double backLeftPower = Range.clip(drive - strafe + turn, -1.0, 1.0);
        double frontRightPower = Range.clip(drive - strafe - turn, -1.0, 1.0);
        double backRightPower = Range.clip(drive + strafe - turn, -1.0, 1.0);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        if (gamepad1.x){
            leftclaw.setPosition(open1);
            rightclaw.setPosition(open2);
        }
<<<<<<< HEAD
        if (gamepad1.y){
            leftclaw.setPosition(close1);
            rightclaw.setPosition(close2);
            if(gamepad2.dpad_up){
                slider.moveByInchTele(2, 0.5);
            }
            if(gamepad2.dpad_down){
                slider.moveByInchTele(-2, 0.5);
            }
            telemetry.addData("Motors", "frontLeft (%.2f), frontRight (%.2f), backLeft (%.2f), backRight(%.2f)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.addData("lift Position", slider.liftMotor.getCurrentPosition());
            telemetry.addData("lift Target", slider.targetPos);
            telemetry.update();

        }
    }
}
=======
        if (gamepad1.y) {
            leftclaw.setPosition(close1);
            rightclaw.setPosition(close2);
        }
        if (gamepad1.dpad_up) {
            liftDrive.setPower(1.0);
        }
        if (gamepad1.dpad_down) {
            liftDrive.setPower(-1.0);
        }
        if (!gamepad1.dpad_up && !gamepad1.dpad_down) {
            liftDrive.setPower(0);
        }
            telemetry.addData("Motors", "frontLeft (%.2f), frontRight (%.2f), backLeft (%.2f), backRight(%.2f)",
                    frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            telemetry.update();


        }
    }

>>>>>>> 5e63428 (Drive, claw, and slider all work in "teleop")
