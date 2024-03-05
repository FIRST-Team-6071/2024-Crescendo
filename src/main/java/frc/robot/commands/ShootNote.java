// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.PickupSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootNote extends Command {
  private ShooterSubsystem m_shooter;
  private PickupSubsystem m_PickupSubsystem;

  private boolean hasFinished = false;
  private boolean isAmp = false;

  /** Creates a new ShootNote. */
  public ShootNote(ShooterSubsystem p_ShooterSubsystem, PickupSubsystem p_PickupSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_ShooterSubsystem);
    addRequirements(p_PickupSubsystem);

    m_shooter = p_ShooterSubsystem;
    m_PickupSubsystem = p_PickupSubsystem;
  }

  public ShootNote(ShooterSubsystem p_ShooterSubsystem, PickupSubsystem p_PickupSubsystem, boolean p_IsAmp) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(p_ShooterSubsystem);
    addRequirements(p_PickupSubsystem);

    m_shooter = p_ShooterSubsystem;
    m_PickupSubsystem = p_PickupSubsystem;
    isAmp = p_IsAmp;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.RunMotors();
    m_PickupSubsystem.SetTiltRaw(isAmp ? Constants.Intake.TiltPositions.TILT_UP : Constants.Intake.TiltPositions.FULLY_IN);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_PickupSubsystem.InWithinRange()) {
      System.out.println("At Pos");
      hasFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return hasFinished;
  }
}
