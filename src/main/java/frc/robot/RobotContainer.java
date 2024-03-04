// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.BlinkenSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PickupSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.List;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems
    private final BlinkenSubsystem m_BlinkenSubsystem = new BlinkenSubsystem();
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

        m_driverController.a() 
                .onTrue(m_ShooterSubsystem.RunMotors())
                .onFalse(m_ShooterSubsystem.StopMotors());

        // m_auxController.button(5) 
        //         .onTrue(m_ShooterSubsystem.RunMotors())
        //         .onFalse(m_ShooterSubsystem.StopMotors());

        // m_auxController.button(4) 
        //         .onTrue(m_PickupSubsystem.TiltOut())
        //         .onFalse(m_PickupSubsystem.TiltStop());

        // m_auxController.button(3) 
        //         .onTrue(m_PickupSubsystem.TiltIn())
        //         .onFalse(m_PickupSubsystem.TiltStop());

        m_auxController.button(5) 
                .onTrue(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_IN));

        m_auxController.button(4) 
                .onTrue(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.TILT_UP));

        m_auxController.button(3) 
                .onTrue(m_PickupSubsystem.SetTilt(Constants.Intake.TiltPositions.FULLY_OUT));


        m_auxController.button(2) 
                .onTrue(m_PickupSubsystem.PullInToIntake())
                .onFalse(m_PickupSubsystem.StopIntake());

        m_auxController.button(1) 
                .onTrue(m_PickupSubsystem.PushOutOfIntake())
                .onFalse(m_PickupSubsystem.StopIntake());

        m_driverController.start()
                .onTrue(new RunCommand(() -> m_PneumaticsSubsystem.Openclaws(), m_PneumaticsSubsystem));
        m_driverController.back()
                .onTrue(new RunCommand(() -> m_PneumaticsSubsystem.CloseClaws(), m_PneumaticsSubsystem));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new RunCommand(
                        () -> m_robotDrive.setX(),
                        m_robotDrive);

        // // Create config for trajectory
        // TrajectoryConfig config = new TrajectoryConfig(
        //         AutoConstants.kMaxSpeedMetersPerSecond,
        //         AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        //         // Add kinematics to ensure max speed is actually obeyed
        //         .setKinematics(DriveConstants.kDriveKinematics);

        // // An example trajectory to follow. All units in meters.
        // Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        //         // Start at the origin facing the +X direction
        //         new Pose2d(0, 0, new Rotation2d(0)),
        //         // Pass through these two interior waypoints, making an 's' curve path
        //         List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
        //         // End 3 meters straight ahead of where we started, facing forward
        //         new Pose2d(3, 0, new Rotation2d(0)),
        //         config);

        // var thetaController = new ProfiledPIDController(
        //         AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
        // thetaController.enableContinuousInput(-Math.PI, Math.PI);

        // SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        //         exampleTrajectory,
        //         m_robotDrive::getPose, // Functional interface to feed supplier
        //         DriveConstants.kDriveKinematics,

        //         // Position controllers
        //         new PIDController(AutoConstants.kPXController, 0, 0),
        //         new PIDController(AutoConstants.kPYController, 0, 0),

        //         thetaController,
        //         m_robotDrive::setModuleStates,
        //         m_robotDrive);

        // // Reset odometry to the starting pose of the trajectory.
        // m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

        // // Run path following command, then stop at the end.
        // return swerveControllerCommand.andThen(() -> m_robotDrive.drive(0, 0, 0, false, false));
    }
}
