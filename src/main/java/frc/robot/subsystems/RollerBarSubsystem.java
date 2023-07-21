// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class RollerBarSubsystem extends SubsystemBase {

  // Constants
  public static final int talonFXID = 1; // CAN ID
  public static final WPI_TalonFX controller = new WPI_TalonFX(talonFXID);

  public static final double INTAKE_SPEED = -0.3;
  public static final double OUTTAKE_SPEED = 0.3;

  public static double motorPower;
  public static boolean isRunning;

  /** Creates a new RollerBarSubsystem. */
  public RollerBarSubsystem() {
    motorPower = 0;
    isRunning = false;
  }
  
  /**
   * @param power The voltage to be supplied to the motor
   */
  public void runMotor(double power) {
    if (isRunning) controller.set(ControlMode.PercentOutput, power);
  }

  public void stopMotor() {
    controller.set(ControlMode.PercentOutput, 0);
    controller.stopMotor();
  }

  public void disable() {
    isRunning = false;
    stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (isRunning) runMotor(motorPower);
  }
}
