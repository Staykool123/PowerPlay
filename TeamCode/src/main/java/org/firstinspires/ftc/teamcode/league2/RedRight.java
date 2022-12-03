package org.firstinspires.ftc.teamcode.league2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.league2.CameraDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous //change back to teleop of no work
public class RedRight extends LinearOpMode
{
    //    StateMachine stateMachine;
//    private Servo claw, rotator;
//    public static final double open = 0.2;
//    public static final double close = 0.35;
//    public static final double normal = 1;
//    public static final double mid = 0.7;
//    public static final double back = 0.38;
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private ElapsedTime runtime = new ElapsedTime();
    static final double     FORWARD_SPEED = 0.4;
    static final double     BACKWARD_SPEED = -0.4;
    OpenCvCamera camera;
    CameraDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode()
    {
//        stateMachine = new StateMachine(hardwareMap);
//        stateMachine.init();
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
//        claw = hardwareMap.get(Servo.class,"claw");
//        rotator = hardwareMap.get(Servo.class,"rotator");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new CameraDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }

        /*
         * The START command just came in: now work off the latest snapshot acquired
         * during the init loop.
         */

        /* Update the telemetry */
        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        /* Actually do something useful */

        //frontright and backright powers are inverted
        if(tagOfInterest == null || tagOfInterest.id == LEFT){ //left
            frontLeft.setPower(-0.4);
            frontRight.setPower(-0.4);
            backLeft.setPower(0.4);
            backRight.setPower(0.4); // strafe left
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 2000)) {
                telemetry.addData("Yay", "1", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 350)) {
                telemetry.addData("Yay", "2", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0.4);
            frontRight.setPower(0.4);
            backLeft.setPower(-0.4);
            backRight.setPower(-0.4); // strafe right
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 300)) {
                telemetry.addData("Yay", "3", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 350)) {
                telemetry.addData("Yay", "4", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(-0.4);
            frontRight.setPower(0.4);
            backLeft.setPower(-0.4);
            backRight.setPower(0.4); // move forward
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 850)) {
                telemetry.addData("Yay", "5", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 10000)) {
                telemetry.addData("Yay", "6", runtime.seconds());
                telemetry.update();
            }





        }else if(tagOfInterest.id == MIDDLE){ //middle
            frontLeft.setPower(-0.4);
            frontRight.setPower(-0.4);
            backLeft.setPower(0.4);
            backRight.setPower(0.4); // strafe left
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 1600)) {
                telemetry.addData("Yay", "1", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 10000)) {
                telemetry.addData("Yay", "2", runtime.seconds());
                telemetry.update();
            }





        }else{ //right
            frontLeft.setPower(-0.4);
            frontRight.setPower(-0.4);
            backLeft.setPower(0.4);
            backRight.setPower(0.4); // strafe left
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 2000)) {
                telemetry.addData("Yay", "1", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 350)) {
                telemetry.addData("Yay", "2", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0.4);
            frontRight.setPower(0.4);
            backLeft.setPower(-0.4);
            backRight.setPower(-0.4); // strafe right
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 300)) {
                telemetry.addData("Yay", "3", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 350)) {
                telemetry.addData("Yay", "4", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0.4);
            frontRight.setPower(-0.4);
            backLeft.setPower(0.4);
            backRight.setPower(-0.4); // move forward
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 925)) {
                telemetry.addData("Yay", "5", runtime.seconds());
                telemetry.update();
            }
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0); // stop
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 10000)) {
                telemetry.addData("Yay", "6", runtime.seconds());
                telemetry.update();
            }
        }


        while (opModeIsActive()) {sleep(20);}
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}