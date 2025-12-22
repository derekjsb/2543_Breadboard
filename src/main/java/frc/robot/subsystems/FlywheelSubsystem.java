// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Preferences;

import frc.robot.subsystems.LedSubsystem;
import frc.robot.commands.LedColorCommand;

public class FlywheelSubsystem extends SubsystemBase {
  private TalonFX flywheel;
  private TorqueCurrentFOC torquecurrent;
  private double torqueOutput;
  public double fwDeadband;
  public double fwMaxTorque;
  private double flywheelSpeed;
  private LedSubsystem ledSub;
  private int ledDebounce;

  /** Creates a new ExampleSubsystem. */
  public FlywheelSubsystem() {
    Preferences.initDouble(Constants.flywheelDeadbandKey, 0.05);
    Preferences.initDouble(Constants.maxTorqueKey, 20);
    flywheel = new TalonFX(25);
    setConfiguration();
    loadPreferences();
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public void setSpeed(double speed, double torque) {
    if (speed < -fwDeadband) {
      torqueOutput = Math.abs(torque)*fwMaxTorque;
    }
    else if (speed >= fwDeadband) {
      torqueOutput = -Math.abs(torque)*fwMaxTorque;
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
   var currentLimitConfig = new CurrentLimitsConfigs()
      .withStatorCurrentLimitEnable(true)
      .withStatorCurrentLimit(30)
      .withSupplyCurrentLimitEnable(true)
      .withSupplyCurrentLimit(30);

    var motorOutputConfig = new MotorOutputConfigs()
      .withInverted(InvertedValue.CounterClockwise_Positive)
      .withNeutralMode(NeutralModeValue.Coast);
    
    var slot0Config = new Slot0Configs()
      .withGravityType(GravityTypeValue.Elevator_Static)
      .withKA(0)
      .withKG(0.0)
      .withKP(0.0)
      .withKI(0.0)
      .withKS(0.0)
      .withKV(0.0)
      .withKD(0.0);

    var talonFXConfig = new TalonFXConfiguration()
      .withMotorOutput(motorOutputConfig)
      .withSlot0(slot0Config)
      .withCurrentLimits(currentLimitConfig);

    flywheel.getConfigurator().apply(talonFXConfig);
  }

  public void loadPreferences() {
    fwDeadband = Preferences.getDouble(Constants.flywheelDeadbandKey, Constants.flywheelDeadbandDefaultValue);
    fwMaxTorque = Preferences.getDouble(Constants.maxTorqueKey, Constants.torqueDefaultValue);
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
