#!/usr/bin/env python3
"""
Добавление продуктов из HTML таблицы, которых нет в существующих базах
"""

import json
import re

# Загружаем продукты из HTML таблицы
with open('parser/html_table_products.json', 'r', encoding='utf-8') as f:
    html_products = json.load(f)

# Загружаем существующие базы
with open('sources/gi-popular-products.json', 'r', encoding='utf-8') as f:
    popular = json.load(f)

with open('sources/gi-extended-database.json', 'r', encoding='utf-8') as f:
    extended = json.load(f)

# Создаем множество существующих названий (нормализованных)
def normalize_name(name):
    """Нормализация названия для сравнения"""
    name = name.lower().strip()
    # Убираем лишние пробелы
    name = re.sub(r'\s+', ' ', name)
    # Убираем знаки препинания
    name = re.sub(r'[^\w\s]', '', name)
    return name

existing_names = set()
for p in popular + extended:
    if p.get('nameRu'):
        existing_names.add(normalize_name(p['nameRu']))
    if p.get('name'):
        existing_names.add(normalize_name(p['name']))

print(f"Всего существующих продуктов: {len(existing_names)}")
print(f"Продуктов из HTML: {len(html_products)}")

# Категоризация продуктов по ГИ
def categorize_by_gi(gi):
    """Определение категории по ГИ"""
    if gi >= 70:
        return "Хлеб"  # Высокий ГИ - часто хлебобулочные
    elif gi >= 55:
        return "Крупы"  # Средний ГИ - крупы, каши
    elif gi >= 40:
        return "Фрукты"  # Средне-низкий ГИ - фрукты
    else:
        return "Овощи"  # Низкий ГИ - овощи, бобовые

# Улучшенная категоризация по ключевым словам
def smart_categorize(name, gi):
    """Умная категоризация по названию и ГИ"""
    name_lower = name.lower()
    
    # Фрукты
    if any(word in name_lower for word in ['яблок', 'груш', 'апельсин', 'банан', 'виноград', 'персик', 'абрикос', 'слив', 'вишн', 'черешн', 'киви', 'манго', 'ананас', 'арбуз', 'дын', 'клубник', 'малин', 'черник', 'смородин']):
        return "Фрукты"
    
    # Овощи
    if any(word in name_lower for word in ['капуст', 'морков', 'свекл', 'помидор', 'огурец', 'перец', 'лук', 'чеснок', 'кабачок', 'баклажан', 'тыкв', 'редис', 'редьк', 'салат', 'шпинат', 'брокколи', 'цветн']):
        return "Овощи"
    
    # Хлеб и выпечка
    if any(word in name_lower for word in ['хлеб', 'батон', 'булк', 'багет', 'лаваш', 'пита', 'тост', 'сухар', 'крекер', 'печень', 'пирог', 'кекс', 'булочк', 'ватрушк', 'пончик', 'круассан', 'блин', 'оладь']):
        return "Хлеб"
    
    # Крупы и каши
    if any(word in name_lower for word in ['каш', 'крупа', 'рис', 'гречк', 'овс', 'пшен', 'перлов', 'ячмен', 'манн', 'кукуруз', 'булгур', 'киноа', 'кус-кус']):
        return "Крупы"
    
    # Молочные продукты
    if any(word in name_lower for word in ['молок', 'кефир', 'йогурт', 'творог', 'сметан', 'сливк', 'сыр', 'масло', 'ряженк', 'простокваш']):
        return "Молочные продукты"
    
    # Мясо и рыба
    if any(word in name_lower for word in ['мяс', 'говяд', 'свинин', 'курин', 'индейк', 'утк', 'рыб', 'лосос', 'тунец', 'треск', 'сельд', 'скумбри', 'колбас', 'сосиск']):
        return "Мясо и рыба"
    
    # Сладости
    if any(word in name_lower for word in ['шоколад', 'конфет', 'мармелад', 'зефир', 'пастил', 'варень', 'джем', 'мед', 'сахар', 'карамель', 'ирис', 'халв', 'торт', 'пирожн']):
        return "Сладости"
    
    # Напитки
    if any(word in name_lower for word in ['сок', 'компот', 'морс', 'чай', 'кофе', 'какао', 'квас', 'лимонад', 'кола', 'пепси', 'фанта', 'спрайт']):
        return "Напитки"
    
    # Бобовые
    if any(word in name_lower for word in ['фасол', 'горох', 'чечевиц', 'нут', 'соя', 'боб']):
        return "Бобовые"
    
    # Орехи
    if any(word in name_lower for word in ['орех', 'миндал', 'фундук', 'кешью', 'фисташк', 'арахис', 'грецк']):
        return "Орехи"
    
    # Готовые блюда
    if any(word in name_lower for word in ['суп', 'борщ', 'щи', 'солянк', 'рагу', 'плов', 'пельмен', 'вареник', 'голубц', 'котлет', 'тефтел', 'биточк', 'шницел', 'отбивн']):
        return "Готовые блюда"
    
    # Снеки
    if any(word in name_lower for word in ['чипс', 'попкорн', 'сухарик', 'крекер', 'вафл']):
        return "Снеки"
    
    # По умолчанию - по ГИ
    return categorize_by_gi(gi)

# Отбираем новые продукты
new_products = []
for p in html_products:
    name = p['name']
    normalized = normalize_name(name)
    
    if normalized not in existing_names and len(name) >= 3:
        # Создаем продукт в нужном формате
        product = {
            "name": name,
            "nameRu": name,
            "category": smart_categorize(name, p['gi']),
            "gi": p['gi'],
            "gl": None,
            "carbs": None
        }
        new_products.append(product)
        existing_names.add(normalized)

print(f"\nНайдено новых продуктов: {len(new_products)}")

# Сортируем по категориям
new_products.sort(key=lambda x: (x['category'], x['nameRu']))

# Статистика по категориям
categories = {}
for p in new_products:
    cat = p['category']
    categories[cat] = categories.get(cat, 0) + 1

print("\nРаспределение по категориям:")
for cat, count in sorted(categories.items(), key=lambda x: -x[1]):
    print(f"  {cat}: {count}")

# Примеры
print("\nПримеры новых продуктов:")
for i, p in enumerate(new_products[:15]):
    print(f"  {i+1}. {p['nameRu']} (ГИ: {p['gi']}, категория: {p['category']})")

# Сохраняем в новый файл
if new_products:
    output_file = 'sources/gi-html-products.json'
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(new_products, f, ensure_ascii=False, indent=2)
    
    print(f"\nСохранено в: {output_file}")
else:
    print("\nНет новых продуктов для добавления")
