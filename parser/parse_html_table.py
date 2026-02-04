#!/usr/bin/env python3
"""
Парсинг таблицы продуктов из HTML файла
"""

import json
import re
from html.parser import HTMLParser

class TableParser(HTMLParser):
    def __init__(self):
        super().__init__()
        self.in_table = False
        self.in_row = False
        self.in_cell = False
        self.current_row = []
        self.rows = []
        self.current_data = ""
        
    def handle_starttag(self, tag, attrs):
        if tag == 'table':
            self.in_table = True
        elif tag == 'tr' and self.in_table:
            self.in_row = True
            self.current_row = []
        elif tag in ['td', 'th'] and self.in_row:
            self.in_cell = True
            self.current_data = ""
    
    def handle_endtag(self, tag):
        if tag == 'table':
            self.in_table = False
        elif tag == 'tr' and self.in_row:
            if self.current_row:
                self.rows.append(self.current_row)
            self.in_row = False
        elif tag in ['td', 'th'] and self.in_cell:
            self.current_row.append(self.current_data.strip())
            self.in_cell = False
    
    def handle_data(self, data):
        if self.in_cell:
            self.current_data += data

# Читаем HTML файл
with open('Гликемический индекс продуктов питания (таблица).html', 'r', encoding='windows-1251') as f:
    html_content = f.read()

# Парсим таблицы
parser = TableParser()
parser.feed(html_content)

print(f"Найдено строк в таблицах: {len(parser.rows)}")

# Ищем таблицу с продуктами (должна содержать ГИ)
products = []
for row in parser.rows:
    if len(row) < 2:
        continue
    
    # Пропускаем заголовки
    if any(h in row[0].lower() for h in ['продукт', 'название', 'наименование']):
        continue
    
    # Ищем строки с числовыми значениями ГИ
    gi_value = None
    product_name = row[0].strip()
    
    if not product_name or len(product_name) < 3:
        continue
    
    # Ищем ГИ во второй или третьей колонке
    for cell in row[1:]:
        cell = cell.strip()
        # Пробуем извлечь число
        match = re.search(r'\b(\d{1,3})\b', cell)
        if match:
            gi = int(match.group(1))
            if 0 <= gi <= 110:  # Валидный диапазон ГИ
                gi_value = gi
                break
    
    if gi_value is not None and product_name:
        products.append({
            'name': product_name,
            'gi': gi_value,
            'row': row
        })

print(f"\nНайдено продуктов с ГИ: {len(products)}")

# Показываем первые 20 продуктов
print("\nПримеры найденных продуктов:")
for i, p in enumerate(products[:20]):
    print(f"{i+1}. {p['name']} - ГИ: {p['gi']}")

# Сохраняем в JSON для дальнейшей обработки
with open('parser/html_table_products.json', 'w', encoding='utf-8') as f:
    json.dump(products, f, ensure_ascii=False, indent=2)

print(f"\nСохранено в: parser/html_table_products.json")
