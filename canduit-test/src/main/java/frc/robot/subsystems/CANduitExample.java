package frc.robot.subsystems;

import frc.robot.libs.canduit.CANduit;
import frc.robot.libs.canduit.DigitalOutput;
import frc.robot.libs.canduit.PWMInput;
import frc.robot.libs.canduit.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANduitExample extends SubsystemBase {
    public CANduitExample() {}
    CANduit canduit = new CANduit(7);
    DigitalOutput digitalOutput = new DigitalOutput(canduit, 5);
    DigitalInput digitalInput = new DigitalInput(canduit, 4);
    PWMInput pwmInput = new PWMInput(canduit, 6);
    public Command showLED() {
        return runOnce(
                () -> {
                    // System.out.println("Digital Input Value: " + digitalInput.get());
                    digitalOutput.set(digitalInput.get());
                    // digitalOutput.toggle();
                });
    }

    public Command getPWM() {
        return runOnce(
                () -> {
                    System.out.println("PWM Input Value: " + pwmInput.getHighTime());
                });
    }

    public Command initiateDigitalOutput() {
        return runOnce(
                () -> {
                    // digitalOutput.close();
                    // digitalOutput = new DigitalOutput(canduit, 5);
                    // digitalInput.close();
                    // digitalInput = new DigitalInput(canduit, 4);
                    pwmInput.close();
                    pwmInput = new PWMInput(canduit, 6);
                });
    }
}
