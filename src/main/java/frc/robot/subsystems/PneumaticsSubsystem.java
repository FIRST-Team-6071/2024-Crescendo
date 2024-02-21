// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class PneumaticsSubsystem extends SubsystemBase {
    // Solenoids initialization
    private Solenoid leftSolenoid = new Solenoid(
            25,
            PneumaticsModuleType.REVPH, // Recycled REVPH type from old code idk what it is lol
            Constants.PneumaticsSubsystem.LeftSolenoidID);
    private Solenoid RightSolenoid = new Solenoid(
            25,
            PneumaticsModuleType.REVPH,
            Constants.PneumaticsSubsystem.RightSolenoidID);

    Compressor phCompressor = new Compressor(
            25,
            PneumaticsModuleType.REVPH);

    // Rev Pneumatic controller initialization
    // private PneumaticsControlModule PnController = new PneumaticsControlModule(
    //         Constants.PneumaticsSubsystem.PnControllerID);

    /** Creates a new PneumaticsSubsystem. */
    public PneumaticsSubsystem() {
        phCompressor.enableAnalog(110, 120);
    }

    // methods to open and close the claws
    public void Openclaws() {
        leftSolenoid.set(true);
        RightSolenoid.set(true);
    }

    public void CloseClaws() {
        leftSolenoid.set(false);
        RightSolenoid.set(false);
    }

    public void ToggleClaws() {
        leftSolenoid.toggle();
        RightSolenoid.toggle();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("PSI Value", phCompressor.getPressure());
    }
}