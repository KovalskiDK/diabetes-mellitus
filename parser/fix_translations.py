#!/usr/bin/env python3
"""
Исправление переводов продуктов
"""

import json
import re

# Словарь переводов для общих слов
TRANSLATIONS = {
    # Продукты
    'cake': 'торт',
    'bread': 'хлеб',
    'rice': 'рис',
    'pasta': 'паста',
    'noodles': 'лапша',
    'flour': 'мука',
    'wheat': 'пшеничный',
    'yogurt': 'йогурт',
    'milk': 'молоко',
    'cream': 'крем',
    'chocolate': 'шоколад',
    'cocoa': 'какао',
    'sugar': 'сахар',
    'honey': 'мед',
    'fruit': 'фруктовый',
    'berry': 'ягодный',
    'apple': 'яблоко',
    'banana': 'банан',
    'orange': 'апельсин',
    'strawberry': 'клубника',
    'raspberry': 'малина',
    'carrot': 'морковь',
    'potato': 'картофель',
    'tomato': 'помидор',
    'coconut': 'кокос',
    'almond': 'миндаль',
    'peanut': 'арахис',
    'walnut': 'грецкий орех',
    
    # Прилагательные
    'soft': 'мягкий',
    'hard': 'твердый',
    'sweet': 'сладкий',
    'sour': 'кислый',
    'fresh': 'свежий',
    'dried': 'сушеный',
    'frozen': 'замороженный',
    'cooked': 'вареный',
    'baked': 'печеный',
    'fried': 'жареный',
    'boiled': 'вареный',
    'raw': 'сырой',
    
    # Действия
    'prepared with': 'приготовленный с',
    'made from': 'сделанный из',
    'filled with': 'с начинкой из',
    'coated with': 'покрытый',
    'mixed with': 'смешанный с',
    
    # Специфичные термины
    'packet mix': 'готовая смесь',
    'frosting': 'глазурь',
    'iced': 'глазированный',
    'pieces': 'кусочки',
    'Christmas': 'рождественский',
    'sponge': 'бисквит',
    'pound': 'фунтовый',
    'cupcake': 'кекс',
    'mudcake': 'шоколадный торт',
    'plumcake': 'сливовый пирог',
    'coffee': 'кофейный',
    'marmalade': 'мармелад',
}

def translate_product_name(name):
    """Переводит название продукта"""
    # Если уже есть хороший перевод, оставляем
    if not any(eng in name.lower() for eng in ['cake', 'bread', 'flour', 'with', 'made', 'prepared']):
        return name
    
    # Специальные случаи
    special_cases = {
        'Chocolate cake made from packet mix with chocolate frosting': 'Шоколадный торт из готовой смеси с шоколадной глазурью',
        'Chocolate mudcake': 'Шоколадный грязевой торт',
        'Christmas fruit cake': 'Рождественский фруктовый кекс',
        'Cupcake, strawberry-iced': 'Кекс с клубничной глазурью',
        'Plumcake, prepared with wheat flour and yogurt': 'Сливовый пирог из пшеничной муки с йогуртом',
        'Pound cake 0%': 'Фунтовый кекс 0%',
        'Raspberry Coffee cake': 'Малиновый кофейный торт',
        'Sponge cake, filled with marmalade and yogurt cream': 'Бисквит с мармеладом и йогуртовым кремом',
        'Sponge cake with cocoa, milk cream-filled, coated with chocolate': 'Бисквит с какао, молочным кремом, покрытый шоколадом',
        'Carrot cake, prepared with wheat flour and coconut flour': 'Морковный торт из пшеничной и кокосовой муки',
    }
    
    if name in special_cases:
        return special_cases[name]
    
    # Общий перевод
    translated = name
    for eng, rus in TRANSLATIONS.items():
        # Заменяем целые слова
        translated = re.sub(r'\b' + eng + r'\b', rus, translated, flags=re.IGNORECASE)
    
    return translated

def fix_translations(input_file, output_file):
    """Исправляет переводы в файле"""
    print(f"Загрузка {input_file}...")
    
    with open(input_file, 'r', encoding='utf-8') as f:
        products = json.load(f)
    
    print(f"Найдено продуктов: {len(products)}")
    print("Исправление переводов...")
    
    fixed_count = 0
    for product in products:
        old_name = product['nameRu']
        new_name = translate_product_name(product['name'])
        
        if new_name != old_name:
            product['nameRu'] = new_name
            fixed_count += 1
            
            if fixed_count <= 10:  # Показываем первые 10
                print(f"  {product['name']}")
                print(f"    Было: {old_name}")
                print(f"    Стало: {new_name}")
    
    print(f"\nИсправлено переводов: {fixed_count}")
    print(f"Сохранение в {output_file}...")
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(products, f, ensure_ascii=False, indent=2)
    
    print("Готово!")

if __name__ == "__main__":
    fix_translations(
        "gi_products_ru_autotranslate.json",
        "gi_products_ru_fixed.json"
    )
