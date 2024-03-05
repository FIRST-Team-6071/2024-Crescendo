// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ShootNote;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ShuffleboardSubsystem;
import frc.robot.subsystems.BlinkenSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PickupSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems
    private final BlinkenSubsystem m_BlinkenSubsystem = new BlinkenSubsystem();
    private final ShuffleboardSubsystem m_ShuffleboardSubsystem = new ShuffleboardSubsystem();
    private final DriveSubsystem m_robotDrive = new DriveSubsystem();
    private final PickupSubsystem m_PickupSubsystem = new PickupSubsystem();
    private final PneumaticsSubsystem m_PneumaticsSubsystem = new PneumaticsSubsystem();
    private final ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();

    // The driver's controller
    CommandXboxController m_driverController = new CommandXboxController(OIConstants.kDriverControllerPort); 
    CommandJoystick m_auxController = new CommandJoystick(1);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        NamedCommands.registerCommand("intakeOut", m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_OUT));
        NamedCommands.registerCommand("intakeIn", m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_OUT));


        // Configure the button bindings
        configureButtonBindings();

        // Configure default commands
        m_robotDrive.setDefaultCommand(
                // The left stick controls translation of the robot.
                // Turning is controlled by the X axis of the right stick.
                new RunCommand(
                        () -> m_robotDrive.drive(
                                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                                false, true),
                        m_robotDrive));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
     * subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
     * passing it to a
     * {@link JoystickButton}.
     */
    private void configureButtonBindings() {
        m_driverController.rightBumper()
                .whileTrue(new RunCommand(
                        () -> m_robotDrive.setX(),
                        m_robotDrive));


        m_driverController.a().onTrue(new RunCommand(() -> m_ShooterSubsystem.RunMotors(), m_ShooterSubsystem)).onFalse(m_ShooterSubsystem.StopMotors());
        m_driverController.b().onTrue(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.TILT_UP));
        m_driverController.start()
                .onTrue(new RunCommand(() -> m_PneumaticsSubsystem.Openclaws(), m_PneumaticsSubsystem));
        m_driverController.back()
                .onTrue(new RunCommand(() -> m_PneumaticsSubsystem.CloseClaws(), m_PneumaticsSubsystem));


        // Controls for note intake
        m_auxController.button(1) 
                .onTrue(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_OUT));

        m_auxController.button(2) 
                .onTrue(m_PickupSubsystem.PullInToIntake())
                .onFalse(m_PickupSubsystem.StopIntake());

        m_auxController.button(3) 
                .onTrue(m_PickupSubsystem.PushOutOfIntakeLightly())
                .onFalse(m_PickupSubsystem.StopIntake());

        // Controls for shooting
        m_auxController.button(4) 
                .onTrue(
                        new ShootNote(m_ShooterSubsystem, m_PickupSubsystem, true)
                        .andThen(new WaitCommand(0.5)
                        .andThen(m_ShooterSubsystem.StopMotors())                    
                        .andThen(m_PickupSubsystem.StopIntake()))
                        // .andThen(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_IN)))
                );
        m_auxController.button(5) 
                .onTrue(
                        new ShootNote(m_ShooterSubsystem, m_PickupSubsystem, false)
                        .andThen(
                                new WaitCommand(1)
                                .andThen(
                                        m_PickupSubsystem.PushOutOfIntake()
                                        .andThen(
                                                new WaitCommand(0.5)
                                                .andThen(
                                                        m_ShooterSubsystem.StopMotors()
                                                        .andThen(
                                                                m_PickupSubsystem.StopIntake()
                                                        )
                                                )
                                        )
                                )
                        )
                );
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
       return new PathPlannerAuto(m_ShuffleboardSubsystem.GetSelectedAuton());
    }
}
