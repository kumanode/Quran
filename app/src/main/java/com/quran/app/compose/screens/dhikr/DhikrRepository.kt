package com.quran.app.compose.screens.dhikr

import com.quran.app.R

object DhikrRepository {

    fun getDhikrItems(): List<DhikrDuaItem> {
        return listOf(
            // Morning Dhikr
            DhikrDuaItem(
                id = "morning_ayat_kursi",
                category = DhikrCategory.MORNING,
                titleRes = R.string.dhikr_morning_ayat_kursi_title,
                arabicText = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَنْ ذَا الَّذِي يَشْفَعُ عِنْدَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                translationRes = R.string.dhikr_morning_ayat_kursi_trans,
                virtueRes = R.string.dhikr_morning_ayat_kursi_virtue,
                targetCount = 1,
                surahNo = 2,
                verseNo = 255
            ),
            DhikrDuaItem(
                id = "morning_sayyidul_istighfar",
                category = DhikrCategory.MORNING,
                titleRes = R.string.dhikr_sayyidul_istighfar_title,
                arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لاَ إِلَهَ إِلاَّ أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لاَ يَغْفِرُ الذُّنُوبَ إِلاَّ أَنْتَ",
                translationRes = R.string.dhikr_sayyidul_istighfar_trans,
                virtueRes = R.string.dhikr_sayyidul_istighfar_virtue,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "morning_radhitu_billahi",
                category = DhikrCategory.MORNING,
                titleRes = R.string.dhikr_morning_radhitu_billahi_title,
                arabicText = "رَضِيتُ بِاللَّهِ رَبًّا وَبِالإِسْلاَمِ دِينًا وَبِمُحَمَّدٍ نَبِيًّا",
                translationRes = R.string.dhikr_morning_radhitu_billahi_trans,
                virtueRes = R.string.dhikr_morning_radhitu_billahi_virtue,
                targetCount = 3
            ),
            DhikrDuaItem(
                id = "morning_bismillah_illadzi",
                category = DhikrCategory.MORNING,
                titleRes = R.string.dhikr_bismillah_illadzi_title,
                arabicText = "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
                translationRes = R.string.dhikr_bismillah_illadzi_trans,
                virtueRes = R.string.dhikr_bismillah_illadzi_virtue,
                targetCount = 3
            ),
            DhikrDuaItem(
                id = "morning_subhanallah_bihamdihi",
                category = DhikrCategory.MORNING,
                titleRes = R.string.dhikr_subhanallah_bihamdihi_title,
                arabicText = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
                translationRes = R.string.dhikr_subhanallah_bihamdihi_trans,
                virtueRes = R.string.dhikr_subhanallah_bihamdihi_virtue,
                targetCount = 100
            ),

            // Evening Dhikr
            DhikrDuaItem(
                id = "evening_muawwidzatain",
                category = DhikrCategory.EVENING,
                titleRes = R.string.dhikr_muawwidzatain_title,
                arabicText = "قُلْ هُوَ اللَّهُ أَحَدٌ ... قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ ... قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
                translationRes = R.string.dhikr_muawwidzatain_trans,
                virtueRes = R.string.dhikr_muawwidzatain_virtue,
                targetCount = 3,
                surahNo = 112,
                verseNo = 1,
                toVerseNo = 114
            ),
            DhikrDuaItem(
                id = "evening_amsayna",
                category = DhikrCategory.EVENING,
                titleRes = R.string.dhikr_evening_amsayna_title,
                arabicText = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ",
                translationRes = R.string.dhikr_evening_amsayna_trans,
                virtueRes = R.string.dhikr_evening_amsayna_virtue,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "evening_audzu_bikalimatillahi",
                category = DhikrCategory.EVENING,
                titleRes = R.string.dhikr_evening_audzu_bikalimatillahi_title,
                arabicText = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ",
                translationRes = R.string.dhikr_evening_audzu_bikalimatillahi_trans,
                virtueRes = R.string.dhikr_evening_audzu_bikalimatillahi_virtue,
                targetCount = 3
            ),

            // Post-Prayer Dhikr
            DhikrDuaItem(
                id = "post_istighfar",
                category = DhikrCategory.POST_PRAYER,
                titleRes = R.string.dhikr_post_istighfar_title,
                arabicText = "أَسْتَغْفِرُ اللَّهَ (٣×) اللَّهُمَّ أَنْتَ السَّلاَمُ وَمِنْكَ السَّلاَمُ تَبَارَكْتَ يَا ذَا الْجَلاَلِ وَالإِكْرَامِ",
                translationRes = R.string.dhikr_post_istighfar_trans,
                virtueRes = R.string.dhikr_post_istighfar_virtue,
                targetCount = 3
            ),
            DhikrDuaItem(
                id = "post_tasbih_tahmid_takbir",
                category = DhikrCategory.POST_PRAYER,
                titleRes = R.string.dhikr_post_tasbih_title,
                arabicText = "سُبْحَانَ اللَّهِ (٣٣×) ، الْحَمْدُ لِلَّهِ (٣٣×) ، اللَّهُ أَكْبَرُ (٣٣×)",
                translationRes = R.string.dhikr_post_tasbih_trans,
                virtueRes = R.string.dhikr_post_tasbih_virtue,
                targetCount = 33
            ),
            DhikrDuaItem(
                id = "post_dua_prayer",
                category = DhikrCategory.POST_PRAYER,
                titleRes = R.string.dhikr_post_dua_prayer_title,
                arabicText = "اللَّهُمَّ أَعِنِّي عَلَى ذِكْرِكَ وَشُكْرِكَ وَحُسْنِ عِبَادَتِكَ",
                translationRes = R.string.dhikr_post_dua_prayer_trans,
                virtueRes = R.string.dhikr_post_dua_prayer_virtue,
                targetCount = 1
            ),

            // Tahlil Sequence
            DhikrDuaItem(
                id = "tahlil_hadrah",
                category = DhikrCategory.TAHLIL,
                titleRes = R.string.tahlil_hadrah_title,
                arabicText = "إِلَى حَضْرَةِ النَّبِيِّ الْمُصْطَفَى مُحَمَّدٍ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ وَآلِهِ وَأَصْحَابِهِ ... الْفَاتِحَة",
                translationRes = R.string.tahlil_hadrah_trans,
                targetCount = 1,
                surahNo = 1,
                verseNo = 1
            ),
            DhikrDuaItem(
                id = "tahlil_kalimat",
                category = DhikrCategory.TAHLIL,
                titleRes = R.string.tahlil_kalimat_title,
                arabicText = "أَفْضَلُ الذِّكْرِ فَاعْلَمْ أَنَّهُ: لَا إِلَهَ إِلَّا اللَّهُ",
                translationRes = R.string.tahlil_kalimat_trans,
                virtueRes = R.string.tahlil_kalimat_virtue,
                targetCount = 33
            ),
            DhikrDuaItem(
                id = "tahlil_dua_deceased",
                category = DhikrCategory.TAHLIL,
                titleRes = R.string.tahlil_dua_deceased_title,
                arabicText = "اللَّهُمَّ اغْفِرْ لَهُمْ وَارْحَمْهُمْ وَعَافِهِمْ وَاعْفُ عَنْهُمْ وَاجْعَلِ الْجَنَّةَ مَثْوَاهُمْ",
                translationRes = R.string.tahlil_dua_deceased_trans,
                targetCount = 1
            ),

            // Quranic Duas
            DhikrDuaItem(
                id = "quranic_good_both_worlds",
                category = DhikrCategory.QURANIC,
                titleRes = R.string.quranic_good_both_worlds_title,
                arabicText = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
                translationRes = R.string.quranic_good_both_worlds_trans,
                virtueRes = R.string.quranic_good_both_worlds_virtue,
                targetCount = 1,
                surahNo = 2,
                verseNo = 201
            ),
            DhikrDuaItem(
                id = "quranic_parents",
                category = DhikrCategory.QURANIC,
                titleRes = R.string.quranic_parents_title,
                arabicText = "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ يَوْمَ يَقُومُ الْحِسَابُ",
                translationRes = R.string.quranic_parents_trans,
                virtueRes = R.string.quranic_parents_virtue,
                targetCount = 1,
                surahNo = 14,
                verseNo = 41
            ),
            DhikrDuaItem(
                id = "quranic_prophet_yunus",
                category = DhikrCategory.QURANIC,
                titleRes = R.string.quranic_prophet_yunus_title,
                arabicText = "لَا إِلَٰهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
                translationRes = R.string.quranic_prophet_yunus_trans,
                virtueRes = R.string.quranic_prophet_yunus_virtue,
                targetCount = 1,
                surahNo = 21,
                verseNo = 87
            ),
            DhikrDuaItem(
                id = "quranic_righteous_offspring",
                category = DhikrCategory.QURANIC,
                titleRes = R.string.quranic_righteous_offspring_title,
                arabicText = "رَبِّ لَا تَذَرْنِي فَرْدًا وَأَنْتَ خَيْرُ الْوَارِثِينَ",
                translationRes = R.string.quranic_righteous_offspring_trans,
                virtueRes = R.string.quranic_righteous_offspring_virtue,
                targetCount = 1,
                surahNo = 21,
                verseNo = 89
            ),
            DhikrDuaItem(
                id = "quranic_ease_and_speech",
                category = DhikrCategory.QURANIC,
                titleRes = R.string.quranic_ease_and_speech_title,
                arabicText = "رَبِّ اشْرَحْ لِي صَدْرِي وَيَسِّرْ لِي أَمْرِي وَاحْلُلْ عُقْدَةً مِنْ لِسَانِي يَفْقَهُوا قَوْلِي",
                translationRes = R.string.quranic_ease_and_speech_trans,
                virtueRes = R.string.quranic_ease_and_speech_virtue,
                targetCount = 1,
                surahNo = 20,
                verseNo = 25,
                toVerseNo = 28
            ),

            // Daily Duas
            DhikrDuaItem(
                id = "daily_before_sleeping",
                category = DhikrCategory.DAILY,
                titleRes = R.string.dhikr_daily_before_sleeping_title,
                arabicText = "بِسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
                translationRes = R.string.dhikr_daily_before_sleeping_trans,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "daily_waking_up",
                category = DhikrCategory.DAILY,
                titleRes = R.string.dhikr_daily_waking_up_title,
                arabicText = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
                translationRes = R.string.dhikr_daily_waking_up_trans,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "daily_entering_masjid",
                category = DhikrCategory.DAILY,
                titleRes = R.string.dhikr_daily_entering_masjid_title,
                arabicText = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
                translationRes = R.string.dhikr_daily_entering_masjid_trans,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "daily_leaving_home",
                category = DhikrCategory.DAILY,
                titleRes = R.string.dhikr_daily_leaving_home_title,
                arabicText = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
                translationRes = R.string.dhikr_daily_leaving_home_trans,
                virtueRes = R.string.dhikr_daily_leaving_home_virtue,
                targetCount = 1
            ),
            DhikrDuaItem(
                id = "daily_vehicle",
                category = DhikrCategory.DAILY,
                titleRes = R.string.dhikr_daily_vehicle_title,
                arabicText = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَٰذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَىٰ رَبِّنَا لَمُنْقَلِبُونَ",
                translationRes = R.string.dhikr_daily_vehicle_trans,
                virtueRes = R.string.dhikr_daily_vehicle_virtue,
                targetCount = 1
            )
        )
    }
}
