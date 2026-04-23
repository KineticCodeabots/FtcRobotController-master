package org.firstinspires.ftc.teamcode.LearningJavaCode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class BackroundCodeDrivetrain {
    private DcMotor frontLeft, backLeft, frontRight, backRight;
    private IMU imu;

    public void init(HardwareMap hwMap) {
        frontLeft = hwMap.get(DcMotor.class, "LeftFront");
        backLeft = hwMap.get(DcMotor.class, "LeftBack");
        frontRight = hwMap.get(DcMotor.class, "RightFront");
        backRight = hwMap.get(DcMotor.class, "RightBack");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double strafe, double rotate) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double MaxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeft.setPower(MaxSpeed * (frontLeftPower / maxPower));
        frontRight.setPower(MaxSpeed * (frontRightPower / maxPower));
        backLeft.setPower(MaxSpeed * (backLeftPower / maxPower));
        backRight.setPower(MaxSpeed * (backRightPower / maxPower));
    }
    public void driveFieldRelative(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);
        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double new_forward = 0.1; /* r * Math.sin(theta); */
        double new_stafe = 0.1; /* r * Math.cos(theta); */
        this.drive(new_forward, new_stafe, rotate);
    }
}