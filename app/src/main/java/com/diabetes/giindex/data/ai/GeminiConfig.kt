package com.diabetes.giindex.data.ai

object GeminiConfig {
    // Google Gemini API ключ
    // Бесплатный лимит: 60 запросов/минуту, 1500 запросов/день
    const val API_KEY = "AIzaSyB6meIMD5DWTEOWeVcLfOg2scKz016bZyM"
    
    // Примечание: Для production приложения рекомендуется:
    // 1. Хранить ключ в BuildConfig или через Firebase Remote Config
    // 2. Добавить rate limiting для защиты от злоупотреблений
    // 3. Мониторить использование API
}
