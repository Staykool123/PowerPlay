package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
@TeleOp
public class TestingClaw extends OpMode {
    private Servo claw, rotator;

    public static final double open = 0.2;
    public static final double close = 0.35;
    public static final double normal = 0.50;
    public static final double up = 0.90;
    public static final double down = .10;

    @Override
    public void init() {
        claw = hardwareMap.get(Servo.class,"claw");
        rotator = hardwareMap.get(Servo.class,"rotator");
    }

    @Override
    public void loop() {
        if (gamepad2.dpad_up){
            rotator.setPosition(up);
        }
        if (gamepad2.dpad_down){
            rotator.setPosition(down);
        }
        if (gamepad2.dpad_left){
            rotator.setPosition(normal);
        }
        if (gamepad2.x){
            claw.setPosition(open);
        }
        if (gamepad2.y){
            claw.setPosition(close);
        }
    }

}