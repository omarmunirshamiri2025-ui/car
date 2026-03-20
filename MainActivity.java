package com.example.quranapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ==============================
    // بيانات السور القرآنية
    // ==============================
    private static final String[][] SURAHS = {
        {"1",  "الفاتحة",       "Al-Fatihah",       "7",   "مكية"},
        {"2",  "البقرة",        "Al-Baqarah",        "286", "مدنية"},
        {"3",  "آل عمران",      "Al-Imran",           "200", "مدنية"},
        {"4",  "النساء",        "An-Nisa",            "176", "مدنية"},
        {"5",  "المائدة",       "Al-Ma'idah",         "120", "مدنية"},
        {"6",  "الأنعام",       "Al-An'am",           "165", "مكية"},
        {"7",  "الأعراف",       "Al-A'raf",           "206", "مكية"},
        {"8",  "الأنفال",       "Al-Anfal",           "75",  "مدنية"},
        {"9",  "التوبة",        "At-Tawbah",          "129", "مدنية"},
        {"10", "يونس",          "Yunus",              "109", "مكية"},
        {"11", "هود",           "Hud",                "123", "مكية"},
        {"12", "يوسف",          "Yusuf",              "111", "مكية"},
        {"13", "الرعد",         "Ar-Ra'd",            "43",  "مدنية"},
        {"14", "إبراهيم",       "Ibrahim",            "52",  "مكية"},
        {"15", "الحجر",         "Al-Hijr",            "99",  "مكية"},
        {"16", "النحل",         "An-Nahl",            "128", "مكية"},
        {"17", "الإسراء",       "Al-Isra",            "111", "مكية"},
        {"18", "الكهف",         "Al-Kahf",            "110", "مكية"},
        {"19", "مريم",          "Maryam",             "98",  "مكية"},
        {"20", "طه",            "Ta-Ha",              "135", "مكية"},
        {"36", "يس",            "Ya-Sin",             "83",  "مكية"},
        {"55", "الرحمن",        "Ar-Rahman",          "78",  "مدنية"},
        {"56", "الواقعة",       "Al-Waqi'ah",         "96",  "مكية"},
        {"67", "الملك",         "Al-Mulk",            "30",  "مكية"},
        {"78", "النبأ",         "An-Naba",            "40",  "مكية"},
        {"112","الإخلاص",       "Al-Ikhlas",          "4",   "مكية"},
        {"113","الفلق",         "Al-Falaq",           "5",   "مكية"},
        {"114","الناس",         "An-Nas",             "6",   "مكية"},
    };

    // ==============================
    // عناصر الواجهة
    // ==============================
    private LinearLayout surahListContainer;
    private EditText searchEditText;
    private TextView verseDisplayText;
    private TextView surahTitleDisplay;
    private CardView verseCard;
    private Button btnPrev, btnNext, btnRandom;
    private TextView ayahCounter;

    // ==============================
    // بيانات التطبيق
    // ==============================
    private List<String[]> filteredSurahs = new ArrayList<>();
    private int currentSurahIndex = 0;

    // آيات نموذجية للعرض
    private static final String[][] SAMPLE_AYAHS = {
        {"بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "١"},
        {"الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "٢"},
        {"الرَّحْمَٰنِ الرَّحِيمِ", "٣"},
        {"مَالِكِ يَوْمِ الدِّينِ", "٤"},
        {"إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "٥"},
        {"اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "٦"},
        {"صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "٧"},
    };

    private int currentAyahIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupSurahList();
        setupSearch();
        setupAyahControls();
        displayCurrentAyah();
    }

    // ==============================
    // تهيئة عناصر الواجهة
    // ==============================
    private void initViews() {
        surahListContainer = findViewById(R.id.surahListContainer);
        searchEditText     = findViewById(R.id.searchEditText);
        verseDisplayText   = findViewById(R.id.verseDisplayText);
        surahTitleDisplay  = findViewById(R.id.surahTitleDisplay);
        verseCard          = findViewById(R.id.verseCard);
        btnPrev            = findViewById(R.id.btnPrev);
        btnNext            = findViewById(R.id.btnNext);
        btnRandom          = findViewById(R.id.btnRandom);
        ayahCounter        = findViewById(R.id.ayahCounter);
    }

    // ==============================
    // بناء قائمة السور
    // ==============================
    private void setupSurahList() {
        filteredSurahs.clear();
        for (String[] s : SURAHS) filteredSurahs.add(s);
        renderSurahList();
    }

    private void renderSurahList() {
        surahListContainer.removeAllViews();
        for (int i = 0; i < filteredSurahs.size(); i++) {
            final String[] surah = filteredSurahs.get(i);
            final int idx = i;

            View item = getLayoutInflater().inflate(R.layout.item_surah, surahListContainer, false);

            TextView tvNumber  = item.findViewById(R.id.tvSurahNumber);
            TextView tvName    = item.findViewById(R.id.tvSurahName);
            TextView tvMeta    = item.findViewById(R.id.tvSurahMeta);
            TextView tvType    = item.findViewById(R.id.tvSurahType);

            tvNumber.setText(surah[0]);
            tvName.setText(surah[1]);
            tvMeta.setText(surah[4] + " • " + surah[3] + " آية");
            tvType.setText(surah[4]);

            // لون مختلف للمكية والمدنية
            int bgColor = surah[4].equals("مكية")
                    ? getColor(R.color.makkiColor)
                    : getColor(R.color.madaniColor);
            tvType.setBackgroundColor(bgColor);

            item.setOnClickListener(v -> {
                currentSurahIndex = idx;
                currentAyahIndex = 0;
                surahTitleDisplay.setText("سورة " + surah[1]);
                displayCurrentAyah();
                Toast.makeText(this,
                    "تم اختيار سورة " + surah[1] + " • " + surah[3] + " آية",
                    Toast.LENGTH_SHORT).show();
            });

            surahListContainer.addView(item);
        }
    }

    // ==============================
    // البحث في السور
    // ==============================
    private void setupSearch() {
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(android.text.Editable s) {}
            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                filterSurahs(query.toString().trim());
            }
        });
    }

    private void filterSurahs(String query) {
        filteredSurahs.clear();
        for (String[] s : SURAHS) {
            if (query.isEmpty()
                    || s[1].contains(query)
                    || s[2].toLowerCase().contains(query.toLowerCase())
                    || s[0].contains(query)) {
                filteredSurahs.add(s);
            }
        }
        renderSurahList();
    }

    // ==============================
    // التنقل بين الآيات
    // ==============================
    private void setupAyahControls() {
        btnNext.setOnClickListener(v -> {
            currentAyahIndex = (currentAyahIndex + 1) % SAMPLE_AYAHS.length;
            displayCurrentAyah();
        });

        btnPrev.setOnClickListener(v -> {
            currentAyahIndex = (currentAyahIndex - 1 + SAMPLE_AYAHS.length) % SAMPLE_AYAHS.length;
            displayCurrentAyah();
        });

        btnRandom.setOnClickListener(v -> {
            currentAyahIndex = (int)(Math.random() * SAMPLE_AYAHS.length);
            displayCurrentAyah();
            Toast.makeText(this, "آية عشوائية", Toast.LENGTH_SHORT).show();
        });
    }

    private void displayCurrentAyah() {
        String[] ayah = SAMPLE_AYAHS[currentAyahIndex];
        verseDisplayText.setText(ayah[0]);
        ayahCounter.setText("الآية " + ayah[1]);

        // تأثير ظهور خفيف
        verseCard.setAlpha(0f);
        verseCard.animate().alpha(1f).setDuration(400).start();
    }
}
