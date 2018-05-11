package com.dnf.test;

import java.util.Random;

public class Language {
	static final String AD_LANGUAGE = "test:ad:la:";
	
	String[] str = { "all", "Afrikaans", "Afrikaans (South Africa)", "Albanian", "Albanian (Albania)", "Arabic",
			"Arabic (Algeria)", "Arabic (Bahrain)", "Arabic (Egypt)", "Arabic (Iraq)", "Arabic (Jordan)",
			"Arabic (Kuwait)", "Arabic (Lebanon)", "Arabic (Libya)", "Arabic (Morocco)", "Arabic (Oman)",
			"Arabic (Qatar)", "Arabic (Saudi Arabia)", "Arabic (Syria)", "Arabic (Tunisia)", "Arabic (U.A.E.)",
			"Arabic (Yemen)", "Armenian", "Armenian (Armenia)", "Azeri (Cyrillic) (Azerbaijan)", "Azeri (Latin)",
			"Azeri (Latin) (Azerbaijan)", "Basque", "Basque (Spain)", "Belarusian", "Belarusian (Belarus)",
			"Bosnian (Bosnia and Herzegovina)", "Bulgarian", "Bulgarian (Bulgaria)", "Catalan", "Catalan (Spain)",
			"Chinese", "Chinese (Hong Kong)", "Chinese (Macau)", "Chinese (S)", "Chinese (Singapore)", "Chinese (T)",
			"Croatian", "Croatian (Bosnia and Herzegovina)", "Croatian (Croatia)", "Czech", "Czech (Czech Republic)",
			"Danish", "Danish (Denmark)", "Divehi", "Divehi (Maldives)", "Dutch", "Dutch (Belgium)",
			"Dutch (Netherlands)", "English", "English (Australia)", "English (Belize)", "English (Canada)",
			"English (Caribbean)", "English (Ireland)", "English (Jamaica)", "English (New Zealand)",
			"English (Republic of the Philippines)", "English (South Africa)", "English (Trinidad and Tobago)",
			"English (United Kingdom)", "English (United States)", "English (Zimbabwe)", "Esperanto", "Estonian",
			"Estonian (Estonia)", "Faroese", "Faroese (Faroe Islands)", "Farsi", "Farsi (Iran)", "Finnish",
			"Finnish (Finland)", "French", "French (Belgium)", "French (Canada)", "French (France)",
			"French (Luxembourg)", "French (Principality of Monaco)", "French (Switzerland)", "FYRO Macedonian",
			"FYRO Macedonian (Former Yugoslav Republic of Macedonia)", "Galician", "Galician (Spain)", "Georgian",
			"Georgian (Georgia)", "German", "German (Austria)", "German (Germany)", "German (Liechtenstein)",
			"German (Luxembourg)", "German (Switzerland)", "Greek", "Greek (Greece)", "Gujarati", "Gujarati (India)",
			"Hebrew", "Hebrew (Israel)", "Hindi", "Hindi (India)", "Hungarian", "Hungarian (Hungary)", "Icelandic",
			"Icelandic (Iceland)", "Indonesian", "Indonesian (Indonesia)", "Italian", "Italian (Italy)",
			"Italian (Switzerland)", "Japanese", "Japanese (Japan)", "Kannada", "Kannada (India)", "Kazakh",
			"Kazakh (Kazakhstan)", "Konkani", "Konkani (India)", "Korean", "Korean (Korea)", "Kyrgyz",
			"Kyrgyz (Kyrgyzstan)", "Latvian", "Latvian (Latvia)", "Lithuanian", "Lithuanian (Lithuania)", "Malay",
			"Malay (Brunei Darussalam)", "Malay (Malaysia)", "Maltese", "Maltese (Malta)", "Maori",
			"Maori (New Zealand)", "Marathi", "Marathi (India)", "Mongolian", "Mongolian (Mongolia)", "Northern Sotho",
			"Northern Sotho (South Africa)", "Norwegian (Bokm?l)", "Norwegian (Bokm?l) (Norway)",
			"Norwegian (Nynorsk) (Norway)", "Pashto", "Pashto (Afghanistan)", "Polish", "Polish (Poland)", "Portuguese",
			"Portuguese (Brazil)", "Portuguese (Portugal)", "Punjabi", "Punjabi (India)", "Quechua",
			"Quechua (Bolivia)", "Quechua (Ecuador)", "Quechua (Peru)", "Romanian", "Romanian (Romania)", "Russian",
			"Russian (Russia)", "Sami (Inari) (Finland)", "Sami (Lule) (Norway)", "Sami (Lule) (Sweden)",
			"Sami (Northern)", "Sami (Northern) (Finland)", "Sami (Northern) (Norway)", "Sami (Northern) (Sweden)",
			"Sami (Skolt) (Finland)", "Sami (Southern) (Norway)", "Sami (Southern) (Sweden)", "Sanskrit",
			"Sanskrit (India)", "Serbian (Cyrillic) (Bosnia and Herzegovina)",
			"Serbian (Cyrillic) (Serbia and Montenegro)", "Serbian (Latin) (Bosnia and Herzegovina)",
			"Serbian (Latin) (Serbia and Montenegro)", "Slovak", "Slovak (Slovakia)", "Slovenian",
			"Slovenian (Slovenia)", "Spanish", "Spanish (Argentina)", "Spanish (Bolivia)", "Spanish (Castilian)",
			"Spanish (Chile)", "Spanish (Colombia)", "Spanish (Costa Rica)", "Spanish (Dominican Republic)",
			"Spanish (Ecuador)", "Spanish (El Salvador)", "Spanish (Guatemala)", "Spanish (Honduras)",
			"Spanish (Mexico)", "Spanish (Nicaragua)", "Spanish (Panama)", "Spanish (Paraguay)", "Spanish (Peru)",
			"Spanish (Puerto Rico)", "Spanish (Spain)", "Spanish (Uruguay)", "Spanish (Venezuela)", "Swahili",
			"Swahili (Kenya)", "Swedish", "Swedish (Finland)", "Swedish (Sweden)", "Syriac", "Syriac (Syria)",
			"Tagalog", "Tagalog (Philippines)", "Tamil", "Tamil (India)", "Tatar", "Tatar (Russia)", "Telugu",
			"Telugu (India)", "Thai", "Thai (Thailand)", "Tsonga", "Tswana", "Tswana (South Africa)", "Turkish",
			"Turkish (Turkey)", "Ukrainian", "Ukrainian (Ukraine)", "Urdu", "Urdu (Islamic Republic of Pakistan)",
			"Uzbek (Cyrillic) (Uzbekistan)", "Uzbek (Latin)", "Uzbek (Latin) (Uzbekistan)", "Vietnamese",
			"Vietnamese (Viet Nam)", "Welsh", "Welsh (United Kingdom)", "Xhosa", "Xhosa (South Africa)", "Zulu",
			"Zulu (South Africa)" };
	
	Random random = new Random();

	public String getValue() {
		int nextInt = random.nextInt(str.length);
		return AD_LANGUAGE+str[nextInt];
	}
	
	public String getV() {
		int nextInt = random.nextInt(str.length);
		return str[nextInt];
	}
}
