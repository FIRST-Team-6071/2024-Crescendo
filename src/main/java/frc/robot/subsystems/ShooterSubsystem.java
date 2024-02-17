public class ShooterSubsystem {

    //Initialize motors
    private final VictorSPX ShooterRightMotor = new VictorSPX(Constants.ShooterConstants.RightShooterMotor);
    private final VictorSPX ShooterLeftMotor = new VictorSPX(Constants.ShooterConstants.LeftShooterMotor);
    //Motors variables
    private boolean MotorsOn = false; //controler controlled later

    //run both motors at the same time at same speeed
    //motor is very fast (0.0 - 0.1)
    public void RunMotors() {
        if(MotorsOn) {
            ShooterRightMotor.set(15, 0.1); //I have no idea what 15 does here
            //im not sure this is the right motor to invert
            ShooterLeftMotor.setInverted(15, 0.1); 
        }
    }

}