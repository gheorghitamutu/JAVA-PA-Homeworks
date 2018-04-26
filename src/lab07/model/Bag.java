package lab07.model;

import java.util.*;
import java.util.stream.IntStream;

public class Bag {
    // https://en.wikipedia.org/wiki/Scrabble_letter_distributions#English

    private final Queue<Character> letters = new LinkedList<>();
    private final Map<Character, Integer> lettersValue = new HashMap<>();

    public Bag() {
        // need a list in order to be able to shuffle it
        List<Character> lettersList = new ArrayList<>();

        // 0 points
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add(' '));

        lettersValue.put(' ', 0);

        // 1 point
        IntStream.range(0, 12).forEachOrdered(n -> lettersList.add('e'));
        IntStream.range(0, 9).forEachOrdered(n -> lettersList.add('a'));
        IntStream.range(0, 9).forEachOrdered(n -> lettersList.add('i'));
        IntStream.range(0, 8).forEachOrdered(n -> lettersList.add('o'));
        IntStream.range(0, 6).forEachOrdered(n -> lettersList.add('n'));
        IntStream.range(0, 6).forEachOrdered(n -> lettersList.add('r'));
        IntStream.range(0, 6).forEachOrdered(n -> lettersList.add('t'));
        IntStream.range(0, 4).forEachOrdered(n -> lettersList.add('l'));
        IntStream.range(0, 4).forEachOrdered(n -> lettersList.add('s'));
        IntStream.range(0, 4).forEachOrdered(n -> lettersList.add('u'));

        lettersValue.put('a', 1);
        lettersValue.put('e', 1);
        lettersValue.put('i', 1);
        lettersValue.put('o', 1);
        lettersValue.put('n', 1);
        lettersValue.put('r', 1);
        lettersValue.put('t', 1);
        lettersValue.put('l', 1);
        lettersValue.put('s', 1);
        lettersValue.put('u', 1);

        // 2 points
        IntStream.range(0, 4).forEachOrdered(n -> lettersList.add('d'));
        IntStream.range(0, 3).forEachOrdered(n -> lettersList.add('g'));

        lettersValue.put('d', 2);
        lettersValue.put('g', 2);

        // 3 points
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('b'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('c'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('m'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('p'));

        lettersValue.put('b', 3);
        lettersValue.put('c', 3);
        lettersValue.put('m', 3);
        lettersValue.put('p', 3);

        // 4 points
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('f'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('h'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('b'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('w'));
        IntStream.range(0, 2).forEachOrdered(n -> lettersList.add('i'));

        lettersValue.put('f', 4);
        lettersValue.put('h', 4);
        lettersValue.put('b', 4);
        lettersValue.put('w', 4);
        lettersValue.put('i', 4);

        // 5 points
        IntStream.range(0, 1).forEachOrdered(n -> lettersList.add('k'));

        lettersValue.put('k', 5);

        // 8 points
        IntStream.range(0, 1).forEachOrdered(n -> lettersList.add('j'));
        IntStream.range(0, 1).forEachOrdered(n -> lettersList.add('x'));

        lettersValue.put('j', 8);
        lettersValue.put('x', 8);

        // 10 points
        IntStream.range(0, 1).forEachOrdered(n -> lettersList.add('q'));
        IntStream.range(0, 1).forEachOrdered(n -> lettersList.add('z'));

        lettersValue.put('q', 10);
        lettersValue.put('z', 10);

        // suffle the letters in the list
        Collections.shuffle(lettersList);

        // add suffled list in queue
        letters.addAll(lettersList);

    }

    synchronized List<Character> extractLetters(int howMany) {
        // Replace the dots so that the bag is thread-safe
        List<Character> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (letters.isEmpty())
                break;

            extracted.add(letters.poll());
        }

        System.out.println("It were extracted: " + extracted);
        return extracted;
    }

    int getLetterValue(Character c)
    {
        return lettersValue.get(c);
    }
}
