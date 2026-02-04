#!/usr/bin/env python3
"""
Создание базы популярных продуктов с качественными русскими переводами
Источники: Sydney University GI Database, Harvard Health, USDA
"""

import json

# База популярных продуктов с достоверными данными ГИ
products = []

# Уже созданные 120 продуктов загружаем
with open('../gi-popular-products.json', 'r', encoding='utf-8') as f:
    products = json.load(f)

print(f"Загружено базовых продуктов: {len(products)}")

# Добавляем еще популярные продукты для достижения 500+

# Дополнительные фрукты и ягоды
additional_fruits = [
    {"name": "Melon, cantaloupe", "nameRu": "Дыня канталупа", "category": "Фрукты", "gi": 65, "gl": 4.0, "carbs": 8.0},
    {"name": "Melon, honeydew", "nameRu": "Дыня медовая", "category": "Фрукты", "gi": 65, "gl": 6.0, "carbs": 9.0},
    {"name": "Papaya, raw", "nameRu": "Папайя свежая", "category": "Фрукты", "gi": 59, "gl": 10.0, "carbs": 11.0},
    {"name": "Lemon, raw", "nameRu": "Лимон свежий", "category": "Фрукты", "gi": 20, "gl": 1.0, "carbs": 9.0},
    {"name": "Lime, raw", "nameRu": "Лайм свежий", "category": "Фрукты", "gi": 20, "gl": 1.0, "carbs": 9.0},
    {"name": "Pomegranate", "nameRu": "Гранат", "category": "Фрукты", "gi": 35, "gl": 6.0, "carbs": 19.0},
    {"name": "Persimmon", "nameRu": "Хурма", "category": "Фрукты", "gi": 50, "gl": 9.0, "carbs": 18.0},
    {"name": "Fig, fresh", "nameRu": "Инжир свежий", "category": "Фрукты", "gi": 35, "gl": 7.0, "carbs": 19.0},
    {"name": "Figs, dried", "nameRu": "Инжир сушеный", "category": "Фрукты", "gi": 61, "gl": 16.0, "carbs": 64.0},
    {"name": "Apricot, dried", "nameRu": "Курага", "category": "Фрукты", "gi": 30, "gl": 9.0, "carbs": 30.0},
    {"name": "Cranberries, dried", "nameRu": "Клюква сушеная", "category": "Фрукты", "gi": 64, "gl": 11.0, "carbs": 82.0},
    {"name": "Blackberries, raw", "nameRu": "Ежевика свежая", "category": "Фрукты", "gi": 25, "gl": 2.0, "carbs": 10.0},
    {"name": "Gooseberries", "nameRu": "Крыжовник", "category": "Фрукты", "gi": 15, "gl": 1.0, "carbs": 10.0},
    {"name": "Currants, black", "nameRu": "Смородина черная", "category": "Фрукты", "gi": 15, "gl": 1.0, "carbs": 15.0},
    {"name": "Currants, red", "nameRu": "Смородина красная", "category": "Фрукты", "gi": 15, "gl": 1.0, "carbs": 14.0},
]

# Хлебобулочные изделия
additional_bread = [
    {"name": "Bagel, plain", "nameRu": "Бублик простой", "category": "Хлеб", "gi": 72, "gl": 25.0, "carbs": 35.0},
    {"name": "English muffin", "nameRu": "Английский маффин", "category": "Хлеб", "gi": 77, "gl": 11.0, "carbs": 14.0},
    {"name": "Tortilla, wheat", "nameRu": "Тортилья пшеничная", "category": "Хлеб", "gi": 30, "gl": 8.0, "carbs": 26.0},
    {"name": "Tortilla, corn", "nameRu": "Тортилья кукурузная", "category": "Хлеб", "gi": 52, "gl": 12.0, "carbs": 23.0},
    {"name": "Naan bread", "nameRu": "Хлеб наан", "category": "Хлеб", "gi": 71, "gl": 23.0, "carbs": 32.0},
    {"name": "Ciabatta", "nameRu": "Чиабатта", "category": "Хлеб", "gi": 73, "gl": 15.0, "carbs": 21.0},
    {"name": "Focaccia", "nameRu": "Фокачча", "category": "Хлеб", "gi": 69, "gl": 18.0, "carbs": 26.0},
    {"name": "Breadsticks", "nameRu": "Хлебные палочки", "category": "Хлеб", "gi": 68, "gl": 15.0, "carbs": 22.0},
    {"name": "Croutons", "nameRu": "Гренки", "category": "Хлеб", "gi": 74, "gl": 10.0, "carbs": 14.0},
    {"name": "Pumpernickel bread", "nameRu": "Хлеб пумперникель", "category": "Хлеб", "gi": 50, "gl": 6.0, "carbs": 12.0},
]

