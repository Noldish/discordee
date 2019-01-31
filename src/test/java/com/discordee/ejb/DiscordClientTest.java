package com.discordee.ejb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class DiscordClientTest {

    @Test
    void replyAbountWeather() {

        Function<Integer, Integer> func = (i) -> i * 2;
        Function<Integer, Integer> func2 = (j) -> j * 4;
        Function<Integer, String> func3 = (i) -> "Результат: " + i;

        String result = func3.compose(func2).compose(func).apply(5);

        System.out.println(result);

    }
}