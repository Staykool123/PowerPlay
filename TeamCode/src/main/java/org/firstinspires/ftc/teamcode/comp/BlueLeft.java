package org.firstinspires.ftc.teamcode.comp;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous
public class BlueLeft extends LinearOpMode
{
    private Servo claw, rotator;
    private DcMotorEx lift;
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
        claw = hardwareMap.get(Servo.class,"claw");
        rotator = hardwareMap.get(Servo.class,"rotator");
        lift = hardwareMap.get(DcMotorEx.class,"lift");
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

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = new Pose2d(-36, -64.5, 270);
        drive.setPoseEstimate(startPose);

        Trajectory grabSeq = drive.trajectoryBuilder(new Pose2d(-13.5,0, 270), true)
                .addTemporalMarker(0, () -> {
                    rotator.setPosition(0.335);
                    lift.setPower(1.0);
                })
                .addTemporalMarker(2, () -> {
                    lift.setPower(0);
                })
                .splineTo(new Vector2d(-50.5,-11), Math.toRadians(180))
                .build();

        Trajectory grabSeq2= drive.trajectoryBuilder(new Pose2d(-13.5,0, 270), true)
                .splineTo(new Vector2d(-51.5,-5), Math.toRadians(180))
                .build();

        Trajectory deliverSeq = drive.trajectoryBuilder(grabSeq.end())
                .splineTo(new Vector2d(-15,-4), Math.toRadians(0))
                .build();

        Trajectory deliverSeq2 = drive.trajectoryBuilder(grabSeq.end())
                .splineTo(new Vector2d(-14,-5), Math.toRadians(0))
                .build();

        TrajectorySequence dropSeq = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(0.001)
                .addTemporalMarker(0, () -> {
                    lift.setPower(1.0);
                    rotator.setPosition(0.96);
                })
                .addTemporalMarker(0.5, () -> {
                    claw.setPosition(0.1);
                    lift.setPower(0);
                })
                .build();

        TrajectorySequence actualGrabSeq = drive.trajectorySequenceBuilder(new Pose2d())
                .forward(1.5)
                .addTemporalMarker(0.5, () -> {
                    claw.setPosition(.265);
                })
                .build();

        TrajectorySequence startSeq = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    lift.setPower(-1.0);
                    claw.setPosition(0.265);
                })
                .lineTo(
                        new Vector2d(-23,5),
                        SampleMecanumDrive.getVelocityConstraint(18, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .forward(4)
                .addDisplacementMarker(() -> {
                    lift.setPower(0);
                })

                .build();

        TrajectorySequence backSeq = drive.trajectorySequenceBuilder(deliverSeq2.end())
                .back(7)
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(-30,-40,270))
                .build();

        Trajectory rightSeq = drive.trajectoryBuilder(new Pose2d(-30,-40,270))
                .forward(22)
                .build();

        Trajectory leftSeq = drive.trajectoryBuilder(new Pose2d(-30,-40,270))
                .back(22)
                .build();
        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        claw.setPosition(0.265);
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

        if(tagOfInterest == null || tagOfInterest.id == LEFT){ //left
            drive.followTrajectorySequence(startSeq);
            drive.followTrajectorySequence(dropSeq);
            drive.followTrajectory(grabSeq);
            drive.followTrajectorySequence(actualGrabSeq);
            drive.followTrajectory(deliverSeq);
            drive.followTrajectory(grabSeq2);
            drive.followTrajectory(deliverSeq2);
            drive.followTrajectorySequence(backSeq);
            drive.followTrajectory(leftSeq);

        }else if(tagOfInterest.id == MIDDLE){ //middle
            drive.followTrajectorySequence(startSeq);
            drive.followTrajectory(grabSeq);
            drive.followTrajectory(deliverSeq);
            drive.followTrajectory(grabSeq2);
            drive.followTrajectory(deliverSeq2);
            drive.followTrajectorySequence(backSeq);

        }else{ //right
            drive.followTrajectorySequence(startSeq);
            drive.followTrajectory(grabSeq);
            drive.followTrajectory(deliverSeq);
            drive.followTrajectory(grabSeq2);
            drive.followTrajectory(deliverSeq2);
            drive.followTrajectorySequence(backSeq);
            drive.followTrajectory(rightSeq);
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