# Крупы и каши
additional_grains = [
    {"name": "Amaranth, cooked", "nameRu": "Амарант вареный", "category": "Крупы", "gi": 35, "gl": 9.0, "carbs": 19.0},
    {"name": "Bulgur, cooked", "nameRu": "Булгур вареный", "category": "Крупы", "gi": 48, "gl": 12.0, "carbs": 26.0},
    {"name": "Wheat berries, cooked", "nameRu": "Пшеничные зерна вареные", "category": "Крупы", "gi": 45, "gl": 14.0, "carbs": 31.0},
    {"name": "Spelt, cooked", "nameRu": "Полба вареная", "category": "Крупы", "gi": 45, "gl": 11.0, "carbs": 26.0},
    {"name": "Farro, cooked", "nameRu": "Фарро вареное", "category": "Крупы", "gi": 40, "gl": 10.0, "carbs": 26.0},
    {"name": "Teff, cooked", "nameRu": "Тефф вареный", "category": "Крупы", "gi": 57, "gl": 17.0, "carbs": 30.0},
    {"name": "Sorghum, cooked", "nameRu": "Сорго вареное", "category": "Крупы", "gi": 62, "gl": 19.0, "carbs": 30.0},
    {"name": "Wild rice, cooked", "nameRu": "Дикий рис вареный", "category": "Крупы", "gi": 57, "gl": 18.0, "carbs": 32.0},
    {"name": "Jasmine rice, white, cooked", "nameRu": "Рис жасмин белый вареный", "category": "Крупы", "gi": 89, "gl": 36.0, "carbs": 40.0},
    {"name": "Arborio rice, cooked", "nameRu": "Рис арборио вареный", "category": "Крупы", "gi": 69, "gl": 28.0, "carbs": 40.0},
    {"name": "Sticky rice, cooked", "nameRu": "Клейкий рис вареный", "category": "Крупы", "gi": 98, "gl": 31.0, "carbs": 32.0},
    {"name": "Rice noodles, cooked", "nameRu": "Рисовая лапша вареная", "category": "Паста", "gi": 53, "gl": 23.0, "carbs": 44.0},
    {"name": "Udon noodles, cooked", "nameRu": "Лапша удон вареная", "category": "Паста", "gi": 55, "gl": 22.0, "carbs": 40.0},
    {"name": "Soba noodles, cooked", "nameRu": "Лапша соба вареная", "category": "Паста", "gi": 59, "gl": 22.0, "carbs": 37.0},
    {"name": "Vermicelli, cooked", "nameRu": "Вермишель вареная", "category": "Паста", "gi": 35, "gl": 16.0, "carbs": 45.0},
]

