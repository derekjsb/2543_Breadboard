// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlywheelSubsystem extends SubsystemBase {
  private TalonFX flywheel;
  private TorqueCurrentFOC torquecurrent;
  private double torqueOutput;
  /** Creates a new ExampleSubsystem. */
  public FlywheelSubsystem() {
    flywheel = new TalonFX(25);
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public void setSpeed(double speed, double torque) {
    if (speed < -Constants.flywheelDeadband) {
      torqueOutput = Math.abs(torque)*20;
    }
    else if (speed >= Constants.flywheelDeadband) {
      torqueOutput = -Math.abs(torque)*20;
    }
    else {
      torqueOutput = 0;
    }

    torquecurrent = new TorqueCurrentFOC(torqueOutput);
    flywheel.setControl(torquecurrent
    .withMaxAbsDutyCycle(Math.abs(speed)));
    // System.out.println(speed);
  }
  public void setConfiguration() {

  }
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
