/*
Copyright 2020 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package structs;

import java.util.HashMap;

/**
 * A wrapper class that provides data structures containing language info according to ISO-639.
 */
public class LanguageCodes {
    private static final String[][] LANGUAGE_NAMES_AND_CODES = {
        {"Abkhazian", "ab"},
        {"Afar", "aa"},
        {"Afrikaans", "af"},
        {"Akan", "ak"},
        {"Albanian", "sq"},
        {"Amharic", "am"},
        {"Arabic", "ar"},
        {"Aragonese", "an"},
        {"Armenian", "hy"},
        {"Assamese", "as"},
        {"Avaric", "av"},
        {"Avestan", "ae"},
        {"Aymara", "ay"},
        {"Azerbaijani", "az"},
        {"Bambara", "bm"},
        {"Bashkir", "ba"},
        {"Basque", "eu"},
        {"Belarusian", "be"},
        {"Bengali", "bn"},
        {"Bihari", "bh"},
        {"Bislama", "bi"},
        {"Bosnian", "bs"},
        {"Breton", "br"},
        {"Bulgarian", "bg"},
        {"Burmese", "my"},
        {"Catalan", "ca"},
        {"Chamorro", "ch"},
        {"Chechen", "ce"},
        {"Chichewa", "ny"},
        {"Chinese", "zh"},
        {"Chuvash", "cv"},
        {"Cornish", "kw"},
        {"Corsican", "co"},
        {"Cree", "cr"},
        {"Croatian", "hr"},
        {"Czech", "cs"},
        {"Danish", "da"},
        {"Divehi", "dv"},
        {"Dutch", "nl"},
        {"Dzongkha", "dz"},
        {"English", "en"},
        {"Esperanto", "eo"},
        {"Estonian", "et"},
        {"Ewe", "ee"},
        {"Faroese", "fo"},
        {"Fijian", "fj"},
        {"Finnish", "fi"},
        {"French", "fr"},
        {"Fulah", "ff"},
        {"Galician", "gl"},
        {"Georgian", "ka"},
        {"German", "de"},
        {"Greek", "el"},
        {"Guarani", "gn"},
        {"Gujarati", "gu"},
        {"Haitian Creole", "ht"},
        {"Hausa", "ha"},
        {"Hebrew", "he"},
        {"Herero", "hz"},
        {"Hindi", "hi"},
        {"Hiri Motu", "ho"},
        {"Hungarian", "hu"},
        {"Interlingua", "ia"},
        {"Indonesian", "id"},
        {"Interlingue, Occidental", "ie"},
        {"Irish", "ga"},
        {"Igbo", "ig"},
        {"Inupiaq", "ik"},
        {"Ido", "io"},
        {"Icelandic", "is"},
        {"Italian", "it"},
        {"Inuktitut", "iu"},
        {"Japanese", "ja"},
        {"Javanese", "jv"},
        {"Kalaallisut, Greenlandic", "kl"},
        {"Kannada", "kn"},
        {"Kanuri", "kr"},
        {"Kashmiri", "ks"},
        {"Kazakh", "kk"},
        {"Central Khmer", "km"},
        {"Kikuyu", "ki"},
        {"Kinyarwanda", "rw"},
        {"Kirghiz", "ky"},
        {"Komi", "kv"},
        {"Kongo", "kg"},
        {"Korean", "ko"},
        {"Kurdish", "ku"},
        {"Kuanyama, Kwanyama", "kj"},
        {"Latin", "la"},
        {"Luxembourgish", "lb"},
        {"Ganda", "lg"},
        {"Limburgan, Limburgish", "li"},
        {"Lingala", "ln"},
        {"Lao", "lo"},
        {"Lithuanian", "lt"},
        {"Luba-Katanga", "lu"},
        {"Latvian", "lv"},
        {"Manx", "gv"},
        {"Macedonian", "mk"},
        {"Malagasy", "mg"},
        {"Malay", "ms"},
        {"Malayalam", "ml"},
        {"Maltese", "mt"},
        {"Maori", "mi"},
        {"Marathi", "mr"},
        {"Marshallese", "mh"},
        {"Mongolian", "mn"},
        {"Nauru", "na"},
        {"Navajo, Navaho", "nv"},
        {"North Ndebele", "nd"},
        {"Nepali", "ne"},
        {"Ndonga", "ng"},
        {"Norwegian Bokmål", "nb"},
        {"Norwegian Nynorsk", "nn"},
        {"Norwegian", "no"},
        {"Sichuan Yi, Nuosu", "ii"},
        {"South Ndebele", "nr"},
        {"Occitan", "oc"},
        {"Ojibwa", "oj"},
        {"Church Slavic, Old Slavonic", "cu"},
        {"Oromo", "om"},
        {"Oriya", "or"},
        {"Ossetian, Ossetic", "os"},
        {"Punjabi, Panjabi", "pa"},
        {"Pali", "pi"},
        {"Persian", "fa"},
        {"Polish", "pl"},
        {"Pashto, Pushto", "ps"},
        {"Portuguese", "pt"},
        {"Quechua", "qu"},
        {"Romansh", "rm"},
        {"Rundi", "rn"},
        {"Romanian, Moldovan", "ro"},
        {"Russian", "ru"},
        {"Sanskrit", "sa"},
        {"Sardinian", "sc"},
        {"Sindhi", "sd"},
        {"Northern Sami", "se"},
        {"Samoan", "sm"},
        {"Sango", "sg"},
        {"Serbian", "sr"},
        {"Scottish Gaelic", "gd"},
        {"Shona", "sn"},
        {"Sinhala, Sinhalese", "si"},
        {"Slovak", "sk"},
        {"Slovenian", "sl"},
        {"Somali", "so"},
        {"Southern Sotho", "st"},
        {"Spanish, Castilian", "es"},
        {"Sundanese", "su"},
        {"Swahili", "sw"},
        {"Swati", "ss"},
        {"Swedish", "sv"},
        {"Tamil", "ta"},
        {"Telugu", "te"},
        {"Tajik", "tg"},
        {"Thai", "th"},
        {"Tigrinya", "ti"},
        {"Tibetan", "bo"},
        {"Turkmen", "tk"},
        {"Tagalog", "tl"},
        {"Tswana", "tn"},
        {"Tonga (Tonga Islands)", "to"},
        {"Turkish", "tr"},
        {"Tsonga", "ts"},
        {"Tatar", "tt"},
        {"Twi", "tw"},
        {"Tahitian", "ty"},
        {"Uighur, Uyghur", "ug"},
        {"Ukrainian", "uk"},
        {"Urdu", "ur"},
        {"Uzbek", "uz"},
        {"Venda", "ve"},
        {"Vietnamese", "vi"},
        {"Volapük", "vo"},
        {"Walloon", "wa"},
        {"Welsh", "cy"},
        {"Wolof", "wo"},
        {"Western Frisian", "fy"},
        {"Xhosa", "xh"},
        {"Yiddish", "yi"},
        {"Yoruba", "yo"},
        {"Zhuang, Chuang", "za"},
        {"Zulu", "zu"}
    };

