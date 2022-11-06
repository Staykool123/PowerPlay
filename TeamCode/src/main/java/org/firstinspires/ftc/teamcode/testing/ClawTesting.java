package org.firstinspires.ftc.teamcode.testing;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class ClawTesting {
        public Servo leftclaw, rightclaw;

        protected HardwareMap hwMap;

    public void init () {
        leftclaw = hwMap.get(Servo.class,"leftclaw");
        rightclaw = hwMap.get(Servo.class,"rightclaw");
    }




}