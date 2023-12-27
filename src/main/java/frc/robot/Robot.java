// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // CANSparkMax driveLeftSpark = new CANSparkMax(1, MotorType.kBrushed);
  // CANSparkMax driveRightSpark = new CANSparkMax(2, MotorType.kBrushed);

  VictorSPX driveLeftVictor = new VictorSPX(3);
  VictorSPX driveRightVictor = new VictorSPX(4);

  Joystick jDrive = new Joystick(0);


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    // driveLeftSpark.setInverted(true);
    // driveRightSpark.setInverted(false);
    driveLeftVictor.setInverted(false);
    driveRightVictor.setInverted(true);
  }

  public void setDriveMotors(double forward, double turn) {
    SmartDashboard.putNumber("Drive forward power (%)", forward);
    SmartDashboard.putNumber("Drive turn power (%)", turn);

    double left = forward + turn;
    double right = forward - turn;

    SmartDashboard.putNumber("Drive left power (%)", left);
    SmartDashboard.putNumber("Drive right power (%)", right);

    driveLeftVictor.set(ControlMode.PercentOutput, left);
    driveRightVictor.set(ControlMode.PercentOutput, right);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    // driveLeftSpark.setIdleMode(IdleMode.kCoast);
    // driveRightSpark.setIdleMode(IdleMode.kCoast);
    driveLeftVictor.setNeutralMode(NeutralMode.Coast);
    driveRightVictor.setNeutralMode(NeutralMode.Coast);

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Drive with split arcade drive.
    // That means that the Y axis of the left stick moves forward
    // and backward, and the X of the right stick turns left and right.
    
    setDriveMotors(-jDrive.getRawAxis(1), jDrive.getRawAxis(4));
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    // driveLeftSpark.setIdleMode(IdleMode.kCoast);
    // driveRightSpark.setIdleMode(IdleMode.kCoast);
    driveLeftVictor.setNeutralMode(NeutralMode.Coast);
    driveRightVictor.setNeutralMode(NeutralMode.Coast);
  }
  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    // motor speed range is -1.0 to 1.0
    // driveLeftSpark.set(-jDrive.getRawAxis(1));
    // driveRightSpark.set(-jDrive.getRawAxis(5));
    driveLeftVictor.set(ControlMode.PercentOutput, -jDrive.getRawAxis(1));
    driveRightVictor.set(ControlMode.PercentOutput, -jDrive.getRawAxis(5));
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
