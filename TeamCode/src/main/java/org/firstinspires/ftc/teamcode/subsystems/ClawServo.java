package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class ClawServo extends OpMode {
    private Servo leftclaw, rightclaw;

    public static final double open1 = 0.45;
    public static final double open2 = 0.45;
    public static final double close1 = 0.15;
    public static final double close2 = 0.15;

    @Override
    public void init() {
        leftclaw = hardwareMap.get(Servo.class,"leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
    }

    @Override
    public void loop() {
        if (gamepad1.x){
            rightclaw.setPosition(close2);
            leftclaw.setPosition(close1);

        }
        if (gamepad1.y){
            rightclaw.setPosition(open2);
            leftclaw.setPosition(open1);

        }
    }

}