// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.utils.BlinkenColor;

public class BlinkenSubsystem extends SubsystemBase {
  private Spark m_Blinken = new Spark(Constants.BlinkenSubsystem.BlinkenPWMId);

  private BlinkenColor currentColor = BlinkenColor.VIOLET;

  /** Creates a new BlinkenSubsystem. */
  public BlinkenSubsystem() {
    m_Blinken.set(currentColor.getMotorValue());
  }

  public void SetBlinkenColor(BlinkenColor color) {
    currentColor = color;
    m_Blinken.set(color.getMotorValue());
  }

  public void Off() {
    m_Blinken.set(0);
  }

  public void On() {
    m_Blinken.set(currentColor.getMotorValue());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