# Овощи дополнительные
additional_vegetables = [
    {"name": "Asparagus, cooked", "nameRu": "Спаржа вареная", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 4.0},
    {"name": "Artichoke, cooked", "nameRu": "Артишок вареный", "category": "Овощи", "gi": 15, "gl": 1.0, "carbs": 11.0},
    {"name": "Brussels sprouts, cooked", "nameRu": "Брюссельская капуста вареная", "category": "Овощи", "gi": 15, "gl": 1.0, "carbs": 9.0},
    {"name": "Celery, raw", "nameRu": "Сельдерей свежий", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Radish, raw", "nameRu": "Редис свежий", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Turnip, cooked", "nameRu": "Репа вареная", "category": "Овощи", "gi": 30, "gl": 2.0, "carbs": 6.0},
    {"name": "Parsnip, cooked", "nameRu": "Пастернак вареный", "category": "Овощи", "gi": 97, "gl": 12.0, "carbs": 13.0},
    {"name": "Rutabaga, cooked", "nameRu": "Брюква вареная", "category": "Овощи", "gi": 72, "gl": 7.0, "carbs": 10.0},
    {"name": "Leek, cooked", "nameRu": "Лук-порей вареный", "category": "Овощи", "gi": 15, "gl": 1.0, "carbs": 14.0},
    {"name": "Fennel, raw", "nameRu": "Фенхель свежий", "category": "Овощи", "gi": 15, "gl": 1.0, "carbs": 7.0},
    {"name": "Kale, cooked", "nameRu": "Капуста кале вареная", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 9.0},
    {"name": "Swiss chard, cooked", "nameRu": "Мангольд вареный", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 4.0},
    {"name": "Collard greens, cooked", "nameRu": "Листовая капуста вареная", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 6.0},
    {"name": "Bok choy, cooked", "nameRu": "Бок-чой вареный", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 2.0},
    {"name": "Arugula, raw", "nameRu": "Руккола свежая", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 4.0},
    {"name": "Watercress, raw", "nameRu": "Кресс-салат свежий", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 1.0},
    {"name": "Endive, raw", "nameRu": "Эндивий свежий", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Radicchio, raw", "nameRu": "Радиккио свежий", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 5.0},
    {"name": "Mushrooms, raw", "nameRu": "Грибы свежие", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Mushrooms, shiitake", "nameRu": "Грибы шиитаке", "category": "Овощи", "gi": 15, "gl": 0.0, "carbs": 7.0},
]

# Бобовые дополнительные
additional_legumes = [
    {"name": "White beans, boiled", "nameRu": "Фасоль белая вареная", "category": "Бобовые", "gi": 31, "gl": 9.0, "carbs": 25.0},
    {"name": "Navy beans, boiled", "nameRu": "Фасоль морская вареная", "category": "Бобовые", "gi": 38, "gl": 12.0, "carbs": 31.0},
    {"name": "Pinto beans, boiled", "nameRu": "Фасоль пинто вареная", "category": "Бобовые", "gi": 39, "gl": 10.0, "carbs": 26.0},
    {"name": "Lima beans, boiled", "nameRu": "Фасоль лима вареная", "category": "Бобовые", "gi": 32, "gl": 6.0, "carbs": 20.0},
    {"name": "Mung beans, boiled", "nameRu": "Маш вареный", "category": "Бобовые", "gi": 39, "gl": 5.0, "carbs": 19.0},
    {"name": "Split peas, boiled", "nameRu": "Горох колотый вареный", "category": "Бобовые", "gi": 32, "gl": 6.0, "carbs": 20.0},
    {"name": "Broad beans, boiled", "nameRu": "Бобы вареные", "category": "Бобовые", "gi": 79, "gl": 9.0, "carbs": 11.0},
    {"name": "Edamame", "nameRu": "Эдамаме", "category": "Бобовые", "gi": 15, "gl": 1.0, "carbs": 9.0},
]

# Молочные продукты дополнительные
additional_dairy = [
    {"name": "Kefir", "nameRu": "Кефир", "category": "Молочные продукты", "gi": 15, "gl": 1.0, "carbs": 5.0},
    {"name": "Buttermilk", "nameRu": "Пахта", "category": "Молочные продукты", "gi": 15, "gl": 1.0, "carbs": 5.0},
    {"name": "Sour cream", "nameRu": "Сметана", "category": "Молочные продукты", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Cream cheese", "nameRu": "Сливочный сыр", "category": "Молочные продукты", "gi": 15, "gl": 0.0, "carbs": 4.0},
    {"name": "Ricotta cheese", "nameRu": "Сыр рикотта", "category": "Молочные продукты", "gi": 15, "gl": 0.0, "carbs": 3.0},
    {"name": "Mozzarella cheese", "nameRu": "Сыр моцарелла", "category": "Молочные продукты", "gi": 0, "gl": 0.0, "carbs": 2.0},
    {"name": "Parmesan cheese", "nameRu": "Сыр пармезан", "category": "Молочные продукты", "gi": 0, "gl": 0.0, "carbs": 4.0},
    {"name": "Feta cheese", "nameRu": "Сыр фета", "category": "Молочные продукты", "gi": 0, "gl": 0.0, "carbs": 4.0},
    {"name": "Goat cheese", "nameRu": "Козий сыр", "category": "Молочные продукты", "gi": 0, "gl": 0.0, "carbs": 1.0},
    {"name": "Blue cheese", "nameRu": "Сыр с плесенью", "category": "Молочные продукты", "gi": 0, "gl": 0.0, "carbs": 2.0},
    {"name": "Greek yogurt, plain", "nameRu": "Греческий йогурт натуральный", "category": "Молочные продукты", "gi": 11, "gl": 1.0, "carbs": 4.0},
    {"name": "Almond milk, unsweetened", "nameRu": "Миндальное молоко без сахара", "category": "Молочные продукты", "gi": 25, "gl": 0.0, "carbs": 1.0},
    {"name": "Coconut milk", "nameRu": "Кокосовое молоко", "category": "Молочные продукты", "gi": 40, "gl": 2.0, "carbs": 6.0},
    {"name": "Oat milk", "nameRu": "Овсяное молоко", "category": "Молочные продукты", "gi": 69, "gl": 7.0, "carbs": 10.0},
]

# Сладости и десерты
additional_sweets = [
    {"name": "Brownie", "nameRu": "Брауни", "category": "Сладости", "gi": 42, "gl": 12.0, "carbs": 29.0},
    {"name": "Muffin, blueberry", "nameRu": "Маффин с черникой", "category": "Сладости", "gi": 59, "gl": 17.0, "carbs": 29.0},
    {"name": "Cupcake", "nameRu": "Капкейк", "category": "Сладости", "gi": 73, "gl": 22.0, "carbs": 30.0},
    {"name": "Cheesecake", "nameRu": "Чизкейк", "category": "Сладости", "gi": 55, "gl": 16.0, "carbs": 29.0},
    {"name": "Tiramisu", "nameRu": "Тирамису", "category": "Сладости", "gi": 55, "gl": 15.0, "carbs": 27.0},
    {"name": "Pudding, vanilla", "nameRu": "Пудинг ванильный", "category": "Сладости", "gi": 44, "gl": 8.0, "carbs": 18.0},
    {"name": "Custard", "nameRu": "Заварной крем", "category": "Сладости", "gi": 43, "gl": 7.0, "carbs": 17.0},
    {"name": "Jelly beans", "nameRu": "Мармеладные бобы", "category": "Сладости", "gi": 78, "gl": 22.0, "carbs": 28.0},
    {"name": "Gummy bears", "nameRu": "Мармеладные мишки", "category": "Сладости", "gi": 80, "gl": 20.0, "carbs": 25.0},
    {"name": "Licorice", "nameRu": "Лакрица", "category": "Сладости", "gi": 78, "gl": 16.0, "carbs": 20.0},
    {"name": "Marshmallows", "nameRu": "Зефир", "category": "Сладости", "gi": 62, "gl": 15.0, "carbs": 24.0},
    {"name": "Caramel", "nameRu": "Карамель", "category": "Сладости", "gi": 65, "gl": 16.0, "carbs": 25.0},
    {"name": "Fudge", "nameRu": "Помадка", "category": "Сладости", "gi": 65, "gl": 18.0, "carbs": 28.0},
    {"name": "Toffee", "nameRu": "Ириска", "category": "Сладости", "gi": 65, "gl": 17.0, "carbs": 26.0},
    {"name": "Nougat", "nameRu": "Нуга", "category": "Сладости", "gi": 32, "gl": 10.0, "carbs": 31.0},
]

# Снеки и закуски
additional_snacks = [
    {"name": "Rice cakes", "nameRu": "Рисовые хлебцы", "category": "Снеки", "gi": 82, "gl": 17.0, "carbs": 21.0},
    {"name": "Corn chips", "nameRu": "Кукурузные чипсы", "category": "Снеки", "gi": 63, "gl": 17.0, "carbs": 27.0},
    {"name": "Tortilla chips", "nameRu": "Чипсы тортилья", "category": "Снеки", "gi": 63, "gl": 17.0, "carbs": 27.0},
    {"name": "Cheese puffs", "nameRu": "Сырные палочки", "category": "Снеки", "gi": 74, "gl": 15.0, "carbs": 20.0},
    {"name": "Trail mix", "nameRu": "Смесь орехов и сухофруктов", "category": "Снеки", "gi": 30, "gl": 8.0, "carbs": 27.0},
    {"name": "Granola bar", "nameRu": "Батончик мюсли", "category": "Снеки", "gi": 61, "gl": 13.0, "carbs": 21.0},
    {"name": "Protein bar", "nameRu": "Протеиновый батончик", "category": "Снеки", "gi": 35, "gl": 7.0, "carbs": 20.0},
    {"name": "Energy bar", "nameRu": "Энергетический батончик", "category": "Снеки", "gi": 65, "gl": 20.0, "carbs": 31.0},
    {"name": "Beef jerky", "nameRu": "Вяленая говядина", "category": "Снеки", "gi": 0, "gl": 0.0, "carbs": 3.0},
]

# Орехи и семена дополнительные
additional_nuts = [
    {"name": "Pistachios", "nameRu": "Фисташки", "category": "Орехи", "gi": 15, "gl": 1.0, "carbs": 28.0},
    {"name": "Macadamia nuts", "nameRu": "Орехи макадамия", "category": "Орехи", "gi": 10, "gl": 1.0, "carbs": 14.0},
    {"name": "Brazil nuts", "nameRu": "Бразильские орехи", "category": "Орехи", "gi": 10, "gl": 0.0, "carbs": 12.0},
    {"name": "Pecans", "nameRu": "Орехи пекан", "category": "Орехи", "gi": 10, "gl": 0.0, "carbs": 14.0},
    {"name": "Pine nuts", "nameRu": "Кедровые орехи", "category": "Орехи", "gi": 15, "gl": 1.0, "carbs": 13.0},
    {"name": "Chestnuts", "nameRu": "Каштаны", "category": "Орехи", "gi": 54, "gl": 9.0, "carbs": 45.0},
    {"name": "Sunflower seeds", "nameRu": "Семечки подсолнечника", "category": "Орехи", "gi": 35, "gl": 1.0, "carbs": 20.0},
    {"name": "Pumpkin seeds", "nameRu": "Тыквенные семечки", "category": "Орехи", "gi": 25, "gl": 1.0, "carbs": 11.0},
    {"name": "Sesame seeds", "nameRu": "Семена кунжута", "category": "Орехи", "gi": 35, "gl": 1.0, "carbs": 23.0},
    {"name": "Flax seeds", "nameRu": "Семена льна", "category": "Орехи", "gi": 35, "gl": 0.0, "carbs": 29.0},
    {"name": "Chia seeds", "nameRu": "Семена чиа", "category": "Орехи", "gi": 30, "gl": 1.0, "carbs": 42.0},
    {"name": "Hemp seeds", "nameRu": "Семена конопли", "category": "Орехи", "gi": 0, "gl": 0.0, "carbs": 9.0},
]

# Напитки дополнительные
additional_beverages = [
    {"name": "Carrot juice", "nameRu": "Морковный сок", "category": "Напитки", "gi": 43, "gl": 10.0, "carbs": 23.0},
    {"name": "Grape juice", "nameRu": "Виноградный сок", "category": "Напитки", "gi": 55, "gl": 13.0, "carbs": 24.0},
    {"name": "Pineapple juice", "nameRu": "Ананасовый сок", "category": "Напитки", "gi": 46, "gl": 14.0, "carbs": 30.0},
    {"name": "Cranberry juice", "nameRu": "Клюквенный сок", "category": "Напитки", "gi": 52, "gl": 16.0, "carbs": 31.0},
    {"name": "Lemonade", "nameRu": "Лимонад", "category": "Напитки", "gi": 66, "gl": 13.0, "carbs": 20.0},
    {"name": "Energy drink", "nameRu": "Энергетический напиток", "category": "Напитки", "gi": 80, "gl": 20.0, "carbs": 25.0},
    {"name": "Smoothie, fruit", "nameRu": "Смузи фруктовый", "category": "Напитки", "gi": 44, "gl": 11.0, "carbs": 25.0},
    {"name": "Protein shake", "nameRu": "Протеиновый коктейль", "category": "Напитки", "gi": 30, "gl": 5.0, "carbs": 17.0},
    {"name": "Hot chocolate", "nameRu": "Горячий шоколад", "category": "Напитки", "gi": 51, "gl": 12.0, "carbs": 24.0},
    {"name": "Coffee, black", "nameRu": "Кофе черный", "category": "Напитки", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Tea, black", "nameRu": "Чай черный", "category": "Напитки", "gi": 0, "gl": 0.0, "carbs": 0.0},
    {"name": "Tea, green", "nameRu": "Чай зеленый", "category": "Напитки", "gi": 0, "gl": 0.0, "carbs": 0.0},
]

# Готовые блюда и фастфуд
ready_meals = [
    {"name": "Hamburger", "nameRu": "Гамбургер", "category": "Готовые блюда", "gi": 66, "gl": 23.0, "carbs": 35.0},
    {"name": "Cheeseburger", "nameRu": "Чизбургер", "category": "Готовые блюда", "gi": 66, "gl": 23.0, "carbs": 35.0},
    {"name": "Hot dog", "nameRu": "Хот-дог", "category": "Готовые блюда", "gi": 90, "gl": 18.0, "carbs": 20.0},
    {"name": "Fried chicken", "nameRu": "Жареная курица", "category": "Готовые блюда", "gi": 45, "gl": 7.0, "carbs": 15.0},
    {"name": "Chicken nuggets", "nameRu": "Куриные наггетсы", "category": "Готовые блюда", "gi": 46, "gl": 7.0, "carbs": 15.0},
    {"name": "Fish and chips", "nameRu": "Рыба с картофелем фри", "category": "Готовые блюда", "gi": 58, "gl": 27.0, "carbs": 47.0},
    {"name": "Burrito", "nameRu": "Буррито", "category": "Готовые блюда", "gi": 58, "gl": 25.0, "carbs": 43.0},
    {"name": "Taco", "nameRu": "Тако", "category": "Готовые блюда", "gi": 55, "gl": 12.0, "carbs": 22.0},
    {"name": "Quesadilla", "nameRu": "Кесадилья", "category": "Готовые блюда", "gi": 55, "gl": 18.0, "carbs": 33.0},
    {"name": "Nachos with cheese", "nameRu": "Начос с сыром", "category": "Готовые блюда", "gi": 60, "gl": 20.0, "carbs": 33.0},
    {"name": "Sandwich, white bread", "nameRu": "Сэндвич на белом хлебе", "category": "Готовые блюда", "gi": 75, "gl": 22.0, "carbs": 29.0},
    {"name": "Sandwich, whole wheat", "nameRu": "Сэндвич на цельнозерновом хлебе", "category": "Готовые блюда", "gi": 57, "gl": 17.0, "carbs": 30.0},
    {"name": "Wrap, chicken", "nameRu": "Ролл с курицей", "category": "Готовые блюда", "gi": 45, "gl": 15.0, "carbs": 33.0},
    {"name": "Sushi, with white rice", "nameRu": "Суши с белым рисом", "category": "Готовые блюда", "gi": 72, "gl": 20.0, "carbs": 28.0},
    {"name": "Sushi, with brown rice", "nameRu": "Суши с коричневым рисом", "category": "Готовые блюда", "gi": 62, "gl": 17.0, "carbs": 28.0},
    {"name": "Fried rice", "nameRu": "Жареный рис", "category": "Готовые блюда", "gi": 73, "gl": 29.0, "carbs": 40.0},
    {"name": "Noodles, stir-fried", "nameRu": "Лапша жареная", "category": "Готовые блюда", "gi": 55, "gl": 22.0, "carbs": 40.0},
    {"name": "Lasagna", "nameRu": "Лазанья", "category": "Готовые блюда", "gi": 47, "gl": 14.0, "carbs": 30.0},
    {"name": "Ravioli", "nameRu": "Равиоли", "category": "Готовые блюда", "gi": 39, "gl": 15.0, "carbs": 38.0},
    {"name": "Macaroni and cheese", "nameRu": "Макароны с сыром", "category": "Готовые блюда", "gi": 64, "gl": 32.0, "carbs": 50.0},
]

# Соусы и приправы
condiments = [
    {"name": "Ketchup", "nameRu": "Кетчуп", "category": "Соусы", "gi": 55, "gl": 4.0, "carbs": 7.0},
    {"name": "Mustard", "nameRu": "Горчица", "category": "Соусы", "gi": 55, "gl": 1.0, "carbs": 2.0},
    {"name": "Mayonnaise", "nameRu": "Майонез", "category": "Соусы", "gi": 0, "gl": 0.0, "carbs": 1.0},
    {"name": "BBQ sauce", "
