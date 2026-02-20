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
        pwmInput = canduit.createPWMInput(6);
        tofSensor = canduit.createPWMInput(2);
    }
    // DigitalOutput digitalOutput = new DigitalOutput(canduit, 5);
    // DigitalInput digitalInput = new DigitalInput(canduit, 4);
    CANduit.PWMInput pwmInput;

    private CANduit.PWMInput tofSensor;

    @Override
    public void periodic() {
        // System.out.printf("Encoder value: %f\n", 360.0 * (pwmInput.getPulseWidth() / 1000.0 - 1) / 1023.0);
        SmartDashboard.putNumber("encoderValue", pwmInput.getPulseWidth());
        SmartDashboard.putNumber("encoderPeriod", pwmInput.getPeriod());
                    // Timer.delay(0.01);
        // System.out.printf("TOF Sensor Distance: %fmm\n", (tofSensor.getPulseWidth() - 1000000) * 3.0 / 4000.0 - 10);
        SmartDashboard.putNumber("TOFValue", tofSensor.getPulseWidth());
        SmartDashboard.putNumber("TOFPeriod", tofSensor.getPeriod());
    }
}
