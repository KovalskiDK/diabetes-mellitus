package com.diabetes.giindex.data.ai

object GeminiConfig {
    // Бесплатный API ключ Google Gemini
    // Получить свой ключ можно здесь: https://makersuite.google.com/app/apikey
    const val API_KEY = "AIzaSyDemoKeyPleaseReplaceWithYourOwnKey123456789"
    
    // Примечание: Для production приложения рекомендуется:
    // 1. Использовать собственный API ключ
    // 2. Хранить ключ в BuildConfig или через Firebase Remote Config
    // 3. Добавить rate limiting для защиты от злоупотреблений
}
