package typespeed;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class DictionaryService {
    private Random random = new Random();
    private final int minChars = 1;
    private final int maxChars = 7;

    public String getRandomString(){
        int randomChars = random.nextInt(maxChars-1) + minChars;
        String string = RandomStringUtils.randomAlphabetic(randomChars);

        return string;
    }

}
