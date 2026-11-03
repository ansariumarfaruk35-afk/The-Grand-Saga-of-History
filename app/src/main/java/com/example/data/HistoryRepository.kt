package com.example.data

object HistoryRepository {

    const val BOOK_TITLE = "विश्व इतिहासको महागाथा"
    const val BOOK_SUBTITLE = "मानव सभ्यताको प्रारम्भदेखि आधुनिक विश्वसम्मको महान् यात्रा"

    const val BOOK_INTRODUCTION = """
विश्व इतिहास केवल मिति, राजाहरूका नाम र युद्धहरूको नीरस फेहरिस्त होइन; यो त मानव जातिको सामूहिक स्मृति, संघर्ष, पीडा, विजय, र चेतनाको महागाथा हो। 

हामी को हौँ? हामी कहाँबाट आयौँ? हाम्रा पुर्खाहरूले जंगली गुफाहरूबाट निस्किएर कसरी भव्य सहरहरू, साम्राज्यहरू, भाषा, दर्शन, विज्ञान, र कानुनहरूको निर्माण गरे? किन विशाल साम्राज्यहरू शिखरमा पुगेर पनि बालुवाको घर झैँ ढले? किन मानिसहरू धर्म, जाति र भूगोेलका नाममा आपसमा लडे, र फेरि कसरी शान्ति र सहकार्यको खोजीमा एकताबद्ध भए? 

इतिहासको अध्ययन भविष्यको पूर्वतयारी हो। जसरी आफ्नो विगत बिर्सने व्यक्तिले आफ्नो पहिचान गुमाउँछ, त्यसरी नै इतिहास नबुझ्ने समाजले विगतका घातक गल्तीहरू फेरि दोहोर्‍याउँछ। 

यस 'विश्व इतिहासको महागाथा' मा हामी पृथ्वीको उत्पत्ति, आदिम मानवको विकास, प्राचीन सभ्यताहरू (इजिप्ट, मेसोपोटामिया, सिन्धु उपत्यका), महान् सम्राटहरू, नेपालको गौरवमय मौलिक इतिहास, विनाशकारी विश्वयुद्धहरू, युगान्तकारी क्रान्तिहरू, धर्म र संस्कृतिको विकास, विज्ञान र प्रविधिको छलाङ, र अन्ततः आधुनिक विश्व तथा मानव जातिको भविष्यको अनन्त सम्भावनासम्मको यात्रा गर्नेछौँ।

आउनुहोस्, ज्ञान र चेतनाको यो महान् यात्रामा सँगै पाइला चालौँ!
    """

    // Complete, detailed 10 Parts with all 78 Chapters and subheadings
    val allParts: List<BookPart> by lazy {
        generateCompleteBookStructure()
    }

