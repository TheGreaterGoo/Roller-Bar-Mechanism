// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.RollerBarSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  // Defining Subsystems
  private final RollerBarSubsystem RollerBar = new RollerBarSubsystem();
  public static final double DEADBAND = 0.1;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController manipulatorController = new CommandXboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    manipulatorController.a().whileTrue(Commands.runOnce(() -> RollerBar.runMotor(RollerBarSubsystem.INTAKE_SPEED * 0.8)));
    manipulatorController.povDown().whileTrue(Commands.runOnce(() -> RollerBar.runMotor(RollerBarSubsystem.OUTTAKE_SPEED * 0.5)));

    manipulatorController.rightBumper().onTrue(Commands.runOnce(() -> {
      RollerBar.runMotor(RollerBarSubsystem.OUTTAKE_SPEED);
      Commands.waitSeconds(5);
    }));

    manipulatorController.leftBumper().onTrue(Commands.runOnce(() -> {
      RollerBar.runMotor(RollerBarSubsystem.INTAKE_SPEED);
      Commands.waitSeconds(5);
    }));

    double leftX = manipulatorController.getLeftX();
    double leftY = manipulatorController.getLeftY();

    if (Math.abs(leftY) > DEADBAND) {
      Commands.runOnce(() -> RollerBar.runMotor(leftY * RollerBarSubsystem.INTAKE_SPEED));
    }
  }

  public Command getAutonomousCommand() {
      return null;
  }
}
