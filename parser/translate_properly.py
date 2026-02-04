#!/usr/bin/env python3
"""
Качественный перевод всех продуктов
"""

import json
import re

# Расширенный словарь переводов
WORD_TRANSLATIONS = {
    # Основные продукты
    'bread': 'хлеб',
    'toast': 'тост',
    'bun': 'булочка',
    'buns': 'булочки',
    'roll': 'рулет',
    'cake': 'торт',
    'rice': 'рис',
    'pasta': 'паста',
    'noodles': 'лапша',
    'burger': 'бургер',
    'raisin': 'изюм',
    
    # Мука и зерновые
    'flour': 'мука',
    'wheat': 'пшеница',
    'whole': 'цельнозерновой',
    'multigrain': 'мультизерновой',
    'white': 'белый',
    'brown': 'коричневый',
    'sourdough': 'на закваске',
    'gluten-free': 'безглютеновый',
    'gluten free': 'безглютеновый',
    
    # Молочные
    'yogurt': 'йогурт',
    'milk': 'молоко',
    'cream': 'крем',
    'cheese': 'сыр',
    
    # Сладости
    'chocolate': 'шоколад',
    'cocoa': 'какао',
    'sugar': 'сахар',
    'honey': 'мед',
    
    # Фрукты
    'fruit': 'фрукт',
    'dried': 'сушеный',
    'fresh': 'свежий',
    'apple': 'яблоко',
    'banana': 'банан',
    'orange': 'апельсин',
    'strawberry': 'клубника',
    'raspberry': 'малина',
    'raisin': 'изюм',
    
    # Овощи
    'carrot': 'морковь',
    'potato': 'картофель',
    'tomato': 'помидор',
    
    # Орехи
    'coconut': 'кокос',
    'almond': 'миндаль',
    'peanut': 'арахис',
    'walnut': 'грецкий орех',
    
    # Прилагательные
    'soft': 'мягкий',
    'hard': 'твердый',
    'sweet': 'сладкий',
    'sour': 'кислый',
    'raw': 'сырой',
    'cooked': 'вареный',
    'baked': 'печеный',
    'fried': 'жареный',
    'boiled': 'вареный',
    'frozen': 'замороженный',
    'sliced': 'нарезанный',
    
    # Действия и предлоги
    'with': 'с',
    'and': 'и',
    'prepared': 'приготовленный',
    'made': 'сделанный',
    'filled': 'с начинкой',
    'coated': 'покрытый',
    'mixed': 'смешанный',
    
    # Специфичные термины
    'packet mix': 'готовая смесь',
    'frosting': 'глазурь',
    'iced': 'глазированный',
    'pieces': 'кусочки',
    'sponge': 'бисквит',
    'pound': 'фунтовый',
    'cupcake': 'кекс',
    'mudcake': 'грязевой торт',
    'plumcake': 'сливовый пирог',
    'coffee': 'кофейный',
    'marmalade': 'мармелад',
}

# Специальные фразы для замены
PHRASE_TRANSLATIONS = {
    'whole wheat': 'цельнозерновой',
    'whole-wheat': 'цельнозерновой',
    'gluten free': 'безглютеновый',
    'gluten-free': 'безглютеновый',
    'dried fruit': 'сушеные фрукты',
    'with dried': 'с сушеными',
}

# Полные переводы для конкретных продуктов
FULL_TRANSLATIONS = {
    'Raisin Toast, TipTop™': 'Тост с изюмом TipTop™',
    'Whole-wheat bread with dried fruit': 'Цельнозерновой хлеб с сушеными фруктами',
    'Multigrain bread, gluten-free': 'Мультизерновой хлеб, безглютеновый',
    'White bread, gluten free': 'Белый хлеб, безглютеновый',
    'White bread, gluten free, sliced': 'Белый хлеб, безглютеновый, нарезанный',
    'White roll bread, gluten free': 'Белый хлеб-рулет, безглютеновый',
    'White sourdough bread, gluten free, sliced': 'Белый хлеб на закваске, безглютеновый, нарезанный',
    'Burger Buns, 100% Whole wheat Gigantico': 'Булочки для бургеров, 100% цельнозерновые Gigantico',
}

def translate_name(name):
    """Переводит название продукта"""
    # Проверяем полные переводы
    for eng, rus in FULL_TRANSLATIONS.items():
        if eng in name:
            return name.replace(eng, rus)
    
    # Переводим фразы
    result = name
    for eng_phrase, rus_phrase in PHRASE_TRANSLATIONS.items():
        result = re.sub(r'\b' + re.escape(eng_phrase) + r'\b', rus_phrase, result, flags=re.IGNORECASE)
    
    # Переводим отдельные слова
    for eng_word, rus_word in WORD_TRANSLATIONS.items():
        result = re.sub(r'\b' + re.escape(eng_word) + r'\b', rus_word, result, flags=re.IGNORECASE)
    
    return result

def translate_all_products(input_file, output_file):
    """Переводит все продукты"""
    print(f"Загрузка {input_file}...")
    
    with open(input_file, 'r', encoding='utf-8') as f:
        products = json.load(f)
    
    print(f"Найдено продуктов: {len(products)}")
    print("Перевод всех названий...")
    
    translated_count = 0
    for i, product in enumerate(products):
        original = product['name']
        translated = translate_name(original)
        
        # Если перевод отличается от оригинала, используем его
        if translated != original:
            product['nameRu'] = translated
            translated_count += 1
        else:
            # Иначе оставляем оригинал
            product['nameRu'] = original
        
        # Показываем прогресс
        if (i + 1) % 500 == 0:
            print(f"  Обработано: {i + 1}/{len(products)}")
    
    print(f"\nПереведено: {translated_count} из {len(products)}")
    print(f"Сохранение в {output_file}...")
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(products, f, ensure_ascii=False, indent=2)
    
    print("Готово!")
    
    # Показываем примеры
    print("\nПримеры переводов:")
    for i in range(min(20, len(products))):
        if products[i]['name'] != products[i]['nameRu']:
            print(f"  {products[i]['name']}")
            print(f"    => {products[i]['nameRu']}")

if __name__ == "__main__":
    translate_all_products(
        "../gi-database.json",
        "gi-database-translated.json"
    )