    private fun generateCompleteBookStructure(): List<BookPart> {
        val richChaptersMap = mutableMapOf<Int, Chapter>()
        // Load detailed chapters
        HistoryDataPart1To3.getPart1Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart1To3.getPart2Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart1To3.getPart3Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart4Nepal.getNepalChapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart5To7.getPart5Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart5To7.getPart6Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart5To7.getPart7Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart8To10.getPart8Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart8To10.getPart9Chapters().forEach { richChaptersMap[it.id] = it }
        HistoryDataPart8To10.getPart10Chapters().forEach { richChaptersMap[it.id] = it }

        return listOf(
            BookPart(
                partNumber = 1,
                titleDevanagari = "भाग १: मानव उत्पत्तिका कथाहरू र प्रारम्भ",
                titleEnglish = "Part I: The Origins of Humanity",
                timePeriod = "४.५ अर्ब वर्ष पूर्व - ४००० ई.पू.",
                description = "ब्रह्माण्ड, पृथ्वी, जीवनको उत्पत्ति, मानव विकास, आगो र कृषिको ऐतिहासिक सुरुआत।",
                chapters = listOf(
                    richChaptersMap[1] ?: createDefaultChapter(1, 1, 1, "पृथ्वीको उत्पत्ति र जीवनको प्रारम्भ", "The Formation of Earth and the Origins of Life", "४.५ अर्ब वर्ष पूर्व"),
                    richChaptersMap[2] ?: createDefaultChapter(2, 1, 2, "मानव विकास र प्रारम्भिक मानव पुर्खाहरू", "Human Evolution and Early Humans", "६० लाख वर्ष पूर्व"),
                    richChaptersMap[3] ?: createDefaultChapter(3, 1, 3, "आदिम मानव समाज र सिकारी-सङ्कलक जीवन", "Primitive Human Societies", "२ लाख वर्ष पूर्व"),
                    richChaptersMap[4] ?: createDefaultChapter(4, 1, 4, "आगोको आविष्कार र प्रारम्भिक ढुङ्गे औजारहरू", "The Discovery of Fire and Early Tools", "१५ लाख वर्ष पूर्व"),
                    richChaptersMap[5] ?: createDefaultChapter(5, 1, 5, "कृषि क्रान्ति: मानव जीवनशैलीको ऐतिहासिक मोड", "The Agricultural Revolution", "१०,००० ई.पू."),
                    richChaptersMap[6] ?: createDefaultChapter(6, 1, 6, "स्थायी बस्ती, गाउँ र प्रारम्भिक समुदायहरूको जन्म", "The Birth of Settlements and Early Communities", "८००० ई.पू.")
                )
            ),
            BookPart(
                partNumber = 2,
                titleDevanagari = "भाग २: प्राचीन सभ्यताहरू",
                titleEnglish = "Part II: Ancient Civilizations",
                timePeriod = "४००० ई.पू. - ५०० ई.सं.",
                description = "नदी किनारका महान् सभ्यताहरू: इजिप्ट, मेसोपोटामिया, सिन्धु, चीन, ग्रीस, रोम, अफ्रिका र अमेरिका।",
                chapters = listOf(
                    richChaptersMap[7] ?: createDefaultChapter(7, 2, 7, "प्राचीन इजिप्ट/मिश्र सभ्यता: पिरामिड र नील नदीको वरदान", "Ancient Egyptian Civilization", "३१०० ई.पू."),
                    richChaptersMap[8] ?: createDefaultChapter(8, 2, 8, "मेसोपोटामिया सभ्यता: सभ्यताको कोक्रो र कानुनको जग", "Mesopotamian Civilization", "३५०० ई.पू."),
                    richChaptersMap[9] ?: createDefaultChapter(9, 2, 9, "सिन्धु उपत्यका सभ्यता: योजनाबद्ध नगर र हराएको वैभव", "Indus Valley Civilization", "२६०० ई.पू."),
                    richChaptersMap[10] ?: createDefaultChapter(10, 2, 10, "प्राचीन चिनियाँ सभ्यता: पहेँलो नदी र दार्शनिक युग", "Ancient Chinese Civilization", "२००० ई.पू."),
                    richChaptersMap[11] ?: createDefaultChapter(11, 2, 11, "प्राचीन युनानी (ग्रीक) सभ्यता: लोकतन्त्र, दर्शन र ओलम्पिक", "Ancient Greek Civilization", "८०० ई.पू."),
                    richChaptersMap[12] ?: createDefaultChapter(12, 2, 12, "रोमन सभ्यता: गणतन्त्र, साम्राज्य र कानुनको विरासत", "Roman Civilization", "५०९ ई.पू. - ४७६ ई.सं."),
                    richChaptersMap[13] ?: createDefaultChapter(13, 2, 13, "प्राचीन अफ्रिकी सभ्यताहरू: नूबिया, कुश र अक्सुमको वैभव", "Ancient African Civilizations", "१००० ई.पू."),
                    richChaptersMap[14] ?: createDefaultChapter(14, 2, 14, "प्राचीन अमेरिकी सभ्यताहरू: माया, इन्का र एज्टेक", "Ancient American Civilizations", "१२०० ई.पू. - १५०० ई.सं.")
                )
            ),
            BookPart(
                partNumber = 3,
                titleDevanagari = "भाग ३: महान् सम्राट र साम्राज्यहरू",
                titleEnglish = "Part III: Great Kings and Empires",
                timePeriod = "६०० ई.पू. - १९०० ई.सं.",
                description = "विश्व विजयका महत्त्वाकांक्षी सम्राटहरू, साम्राज्यहरूको उदय, उत्कर्ष र पतनको शाश्वत नियम।",
                chapters = listOf(
                    richChaptersMap[15] ?: createDefaultChapter(15, 3, 15, "सिकन्दर महान्/अलेक्ज्यान्डर: विश्व विजयको तृष्णा", "Alexander the Great", "३५६ - ३२३ ई.पू."),
                    richChaptersMap[16] ?: createDefaultChapter(16, 3, 16, "सम्राट अशोक महान्: युद्धको विभीषिकाबाट धर्मविजयसम्म", "Ashoka the Great", "३०४ - २३२ ई.पू."),
                    richChaptersMap[17] ?: createDefaultChapter(17, 3, 17, "चन्द्रगुप्त मौर्य र चाणक्य: अखण्ड भारतको परिकल्पना", "Chandragupta Maurya", "३२२ - २९८ ई.पू."),
                    richChaptersMap[18] ?: createDefaultChapter(18, 3, 18, "फारसी (पारसी) साम्राज्य: साइरस महान् र शाही मार्ग", "The Persian Empire", "५५० - ३३० ई.पू."),
                    richChaptersMap[19] ?: createDefaultChapter(19, 3, 19, "मङ्गोल साम्राज्य: चंगेज खान र स्टेपी घाँसेमैदानको आँधी", "The Mongol Empire", "१२०६ - १३६८ ई.सं."),
                    richChaptersMap[20] ?: createDefaultChapter(20, 3, 20, "मुगल साम्राज्य: बाबरदेखि अकवर र शाहजहाँसम्म", "The Mughal Empire", "१५२६ - १८५७ ई.सं."),
                    richChaptersMap[21] ?: createDefaultChapter(21, 3, 21, "अटोमन (ओटोमन) साम्राज्य: कन्स्टान्टिनोपल पतन र तीन महादेश", "The Ottoman Empire", "१२९९ - १९२२ ई.सं."),
                    richChaptersMap[22] ?: createDefaultChapter(22, 3, 22, "विश्वका महान् साम्राज्यहरूको उदय र पतनको नियम", "The Rise and Fall of Great World Empires", "ऐतिहासिक विश्लेषण"),
                    richChaptersMap[23] ?: createDefaultChapter(23, 3, 23, "विश्वलाई बदल्ने सबैभन्दा प्रभावशाली शासकहरू", "The World's Most Influential Rulers", "तुलनात्मक अध्ययन")
                )
            ),
            BookPart(
                partNumber = 4,
                titleDevanagari = "भाग ४: नेपालको गौरवमय इतिहास",
                titleEnglish = "Part IV: The History of Nepal",
                timePeriod = "प्राचीन काल - वर्तमान",
                description = "काठमाडौँ दहको उत्पत्तिदेखि किरात, लिच्छवि, मल्ल, गोर्खाली एकीकरण, राणा शासन र आधुनिक गणतन्त्रसम्म।",
                chapters = richChaptersMap.values.filter { it.partNumber == 4 }.sortedBy { it.chapterNumber }
            ),
            BookPart(
                partNumber = 5,
                titleDevanagari = "भाग ५: विश्व इतिहासका विनाशकारी महायुद्धहरू",
                titleEnglish = "Part V: Major Wars in World History",
                timePeriod = "१९१४ - १९९१ ई.सं.",
                description = "प्रथम विश्वयुद्ध, दोस्रो विश्वयुद्ध, होलोकास्ट, हिरोसिमा आणविक प्रलय, र शीतयुद्धको होड।",
                chapters = listOf(
                    richChaptersMap[33] ?: createDefaultChapter(33, 5, 33, "प्रथम विश्वयुद्ध (१९१४-१९१८): खाइयुद्ध र साम्राज्यहरूको अन्त्य", "The First World War", "१९१४ - १९१८"),
                    richChaptersMap[34] ?: createDefaultChapter(34, 5, 34, "दोस्रो विश्वयुद्ध (१९३९-१९४५): फाँसीवाद र विश्वव्यापी विनाश", "The Second World War", "१९३९ - १९४५"),
                    richChaptersMap[35] ?: createDefaultChapter(35, 5, 35, "होलोकास्ट: मानव इतिहासको सबैभन्दा क्रूर नाजी नरसंहार", "The Holocaust", "१९४१ - १९४५"),
                    richChaptersMap[36] ?: createDefaultChapter(36, 5, 36, "हिरोसिमा र नागासाकी: आणविक प्रलयको कालो दिन", "Hiroshima and Nagasaki", "६ र ९ अगस्ट १९४५"),
                    richChaptersMap[37] ?: createDefaultChapter(37, 5, 37, "शीतयुद्ध: पुँजीवाद र साम्यवाद बीचको महाशक्तिको होड", "The Cold War", "१९४७ - १९९१"),
                    richChaptersMap[38] ?: createDefaultChapter(38, 5, 38, "भियतनाम युद्ध: महाशक्तिको अहङ्कार र जनप्रतिरोध", "The Vietnam War", "१९५५ - १९७५"),
                    richChaptersMap[39] ?: createDefaultChapter(39, 5, 39, "प्रमुख विश्व द्वन्द्वहरू र तिनका दूरगामी प्रभावहरू", "Major Conflicts and Global Consequences", "ऐतिहासिक विश्लेषण")
                )
            ),
            BookPart(
                partNumber = 6,
                titleDevanagari = "भाग ६: क्रान्ति तथा राजनीतिक आन्दोलनहरू",
                titleEnglish = "Part VI: Revolutions and Political Movements",
                timePeriod = "१७७६ - वर्तमान",
                description = "अमेरिकी, फ्रान्सेली र रुसी क्रान्ति, औद्योगिक क्रान्ति, भारतीय स्वतन्त्रता, र लोकतन्त्रको विश्व सङ्घर्ष।",
                chapters = listOf(
                    richChaptersMap[40] ?: createDefaultChapter(40, 6, 40, "अमेरिकी क्रान्ति (१७७६): स्वतन्त्रताको घोषणा र गणतन्त्र", "The American Revolution", "१७७५ - १७८३"),
                    richChaptersMap[41] ?: createDefaultChapter(41, 6, 41, "फ्रान्सेली क्रान्ति (१७८९): स्वतन्त्रता, समानता र भ्रातृत्व", "The French Revolution", "१७८९ - १७९९"),
                    richChaptersMap[42] ?: createDefaultChapter(42, 6, 42, "रुसी अक्टोबर क्रान्ति (१९१७): लेनिन र सोभियत संघको उदय", "The Russian Revolution", "१९१७"),
                    richChaptersMap[43] ?: createDefaultChapter(43, 6, 43, "औद्योगिक क्रान्ति: वाष्प इन्जिन, कारखाना र श्रमको रूपान्तरण", "The Industrial Revolution", "१७६० - १८४०"),
                    richChaptersMap[44] ?: createDefaultChapter(44, 6, 44, "भारतीय स्वतन्त्रता सङ्ग्राम: महात्मा गान्धी र सत्याग्रह", "India's Independence Movement", "१८५७ - १९४७"),
                    richChaptersMap[45] ?: createDefaultChapter(45, 6, 45, "राष्ट्रवादको उदय: राष्ट्र-राज्यहरूको गठन र प्रभाव", "The Rise of Nationalism", "१९औँ शताब्दी"),
                    richChaptersMap[46] ?: createDefaultChapter(46, 6, 46, "लोकतन्त्र र मानव अधिकारका लागि विश्वव्यापी सङ्घर्ष", "The Struggle for Democracy and Freedom", "२०औँ शताब्दी")
                )
            ),
            BookPart(
                partNumber = 7,
                titleDevanagari = "भाग ७: धर्म, संस्कृति तथा समाजको विकास",
                titleEnglish = "Part VII: Religion, Culture, and Society",
                timePeriod = "प्राचीन काल - आधुनिक युग",
                description = "विश्वका महान् धर्महरू, भाषा, लिपि, विश्वविद्यालयहरूको इतिहास, कला, साहित्य, र मानव अधिकारको विकास।",
                chapters = listOf(
                    richChaptersMap[47] ?: createDefaultChapter(47, 7, 47, "विश्वका प्रमुख धर्महरूको उत्पत्ति र ऐतिहासिक विकास", "Origins of Major Religions", "प्राचीन काल"),
                    richChaptersMap[48] ?: createDefaultChapter(48, 7, 48, "इस्लाम धर्मको इतिहास: पैगम्बर मुहम्मद र इस्लामी स्वर्णयुग", "The History of Islam", "६१० ई.सं. - वर्तमान"),
                    richChaptersMap[49] ?: createDefaultChapter(49, 7, 49, "इसाई धर्मको इतिहास: येशू ख्रीष्ट, चर्च र सुधार आन्दोलन", "The History of Christianity", "प्रथम शताब्दी - वर्तमान"),
                    richChaptersMap[50] ?: createDefaultChapter(50, 7, 50, "हिन्दु धर्म र बौद्ध धर्म: वेद, उपनिषद्, गौतम बुद्ध र मध्यममार्ग", "Hinduism and Buddhism", "ई.पू. १५०० - वर्तमान"),
                    richChaptersMap[51] ?: createDefaultChapter(51, 7, 51, "भाषा, लिपि र लेखन पद्धतिको क्रमिक विकास", "Development of Languages and Writing", "३५०० ई.पू. - वर्तमान"),
                    richChaptersMap[52] ?: createDefaultChapter(52, 7, 52, "शिक्षा र विश्वविद्यालयहरूको इतिहास: नालन्दादेखि अक्सफोर्डसम्म", "History of Education and Universities", "प्राचीन काल - आधुनिक"),
                    richChaptersMap[53] ?: createDefaultChapter(53, 7, 53, "कला, साहित्य र सांस्कृतिक पुनर्जागरण (Renaissance)", "Art, Literature, and Cultural Development", "१४औँ - १७औँ शताब्दी"),
                    richChaptersMap[54] ?: createDefaultChapter(54, 7, 54, "मानव अधिकारको ऐतिहासिक विकास: म्याग्नाकार्टादेखि राष्ट्रसंघसम्म", "The Evolution of Human Rights", "१२१५ - १९४८ ई.सं.")
                )
            ),
            BookPart(
                partNumber = 8,
                titleDevanagari = "भाग ८: विज्ञान, आविष्कार तथा प्रविधि",
                titleEnglish = "Part VIII: Science, Inventions, and Technology",
                timePeriod = "पाङ्ग्रादेखि कृत्रिम बौद्धिकतासम्म",
                description = "पाङ्ग्रा, छापाखाना, विद्युत्, चिकित्सा विज्ञान, कम्प्युटर, इन्टरनेट, अन्तरिक्ष अन्वेषण र एआई।",
                chapters = listOf(
                    richChaptersMap[55] ?: createDefaultChapter(55, 8, 55, "पाङ्ग्राको आविष्कार: गतिशीलताको पहिलो क्रान्ति", "The Invention of the Wheel", "३५०० ई.पू."),
                    richChaptersMap[56] ?: createDefaultChapter(56, 8, 56, "कागज र लेखन सामग्रीको विकास: ज्ञानको स्थायी संरक्षण", "The Development of Writing and Paper", "१०५ ई.सं. (चीन)"),
                    richChaptersMap[57] ?: createDefaultChapter(57, 8, 57, "छापाखाना क्रान्ति: गुटेनबर्ग र सूचनाको लोकतान्त्रिकीकरण", "The Printing Revolution", "१४४० ई.सं."),
                    richChaptersMap[58] ?: createDefaultChapter(58, 8, 58, "विद्युत् शक्तिको खोज र प्रयोग: विश्वलाई उज्यालो बनाउने शक्ति", "Discovery and Use of Electricity", "१८औँ - १९औँ शताब्दी"),
                    richChaptersMap[59] ?: createDefaultChapter(59, 8, 59, "चिकित्सा विज्ञानको इतिहास: खोप, एन्टिबायोटिक र आयु वृद्धि", "The History of Medicine", "प्राचीनदेखि आधुनिक"),
                    richChaptersMap[60] ?: createDefaultChapter(60, 8, 60, "यातायात र सञ्चार क्रान्ति: रेल, टेलिफोन, रेडियो र हवाइजहाज", "Transportation and Communication", "१९औँ - २०औँ शताब्दी"),
                    richChaptersMap[61] ?: createDefaultChapter(61, 8, 61, "कम्प्युटरको आविष्कार: गणना यन्त्रदेखि सिलिकन क्रान्तिसम्म", "The Invention of Computers", "१९४० को दशक"),
                    richChaptersMap[62] ?: createDefaultChapter(62, 8, 62, "इन्टरनेटको जन्म: विश्वलाई जोड्ने डिजिटल सञ्जाल", "The Birth of the Internet", "१९६९ - १९९०"),
                    richChaptersMap[63] ?: createDefaultChapter(63, 8, 63, "अन्तरिक्ष अन्वेषण: चन्द्रमा अवतरणदेखि मंगल अभियानसम्म", "Space Exploration", "१९५७ - वर्तमान"),
                    richChaptersMap[64] ?: createDefaultChapter(64, 8, 64, "कृत्रिम बौद्धिकता (AI) र डिजिटल युगको सम्भावना", "Artificial Intelligence and Digital Revolution", "१९५० - भविष्य")
                )
            ),
            BookPart(
                partNumber = 9,
                titleDevanagari = "भाग ९: व्यापार, अर्थतन्त्र तथा विश्व शक्ति",
                titleEnglish = "Part IX: Trade, Economics, and Global Power",
                timePeriod = "वस्तु विनिमयदेखि विश्व बजारसम्म",
                description = "पैसा, बैङ्किङ, रेशम मार्ग (सिल्क रोड), उपनिवेशवाद, पूँजीवाद, र आर्थिक महाशक्तिहरूको उदय।",
                chapters = listOf(
                    richChaptersMap[65] ?: createDefaultChapter(65, 9, 65, "व्यापारको प्रारम्भ: वस्तु विनिमय (Barter) देखि बजारसम्म", "The Origins of Trade", "प्राचीन काल"),
                    richChaptersMap[66] ?: createDefaultChapter(66, 9, 66, "मुद्रा/पैसाको इतिहास: कौडी, धातुका सिक्कादेखि कागजी नोटसम्म", "The History of Money", "६०० ई.पू. - वर्तमान"),
                    richChaptersMap[67] ?: createDefaultChapter(67, 9, 67, "बैङ्किङ प्रणालीको विकास: इटालीका साहुकारदेखि केन्द्रीय बैंकसम्म", "The Development of Banking", "मध्यकाल - आधुनिक"),
                    richChaptersMap[68] ?: createDefaultChapter(68, 9, 68, "रेशम मार्ग (सिल्क रोड): पूर्व र पश्चिमलाई जोड्ने सेतु", "The Silk Road", "ई.पू. १३० - १४५३ ई.सं."),
                    richChaptersMap[69] ?: createDefaultChapter(69, 9, 69, "उपनिवेशवाद र साम्राज्यवाद: शोषण र पराधीनताको युग", "Colonialism and Imperialism", "१६औँ - २०औँ शताब्दी"),
                    richChaptersMap[70] ?: createDefaultChapter(70, 9, 70, "विश्व पुँजीवादको विकास र औद्योगिक बजार", "The Development of Global Capitalism", "१८औँ शताब्दी - हाल"),
                    richChaptersMap[71] ?: createDefaultChapter(71, 9, 71, "विश्वका आर्थिक महाशक्तिहरूको उदय र विश्व अर्थतन्त्र", "The Rise of Global Economic Powers", "२०औँ - २१औँ शताब्दी")
                )
            ),
            BookPart(
                partNumber = 10,
                titleDevanagari = "भाग १०: आधुनिक विश्व तथा भविष्यको दिशा",
                titleEnglish = "Part X: The Modern World and the Future",
                timePeriod = "वर्तमान - आगामी सहस्राब्दीहरू",
                description = "संयुक्त राष्ट्रसंघ, भू-राजनीति, विश्वव्यापीकरण, जलवायु संकट, र अन्तरिक्ष सभ्यताको भविष्य।",
                chapters = listOf(
                    richChaptersMap[72] ?: createDefaultChapter(72, 10, 72, "आधुनिक विश्वको निर्माण र भू-राजनीतिक समीकरण", "Formation of the Modern World", "१९४५ - वर्तमान"),
                    richChaptersMap[73] ?: createDefaultChapter(73, 10, 73, "संयुक्त राष्ट्रसंघ (UN) को स्थापना र विश्व शान्ति", "Establishment of the United Nations", "२४ अक्टोबर १९४५"),
                    richChaptersMap[74] ?: createDefaultChapter(74, 10, 74, "लोकतन्त्र, गणतन्त्र र आधुनिक राजनीतिक प्रणालीहरू", "Democracy and Political Systems", "आधुनिक काल"),
                    richChaptersMap[75] ?: createDefaultChapter(75, 10, 75, "विश्वव्यापीकरण (Globalization): अवसर र चुनौतीहरू", "Globalization", "१९९० - वर्तमान"),
                    richChaptersMap[76] ?: createDefaultChapter(76, 10, 76, "आधुनिक वैज्ञानिक खोजहरू: क्वान्टम भौतिकीदेखि जेनेटिक्ससम्म", "Modern Scientific Discoveries", "२१औँ शताब्दी"),
                    richChaptersMap[77] ?: createDefaultChapter(77, 10, 77, "जलवायु परिवर्तन: पृथ्वी र मानव जातिको अस्तित्वको संकट", "Climate Change and Humanity", "वर्तमान संकट"),
                    richChaptersMap[78] ?: createDefaultChapter(78, 10, 78, "मानव सभ्यताको भविष्य: अन्तरिक्ष उपनिवेश र अस्तित्व रक्षा", "Future of Human Civilization", "भविष्य")
                )
            )
        )
    }

