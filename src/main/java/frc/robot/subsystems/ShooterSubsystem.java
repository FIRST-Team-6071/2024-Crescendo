// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  // Initialize motors
  private final VictorSPX ShooterRightMotor = new VictorSPX(Constants.ShooterConstants.RightShooterMotor);
  private final VictorSPX ShooterLeftMotor = new VictorSPX(Constants.ShooterConstants.LeftShooterMotor);

  private boolean ShouldRunMotors = false;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    ShooterLeftMotor.setInverted(false);
    ShooterRightMotor.setInverted(true);
  }

  public void RunMotors() {
    ShooterRightMotor.set(VictorSPXControlMode.PercentOutput, Constants.ShooterConstants.ShooterSpeed);
    ShooterLeftMotor.set(VictorSPXControlMode.PercentOutput, Constants.ShooterConstants.ShooterSpeed);
  }

  public Command StopMotors() {
    return runOnce(
      () -> {
        ShooterRightMotor.set(VictorSPXControlMode.PercentOutput, 0);
        ShooterLeftMotor.set(VictorSPXControlMode.PercentOutput, 0);
      }
    );
  }


  @Override
  public void periodic() {
  }
}
