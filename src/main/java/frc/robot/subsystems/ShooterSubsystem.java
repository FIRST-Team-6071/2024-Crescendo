// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
   //Initialize motors
  private final VictorSPX ShooterRightMotor = new VictorSPX(Constants.ShooterConstants.RightShooterMotor);
  private final VictorSPX ShooterLeftMotor = new VictorSPX(Constants.ShooterConstants.LeftShooterMotor);

  private boolean ShouldRunMotors = false;
  
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    ShooterLeftMotor.setInverted(true); 
  }

  public void RunMotors() {
    ShouldRunMotors = true;
  }
    public void StopMotors() {
    ShouldRunMotors = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (ShouldRunMotors) {
      ShooterRightMotor.set(VictorSPXControlMode.PercentOutput, Constants.ShooterConstants.ShooterSpeed); //I have no idea what 15 does here
      ShooterLeftMotor.set(VictorSPXControlMode.PercentOutput, Constants.ShooterConstants.ShooterSpeed); //I have no idea what 15 does here
      
    }
  }
}