    private fun createDefaultChapter(
        id: Int,
        partNum: Int,
        chapNum: Int,
        titleDev: String,
        titleEng: String,
        period: String
    ): Chapter {
        return Chapter(
            id = id,
            partNumber = partNum,
            chapterNumber = chapNum,
            titleDevanagari = titleDev,
            titleEnglish = titleEng,
            timePeriod = period,
            subheadings = listOf(
                "$chapNum.१ $titleDev को ऐतिहासिक पृष्ठभूमि तथा उद्भव",
                "$chapNum.२ मुख्य घटनाहरू, समाज र संरचनागत रूपान्तरण",
                "$chapNum.३ प्रमुख व्यक्तित्वहरू र तिनको विश्वव्यापी योगदान",
                "$chapNum.४ समकालीन समाजमा यस अध्यायको प्रभाव र भविष्यका पाठहरू"
            ),
            keyQuote = "इतिहासको यो अध्याय मानव सभ्यताको निरन्तर विकासको अर्को एउटा महत्वपूर्ण कडी हो।",
            historicalBackground = "$titleDev को उदय तत्कालीन सामाजिक, भौगोलिक र राजनीतिक परिस्थितिहरूको उपज थियो। यसले तत्कालीन समाजमा नयाँ चेतना र दिशा प्रदान गरेको थियो।",
            detailedExplanation = "$titleDev ले मानव समाजलाई नयाँ मोडमा डोहोर्‍यायो। यस कालखण्डमा मानिसहरूले नयाँ प्रविधि, दर्शन, र प्रशासनिक प्रणालीहरूको विकास गरे। घटनाक्रमहरूले विश्वलाई आधुनिक युगमा प्रवेश गराउन मद्दत गरे।",
            importantPersonalities = listOf(
                Personality("ऐतिहासिक नायकहरू", "युगप्रवर्तक व्यक्तित्व", "जसले आफ्नो नेतृत्व र सोचमार्फत समाजको दिशा परिवर्तन गरे।")
            ),
            timelineEvents = listOf(
                TimelineEvent(period, "ऐतिहासिक विन्दु", "$titleDev को चरमोत्कर्ष र विश्वव्यापी प्रभाव।")
            ),
            causesAndFactors = listOf(
                "सामाजिक र आर्थिक आवश्यकताहरूको दबाब।",
                "प्रविधिको विकास र नयाँ विचारहरूको प्रसार।",
                "सामूहिक मानव इच्छाशक्ति र सहकार्य।"
            ),
            consequencesAndImpact = listOf(
                "मानव चेतनामा युगान्तकारी रूपान्तरण।",
                "सभ्यताको भौगोलिक तथा सांस्कृतिक सीमाहरूको विस्तार।",
                "वर्तमान विश्व व्यवस्थाको आधार निर्माण।"
            ),
            interestingFacts = listOf(
                "यस कालखण्डका धेरै घटनाहरूले आज पनि हाम्रो दैनिक जीवनलाई प्रत्यक्ष प्रभाव पारिरहेका छन्।"
            ),
            historiansPerspectives = "इतिहासकारहरू यस घटनालाई मानव चेतनाको परिपक्वतातर्फको एक अपरिहार्य ऐतिहासिक कदम मान्दछन्।",
            modernWorldConnection = "यस अध्यायबाट सिकिएका पाठहरूले आजका भू-राजनीतिक, सामाजिक र प्राविधिक चुनौतीहरू समाधान गर्न मार्गनिर्देश गर्छन्।",
            lessonsForHumanity = listOf(
                "परिवर्तन नै प्रकृतिको शाश्वत नियम हो; अनुकूलनशीलता नै बाँच्ने आधार हो।",
                "मानव हित र कल्याणलाई केन्द्रमा राखेर मात्र समाज दिगो बन्न सक्छ।"
            ),
            powerfulConclusion = "$titleDev को अध्ययनले हामीलाई स्मरण गराउँछ कि हामी विगतका सपनाहरूका प्रतिफल हौँ र भविष्यका सिर्जनाकार हौँ।"
        )
    }

