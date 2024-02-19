package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

//two solenoids, SingleSolenoids.
//on when extended. will propbably be set automatically to off*
public class PneumaticsSubsystem {
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
        Constants.PneumaticsSubsystem.PnControllerID);

    //methods to open and close the claws
    public static void Openclaws() {
        leftSolenoid.set(true);
        RightSolenoid.set(true);
    }

    public static void CloseClaws() {
        leftSolenoid.set(false);
        RightSolenoid.set(false);
    }
}