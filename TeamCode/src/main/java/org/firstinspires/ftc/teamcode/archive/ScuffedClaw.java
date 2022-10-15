package org.firstinspires.ftc.teamcode.archive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ScuffedClaw extends OpMode {
    private Servo leftclaw, rightclaw;
    public static final double open1 = 0;
    public static final double close1 = 0.45;
    public static final double open2 = 0.45;
    public static final double close2 = 0;

    @Override
    public void init() {
        leftclaw = hardwareMap.get(Servo.class,"leftclaw");
        rightclaw = hardwareMap.get(Servo.class,"rightclaw");
    }

    @Override
    public void loop() {
        if (gamepad1.x){
            leftclaw.setPosition(open1);
            rightclaw.setPosition(open2);
        }
        if (gamepad1.y){
            leftclaw.setPosition(close1);
            rightclaw.setPosition(close2);
        }
    }

}