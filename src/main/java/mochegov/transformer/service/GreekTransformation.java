package mochegov.transformer.service;

import java.util.HashMap;
import java.util.Map;
import mochegov.transformer.params.GreekTransformationParams;
import org.springframework.stereotype.Service;

@Service
public class GreekTransformation implements Transformation<GreekTransformationParams> {
    private final static Map<Character, String> GREEK_TO_LATIN_MAP = new HashMap<>();

    static {
        GREEK_TO_LATIN_MAP.put('Α', "A");
        GREEK_TO_LATIN_MAP.put('Β', "B");
        GREEK_TO_LATIN_MAP.put('Γ', "G");
        GREEK_TO_LATIN_MAP.put('Δ', "D");
        GREEK_TO_LATIN_MAP.put('Ε', "E");
        GREEK_TO_LATIN_MAP.put('Ζ', "Z");
        GREEK_TO_LATIN_MAP.put('Η', "H");
        GREEK_TO_LATIN_MAP.put('Θ', "Th");
        GREEK_TO_LATIN_MAP.put('Ι', "I");
        GREEK_TO_LATIN_MAP.put('Κ', "K");
        GREEK_TO_LATIN_MAP.put('Λ', "L");
        GREEK_TO_LATIN_MAP.put('Μ', "M");
        GREEK_TO_LATIN_MAP.put('Ν', "N");
        GREEK_TO_LATIN_MAP.put('Ξ', "X");
        GREEK_TO_LATIN_MAP.put('Ο', "O");
        GREEK_TO_LATIN_MAP.put('Π', "P");
        GREEK_TO_LATIN_MAP.put('Ρ', "R");
        GREEK_TO_LATIN_MAP.put('Σ', "S");
        GREEK_TO_LATIN_MAP.put('Τ', "T");
        GREEK_TO_LATIN_MAP.put('Υ', "Y");
        GREEK_TO_LATIN_MAP.put('Φ', "Ph");
        GREEK_TO_LATIN_MAP.put('Χ', "Ch");
        GREEK_TO_LATIN_MAP.put('Ψ', "Ps");
        GREEK_TO_LATIN_MAP.put('Ω', "O");

        GREEK_TO_LATIN_MAP.put('α', "a");
        GREEK_TO_LATIN_MAP.put('β', "b");
        GREEK_TO_LATIN_MAP.put('γ', "g");
        GREEK_TO_LATIN_MAP.put('δ', "d");
        GREEK_TO_LATIN_MAP.put('ε', "e");
        GREEK_TO_LATIN_MAP.put('ζ', "z");
        GREEK_TO_LATIN_MAP.put('η', "h");
        GREEK_TO_LATIN_MAP.put('θ', "th");
        GREEK_TO_LATIN_MAP.put('ι', "i");
        GREEK_TO_LATIN_MAP.put('κ', "k");
        GREEK_TO_LATIN_MAP.put('λ', "l");
        GREEK_TO_LATIN_MAP.put('μ', "m");
        GREEK_TO_LATIN_MAP.put('ν', "n");
        GREEK_TO_LATIN_MAP.put('ξ', "x");
        GREEK_TO_LATIN_MAP.put('ο', "o");
        GREEK_TO_LATIN_MAP.put('π', "p");
        GREEK_TO_LATIN_MAP.put('ρ', "r");
        GREEK_TO_LATIN_MAP.put('σ', "s");
        GREEK_TO_LATIN_MAP.put('ς', "s");
        GREEK_TO_LATIN_MAP.put('τ', "t");
        GREEK_TO_LATIN_MAP.put('υ', "y");
        GREEK_TO_LATIN_MAP.put('φ', "ph");
        GREEK_TO_LATIN_MAP.put('χ', "ch");
        GREEK_TO_LATIN_MAP.put('ψ', "ps");
        GREEK_TO_LATIN_MAP.put('ω', "o");
    }

    @Override
    public String perform(String value, GreekTransformationParams params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (GREEK_TO_LATIN_MAP.containsKey(c)) {
                stringBuilder.append(GREEK_TO_LATIN_MAP.get(c));
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }
}
