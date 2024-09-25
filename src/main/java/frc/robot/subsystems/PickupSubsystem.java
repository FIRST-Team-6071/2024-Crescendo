// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class PickupSubsystem extends SubsystemBase {

  private Spark StageOneMotor = new Spark(Constants.Intake.StageOneMotorId);
  private Spark StageTwoMotor = new Spark(Constants.Intake.StageTwoMotorId);
  private final VictorSPX StageThreeLeftMotor = new VictorSPX (Constants.Intake.StageThreeLeftMotorId);
  private final VictorSPX StageThreeRightMotor = new VictorSPX(Constants.Intake.StageThreeRightMotorId);
  
  /** Creates a new PickupSubsystem. */
  public PickupSubsystem() {
   
  }

  public void StartNotePullIn() {
    StageOneMotor.set(Constants.Intake.StageOneSpeed);    
    StageTwoMotor.set(Constants.Intake.StageTwoSpeed);

}
 public void StopNotePullIn() {
  StageOneMotor.set (Constants.Intake.StopStageOneSpeed);
  StageTwoMotor.set (Constants.Intake.StopStageTwoSpeed);
  
 }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
