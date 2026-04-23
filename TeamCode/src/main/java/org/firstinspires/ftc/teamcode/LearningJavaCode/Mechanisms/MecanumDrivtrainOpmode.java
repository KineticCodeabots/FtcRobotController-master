package org.firstinspires.ftc.teamcode.LearningJavaCode.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.LearningJavaCode.BackroundCodeDrivetrain;
@TeleOp
public class MecanumDrivtrainOpmode extends OpMode {
    BackroundCodeDrivetrain drive = new BackroundCodeDrivetrain();
    double forward, strafe, rotate;


    @Override
    public void init() {
      drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        drive.driveFieldRelative(forward, strafe, rotate);


    }
}
