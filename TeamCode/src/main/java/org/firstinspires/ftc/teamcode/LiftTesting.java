package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Lift Testing")
public class LiftTesting extends OpMode {
    //don't care about this it's totally useless atm
    private DcMotor lift;

    ElapsedTime liftTimer = new ElapsedTime();

    final int LIFT_LOW = 0;
    final int LIFT_HIGH = 10;
    final int LIFT_MIDDLE = 5;

    public enum liftState {
        LOW,
        HIGH,
        MIDDLE,
        START
    }

    liftState state = liftState.START;

    public void init() {
        liftTimer.reset();
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void loop() {
        switch (state) {
            case START:
                if (gamepad2.x) {
                    lift.setTargetPosition(LIFT_HIGH);
                    state = liftState.HIGH;
                }
                break;
            case LOW:
                if (gamepad2.y) {
                    lift.setTargetPosition(LIFT_LOW);
                    state = liftState.LOW;
                }
                break;
            case MIDDLE:
                if (gamepad2.a) {
                    lift.setTargetPosition(LIFT_MIDDLE);
                    state = liftState.MIDDLE;
                }
                break;
                }

                //drive stuff here


        }
    }