    fun getAllChapters(): List<Chapter> {
        return allParts.flatMap { it.chapters }
    }

    fun getChapterById(id: Int): Chapter? {
        return getAllChapters().find { it.id == id }
    }

    fun searchChapters(query: String): List<Chapter> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return getAllChapters()
        return getAllChapters().filter {
            it.titleDevanagari.lowercase().contains(q) ||
            it.titleEnglish.lowercase().contains(q) ||
            it.subheadings.any { sub -> sub.lowercase().contains(q) } ||
            it.historicalBackground.lowercase().contains(q)
        }
    }

    // Historical Quizzes in Nepali Devanagari
    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "नेपालको इतिहासमा प्रथम प्रामाणिक शिलालेख मानिने चाँगुनारायणको स्तम्भ अभिलेख कुन राजाको पालामा कुँदिएको थियो?",
            options = listOf("राजा मानदेव", "अंशुवर्मा", "जयस्थिति मल्ल", "पृथ्वीनारायण शाह"),
            correctIndex = 0,
            explanation = "वि.सं. ५२१ (ई.सं. ४६४) मा लिच्छवि राजा मानदेवले चाँगुनारायण मन्दिरमा नेपालको पहिलो प्रामाणिक स्तम्भ अभिलेख स्थापना गरेका थिए।"
        ),
        QuizQuestion(
            id = 2,
            question = "'नेपाल दुई ढुङ्गा बीचको तरुल हो' र 'प्रजा मोटा भया दरबार बलियो रहन्छ' भन्ने अमर वाणी कसको हो?",
            options = listOf("राजा जनक", "बहादुर शाह", "श्री ५ बडामहाराजाधिराज पृथ्वीनारायण शाह", "जङ्गबहादुर राणा"),
            correctIndex = 2,
            explanation = "पृथ्वीनारायण शाहले आफ्नो देहावसान पूर्व नुवाकोट दरबारमा दिएको 'दिव्य उपदेश' मा नेपालको भू-राजनीतिक यथार्थ र आर्थिक स्वावलम्बनबारे यो मार्गदर्शन दिएका थिए।"
        ),
        QuizQuestion(
            id = 3,
            question = "ई.पू. २६१ को कलिंग युद्धको भयानक रक्तपातपछि युद्ध त्यागेर बौद्ध धर्म अँगाल्ने महान् सम्राट को हुन्?",
            options = listOf("चन्द्रगुप्त मौर्य", "सम्राट अशोक महान्", "अलेक्ज्यान्डर", "कनिष्क"),
            correctIndex = 1,
            explanation = "कलिंग युद्धमा लाखौँ मानिसको मृत्यु भएपछि सम्राट अशोकले सधैँका लागि युद्ध त्यागी 'धम्म विजय' को नीति लिए र लुम्बिनीमा समेत अशोक स्तम्भ स्थापना गरे।"
        ),
        QuizQuestion(
            id = 4,
            question = "विश्वको पहिलो विस्तृत लिखित कानुनी संहिता 'हम्मुराबी कोड' (आँखाको बदला आँखा) कुन प्राचीन सभ्यताको देन हो?",
            options = listOf("मेसोपोटामिया (बेबिलोन)", "इजिप्ट", "सिन्धु उपत्यका", "माया सभ्यता"),
            correctIndex = 0,
            explanation = "बेबिलोनका राजा हम्मुराबीले ई.पू. १८औँ शताब्दीमा २८२ धारा भएको संसारकै पहिलो लिखित कानुनी संहिता जारी गरेका थिए।"
        ),
        QuizQuestion(
            id = 5,
            question = "नेपालमा १०४ वर्षे जहाँनिया राणा शासनको सुरुआत कुन ऐतिहासिक पर्व/हत्याकाण्डबाट भएको थियो?",
            options = listOf("भण्डारखाल पर्व", "अलौ पर्व", "कोतपर्व (वि.सं. १९०३)", "दरबार हत्याकाण्ड"),
            correctIndex = 2,
            explanation = "वि.सं. १९०३ असोज २ गते राति भएको कोतपर्वमा विरोधी भारदारहरूको संहार गरी जङ्गबहादुर कुँवर (राणा) ले शासन सत्ता आफ्नो हातमा लिएका थिए।"
        ),
        QuizQuestion(
            id = 6,
            question = "मानव इतिहासमा 'स्वतन्त्रता, समानता र भ्रातृत्व' (Liberty, Equality, Fraternity) को अमर नारा कुन क्रान्तिले दिएको थियो?",
            options = listOf("अमेरिकी क्रान्ति", "फ्रान्सेली क्रान्ति (१७८९)", "रुसी क्रान्ति", "औद्योगिक क्रान्ति"),
            correctIndex = 1,
            explanation = "१७८९ को फ्रान्सेली क्रान्तिले बास्टिल किल्ला ढालेर सामन्तवाद अन्त्य गर्दै विश्वलाई यो ऐतिहासिक नारा दिएको थियो।"
        ),
        QuizQuestion(
            id = 7,
            question = "भगवान् गौतम बुद्धको जन्म नेपालको कुन पवित्र ऐतिहासिक स्थानमा भएको थियो?",
            options = listOf("काठमाडौँ", "जनकपुर", "लुम्बिनी", "देवदह"),
            correctIndex = 2,
            explanation = "ई.पू. ६२३ मा शाक्यमुनि गौतम बुद्धको जन्म नेपालको लुम्बिनीमा भएको थियो, जसको प्रामाणिक पुष्टि सम्राट अशोकको स्तम्भले गर्दछ।"
        ),
        QuizQuestion(
            id = 8,
            question = "मानव इतिहासको सबैभन्दा ठूलो प्राविधिक क्रान्ति मानिने पहिलो यान्त्रिक पाङ्ग्राको प्रयोग कहाँ भएको मानिन्छ?",
            options = listOf("प्राचीन सुमेर (मेसोपोटामिया)", "रोम", "इन्का", "ग्रीस"),
            correctIndex = 0,
            explanation = "करिब ई.पू. ३५०० तिर मेसोपोटामिया (सुमेर) मा कुमालेको चक्र र पछि गोरु गाडाका लागि पाङ्ग्राको पहिलो प्रयोग सुरु भएको थियो।"
        )
    )

    // Master Chronology of Human Civilization
    val masterTimeline: List<TimelineEvent> = listOf(
        TimelineEvent("४.५ अर्ब वर्ष पूर्व", "पृथ्वीको उत्पत्ति", "सौर्यमण्डलको रचना र आदिम महासागरहरूको निर्माण।", "पृथ्वी काल"),
        TimelineEvent("३.८ अर्ब वर्ष पूर्व", "पहिलो जीवन", "महासागरमा पहिलो एकल-कोशिकीय ब्याक्टेरियाको उपस्थिति।", "जैविक काल"),
        TimelineEvent("३ लाख वर्ष पूर्व", "होमो सेपियन्स", "अफ्रिकामा आधुनिक बुद्धियुक्त मानव पुर्खाको विकास।", "पाषाण काल"),
        TimelineEvent("१०,००० ई.पू.", "कृषि क्रान्ति", "फिरन्ते जीवनबाट स्थायी खेती, गाउँ र अन्नको घरेलुकरण।", "नवपाषाण"),
        TimelineEvent("३५०० ई.पू.", "सभ्यताको सुरुआत", "मेसोपोटामियामा पहिलो सहर उरुक र क्युनिफर्म लिपिको आविष्कार।", "काँस्य युग"),
        TimelineEvent("३१०० ई.पू.", "इजिप्ट र पिरामिड", "फारो नार्मरद्वारा इजिप्ट एकीकरण र गिजाका महान् पिरामिडहरू।", "प्राचीन"),
        TimelineEvent("२६०० ई.पू.", "सिन्धु उपत्यका", "हडप्पा र मोहेन्जोदारोमा ग्रिड सहर र आधुनिक ढल निकास।", "प्राचीन"),
        TimelineEvent("ई.पू. ८००", "किरात काल नेपाल", "राजा यलम्बरद्वारा महिषपाल राजा पराजित गरी किरात राज्य स्थापना।", "नेपाल इतिहास"),
        TimelineEvent("ई.पू. ६२३", "गौतम बुद्धको जन्म", "नेपालको लुम्बिनीमा विश्व शान्तिदूत बुद्धको प्रादुर्भाव।", "आध्यात्मिक"),
        TimelineEvent("ई.पू. ३२६", "अलेक्ज्यान्डर र भारत", "झेलम किनारमा राजा पोरससँग ऐतिहासिक युद्ध।", "साम्राज्य"),
        TimelineEvent("ई.पू. २६१", "कलिंग युद्ध र अशोक", "युद्ध त्यागेर बौद्ध धर्म र लुम्बिनीमा अशोक स्तम्भ स्थापना।", "मौर्य काल"),
        TimelineEvent("ई.सं. ४६४", "चाँगुनारायण अभिलेख", "राजा मानदेवद्वारा नेपालको पहिलो प्रामाणिक इतिहास अंकित।", "लिच्छवि युग"),
        TimelineEvent("१२५७ ई.सं.", "मल्ल कालको सुरुआत", "काठमाडौँ उपत्यकामा कला, संस्कृति र वास्तुकलाको उत्कर्ष।", "मध्यकाल"),
        TimelineEvent("१७४३ - १७६९ ई.सं.", "नेपाल एकीकरण", "पृथ्वीनारायण शाहद्वारा आधुनिक अखण्ड नेपाल राष्ट्रको निर्माण।", "आधुनिक नेपाल"),
        TimelineEvent("१७८९ ई.सं.", "फ्रान्सेली क्रान्ति", "स्वतन्त्रता, समानता र भ्रातृत्वको विश्वव्यापी घोषणा।", "क्रान्ति युग"),
        TimelineEvent("१८२५ ई.सं.", "औद्योगिक क्रान्ति र रेल", "वाष्प इन्जिन, कारखाना र पहिलो सार्वजनिक रेलमार्ग।", "औद्योगिक"),
        TimelineEvent("१८४६ ई.सं.", "कोतपर्व र राणा शासन", "नेपालमा १०४ वर्षे जहाँनिया राणा शासनको सुरुआत।", "नेपाल"),
        TimelineEvent("१९१४ - १९१८", "प्रथम विश्वयुद्ध", "साम्राज्यहरूको अन्त्य र आधुनिक सैन्य विभीषिका।", "महायुद्ध"),
        TimelineEvent("१९३९ - १९४५", "दोस्रो विश्वयुद्ध", "फाँसीवादको पराजय र हिरोसिमा आणविक प्रलय।", "महायुद्ध"),
        TimelineEvent("१९४५ अक्टोबर २४", "संयुक्त राष्ट्रसंघ (UN)", "विश्व शान्ति र मानव अधिकारको विश्व मञ्च स्थापना।", "आधुनिक विश्व"),
        TimelineEvent("१९५१ (२००७ साल)", "नेपालमा प्रजातन्त्र", "राणा शासन अन्त्य भई प्रजातान्त्रिक युगको सुरुआत।", "नेपाल"),
        TimelineEvent("१९६९ जुलाई २०", "चन्द्रमा अवतरण", "मानव जाति पहिलोपटक पृथ्वी बाहिर अर्को पिण्डमा।", "अन्तरिक्ष"),
        TimelineEvent("२००६ (२०६२/६३)", "नेपाल दोस्रो जनआन्दोलन", "राजतन्त्र अन्त्य र संघीय लोकतान्त्रिक गणतन्त्रको घोषणा।", "नेपाल"),
        TimelineEvent("वर्तमान र भविष्य", "कृत्रिम बौद्धिकता (AI)", "चौथो औद्योगिक क्रान्ति, एआई, र बहुग्रहिय सभ्यताको यात्रा।", "भविष्य")
    )
}
