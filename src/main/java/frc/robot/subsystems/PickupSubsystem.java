// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PickupSubsystem extends SubsystemBase {
  private CANSparkMax m_IntakeWheels = new CANSparkMax(Constants.Intake.IntakeWheelsId, MotorType.kBrushless);
  private CANSparkMax m_Tilt = new CANSparkMax(Constants.Intake.TiltMotorId, MotorType.kBrushless);

  private double wantedTiltPosition = Constants.Intake.TiltPositions.FULLY_IN;

  DutyCycleEncoder tiltEncoder = new DutyCycleEncoder(Constants.Intake.TiltSensorId);

  PIDController tiltPID = new PIDController(9, 0.0001 ,0);

  boolean isAtPosition = false;
  
  /** Creates a new PickupSubsystem. */
  public PickupSubsystem() {
    tiltEncoder.setPositionOffset(Constants.Intake.TiltOffset);
  }

  // Sets the new wanted tilt position.
  public Command SetTilt(double newTiltPos) {
    return runOnce(
      () -> {
        isAtPosition = false;
        wantedTiltPosition = newTiltPos;
      }
    );
  }

  public Command AlternateTilt() {
    return runOnce(() -> {
      if (wantedTiltPosition == Constants.Intake.TiltPositions.FULLY_IN) {
        wantedTiltPosition = Constants.Intake.TiltPositions.FULLY_OUT;
      } else {
        wantedTiltPosition = Constants.Intake.TiltPositions.FULLY_IN;
      }
    });
  }

  public void SetTiltRaw(double newTiltPos) {
    isAtPosition = false;
    wantedTiltPosition = newTiltPos;
  }

  // Runs the intake to pull in a note.
  public Command PullInToIntake() {
    return runOnce(
      () -> {
        m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.InSpeed);
      }
    );
  }

   // Runs the intake to pull in a note.
  public Command StopIntake() {
    return runOnce(
      () -> {
        m_IntakeWheels.set(0);
      }
    );
  }

  // Runs the intake to push out a note.
  public Command PushOutOfIntake() {
    return runOnce(() -> {
      m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.OutSpeed);
    });
  }

  public Command PushOutOfIntakeAmp() {
    return runOnce(() -> {
      m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.OutSpeed * .9);
    });
  }

  public Command PushOutOfIntakeLightly() {
    return runOnce(
      () -> {
        m_IntakeWheels.set(Constants.Intake.IntakeSpeeds.OutSpeed / 2);
      }
    );
  }

  // Runs the tilt motor out.
  public Command TiltOut() {
    return runOnce(
      () -> {
        m_Tilt.set(Constants.Intake.TiltSpeeds.OutSpeed);
      }
    );
  }

  
  // Runs the tilt motor in.
  public Command TiltIn() {
    return runOnce(
      () -> {
        m_Tilt.set(Constants.Intake.TiltSpeeds.InSpeed);
      }
    );
  }

  // Stops Tilt Motor
  public Command TiltStop() {
    return runOnce(
      () -> {
        m_Tilt.set(0);
      }
    );
  }

  public boolean IsAtPosition() {
    return isAtPosition;
  }

  public boolean InWithinRange() {
    return wantedTiltPosition <= tiltEncoder.get() + (wantedTiltPosition + .01) 
      || wantedTiltPosition >= tiltEncoder.get() + (wantedTiltPosition - .01);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Actual Intake Tilt Position", tiltEncoder.getAbsolutePosition());
    SmartDashboard.putNumber("Actual Intake Tilt Distance", tiltEncoder.get());
    SmartDashboard.putNumber("Wanted Intake Tilt Position", wantedTiltPosition);
    SmartDashboard.putBoolean("Intake Encoder Connected", tiltEncoder.isConnected());

    // Calculate the PID Value
    double pidOutput = tiltPID.calculate(tiltEncoder.getAbsolutePosition(), wantedTiltPosition);
      SmartDashboard.putNumber("Tilt PID Output Changed", MathUtil.clamp(pidOutput, -0.5, 0.5));
    SmartDashboard.putNumber("Tilt PID Output", pidOutput);

    // Check to see of we are moving the tilt in, if so, ignore the lower deadzone.
    if (
      MathUtil.clamp(pidOutput, -0.5, 0.5) < -0.3 &&
      !(tiltEncoder.getAbsolutePosition() < Constants.Intake.TiltPIDCutoffPositions.CutoffIn)
    ) {
      SmartDashboard.putString("Tilt Movement Position", "Move In");
      isAtPosition = false;
      m_Tilt.set(MathUtil.clamp(pidOutput, -0.5, 0.5));
    }

    if (
      MathUtil.clamp(pidOutput, -0.5, 0.5) > 0.3 &&
      !(tiltEncoder.getAbsolutePosition() > Constants.Intake.TiltPIDCutoffPositions.CutoffOut)
    ) {
      SmartDashboard.putString("Tilt Movement Position", "Move Out");
      isAtPosition = false;
      m_Tilt.set(MathUtil.clamp(pidOutput, -0.5, 0.5));
    }

    if ( 
      !(
        MathUtil.clamp(pidOutput, -0.5, 0.5) < -0.3 &&
        !(tiltEncoder.getAbsolutePosition() < Constants.Intake.TiltPIDCutoffPositions.CutoffIn)
        ) &&
      !(
        MathUtil.clamp(pidOutput, -0.5, 0.5) > 0.3 &&
        !(tiltEncoder.getAbsolutePosition() > Constants.Intake.TiltPIDCutoffPositions.CutoffOut)
      )
    ) {
      SmartDashboard.putString("Tilt Movement Position", "Don't Move");
      isAtPosition = true;
      m_Tilt.set(0);
    }
  }
}
