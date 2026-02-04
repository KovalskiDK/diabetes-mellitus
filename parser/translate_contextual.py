#!/usr/bin/env python3
"""
Контекстный перевод продуктов с правильной грамматикой
"""

import json
import re

# Полные контекстные переводы для типичных конструкций
CONTEXTUAL_PATTERNS = [
    # Торты и выпечка
    (r'^(.+) cake, prepared with (.+) flour and (.+)$', 
     lambda m: f"{translate_ingredient(m.group(1))} торт, приготовленный из {translate_ingredient(m.group(2))} муки и {translate_ingredient(m.group(3))}"),
    
    (r'^(.+) cake made from (.+) with (.+)$',
     lambda m: f"{translate_ingredient(m.group(1))} торт из {translate_ingredient(m.group(2))} с {translate_ingredient(m.group(3))}"),
    
    (r'^(.+) cake$',
     lambda m: f"{translate_ingredient(m.group(1))} торт"),
    
    # Хлеб
    (r'^(.+) bread, gluten[- ]free, sliced$',
     lambda m: f"{translate_ingredient(m.group(1))} хлеб, безглютеновый, нарезанный"),
    
    (r'^(.+) bread, gluten[- ]free$',
     lambda m: f"{translate_ingredient(m.group(1))} хлеб, безглютеновый"),
    
    (r'^(.+) bread with (.+)$',
     lambda m: f"{translate_ingredient(m.group(1))} хлеб с {translate_ingredient(m.group(2))}"),
    
    (r'^(.+) bread$',
     lambda m: f"{translate_ingredient(m.group(1))} хлеб"),
    
    # Булочки
    (r'^(.+) buns?$',
     lambda m: f"{translate_ingredient(m.group(1))} булочки" if 'buns' in m.group(0).lower() else f"{translate_ingredient(m.group(1))} булочка"),
    
    # Тосты
    (r'^(.+) toast$',
     lambda m: f"{translate_ingredient(m.group(1))} тост"),
]

# Словарь ингредиентов с правильными падежами
INGREDIENTS = {
    # Основные
    'carrot': 'морковный',
    'chocolate': 'шоколадный',
    'christmas': 'рождественский',
    'fruit': 'фруктовый',
    'raisin': 'изюмный',
    'strawberry': 'клубничный',
    'raspberry': 'малиновый',
    'blueberry': 'черничный',
    'banana': 'банановый',
    'apple': 'яблочный',
    'orange': 'апельсиновый',
    'lemon': 'лимонный',
    
    # Типы муки и зерна
    'wheat': 'пшеничный',
    'whole wheat': 'цельнозерновой',
    'whole-wheat': 'цельнозерновой',
    'white': 'белый',
    'brown': 'коричневый',
    'multigrain': 'мультизерновой',
    'sourdough': 'на закваске',
    'coconut': 'кокосовый',
    
    # Молочные
    'yogurt': 'йогурт',
    'milk': 'молоко',
    'cream': 'крем',
    
    # Другое
    'packet mix': 'готовая смесь',
    'frosting': 'глазурь',
    'dried fruit': 'сушеные фрукты',
    'gluten free': 'безглютеновый',
    'gluten-free': 'безглютеновый',
}

