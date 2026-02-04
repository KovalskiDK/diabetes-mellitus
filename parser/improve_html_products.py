#!/usr/bin/env python3
"""
Улучшение базы gi-html-products:
1. Добавление английских названий
2. Расчет гликемической нагрузки (ГН)
3. Добавление типичных значений углеводов
"""

import json

# Загружаем текущую базу
with open('sources/gi-html-products.json', 'r', encoding='utf-8') as f:
    products = json.load(f)

# Словарь переводов и типичных значений углеводов (г на 100г продукта)
# Формат: "русское название": ("English name", углеводы_на_100г)
product_data = {
    # Бобовые
    "горох (лущеный)": ("Split peas", 53.0),
    "горох зелёный (свежий)": ("Green peas, fresh", 13.0),
    "соя (зерно)": ("Soybeans", 30.0),
    
    # Крупы
    "кукурузная каша": ("Corn porridge", 19.0),
    "манная каша": ("Semolina porridge", 16.0),
    "мюсли": ("Muesli", 66.0),
    "овсяная каша (на воде)": ("Oatmeal, water", 12.0),
    "овсяные хлопья": ("Oat flakes", 66.0),
    "перловая каша": ("Pearl barley porridge", 23.0),
    "пшенная каша": ("Millet porridge", 17.0),
    "рис белый (вареный)": ("White rice, boiled", 28.0),
    "рис коричневый (вареный)": ("Brown rice, boiled", 23.0),
    "гречневая каша": ("Buckwheat porridge", 20.0),
    "кус-кус": ("Couscous", 23.0),
    "булгур": ("Bulgur", 19.0),
    "киноа": ("Quinoa", 21.0),
    "ячневая каша": ("Barley porridge", 16.0),
    "кукурузные хлопья": ("Corn flakes", 81.0),
    
    # Фрукты
    "абрикосы": ("Apricots", 9.0),
    "ананас": ("Pineapple", 11.0),
    "апельсины": ("Oranges", 8.0),
    "арбуз": ("Watermelon", 8.0),
    "бананы": ("Bananas", 21.0),
    "виноград": ("Grapes", 16.0),
    "вишня": ("Cherries", 11.0),
    "грейпфрут": ("Grapefruit", 6.5),
    "груши": ("Pears", 10.0),
    "дыня": ("Melon", 7.0),
    "киви": ("Kiwi", 10.0),
    "клубника": ("Strawberries", 6.0),
    "курага": ("Dried apricots", 51.0),
    "лимон": ("Lemon", 3.0),
    "малина": ("Raspberries", 8.0),
    "манго": ("Mango", 15.0),
    "мандарины": ("Mandarins", 8.0),
    "персики": ("Peaches", 9.0),
    "сливы": ("Plums", 9.0),
    "смородина черная": ("Black currants", 8.0),
    "финики": ("Dates", 75.0),
    "хурма": ("Persimmon", 15.0),
    "черешня": ("Sweet cherries", 11.0),
    "черника": ("Blueberries", 8.0),
    "яблоки": ("Apples", 10.0),
    "изюм": ("Raisins", 66.0),
    "чернослив": ("Prunes", 58.0),
    
    # Овощи
    "баклажаны": ("Eggplants", 4.5),
    "брокколи": ("Broccoli", 4.0),
    "кабачки": ("Zucchini", 4.0),
    "капуста белокочанная": ("White cabbage", 5.0),
    "капуста цветная": ("Cauliflower", 4.0),
    "картофель (вареный)": ("Potatoes, boiled", 16.0),
    "картофель (жареный)": ("Potatoes, fried", 23.0),
    "морковь (вареная)": ("Carrots, boiled", 5.0),
    "морковь (сырая)": ("Carrots, raw", 7.0),
    "огурцы": ("Cucumbers", 3.0),
    "перец сладкий": ("Bell peppers", 5.0),
    "помидоры": ("Tomatoes", 4.0),
    "свекла (вареная)": ("Beets, boiled", 8.0),
    "тыква": ("Pumpkin", 4.5),
    
    # Хлеб и выпечка
    "багет": ("Baguette", 51.0),
    "батон": ("White bread loaf", 50.0),
    "белый хлеб": ("White bread", 49.0),
    "бородинский хлеб": ("Borodino bread", 40.0),
    "булочка сдобная": ("Sweet bun", 55.0),
    "лаваш": ("Lavash", 56.0),
    "пита": ("Pita bread", 55.0),
    "ржаной хлеб": ("Rye bread", 40.0),
    "сухари": ("Rusks", 72.0),
    "тосты": ("Toast", 50.0),
    "хлеб белый (тостовый)": ("White toast bread", 50.0),
    "хлеб зерновой": ("Whole grain bread", 43.0),
    "хлеб из муки грубого помола": ("Whole wheat bread", 42.0),
    "хлеб пшеничный": ("Wheat bread", 48.0),
    "хлеб с отрубями": ("Bran bread", 45.0),
    "хлебцы": ("Crispbread", 70.0),
    "черный хлеб": ("Black bread", 40.0),
    "блины": ("Pancakes", 32.0),
    "вафли": ("Waffles", 65.0),
    "круассан": ("Croissant", 45.0),
    "оладьи": ("Fritters", 38.0),
    "печенье песочное": ("Shortbread cookies", 65.0),
    "пирожное": ("Pastry", 55.0),
    "пончики": ("Donuts", 51.0),
    "сушки": ("Sushki", 73.0),
    "торт": ("Cake", 50.0),
    
    # Молочные продукты
    "йогурт натуральный": ("Natural yogurt", 4.0),
    "йогурт фруктовый": ("Fruit yogurt", 15.0),
    "кефир": ("Kefir", 4.0),
    "молоко": ("Milk", 5.0),
    "мороженое": ("Ice cream", 22.0),
    "сливки": ("Cream", 4.0),
    "сметана": ("Sour cream", 3.0),
    "творог": ("Cottage cheese", 3.0),
    
    # Сладости
    "варенье": ("Jam", 70.0),
    "зефир": ("Marshmallow", 79.0),
    "карамель": ("Caramel", 88.0),
    "мармелад": ("Marmalade", 76.0),
    "мед": ("Honey", 82.0),
    "сахар": ("Sugar", 99.0),
    "халва": ("Halva", 54.0),
    "шоколад молочный": ("Milk chocolate", 54.0),
    "шоколад темный": ("Dark chocolate", 48.0),
    "конфеты": ("Candies", 75.0),
    
    # Напитки
    "квас": ("Kvass", 5.0),
    "кока-кола": ("Coca-Cola", 10.0),
    "лимонад": ("Lemonade", 11.0),
    "пепси": ("Pepsi", 11.0),
    "сок апельсиновый": ("Orange juice", 10.0),
    "сок яблочный": ("Apple juice", 10.0),
    
    # Снеки
    "попкорн": ("Popcorn", 63.0),
    
    # Готовые блюда
    "пицца": ("Pizza", 26.0),
    
    # Мясо
    "колбаса вареная": ("Boiled sausage", 2.0),
    "сосиски говяжьи": ("Beef sausages", 1.5),
    
    # Дополнительные продукты
    "горох зелёный (свежий)": ("Green peas, fresh", 13.0),
    "суп фасолевый": ("Bean soup", 12.0),
    "фасоль (стручковая)": ("Green beans", 7.0),
    "чечевица (зерно)": ("Lentils", 20.0),
    "пельмени отварные": ("Dumplings, boiled", 16.0),
    "голубика": ("Blueberries", 8.0),
    "каша гречневая (из крупы ядрица)": ("Buckwheat porridge", 20.0),
    "каша из овсяных хлопьев геркулес": ("Oatmeal, Hercules", 14.0),
    "каша манная": ("Semolina porridge", 16.0),
    "каша овсяная": ("Oatmeal", 12.0),
    "каша перловая": ("Pearl barley porridge", 23.0),
    "каша пшеничная": ("Wheat porridge", 16.0),
    "каша пшенная": ("Millet porridge", 17.0),
    "каша рисовая": ("Rice porridge", 17.0),
    "крупа гречневая (ядрица)": ("Buckwheat groats", 62.0),
    "крупа овсяная": ("Oat groats", 55.0),
    "кукуруза консервированная": ("Corn, canned", 15.0),
    "кукуруза сладкая": ("Sweet corn", 19.0),
    "макароны отварные": ("Pasta, boiled", 25.0),
    "мука пшеничная в/с": ("Wheat flour", 70.0),
    "пряники заварные": ("Gingerbread", 70.0),
    "рис (зерно)": ("Rice", 78.0),
    "йогурт 3,2% сладкий": ("Yogurt 3.2%, sweet", 15.0),
    "масло арахисовое": ("Peanut butter", 20.0),
    "сырки глазированные 27,7% жирности": ("Glazed cheese curds", 32.0),
    "сок томатный": ("Tomato juice", 4.0),
    "алыча": ("Cherry plum", 7.0),
    "брусника": ("Lingonberry", 8.0),
    "гранат": ("Pomegranate", 14.0),
    "зелёный горошек (консервы)": ("Green peas, canned", 9.0),
    "икра из баклажанов (консервы)": ("Eggplant caviar", 7.0),
    "икра кабачковая (консервы)": ("Zucchini caviar", 9.0),
    "инжир свежий": ("Fresh figs", 13.0),
    "лук зелёный (перо)": ("Green onions", 4.0),
    "лук репчатый": ("Onions", 9.0),
    "морковь отварная": ("Carrots, boiled", 5.0),
    "огурец": ("Cucumber", 3.0),
    "помидор (томат)": ("Tomato", 4.0),
    "редис": ("Radish", 3.0),
    "репа": ("Turnip", 5.0),
    "салат листовой (зелень)": ("Lettuce", 2.0),
    "свекла отварная": ("Beets, boiled", 8.0),
    "семена подсолнечника (семечки)": ("Sunflower seeds", 11.0),
    "укроп (зелень)": ("Dill", 4.0),
    "чеснок": ("Garlic", 30.0),
    "щавель (зелень)": ("Sorrel", 3.0),
    "грецкий орех": ("Walnuts", 11.0),
    "пастила": ("Pastila", 80.0),
    "шоколад горький": ("Dark chocolate", 48.0),
    "груша": ("Pear", 10.0),
    "земляника": ("Wild strawberry", 6.0),
    "крыжовник": ("Gooseberry", 9.0),
    "слива": ("Plum", 9.0),
    "смородина красная": ("Red currants", 8.0),
    "сок абрикосовый": ("Apricot juice", 12.0),
    "сок вишнёвый": ("Cherry juice", 11.0),
    "сок персиковый": ("Peach juice", 12.0),
    "томатная паста": ("Tomato paste", 19.0),
    "запеканка картофельная": ("Potato casserole", 14.0),
    "картофель жареный": ("Fried potatoes", 23.0),
    "масса творожная 16,5% жирности": ("Cottage cheese mass", 15.0),
    "печенье сдобное": ("Butter cookies", 68.0),
    "хлеб бородинский": ("Borodino bread", 40.0),
    "хлеб цельнозерновой": ("Whole grain bread", 43.0),
}

