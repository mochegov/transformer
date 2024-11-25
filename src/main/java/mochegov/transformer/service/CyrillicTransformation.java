package mochegov.transformer.service;

import java.util.HashMap;
import java.util.Map;
import mochegov.transformer.params.CyrillicTransformationParams;
import org.springframework.stereotype.Service;

@Service
public class CyrillicTransformation implements Transformation<CyrillicTransformationParams> {
    private final static Map<Character, String> CYRILLIC_TO_LATIN_MAP = new HashMap<>();

    static {
        CYRILLIC_TO_LATIN_MAP.put('А', "A");
        CYRILLIC_TO_LATIN_MAP.put('Б', "B");
        CYRILLIC_TO_LATIN_MAP.put('В', "V");
        CYRILLIC_TO_LATIN_MAP.put('Г', "G");
        CYRILLIC_TO_LATIN_MAP.put('Д', "D");
        CYRILLIC_TO_LATIN_MAP.put('Е', "E");
        CYRILLIC_TO_LATIN_MAP.put('Ё', "Yo");
        CYRILLIC_TO_LATIN_MAP.put('Ж', "Zh");
        CYRILLIC_TO_LATIN_MAP.put('З', "Z");
        CYRILLIC_TO_LATIN_MAP.put('И', "I");
        CYRILLIC_TO_LATIN_MAP.put('Й', "Y");
        CYRILLIC_TO_LATIN_MAP.put('К', "K");
        CYRILLIC_TO_LATIN_MAP.put('Л', "L");
        CYRILLIC_TO_LATIN_MAP.put('М', "M");
        CYRILLIC_TO_LATIN_MAP.put('Н', "N");
        CYRILLIC_TO_LATIN_MAP.put('О', "O");
        CYRILLIC_TO_LATIN_MAP.put('П', "P");
        CYRILLIC_TO_LATIN_MAP.put('Р', "R");
        CYRILLIC_TO_LATIN_MAP.put('С', "S");
        CYRILLIC_TO_LATIN_MAP.put('Т', "T");
        CYRILLIC_TO_LATIN_MAP.put('У', "U");
        CYRILLIC_TO_LATIN_MAP.put('Ф', "F");
        CYRILLIC_TO_LATIN_MAP.put('Х', "Kh");
        CYRILLIC_TO_LATIN_MAP.put('Ц', "Ts");
        CYRILLIC_TO_LATIN_MAP.put('Ч', "Ch");
        CYRILLIC_TO_LATIN_MAP.put('Ш', "Sh");
        CYRILLIC_TO_LATIN_MAP.put('Щ', "Shch");
        CYRILLIC_TO_LATIN_MAP.put('Ы', "Y");
        CYRILLIC_TO_LATIN_MAP.put('Э', "E");
        CYRILLIC_TO_LATIN_MAP.put('Ю', "Yu");
        CYRILLIC_TO_LATIN_MAP.put('Я', "Ya");

        for (Map.Entry<Character, String> entry : new HashMap<>(CYRILLIC_TO_LATIN_MAP).entrySet()) {
            CYRILLIC_TO_LATIN_MAP.put(Character.toLowerCase(entry.getKey()), entry.getValue().toLowerCase());
        }
    }

    @Override
    public String perform(String value, CyrillicTransformationParams params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (CYRILLIC_TO_LATIN_MAP.containsKey(c)) {
                stringBuilder.append(CYRILLIC_TO_LATIN_MAP.get(c));
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }
}