# Специальные полные переводы
FULL_TRANSLATIONS = {
    'Cake, NS, decreased GI variant, sugar-to-flour ratio: 0.80': 
        'Торт, без уточнения, вариант со сниженным ГИ, соотношение сахара к муке: 0.80',
    
    'Carrot cake, prepared with wheat flour and coconut flour': 
        'Морковный торт, приготовленный из пшеничной и кокосовой муки',
    
    'Chocolate cake made from packet mix with chocolate frosting': 
        'Шоколадный торт из готовой смеси с шоколадной глазурью',
    
    'Chocolate mudcake': 
        'Шоколадный грязевой торт',
    
    'Christmas fruit cake': 
        'Рождественский фруктовый кекс',
    
    'Cupcake, strawberry-iced': 
        'Кекс с клубничной глазурью',
    
    'Plumcake, prepared with wheat flour and yogurt': 
        'Сливовый пирог, приготовленный из пшеничной муки с йогуртом',
    
    'Pound cake 0%': 
        'Фунтовый кекс 0%',
    
    'Raspberry Coffee cake': 
        'Малиновый кофейный торт',
    
    'Sponge cake, filled with marmalade and yogurt cream': 
        'Бисквитный торт с мармеладом и йогуртовым кремом',
    
    'Raisin Toast, TipTop™': 
        'Тост с изюмом TipTop™',
    
    'Whole-wheat bread with dried fruit': 
        'Цельнозерновой хлеб с сушеными фруктами',
    
    'Multigrain bread, gluten-free': 
        'Мультизерновой хлеб, безглютеновый',
    
    'White bread, gluten free': 
        'Белый хлеб, безглютеновый',
    
    'White bread, gluten free, sliced': 
        'Белый хлеб, безглютеновый, нарезанный',
    
    'White roll bread, gluten free': 
        'Белый хлеб-рулет, безглютеновый',
    
    'White sourdough bread, gluten free, sliced': 
        'Белый хлеб на закваске, безглютеновый, нарезанный',
    
    'Burger Buns, 100% Whole wheat Gigantico': 
        'Булочки для бургеров, 100% цельнозерновые Gigantico',
}

def translate_ingredient(text):
    """Переводит ингредиент с учетом контекста"""
    text = text.strip().lower()
    
    # Проверяем полные совпадения
    if text in INGREDIENTS:
        return INGREDIENTS[text]
    
    # Проверяем частичные совпадения
    for eng, rus in INGREDIENTS.items():
        if eng in text:
            return rus
    
    # Если не нашли, возвращаем как есть
    return text

def translate_contextual(name):
    """Контекстный перевод названия продукта"""
    # Проверяем полные переводы
    if name in FULL_TRANSLATIONS:
        return FULL_TRANSLATIONS[name]
    
    # Проверяем паттерны
    for pattern, translator in CONTEXTUAL_PATTERNS:
        match = re.match(pattern, name, re.IGNORECASE)
        if match:
            try:
                return translator(match)
            except:
                pass
    
    # Если паттерн не подошел, делаем простой перевод
    result = name
    
    # Заменяем известные фразы
    replacements = {
        'prepared with': 'приготовленный из',
        'made from': 'из',
        'with': 'с',
        'and': 'и',
        ', gluten free': ', безглютеновый',
        ', gluten-free': ', безглютеновый',
        'gluten free': 'безглютеновый',
        'gluten-free': 'безглютеновый',
        'sliced': 'нарезанный',
        'whole wheat': 'цельнозерновой',
        'whole-wheat': 'цельнозерновой',
    }
    
    for eng, rus in replacements.items():
        result = re.sub(r'\b' + re.escape(eng) + r'\b', rus, result, flags=re.IGNORECASE)
    
    # Заменяем отдельные слова
    for eng, rus in INGREDIENTS.items():
        if len(eng) > 3:  # Только длинные слова
            result = re.sub(r'\b' + re.escape(eng) + r'\b', rus, result, flags=re.IGNORECASE)
    
    return result

def process_file(input_file, output_file):
    """Обрабатывает файл с переводами"""
    print(f"Загрузка {input_file}...")
    
    with open(input_file, 'r', encoding='utf-8') as f:
        products = json.load(f)
    
    print(f"Найдено продуктов: {len(products)}")
    print("Контекстный перевод...")
    
    improved = 0
    for i, product in enumerate(products):
        original_name = product['name']
        new_translation = translate_contextual(original_name)
        
        # Обновляем только если перевод изменился
        if new_translation != product.get('nameRu', ''):
            product['nameRu'] = new_translation
            improved += 1
        
        if (i + 1) % 500 == 0:
            print(f"  Обработано: {i + 1}/{len(products)}")
    
    print(f"\nУлучшено переводов: {improved}")
    print(f"Сохранение в {output_file}...")
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(products, f, ensure_ascii=False, indent=2)
    
    print("Готово!")
    
    # Показываем примеры
    print("\nПримеры улучшенных переводов:")
    count = 0
    for product in products[:100]:
        if product['name'] in FULL_TRANSLATIONS:
            print(f"  {product['name']}")
            print(f"    => {product['nameRu']}")
            count += 1
            if count >= 10:
                break

if __name__ == "__main__":
    process_file(
        "../gi-database-ru.json",
        "gi-database-final.json"
    )
