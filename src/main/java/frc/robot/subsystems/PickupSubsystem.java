// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PickupSubsystem extends SubsystemBase {
  private CANSparkMax m_IntakeWheels = new CANSparkMax(Constants.Intake.IntakeWheelsId, MotorType.kBrushless);
  private CANSparkMax m_Tilt = new CANSparkMax(Constants.Intake.TiltMotorId, MotorType.kBrushless);

  private double wantedTiltPosition = Constants.Intake.TiltPositions.FULLY_IN;

  DutyCycleEncoder tiltEncoder = new DutyCycleEncoder(Constants.Intake.TiltSensorId);

  PIDController tiltPID = new PIDController(10,5,0);

  /** Creates a new PickupSubsystem. */
  public PickupSubsystem() {
    tiltEncoder.setPositionOffset(Constants.Intake.TiltOffset);
  }

  // Sets the new wanted tilt position.
  public Command SetTilt(double newTiltPos) {
    return runOnce(
      () -> {
        wantedTiltPosition = newTiltPos;
      }
    );
  }

  // Runs the intake to pull in a note.
  public Command PullInToIntake() {
    return runOnce(
      () -> {
        m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.InSpeed);
      }
    );
  }

  // Runs the intake to push out a note.
  public Command PushOutOfIntake() {
    return runOnce(
      () -> {
        m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.OutSpeed);
      }
    );
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Wanted Intake Tilt Position", tiltEncoder.getAbsolutePosition());

    // Calculate the PID Value
    double pidOutput = tiltPID.calculate(tiltEncoder.getAbsolutePosition(), wantedTiltPosition);
    SmartDashboard.putNumber("Tilt PID Output", pidOutput);

    // Check to see of we are moving the tilt in, if so, ignore the lower deadzone.
    if (
      pidOutput > 0 &&
      !(tiltEncoder.getAbsolutePosition() > Constants.Intake.TiltPIDCutoffPositions.CutoffIn)
    ) {
      m_Tilt.set(MathUtil.clamp(pidOutput, -0.2, 0.2));
    }

    if (
      pidOutput < 0 &&
      !(tiltEncoder.getAbsolutePosition() < Constants.Intake.TiltPIDCutoffPositions.CutoffIn)
    ) {
      m_Tilt.set(MathUtil.clamp(pidOutput, -0.2, 0.2));
    }
  }
}
