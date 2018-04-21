package lab07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ScrabbleDictionary implements Dictionary {

    private final String filename = "resources\\lab07\\words.txt";
    private final List<String> words = new ArrayList<>();
    private List<Character> charactersWeDontWant = new ArrayList<>();

    ScrabbleDictionary()
    {
        try {
            Files.lines(Paths.get(filename))
                    .filter(line ->
                            line.length() > 1 &&
                            line.length() < 9 &&
                            !containsNotWantedChars(line))
                    .forEach(words::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dictionary has " + words.size() + " words!");
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    private boolean containsNotWantedChars(String word)
    {
        if(charactersWeDontWant.size() == 0)
            IntStream.range(0, 256)
                    .filter(n -> n < 65 || n > 90)
                    .forEachOrdered(n -> charactersWeDontWant.add((char)n));

        StringBuilder sb = new StringBuilder();
        charactersWeDontWant.stream()
                .filter(c -> word.toUpperCase().contains(c.toString().toLowerCase()))
                .forEach(sb::append);

        return sb.length() == word.length();
    }
}
