#!/usr/bin/env python3
"""
Парсер для glycemicindex.com с использованием Selenium
Собирает все продукты с их гликемическими индексами
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import json
import time
import re
from typing import List, Dict

class GlycemicIndexSeleniumScraper:
    def __init__(self):
        self.base_url = "https://glycemicindex.com"
        self.search_url = f"{self.base_url}/gi-search/"
        self.products = []
        self.driver = None
        
    def setup_driver(self):
        """Настройка Chrome WebDriver"""
        chrome_options = Options()
        chrome_options.add_argument('--headless')  # Без GUI
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-dev-shm-usage')
        chrome_options.add_argument('--disable-gpu')
        chrome_options.add_argument('--window-size=1920,1080')
        chrome_options.add_argument('user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36')
        
        service = Service(ChromeDriverManager().install())
        self.driver = webdriver.Chrome(service=service, options=chrome_options)
        print("Chrome WebDriver initialized")
    
    def close_driver(self):
        """Закрытие драйвера"""
        if self.driver:
            self.driver.quit()
    
    def extract_number(self, text: str) -> float:
        """Извлекает число из текста"""
        if not text:
            return None
        cleaned = re.sub(r'[^\d.-]', '', text.strip())
        try:
            return float(cleaned) if cleaned else None
        except:
            return None
    
    def scrape_page(self, page_num: int) -> List[Dict]:
        """Парсит одну страницу результатов"""
        products = []
        
        try:
            # Загружаем страницу
            url = f"{self.search_url}?page={page_num}" if page_num > 1 else self.search_url
            print(f"Loading page {page_num}: {url}")
            self.driver.get(url)
            
            # Ждем загрузки таблицы
            wait = WebDriverWait(self.driver, 10)
            
            # Пробуем разные селекторы для таблицы
            table_selectors = [
                "table.gi-table",
                "table.search-results",
                "table",
                ".gi-search-results table",
                "#search-results table"
            ]
            
            table = None
            for selector in table_selectors:
                try:
                    table = wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, selector)))
                    print(f"Found table with selector: {selector}")
                    break
                except:
                    continue
            
            if not table:
                print(f"No table found on page {page_num}")
                return products
            
            # Парсим строки таблицы
            rows = table.find_elements(By.TAG_NAME, "tr")
            print(f"Found {len(rows)} rows")
            
            for i, row in enumerate(rows):
                if i == 0:  # Пропускаем заголовок
                    continue
                
                try:
                    cells = row.find_elements(By.TAG_NAME, "td")
                    if len(cells) < 3:
                        continue
                    
                    # Извлекаем данные (структура может отличаться)
                    product_name = cells[0].text.strip()
                    gi_text = cells[1].text.strip() if len(cells) > 1 else ""
                    gl_text = cells[2].text.strip() if len(cells) > 2 else ""
                    carbs_text = cells[3].text.strip() if len(cells) > 3 else ""
                    
                    gi = self.extract_number(gi_text)
                    gl = self.extract_number(gl_text)
                    carbs = self.extract_number(carbs_text)
                    
                    if product_name and gi is not None:
                        product = {
                            "name": product_name,
                            "gi": int(gi),
                            "gl": float(gl) if gl else 0.0,
                            "carbs": float(carbs) if carbs else 0.0
                        }
                        products.append(product)
                        
                except Exception as e:
                    continue
            
            print(f"Extracted {len(products)} products from page {page_num}")
            
        except Exception as e:
            print(f"Error scraping page {page_num}: {e}")
        
        return products
    
    def scrape_all_pages(self, max_pages: int = 450):
        """Парсит все страницы"""
        print(f"Starting full scraping (up to {max_pages} pages)...")
        
        self.setup_driver()
        
        try:
            for page in range(1, max_pages + 1):
                page_products = self.scrape_page(page)
                
                if not page_products:
                    print(f"No products found on page {page}. Stopping.")
                    break
                
                self.products.extend(page_products)
                print(f"Total products so far: {len(self.products)}")
                
                # Пауза между запросами
                time.sleep(2)
                
                # Каждые 50 страниц сохраняем промежуточный результат
                if page % 50 == 0:
                    self.save_to_json(f"gi-database-checkpoint-{page}.json")
        
        finally:
            self.close_driver()
        
        print(f"\nScraping completed! Total products: {len(self.products)}")
        return self.products
    
    def categorize_products(self):
        """Категоризирует продукты"""
        categories = {
            'Фрукты': ['apple', 'banana', 'orange', 'grape', 'berry', 'melon', 'peach', 'pear', 'plum', 'cherry', 'apricot', 'mango', 'pineapple', 'kiwi', 'fruit', 'strawberry', 'blueberry', 'raspberry'],
            'Овощи': ['potato', 'carrot', 'tomato', 'cucumber', 'cabbage', 'broccoli', 'spinach', 'lettuce', 'pepper', 'onion', 'garlic', 'beetroot', 'pumpkin', 'corn', 'peas', 'vegetable'],
            'Крупы': ['rice', 'pasta', 'oat', 'wheat', 'barley', 'quinoa', 'buckwheat', 'cereal', 'flakes', 'muesli', 'granola', 'grain', 'noodle', 'spaghetti'],
            'Хлебобулочные': ['bread', 'baguette', 'bagel', 'croissant', 'bun', 'roll', 'pita', 'tortilla', 'toast'],
            'Бобовые': ['bean', 'lentil', 'chickpea', 'soy', 'tofu', 'hummus', 'pea', 'legume'],
            'Молочные': ['milk', 'yogurt', 'cheese', 'ice cream', 'cream', 'dairy', 'yoghurt'],
            'Сладости': ['chocolate', 'cake', 'cookie', 'candy', 'honey', 'sugar', 'jam', 'marmalade', 'sweet', 'dessert', 'pudding'],
            'Напитки': ['juice', 'cola', 'soda', 'drink', 'tea', 'coffee', 'beverage'],
            'Орехи': ['nut', 'almond', 'walnut', 'cashew', 'peanut', 'hazelnut', 'pecan'],
            'Снеки': ['chip', 'cracker', 'popcorn', 'pretzel', 'snack']
        }
        
        for product in self.products:
            name_lower = product['name'].lower()
            product['category'] = 'Другое'
            
            for category, keywords in categories.items():
                if any(keyword in name_lower for keyword in keywords):
                    product['category'] = category
                    break
    
    def save_to_json(self, filename: str = "gi-database-raw.json"):
        """Сохраняет данные в JSON"""
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump(self.products, f, ensure_ascii=False, indent=2)
        print(f"Saved {len(self.products)} products to {filename}")

if __name__ == "__main__":
    scraper = GlycemicIndexSeleniumScraper()
    
    # Запускаем полный парсинг
    scraper.scrape_all_pages(max_pages=450)
    
    # Категоризируем
    scraper.categorize_products()
    
    # Сохраняем
    scraper.save_to_json("gi-database-raw.json")
    
    print("\nDone! Check gi-database-raw.json")
