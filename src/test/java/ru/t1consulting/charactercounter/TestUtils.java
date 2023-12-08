package ru.t1consulting.charactercounter;

import org.springframework.beans.factory.annotation.Autowired;
import ru.t1consulting.charactercounter.config.GeneralProperties;

import java.util.Random;

public class TestUtils {

    @Autowired
    private GeneralProperties generalProperties;
    private final Random random = new Random();

    public String generateHugeString(int length) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length + 1; i++) {
            char c = (char) (random.nextInt('z' - 'a') + 'a');
            sb.append(c);
        }

        return sb.toString();
    }
}
