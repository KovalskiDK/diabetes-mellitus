#!/usr/bin/env python3
"""
Расширение базы популярных продуктов - добавление готовых блюд и больше продуктов
"""

import json

# Загружаем существующую базу
with open('gi-popular-products.json', 'r', encoding='utf-8') as f:
    products = json.load(f)

print(f"Загружено продуктов: {len(products)}")

# Добавляем готовые блюда и популярные продукты
additional_products = [
    # Готовые блюда - русская кухня
    {"name": "Borscht", "nameRu": "Борщ", "category": "Готовые блюда", "gi": 35, "gl": 7.0, "carbs": 12.0},
    {"name": "Pelmeni", "nameRu": "Пельмени", "category": "Готовые блюда", "gi": 60, "gl": 18.0, "carbs": 30.0},
    {"name": "Vareniki with potato", "nameRu": "Вареники с картошкой", "category": "Готовые блюда", "gi": 66, "gl": 20.0, "carbs": 30.0},
    {"name": "Vareniki with cottage cheese", "nameRu": "Вареники с творогом", "category": "Готовые блюда", "gi": 50, "gl": 12.0, "carbs": 24.0},
    {"name": "Blini with jam", "nameRu": "Блины с вареньем", "category": "Готовые блюда", "gi": 69, "gl": 25.0, "carbs": 36.0},
    {"name": "Blini with cottage cheese", "nameRu": "Блины с творогом", "category": "Готовые блюда", "gi": 58, "gl": 18.0, "carbs": 31.0},
    {"name": "Olivier salad", "nameRu": "Салат Оливье", "category": "Готовые блюда", "gi": 52, "gl": 8.0, "carbs": 15.0},
    {"name": "Vinegret salad", "nameRu": "Винегрет", "category": "Готовые блюда", "gi": 55, "gl": 9.0, "carbs": 16.0},
    {"name": "Shchi", "nameRu": "Щи", "category": "Готовые блюда", "gi": 30, "gl": 5.0, "carbs": 10.0},
    {"name": "Solyanka soup", "nameRu": "Солянка", "category": "Готовые блюда", "gi": 35, "gl": 6.0, "carbs": 11.0},
    {"name": "Chicken soup", "nameRu": "Куриный суп", "category": "Готовые блюда", "gi": 38, "gl": 7.0, "carbs": 12.0},
    {"name": "Mushroom soup", "nameRu": "Грибной суп", "category": "Готовые блюда", "gi": 32, "gl": 5.0, "carbs": 10.0},
    {"name": "Beef stroganoff", "nameRu": "Бефстроганов", "category": "Готовые блюда", "gi": 45, "gl": 8.0, "carbs": 12.0},
    {"name": "Chicken cutlets", "nameRu": "Куриные котлеты", "category": "Готовые блюда", "gi": 50, "gl": 9.0, "carbs": 15.0},
    {"name": "Fish cutlets", "nameRu": "Рыбные котлеты", "category": "Готовые блюда", "gi": 48, "gl": 8.0, "carbs": 14.0},
    {"name": "Stuffed cabbage rolls", "nameRu": "Голубцы", "category": "Готовые блюда", "gi": 55, "gl": 12.0, "carbs": 22.0},
    {"name": "Stuffed peppers", "nameRu": "Фаршированные перцы", "category": "Готовые блюда", "gi": 52, "gl": 11.0, "carbs": 21.0},
    
    # Готовые блюда - международная кухня
    {"name": "Pasta carbonara", "nameRu": "Паста карбонара", "category": "Готовые блюда", "gi": 55, "gl": 22.0, "carbs": 40.0},
    {"name": "Pasta bolognese", "nameRu": "Паста болоньезе", "category": "Готовые блюда", "gi": 52, "gl": 20.0, "carbs": 38.0},
    {"name": "Risotto", "nameRu": "Ризотто", "category": "Готовые блюда", "gi": 69, "gl": 28.0, "carbs": 40.0},
    {"name": "Paella", "nameRu": "Паэлья", "category": "Готовые блюда", "gi": 58, "gl": 23.0, "carbs": 40.0},
    {"name": "Chicken curry with rice", "nameRu": "Курица карри с рисом", "category": "Готовые блюда", "gi": 65, "gl": 26.0, "carbs": 40.0},
    {"name": "Pad Thai", "nameRu": "Пад Тай", "category": "Готовые блюда", "gi": 59, "gl": 24.0, "carbs": 41.0},
    {"name": "Ramen", "nameRu": "Рамен", "category": "Готовые блюда", "gi": 55, "gl": 22.0, "carbs": 40.0},
    {"name": "Pho", "nameRu": "Фо", "category": "Готовые блюда", "gi": 52, "gl": 21.0, "carbs": 40.0},
    {"name": "Falafel", "nameRu": "Фалафель", "category": "Готовые блюда", "gi": 32, "gl": 8.0, "carbs": 25.0},
    {"name": "Hummus", "nameRu": "Хумус", "category": "Готовые блюда", "gi": 6, "gl": 1.0, "carbs": 14.0},
    {"name": "Shawarma", "nameRu": "Шаурма", "category": "Готовые блюда", "gi": 60, "gl": 18.0, "carbs": 30.0},
    {"name": "Kebab", "nameRu": "Кебаб", "category": "Готовые блюда", "gi": 55, "gl": 16.0, "carbs": 29.0},
    {"name": "Gyros", "nameRu": "Гирос", "category": "Готовые блюда", "gi": 58, "gl": 17.0, "carbs": 29.0},
    
    # Салаты
    {"name": "Caesar salad", "nameRu": "Салат Цезарь", "category": "Готовые блюда", "gi": 40, "gl": 6.0, "carbs": 15.0},
    {"name": "Greek salad", "nameRu": "Греческий салат", "category": "Готовые блюда", "gi": 15, "gl": 2.0, "carbs": 8.0},
    {"name": "Caprese salad", "nameRu": "Салат Капрезе", "category": "Готовые блюда", "gi": 15, "gl": 1.0, "carbs": 5.0},
    {"name": "Coleslaw", "nameRu": "Салат Коулслоу", "category": "Готовые блюда", "gi": 35, "gl": 4.0, "carbs": 11.0},
    {"name": "Tuna salad", "nameRu": "Салат с тунцом", "category": "Готовые блюда", "gi": 25, "gl": 3.0, "carbs": 8.0},
    
    # Завтраки
    {"name": "Omelette", "nameRu": "Омлет", "category": "Завтраки", "gi": 0, "gl": 0.0, "carbs": 2.0},
    {"name": "Scrambled eggs", "nameRu": "Яичница-болтунья", "category": "Завтраки", "gi": 0, "gl": 0.0, "carbs": 2.0},
    {"name": "Fried eggs", "nameRu": "Яичница глазунья", "category": "Завтраки", "gi": 0, "gl": 0.0, "carbs": 1.0},
    {"name": "Porridge with milk", "nameRu": "Каша на молоке", "category": "Завтраки", "gi": 60, "gl": 14.0, "carbs": 24.0},
    {"name": "Oatmeal with fruits", "nameRu": "Овсянка с фруктами", "category": "Завтраки", "gi": 58, "gl": 15.0, "carbs": 26.0},
    {"name": "Granola", "nameRu": "Гранола", "category": "Завтраки", "gi": 65, "gl": 16.0, "carbs": 25.0},
    {"name": "French toast", "nameRu": "Французские тосты", "category": "Завтраки", "gi": 69, "gl": 18.0, "carbs": 26.0},
    {"name": "Bagel with cream cheese", "nameRu": "Бублик со сливочным сыром", "category": "Завтраки", "gi": 72, "gl": 25.0, "carbs": 35.0},
    {"name": "Avocado toast", "nameRu": "Тост с авокадо", "category": "Завтраки", "gi": 55, "gl": 8.0, "carbs": 15.0},
    
    # Дополнительные овощи
    {"name": "Avocado", "nameRu": "Авокадо", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 9.0},
    {"name": "Olives", "nameRu": "Оливки", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 6.0},
    {"name": "Pickles", "nameRu": "Соленые огурцы", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Sauerkraut", "nameRu": "Квашеная капуста", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 4.0},
    {"name": "Kimchi", "nameRu": "Кимчи", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 4.0},
    
    # Дополнительные фрукты
    {"name": "Tangerine", "nameRu": "Мандарин", "category": "Фрукты", "gi": 42, "gl": 5.0, "carbs": 13.0},
    {"name": "Nectarine", "nameRu": "Нектарин", "category": "Фрукты", "gi": 43, "gl": 5.0, "carbs": 11.0},
    {"name": "Passion fruit", "nameRu": "Маракуйя", "category": "Фрукты", "gi": 30, "gl": 4.0, "carbs": 13.0},
    {"name": "Dragon fruit", "nameRu": "Питайя", "category": "Фрукты", "gi": 48, "gl": 6.0, "carbs": 13.0},
    {"name": "Lychee", "nameRu": "Личи", "category": "Фрукты", "gi": 50, "gl": 8.0, "carbs": 17.0},
    {"name": "Guava", "nameRu": "Гуава", "category": "Фрукты", "gi": 12, "gl": 1.0, "carbs": 14.0},
    
    # Каши и гарниры
    {"name": "Mashed potatoes with milk", "nameRu": "Картофельное пюре с молоком", "category": "Готовые блюда", "gi": 85, "gl": 17.0, "carbs": 20.0},
    {"name": "Potato salad", "nameRu": "Картофельный салат", "category": "Готовые блюда", "gi": 70, "gl": 14.0, "carbs": 20.0},
    {"name": "Rice pilaf", "nameRu": "Плов", "category": "Готовые блюда", "gi": 65, "gl": 26.0, "carbs": 40.0},
    {"name": "Buckwheat with mushrooms", "nameRu": "Гречка с грибами", "category": "Готовые блюда", "gi": 50, "gl": 15.0, "carbs": 30.0},
    {"name": "Polenta", "nameRu": "Полента", "category": "Крупы", "gi": 68, "gl": 20.0, "carbs": 30.0},
    
    # Мясные блюда
    {"name": "Steak", "nameRu": "Стейк", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Pork chop", "nameRu": "Свиная отбивная", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Lamb chop", "nameRu": "Бараньи отбивные", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Meatballs", "nameRu": "Фрикадельки", "category": "Готовые блюда", "gi": 52, "gl": 10.0, "carbs": 15.0},
    {"name": "Meatloaf", "nameRu": "Мясной рулет", "category": "Готовые блюда", "gi": 50, "gl": 9.0, "carbs": 14.0},
    {"name": "Sausages", "nameRu": "Сосиски", "category": "Мясо и рыба", "gi": 28, "gl": 1.0, "carbs": 3.0},
    {"name": "Bacon", "nameRu": "Бекон", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 1.0},
    {"name": "Ham", "nameRu": "Ветчина", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 1.0},
    
    # Рыбные блюда
    {"name": "Fish sticks", "nameRu": "Рыбные палочки", "category": "Мясо и рыба", "gi": 38, "gl": 7.0, "carbs": 18.0},
    {"name": "Grilled fish", "nameRu": "Рыба на гриле", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Baked fish", "nameRu": "Рыба запеченная", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Shrimp", "nameRu": "Креветки", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Squid", "nameRu": "Кальмары", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 3.0},
    {"name": "Mussels", "nameRu": "Мидии", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 7.0},
    {"name": "Crab", "nameRu": "Краб", "category": "Мясо и рыба", "gi": 0, "gl": 0.0, "carbs": 0.0},
    
    # Десерты и выпечка
    {"name": "Apple pie", "nameRu": "Яблочный пирог", "category": "Сладости", "gi": 44, "gl": 13.0, "carbs": 29.0},
    {"name": "Cherry pie", "nameRu": "Вишневый пирог", "category": "Сладости", "gi": 47, "gl": 14.0, "carbs": 30.0},
    {"name": "Pumpkin pie", "nameRu": "Тыквенный пирог", "category": "Сладости", "gi": 75, "gl": 18.0, "carbs": 24.0},
    {"name": "Cheesecake New York", "nameRu": "Чизкейк Нью-Йорк", "category": "Сладости", "gi": 55, "gl": 16.0, "carbs": 29.0},
    {"name": "Chocolate cake", "nameRu": "Шоколадный торт", "category": "Сладости", "gi": 38, "gl": 20.0, "carbs": 52.0},
    {"name": "Carrot cake", "nameRu": "Морковный пирог", "category": "Сладости", "gi": 36, "gl": 16.0, "carbs": 44.0},
    {"name": "Banana bread", "nameRu": "Банановый хлеб", "category": "Сладости", "gi": 47, "gl": 14.0, "carbs": 30.0},
    {"name": "Cinnamon roll", "nameRu": "Булочка с корицей", "category": "Сладости", "gi": 62, "gl": 20.0, "carbs": 32.0},
    {"name": "Eclair", "nameRu": "Эклер", "category": "Сладости", "gi": 42, "gl": 12.0, "carbs": 29.0},
    {"name": "Profiteroles", "nameRu": "Профитроли", "category": "Сладости", "gi": 45, "gl": 13.0, "carbs": 29.0},
    {"name": "Macarons", "nameRu": "Макаруны", "category": "Сладости", "gi": 55, "gl": 16.0, "carbs": 29.0},
    {"name": "Pavlova", "nameRu": "Павлова", "category": "Сладости", "gi": 65, "gl": 19.0, "carbs": 29.0},
    
    # Напитки и смузи
    {"name": "Green smoothie", "nameRu": "Зеленый смузи", "category": "Напитки", "gi": 30, "gl": 5.0, "carbs": 17.0},
    {"name": "Berry smoothie", "nameRu": "Ягодный смузи", "category": "Напитки", "gi": 42, "gl": 10.0, "carbs": 24.0},
    {"name": "Banana smoothie", "nameRu": "Банановый смузи", "category": "Напитки", "gi": 50, "gl": 12.0, "carbs": 24.0},
    {"name": "Milkshake", "nameRu": "Молочный коктейль", "category": "Напитки", "gi": 46, "gl": 14.0, "carbs": 30.0},
    {"name": "Iced coffee", "nameRu": "Холодный кофе", "category": "Напитки", "gi": 40, "gl": 8.0, "carbs": 20.0},
    {"name": "Cappuccino", "nameRu": "Капучино", "category": "Напитки", "gi": 35, "gl": 4.0, "carbs": 12.0},
    {"name": "Latte", "nameRu": "Латте", "category": "Напитки", "gi": 35, "gl": 5.0, "carbs": 14.0},
    {"name": "Mocha", "nameRu": "Мокко", "category": "Напитки", "gi": 45, "gl": 10.0, "carbs": 22.0},
    {"name": "Kombucha", "nameRu": "Комбуча", "category": "Напитки", "gi": 30, "gl": 3.0, "carbs": 10.0},
    {"name": "Kvass", "nameRu": "Квас", "category": "Напитки", "gi": 30, "gl": 3.0, "carbs": 10.0},
    
    # Соусы и приправы
    {"name": "Ketchup", "nameRu": "Кетчуп", "category": "Соусы", "gi": 55, "gl": 4.0, "carbs": 7.0},
    {"name": "Mustard", "nameRu": "Горчица", "category": "Соусы", "gi": 55, "gl": 1.0, "carbs": 2.0},
    {"name": "Mayonnaise", "nameRu": "Майонез", "category": "Соусы", "gi": 0, "gl": 0.0, "carbs": 1.0},
    {"name": "BBQ sauce", "nameRu": "Соус барбекю", "category": "Соусы", "gi": 55, "gl": 5.0, "carbs": 9.0},
    {"name": "Soy sauce", "nameRu": "Соевый соус", "category": "Соусы", "gi": 20, "gl": 1.0, "carbs": 5.0},
    {"name": "Teriyaki sauce", "nameRu": "Соус терияки", "category": "Соусы", "gi": 55, "gl": 6.0, "carbs": 11.0},
    {"name": "Sweet and sour sauce", "nameRu": "Кисло-сладкий соус", "category": "Соусы", "gi": 60, "gl": 8.0, "carbs": 13.0},
    {"name": "Tomato sauce", "nameRu": "Томатный соус", "category": "Соусы", "gi": 35, "gl": 3.0, "carbs": 8.0},
    {"name": "Pesto", "nameRu": "Песто", "category": "Соусы", "gi": 15, "gl": 1.0, "carbs": 5.0},
    {"name": "Ranch dressing", "nameRu": "Соус ранч", "category": "Соусы", "gi": 15, "gl": 1.0, "carbs": 3.0},
    {"name": "Caesar dressing", "nameRu": "Соус цезарь", "category": "Соусы", "gi": 15, "gl": 1.0, "carbs": 3.0},
    
    # Закуски
    {"name": "Spring rolls", "nameRu": "Спринг-роллы", "category": "Снеки", "gi": 70, "gl": 14.0, "carbs": 20.0},
    {"name": "Samosa", "nameRu": "Самоса", "category": "Снеки", "gi": 65, "gl": 13.0, "carbs": 20.0},
    {"name": "Onion rings", "nameRu": "Луковые кольца", "category": "Снеки", "gi": 72, "gl": 15.0, "carbs": 21.0},
    {"name": "Mozzarella sticks", "nameRu": "Сырные палочки моцарелла", "category": "Снеки", "gi": 55, "gl": 11.0, "carbs": 20.0},
    {"name": "Chicken wings", "nameRu": "Куриные крылышки", "category": "Снеки", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Buffalo wings", "nameRu": "Крылышки баффало", "category": "Снеки", "gi": 5, "gl": 0.0, "carbs": 2.0},
    
    # Дополнительные молочные продукты
    {"name": "Whipped cream", "nameRu": "Взбитые сливки", "category": "Молочные продукты", "gi": 15, "gl": 1.0, "carbs": 7.0},
    {"name": "Condensed milk", "nameRu": "Сгущенное молоко", "category": "Молочные продукты", "gi": 61, "gl": 34.0, "carbs": 56.0},
    {"name": "Evaporated milk", "nameRu": "Сгущенное молоко без сахара", "category": "Молочные продукты", "gi": 40, "gl": 4.0, "carbs": 10.0},
    {"name": "Cream", "nameRu": "Сливки", "category": "Молочные продукты", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Half and half", "nameRu": "Сливки 10%", "category": "Молочные продукты", "gi": 15, "gl": 1.0, "carbs": 4.0},
    
    # Дополнительные орехи и семена
    {"name": "Peanut butter", "nameRu": "Арахисовая паста", "category": "Орехи", "gi": 14, "gl": 1.0, "carbs": 20.0},
    {"name": "Almond butter", "nameRu": "Миндальная паста", "category": "Орехи", "gi": 0, "gl": 0.0, "carbs": 19.0},
    {"name": "Tahini", "nameRu": "Тахини", "category": "Орехи", "gi": 15, "gl": 1.0, "carbs": 21.0},
    {"name": "Nutella", "nameRu": "Нутелла", "category": "Сладости", "gi": 33, "gl": 18.0, "carbs": 57.0},
    
    # Дополнительные бобовые
    {"name": "Baked beans", "nameRu": "Фасоль в томатном соусе", "category": "Бобовые", "gi": 40, "gl": 7.0, "carbs": 18.0},
    {"name": "Refried beans", "nameRu": "Жареная фасоль", "category": "Бобовые", "gi": 38, "gl": 8.0, "carbs": 21.0},
    {"name": "Bean soup", "nameRu": "Фасолевый суп", "category": "Готовые блюда", "gi": 35, "gl": 7.0, "carbs": 20.0},
    {"name": "Lentil soup", "nameRu": "Чечевичный суп", "category": "Готовые блюда", "gi": 30, "gl": 6.0, "carbs": 20.0},
    
    # Дополнительные крупы
    {"name": "Semolina porridge", "nameRu": "Манная каша", "category": "Крупы", "gi": 66, "gl": 16.0, "carbs": 24.0},
    {"name": "Rice pudding", "nameRu": "Рисовая каша", "category": "Крупы", "gi": 69, "gl": 17.0, "carbs": 25.0},
    {"name": "Corn grits", "nameRu": "Кукурузная каша", "category": "Крупы", "gi": 69, "gl": 20.0, "carbs": 29.0},
    {"name": "Wheat porridge", "nameRu": "Пшеничная каша", "category": "Крупы", "gi": 67, "gl": 19.0, "carbs": 28.0},
]

# Объединяем с существующими
products.extend(additional_products)

# Сортируем по категориям и названиям
products.sort(key=lambda x: (x['category'], x['nameRu']))

# Сохраняем обновленную базу
with open('gi-popular-products.json', 'w', encoding='utf-8') as f:
    json.dump(products, f, ensure_ascii=False, indent=2)

print(f"\nОбновленная база: {len(products)} продуктов")

# Статистика по категориям
categories = {}
for p in products:
    cat = p['category']
    categories[cat] = categories.get(cat, 0) + 1

print("\nРаспределение по категориям:")
for cat, count in sorted(categories.items()):
    print(f"  {cat}: {count}")
