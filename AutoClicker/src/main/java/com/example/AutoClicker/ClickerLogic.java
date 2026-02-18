package com.example.AutoClicker;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClickerLogic implements NativeKeyListener {

    private final Robot robot;
    private final Random random = new Random();

    // Настройки
    private boolean isActive = false;
    private final int baseDelay = 90; // Базовая скорость (мс)
    private final int noiseRange = 100; // Шум +/- 50мс

    public ClickerLogic() throws AWTException {
        this.robot = new Robot();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }

    public void start() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);

            System.out.println("--- Автокликер запущен ---");
            System.out.println("Нажми 'R' для включения/выключения");

            while (true) {
                if (isActive) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                    int currentNoise = random.nextInt(noiseRange);
                    Thread.sleep(baseDelay + currentNoise);
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (NativeHookException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase("R")) {
            isActive = !isActive;
            System.out.println("Статус: " + (isActive ? "РАБОТАЕТ" : "ПАУЗА"));
        }
    }
}