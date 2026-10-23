package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public abstract class Container extends LinearOpMode {
    // Drivetrain hardware
    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;

    // Intake hardware
    public DcMotor intakeMotor;
    //public Servo leftIntakeServo;
    //public Servo rightIntakeServo;
    public DcMotor shooterMotor = null;

    // Call this inside runOpMode() in your TeleOp/Auto classes
    public void initHardware() {
        // drivetrain initialzation
        frontLeft = hardwareMap.get(DcMotor.class, "topLeftWheel");
        backLeft = hardwareMap.get(DcMotor.class, "bottomLeftWheel");
        frontRight = hardwareMap.get(DcMotor.class, "topRightWheel");
        backRight = hardwareMap.get(DcMotor.class, "bottomRightWheel");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //  Intake Initialization
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        //leftIntakeServo = hardwareMap.get(Servo.class, "leftIntakeServo");
        //rightIntakeServo = hardwareMap.get(Servo.class, "rightIntakeServo");

        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        //leftIntakeServo.setDirection(Servo.Direction.REVERSE);
        //rightIntakeServo.setDirection(Servo.Direction.REVERSE);

        //intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");

        // Set direction (REVERSE if the intake runs backwards relative to controller input)
        shooterMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set motor to brake when no power is applied
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void driveRobot(double y, double x, double rx) {
        double frontLeftPower  = (y + x) + (rx * 1.05);
        double backLeftPower   = (y - x) + (rx * 1.05);
        double frontRightPower = (y - x) - (rx * 1.05);
        double backRightPower  = (y + x) - (rx * 1.05);

        double max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(-backRightPower);

        telemetry.addData("Drive Vectors", "Y: %.2f, X: %.2f, RX: %.2f", y, x, rx);
        telemetry.addData("Motor Powers", "FL: %.2f, FR: %.2f, BL: %.2f, BR: %.2f",
                frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        telemetry.update();
    }
    public void shoot(double shotPower){
        if (shotPower > 0.1) {
            shooterMotor.setPower(shotPower); // Spins when trigger is pulled
        } else {
            shooterMotor.setPower(0.0);      // Stops only when trigger is released
        }
    }
    public void Intake(double takeIn){
        if(takeIn > 0.1){
            intakeMotor.setPower(takeIn);
        }
        else{
            intakeMotor.setPower(0.0);
        }
    }



}