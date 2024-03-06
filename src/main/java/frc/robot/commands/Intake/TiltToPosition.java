// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PickupSubsystem;

public class TiltToPosition extends Command {
  PickupSubsystem m_PickupSubsystem;
  double wantedTiltPos;
  boolean hasFinished = false;

  /** Creates a new TiltToPosition. */
  public TiltToPosition(PickupSubsystem p_PickupSubsystem, double p_wantedTiltPosition) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_PickupSubsystem);
    m_PickupSubsystem = p_PickupSubsystem;
    wantedTiltPos = p_wantedTiltPosition;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_PickupSubsystem.SetTiltRaw(wantedTiltPos);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_PickupSubsystem.InWithinRange()) {
      hasFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return hasFinished;
  }
}
