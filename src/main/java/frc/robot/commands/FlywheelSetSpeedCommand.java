// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.FlywheelSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FlywheelSetSpeedCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final FlywheelSubsystem m_subsystem;
  private DoubleSupplier speed;
  private DoubleSupplier torque;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FlywheelSetSpeedCommand(FlywheelSubsystem subsystem, DoubleSupplier setSpeed, DoubleSupplier setTorque) {
    m_subsystem = subsystem;
    speed = setSpeed;
    torque = setTorque;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  // m_subsystem.setSpeed(speed.getAsDouble());
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  m_subsystem.setSpeed(speed.getAsDouble(),torque.getAsDouble());
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
