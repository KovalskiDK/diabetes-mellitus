#!/usr/bin/env python3
"""
Парсер для glycemicindex.com
Собирает все продукты с их гликемическими индексами
"""

import requests
from bs4 import BeautifulSoup
import json
import time
import re
from typing import List, Dict

class GlycemicIndexScraper:
    def __init__(self):
        self.base_url = "https://glycemicindex.com"
        self.search_url = f"{self.base_url}/gi-search/"
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36'
        }
        self.products = []
        
    def get_page(self, url: str) -> BeautifulSoup:
        """Получает HTML страницы"""
        try:
            response = requests.get(url, headers=self.headers, timeout=10)
            response.raise_for_status()
            return BeautifulSoup(response.content, 'html.parser')
        except Exception as e:
            print(f"Error fetching {url}: {e}")
            return None
    
    def parse_product_row(self, row) -> Dict:
        """Парсит строку таблицы с продуктом"""
        try:
            cells = row.find_all('td')
            if len(cells) < 5:
                return None
            
            # Извлекаем данные из ячеек
            product_name = cells[0].get_text(strip=True)
            gi_value = cells[1].get_text(strip=True)
            serving_size = cells[2].get_text(strip=True)
            carbs = cells[3].get_text(strip=True)
            gl_value = cells[4].get_text(strip=True)
            
            # Очищаем числовые значения
            gi = self.extract_number(gi_value)
            gl = self.extract_number(gl_value)
            carbs_per_100g = self.extract_number(carbs)
            
            if gi is None or product_name == "":
                return None
            
            return {
                "name": product_name,
                "gi": gi,
                "gl": gl if gl else 0.0,
                "carbs": carbs_per_100g if carbs_per_100g else 0.0,
                "serving": serving_size
            }
        except Exception as e:
            print(f"Error parsing row: {e}")
            return None
    
    def extract_number(self, text: str) -> float:
        """Извлекает число из текста"""
        if not text:
            return None
        # Удаляем все кроме цифр, точки и минуса
        cleaned = re.sub(r'[^\d.-]', '', text)
        try:
            return float(cleaned) if cleaned else None
        except:
            return None
    
    def scrape_all_pages(self, max_pages: int = 500):
        """Парсит все страницы с результатами"""
        print(f"Starting scraping glycemicindex.com...")
        
        page = 1
        while page <= max_pages:
            print(f"Scraping page {page}/{max_pages}...")
            
            # Формируем URL страницы (нужно изучить как работает пагинация)
            # Это примерный URL, нужно проверить реальную структуру
            url = f"{self.search_url}?page={page}"
            
            soup = self.get_page(url)
            if not soup:
                break
            
            # Ищем таблицу с результатами
            table = soup.find('table', class_='gi-table')  # Нужно найти правильный селектор
            if not table:
                # Пробуем другие варианты
                table = soup.find('table')
            
            if not table:
                print(f"No table found on page {page}")
                break
            
            # Парсим строки таблицы
            rows = table.find_all('tr')[1:]  # Пропускаем заголовок
            
            if not rows:
                print(f"No more results on page {page}")
                break
            
            products_found = 0
            for row in rows:
                product = self.parse_product_row(row)
                if product:
                    self.products.append(product)
                    products_found += 1
            
            print(f"Found {products_found} products on page {page}")
            
            if products_found == 0:
                break
            
            page += 1
            time.sleep(1)  # Пауза между запросами
        
        print(f"\nTotal products scraped: {len(self.products)}")
        return self.products
    
    def save_to_json(self, filename: str = "gi-database-raw.json"):
        """Сохраняет данные в JSON"""
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump(self.products, f, ensure_ascii=False, indent=2)
        print(f"Saved {len(self.products)} products to {filename}")
    
    def categorize_products(self):
        """Категоризирует продукты"""
        categories = {
            'Фрукты': ['apple', 'banana', 'orange', 'grape', 'berry', 'melon', 'peach', 'pear', 'plum', 'cherry', 'apricot', 'mango', 'pineapple', 'kiwi'],
            'Овощи': ['potato', 'carrot', 'tomato', 'cucumber', 'cabbage', 'broccoli', 'spinach', 'lettuce', 'pepper', 'onion', 'garlic', 'beetroot', 'pumpkin', 'corn', 'peas'],
            'Крупы': ['rice', 'pasta', 'bread', 'oat', 'wheat', 'barley', 'quinoa', 'buckwheat', 'cereal', 'flakes', 'muesli', 'granola'],
            'Хлебобулочные': ['bread', 'baguette', 'bagel', 'croissant', 'bun', 'roll', 'pita', 'tortilla'],
            'Бобовые': ['bean', 'lentil', 'chickpea', 'soy', 'tofu', 'hummus', 'pea'],
            'Молочные': ['milk', 'yogurt', 'cheese', 'ice cream', 'cream'],
            'Сладости': ['chocolate', 'cake', 'cookie', 'candy', 'honey', 'sugar', 'jam', 'marmalade'],
            'Напитки': ['juice', 'cola', 'soda', 'drink', 'tea', 'coffee'],
            'Орехи': ['nut', 'almond', 'walnut', 'cashew', 'peanut', 'hazelnut', 'pecan'],
            'Снеки': ['chip', 'cracker', 'popcorn', 'pretzel']
        }
        
        for product in self.products:
            name_lower = product['name'].lower()
            product['category'] = 'Другое'
            
            for category, keywords in categories.items():
                if any(keyword in name_lower for keyword in keywords):
                    product['category'] = category
                    break

if __name__ == "__main__":
    scraper = GlycemicIndexScraper()
    
    # Сначала проверим одну страницу
    print("Testing with first page...")
    scraper.scrape_all_pages(max_pages=1)
    
    if scraper.products:
        print("\nSample products:")
        for p in scraper.products[:5]:
            print(f"  - {p['name']}: GI={p['gi']}, GL={p['gl']}")
        
        # Если работает, запускаем полный парсинг
        response = input("\nContinue with full scraping? (y/n): ")
        if response.lower() == 'y':
            scraper.products = []  # Очищаем
            scraper.scrape_all_pages(max_pages=500)
            scraper.categorize_products()
            scraper.save_to_json()
    else:
        print("\nNo products found. Need to adjust selectors.")
        print("Please check the HTML structure of glycemicindex.com")
