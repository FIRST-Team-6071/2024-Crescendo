// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShuffleboardSubsystem extends SubsystemBase {
  SendableChooser<String> m_autoChooser = new SendableChooser<>();

  /** Creates a new ShuffleboardSubsystem. */
  public ShuffleboardSubsystem() {
    m_autoChooser.addOption("Amp Side Auton", "amp_side");
    m_autoChooser.addOption("1 Note Amp Side Auton", "1_note_amp_side");

    m_autoChooser.addOption("Source Side Auton", "source_side");
    m_autoChooser.addOption("1 Note Source Side Auton", "source_side_1_note");


    m_autoChooser.setDefaultOption("1 Note Center Auton", "center_1_note"); 
    m_autoChooser.setDefaultOption("Center Auton", "center_4_note"); 

    
     m_autoChooser.addOption("Test", "test");

    SmartDashboard.putData(m_autoChooser);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public String GetSelectedAuton() {
    return m_autoChooser.getSelected();
  }
}