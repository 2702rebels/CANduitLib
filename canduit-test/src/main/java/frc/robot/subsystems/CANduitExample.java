package frc.robot.subsystems;

import frc.robot.libs.canduit.CANduit;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANduitExample extends SubsystemBase {
    private CANduit canduit;
    public CANduitExample(CANduit canduit) {
        this.canduit = canduit;
        // pwmInput = canduit.createPWMInput(6);
        // tofSensor = canduit.createPWMInput(2);
        // digInput = canduit.createDigitalInput(5);
        // digIn2 = canduit.createDigitalInput(7);
        for (int i = 0; i <= 7; i++) {
            digOuts[i] = canduit.createDigitalOutput(i);
        }
    }
    // DigitalOutput digitalOutput = new DigitalOutput(canduit, 5);
    // DigitalInput digitalInput = new DigitalInput(canduit, 4);
    CANduit.PWMInput pwmInput;

    private CANduit.PWMInput tofSensor;

    protected CANduit.DigitalInput digInput;

    CANduit.DigitalInput digIn2;

    CANduit.DigitalOutput[] digOuts = new CANduit.DigitalOutput[8];

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Encoder angle", 360.0 * ((double)pwmInput.getPulseWidth() / Math.min((double)pwmInput.getPeriod(), 1025000.0)));
        // SmartDashboard.putNumber("encoderValue", pwmInput.getPulseWidth());
        // SmartDashboard.putNumber("encoderPeriod", pwmInput.getPeriod());
        //             // Timer.delay(0.01);
        // SmartDashboard.putNumber("TOF Distance", (tofSensor.getPulseWidth() - 1000000) * 3.0 / 4000.0 - 10);
        // SmartDashboard.putNumber("TOFValue", tofSensor.getPulseWidth());
        // SmartDashboard.putNumber("TOFPeriod", tofSensor.getPeriod());

        // SmartDashboard.putBoolean("switchValue", digInput.get());
        for (CANduit.DigitalOutput digitalOutput : digOuts) {
            digitalOutput.set((int)(Timer.getFPGATimestamp() * 10 % 8) == digitalOutput.getPin());
        }
    }
}