    /**
     * An array including every language's name.
     */
    public static final String[] LANGUAGES_NAMES = getLanguagesNames();

    /**
     * A HashMap mapping language names to the associated 2-letter codes.
     */
    public static final HashMap<String, String> LANGUAGE_NAMES_TO_CODES_MAP = getLanguageNamesToCodesMap();

    // Private constructor to avoid instance creation.
    private LanguageCodes() {}

    /**
     * Constructs a list including every language's name.
     *
     * @return the list of language names
     */
    private static String[] getLanguagesNames() {
        int numberOfLanguages = LANGUAGE_NAMES_AND_CODES.length;
        String[] languagesNames = new String[numberOfLanguages];
        for (int i = 0; i < numberOfLanguages; i++) {
            languagesNames[i] = LANGUAGE_NAMES_AND_CODES[i][0];
        }
        return languagesNames;
    }

    /**
     * Constructs a HashMap with language names as keys and 2-letter language codes as values.
     *
     * @return the HashMap mapping language names to language codes
     */
    private static HashMap<String, String> getLanguageNamesToCodesMap() {
        HashMap<String, String> languageNamesToCodesMap = new HashMap<>();
        for (String[] languageNameAndCode : LANGUAGE_NAMES_AND_CODES) {
            String languageName = languageNameAndCode[0];
            String languageCode = languageNameAndCode[1];
            languageNamesToCodesMap.put(languageName, languageCode);
        }
        return languageNamesToCodesMap;
    }

    /**
     * Get the language code associated with a given language.
     *
     * @param language the String language name
     * @return the 2-letter language code according to ISO 639-1
     */
    public static String getLanguageCode(String language) {
        return LANGUAGE_NAMES_TO_CODES_MAP.get(language);
    }
}
