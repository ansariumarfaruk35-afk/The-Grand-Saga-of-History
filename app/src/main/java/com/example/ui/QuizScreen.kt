package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.HistoryRepository
import com.example.data.QuizQuestion
import com.example.ui.theme.DarkBg
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MartelFontFamily
import com.example.ui.theme.TerracottaAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier
) {
    val questions = HistoryRepository.quizQuestions
    var currentIndex by remember { mutableStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "इतिहास ज्ञान परीक्षण (History Quiz)",
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "विश्व तथा नेपालको इतिहास सम्बन्धी ज्ञानको मूल्याङ्कन",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldPrimary.copy(alpha = 0.2f),
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = "अङ्क: $score",
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (isFinished) {
                // Completed Screen
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(2.dp, GoldPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "बधाई छ! ज्ञान परीक्षण सम्पन्न भयो",
                            fontFamily = MartelFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "तपाईंले ${questions.size} प्रश्नहरू मध्ये $score अङ्क प्राप्त गर्नुभयो।",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                currentIndex = 0
                                selectedOptionIndex = null
                                isSubmitted = false
                                score = 0
                                isFinished = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = DarkBg)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("पुनः परीक्षण गर्नुहोस्", color = DarkBg, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                // Quiz Question View
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Progress Indicator
                    item {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "प्रश्न ${currentIndex + 1} / ${questions.size}",
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "${((currentIndex + 1) * 100) / questions.size}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = GoldPrimary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }

                    // Question Card
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    text = currentQuestion.question,
                                    fontFamily = MartelFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    lineHeight = 26.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    // Options
                    items(count = currentQuestion.options.size) { optIndex ->
                        val optionText = currentQuestion.options[optIndex]
                        val isSelected = selectedOptionIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.correctIndex

                        val containerColor = when {
                            !isSubmitted && isSelected -> GoldPrimary.copy(alpha = 0.2f)
                            isSubmitted && isCorrect -> Color(0xFF2E7D32).copy(alpha = 0.2f)
                            isSubmitted && isSelected && !isCorrect -> TerracottaAccent.copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val borderColor = when {
                            !isSubmitted && isSelected -> GoldPrimary
                            isSubmitted && isCorrect -> Color(0xFF2E7D32)
                            isSubmitted && isSelected && !isCorrect -> TerracottaAccent
                            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        }

                        Surface(
                            onClick = {
                                if (!isSubmitted) {
                                    selectedOptionIndex = optIndex
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = containerColor,
                            border = BorderStroke(1.5.dp, borderColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("quiz_option_$optIndex")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(borderColor.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${('क'.code + optIndex).toChar()}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )

                                if (isSubmitted) {
                                    if (isCorrect) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                                    } else if (isSelected) {
                                        Icon(Icons.Default.Cancel, contentDescription = null, tint = TerracottaAccent)
                                    }
                                }
                            }
                        }
                    }

                    // Explanation (if submitted)
                    item {
                        AnimatedVisibility(visible = isSubmitted) {
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = GoldPrimary.copy(alpha = 0.1f)
                                ),
                                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Info, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "ऐतिहासिक व्याख्या:",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = GoldPrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = currentQuestion.explanation,
                                        style = MaterialTheme.typography.bodySmall,
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // Action Button (Submit or Next)
                Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    if (!isSubmitted) {
                        Button(
                            onClick = {
                                if (selectedOptionIndex != null) {
                                    isSubmitted = true
                                    if (selectedOptionIndex == currentQuestion.correctIndex) {
                                        score++
                                    }
                                }
                            },
                            enabled = selectedOptionIndex != null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("btn_quiz_submit"),
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                        ) {
                            Text("उत्तर जाँच्नुहोस् (Check Answer)", color = DarkBg, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                if (currentIndex < questions.size - 1) {
                                    currentIndex++
                                    selectedOptionIndex = null
                                    isSubmitted = false
                                } else {
                                    isFinished = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("btn_quiz_next"),
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                        ) {
                            Text(
                                text = if (currentIndex < questions.size - 1) "अर्को प्रश्न →" else "नतिजा हेर्नुहोस् (See Results)",
                                color = DarkBg,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
