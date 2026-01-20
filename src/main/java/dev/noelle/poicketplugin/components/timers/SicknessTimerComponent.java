package dev.noelle.poicketplugin.components.timers;

public class SicknessTimerComponent extends LivingInWorldTimerComponent {
    public SicknessTimerComponent(int tickstocompletion) {
        super(tickstocompletion);
    }
    public SicknessTimerComponent(SicknessTimerComponent timer) {
        super(timer.getTicksUntilComplete());
    }
    public SicknessTimerComponent() {
        int seconds = 10;
        int minutes = 0;
        int timeuntillsickness = ((30*60)*minutes) + 30*seconds;
        this(timeuntillsickness);
    }
}
