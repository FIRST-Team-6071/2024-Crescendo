// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class PneumaticsSubsystem extends SubsystemBase {
  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {}

  //Solenoids initialization
  private Solenoid leftSolenoid = new Solenoid(
      Constants.PneumaticsSubsystem.leftSolenoidID, 
      PneumaticsModuleType.REVPH, //Recycled REVPH type from old code idk what it is lol
      Constants.PneumaticsSubsystem.SolenoidChannel
  );
  private Solenoid RightSolenoid = new Solenoid(
      Constants.PneumaticsSubsystem.RightSolenoidID, 
      PneumaticsModuleType.REVPH, 
      Constants.PneumaticsSubsystem.SolenoidChannel
  );
  //Rev Pneumatic controller initialization
  private PneumaticsControlModule PnController = new PneumaticsControlModule( 
      Constants.PneumaticsSubsystem.PnControllerID
  );

  //methods to open and close the claws
  public static void Openclaws() {
      leftSolenoid.set(true);
      RightSolenoid.set(true);
  }

  public static void CloseClaws() {
      leftSolenoid.set(false);
      RightSolenoid.set(false);
  }

  @Override
  public void periodic() {
  // This method will be called once per scheduler run
  }
}



