package com.example.AutoClicker;

import com.example.AutoClicker.ClickerLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.*;

@SpringBootApplication
public class AutoClickerApplication {

    public static void main(String[] args) {
        // 1. Создаем билдер для настройки Spring
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AutoClickerApplication.class);

        // 2. Явно отключаем headless режим
        builder.headless(false).run(args);

        // 3. Запуск вашей логики
        new Thread(() -> {
            try {
                new ClickerLogic().start();
            } catch (AWTException e) {
                System.err.println("Ошибка: Не удалось инициализировать Robot. " + e.getMessage());
            }
        }).start();
    }
}