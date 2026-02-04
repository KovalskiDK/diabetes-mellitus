#!/usr/bin/env python3
"""
Извлечение продуктов с качественными переводами из gi_products_ru_better_vA.json
которых нет в gi-popular-products.json
"""

import json

# Загружаем существующую базу популярных продуктов
with open('sources/gi-popular-products.json', 'r', encoding='utf-8') as f:
    popular_products = json.load(f)

# Создаем множество названий для быстрого поиска
popular_names = set([p['nameRu'].lower() for p in popular_products])

# Загружаем базу с качественными переводами
with open('gi_products_ru_better_vA.json', 'r', encoding='utf-8') as f:
    all_products = json.load(f)

print(f"Всего продуктов в gi_products_ru_better_vA.json: {len(all_products)}")
print(f"Продуктов в gi-popular-products.json: {len(popular_products)}")

# Критерии качественного перевода:
def is_quality_translation(product):
    name_ru = product.get('nameRu', '')
    name_en = product.get('name', '')
    
    # 1. Проверка на латиницу в русском названии (кроме брендов в скобках)
    import re
    # Убираем содержимое скобок (там могут быть бренды)
    name_without_brackets = re.sub(r'\([^)]*\)', '', name_ru)
    # Считаем латинские буквы
    latin_chars = len(re.findall(r'[a-zA-Z]', name_without_brackets))
    # Если больше 3 латинских букв - плохой перевод
    if latin_chars > 3:
        return False
    
    # 2. Перевод содержит английские/технические слова
    bad_words = [
        'NS', 'GI', 'GL', 'variant', 'ratio', 'mix', 'packet', 'flour', 
        'wheat', 'coconut', 'sugar', 'added', 'premix', 'fibre', 'fiber',
        'noodle', 'burger', 'high', 'low', 'no', 'free', 'lite', 'light',
        'AF-', 'LC', 'HFP', 'made', 'with', 'from', 'prepared', 'cooked',
        'boiled', 'fried', 'baked', 'raw', 'fresh', 'frozen', 'canned',
        'decreased', 'increased', 'reduced', 'enriched', 'fortified'
    ]
    name_lower = name_ru.lower()
    for word in bad_words:
        if word.lower() in name_lower:
            return False
    
    # 3. Перевод слишком длинный и технический
    if len(name_ru) > 70:
        return False
    
    # 4. Транслит вместо перевода
    translits = [
        'мадкейк', 'капкейк', 'крекер', 'чипс', 'снек', 'микс',
        'фреш', 'лайт', 'фри', 'бургер', 'ролл', 'сэндвич'
    ]
    for translit in translits:
        if translit in name_lower:
            return False
    
    # 5. Перевод идентичен английскому
    if name_ru == name_en and len(name_en) > 5:
        return False
    
    # 6. Проверка на минимальное количество русских букв
    russian_chars = len(re.findall(r'[а-яА-ЯёЁ]', name_ru))
    if russian_chars < 5:
        return False
    
    return True

# Отбираем качественные продукты, которых нет в популярной базе
quality_products = []
skipped_bad_translation = 0
skipped_duplicate = 0

for product in all_products:
    name_ru = product.get('nameRu', '').lower()
    
    # Пропускаем дубликаты
    if name_ru in popular_names:
        skipped_duplicate += 1
        continue
    
    # Проверяем качество перевода
    if not is_quality_translation(product):
        skipped_bad_translation += 1
        continue
    
    quality_products.append(product)

print(f"\nНайдено уникальных продуктов с качественным переводом: {len(quality_products)}")
print(f"Пропущено дубликатов: {skipped_duplicate}")
print(f"Пропущено с плохим переводом: {skipped_bad_translation}")

# Сортируем по категориям и названиям
quality_products.sort(key=lambda x: (x['category'], x['nameRu']))

# Сохраняем в новый файл
output_file = 'sources/gi-extended-database.json'
with open(output_file, 'w', encoding='utf-8') as f:
    json.dump(quality_products, f, ensure_ascii=False, indent=2)

print(f"\nСохранено в: {output_file}")

# Статистика по категориям
categories = {}
for p in quality_products:
    cat = p['category']
    categories[cat] = categories.get(cat, 0) + 1

print("\nРаспределение по категориям:")
for cat, count in sorted(categories.items(), key=lambda x: -x[1]):
    print(f"  {cat}: {count}")

# Примеры продуктов
print("\nПримеры отобранных продуктов:")
for i, p in enumerate(quality_products[:10]):
    print(f"  {i+1}. {p['nameRu']} (ГИ: {p['gi']}, категория: {p['category']})")