# Функция для нормализации названия
def normalize_name(name):
    return name.lower().strip().replace('ё', 'е')

# Функция расчета ГН: ГН = (ГИ × углеводы) / 100
def calculate_gl(gi, carbs):
    if gi is None or carbs is None:
        return None
    return round((gi * carbs) / 100, 1)

# Обновляем продукты
updated_count = 0
for product in products:
    name_ru = product['nameRu']
    normalized = normalize_name(name_ru)
    
    # Ищем данные для продукта
    if normalized in product_data:
        english_name, carbs = product_data[normalized]
        product['name'] = english_name
        product['carbs'] = carbs
        product['gl'] = calculate_gl(product['gi'], carbs)
        updated_count += 1
    else:
        # Если точного совпадения нет, пробуем найти частичное
        found = False
        for key, (eng, carbs) in product_data.items():
            if key in normalized or normalized in key:
                product['name'] = eng
                product['carbs'] = carbs
                product['gl'] = calculate_gl(product['gi'], carbs)
                updated_count += 1
                found = True
                break
        
        if not found:
            # Оставляем как есть, но предупреждаем
            print(f"WARNING: Не найдены данные для: {name_ru}")

print(f"\nОбновлено продуктов: {updated_count} из {len(products)}")

# Сортируем по категориям и названиям
products.sort(key=lambda x: (x['category'], x['nameRu']))

# Статистика
complete = sum(1 for p in products if p['gl'] is not None)
incomplete = len(products) - complete

print(f"Продуктов с полными данными (ГИ + ГН + углеводы): {complete}")
print(f"Продуктов без данных: {incomplete}")

# Примеры обновленных продуктов
print("\nПримеры обновленных продуктов:")
for i, p in enumerate([p for p in products if p['gl'] is not None][:10]):
    print(f"  {i+1}. {p['nameRu']} ({p['name']}) - ГИ: {p['gi']}, ГН: {p['gl']}, Углеводы: {p['carbs']}г")

# Сохраняем обновленную базу
with open('sources/gi-html-products.json', 'w', encoding='utf-8') as f:
    json.dump(products, f, ensure_ascii=False, indent=2)

print(f"\nСохранено в: sources/gi-html-products.json")
