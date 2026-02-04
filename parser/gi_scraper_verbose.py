#!/usr/bin/env python3
"""
Парсер для glycemicindex.com с подробным выводом
Поэтапная загрузка с сохранением промежуточных результатов
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
import sys

class VerboseGIScraper:
    def __init__(self):
        self.base_url = "https://glycemicindex.com"
        self.search_url = f"{self.base_url}/gi-search/"
        self.products = []
        self.driver = None
        
    def log(self, message):
        """Вывод с flush для немедленного отображения"""
        print(f"[GI Scraper] {message}", flush=True)
        
    def setup_driver(self):
        """Настройка Chrome WebDriver"""
        self.log("Настройка Chrome WebDriver...")
        
        chrome_options = Options()
        # Убираем headless для отладки
        # chrome_options.add_argument('--headless')
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-dev-shm-usage')
        chrome_options.add_argument('--disable-gpu')
        chrome_options.add_argument('--window-size=1920,1080')
        chrome_options.add_argument('user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36')
        
        self.log("Загрузка ChromeDriver...")
        service = Service(ChromeDriverManager().install())
        self.driver = webdriver.Chrome(service=service, options=chrome_options)
        self.log("[OK] Chrome WebDriver готов!")
        
    def close_driver(self):
        """Закрытие драйвера"""
        if self.driver:
            self.driver.quit()
            self.log("Chrome WebDriver закрыт")
    
    def extract_number(self, text: str) -> float:
        """Извлекает число из текста"""
        if not text:
            return None
        cleaned = re.sub(r'[^\d.-]', '', text.strip())
        try:
            return float(cleaned) if cleaned else None
        except:
            return None
    
    def test_page_structure(self):
        """Тестирует структуру первой страницы"""
        self.log("=" * 60)
        self.log("ТЕСТИРОВАНИЕ СТРУКТУРЫ СТРАНИЦЫ")
        self.log("=" * 60)
        
        try:
            self.log(f"Загрузка: {self.search_url}")
            self.driver.get(self.search_url)
            time.sleep(3)
            
            self.log(f"Заголовок страницы: {self.driver.title}")
            
            # Проверяем разные селекторы
            selectors_to_try = [
                ("table", "Любая таблица"),
                ("table.gi-table", "Таблица с классом gi-table"),
                ("table.search-results", "Таблица с классом search-results"),
                (".gi-search-results", "Блок результатов поиска"),
                ("#search-results", "Блок с ID search-results"),
                ("tbody tr", "Строки в tbody"),
            ]
            
            for selector, description in selectors_to_try:
                try:
                    elements = self.driver.find_elements(By.CSS_SELECTOR, selector)
                    self.log(f"  {description} ({selector}): найдено {len(elements)} элементов")
                except Exception as e:
                    self.log(f"  {description} ({selector}): ошибка - {e}")
            
            # Сохраняем HTML для анализа
            with open("page_source.html", "w", encoding="utf-8") as f:
                f.write(self.driver.page_source)
            self.log("[OK] HTML страницы сохранен в page_source.html")
            
        except Exception as e:
            self.log(f"[ERROR] Ошибка при тестировании: {e}")
    
    def scrape_page_interactive(self, page_num: int):
        """Интерактивный парсинг одной страницы с выводом"""
        self.log("=" * 60)
        self.log(f"ПАРСИНГ СТРАНИЦЫ {page_num}")
        self.log("=" * 60)
        
        products = []
        
        try:
            url = f"{self.search_url}?page={page_num}" if page_num > 1 else self.search_url
            self.log(f"URL: {url}")
            self.driver.get(url)
            
            self.log("Ожидание загрузки таблицы...")
            time.sleep(2)
            
            # Ищем таблицу
            table = None
            try:
                table = self.driver.find_element(By.TAG_NAME, "table")
                self.log("[OK] Таблица найдена!")
            except:
                self.log("[ERROR] Таблица не найдена")
                return products
            
            # Парсим строки
            rows = table.find_elements(By.TAG_NAME, "tr")
            self.log(f"Найдено строк: {len(rows)}")
            
            for i, row in enumerate(rows):
                if i == 0:
                    # Показываем заголовки
                    headers = [cell.text.strip() for cell in row.find_elements(By.TAG_NAME, "th")]
                    if headers:
                        self.log(f"Заголовки: {headers}")
                    continue
                
                try:
                    cells = row.find_elements(By.TAG_NAME, "td")
                    if len(cells) < 2:
                        continue
                    
                    # Показываем первые 3 строки для отладки
                    if i <= 3:
                        cell_texts = [cell.text.strip() for cell in cells]
                        self.log(f"  Строка {i}: {cell_texts}")
                    
                    # Извлекаем данные
                    product_name = cells[0].text.strip()
                    gi_text = cells[1].text.strip() if len(cells) > 1 else ""
                    
                    gi = self.extract_number(gi_text)
                    
                    if product_name and gi is not None:
                        product = {
                            "name": product_name,
                            "gi": int(gi),
                            "gl": 0.0,
                            "carbs": 0.0
                        }
                        products.append(product)
                        
                except Exception as e:
                    if i <= 3:
                        self.log(f"  Ошибка в строке {i}: {e}")
                    continue
            
            self.log(f"[OK] Извлечено продуктов: {len(products)}")
            
            # Показываем первые 5 продуктов
            if products:
                self.log("Примеры продуктов:")
                for p in products[:5]:
                    self.log(f"  - {p['name']}: GI={p['gi']}")
            
        except Exception as e:
            self.log(f"[ERROR] Ошибка: {e}")
        
        return products
    
    def save_checkpoint(self, filename="checkpoint.json"):
        """Сохранение промежуточных результатов"""
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump(self.products, f, ensure_ascii=False, indent=2)
        self.log(f"[OK] Checkpoint сохранен: {filename} ({len(self.products)} продуктов)")

if __name__ == "__main__":
    scraper = VerboseGIScraper()
    
    try:
        scraper.setup_driver()
        
        # Сначала тестируем структуру
        scraper.test_page_structure()
        
        print("\n" + "=" * 60)
        scraper.log("Автоматическое продолжение парсинга...")
        
        if True:
            # Парсим первые 5 страниц для теста
            for page in range(1, 6):
                products = scraper.scrape_page_interactive(page)
                scraper.products.extend(products)
                
                if len(products) == 0:
                    scraper.log("Нет продуктов, останавливаемся")
                    break
                
                scraper.log(f"Всего собрано: {len(scraper.products)} продуктов")
                scraper.save_checkpoint(f"checkpoint-page-{page}.json")
                
                time.sleep(2)
            
            # Финальное сохранение
            scraper.save_checkpoint("gi-database-scraped.json")
            scraper.log(f"\n[SUCCESS] ГОТОВО! Собрано {len(scraper.products)} продуктов")
        
    except KeyboardInterrupt:
        scraper.log("\n[STOP] Прервано пользователем")
    except Exception as e:
        scraper.log(f"\n[ERROR] Ошибка: {e}")
    finally:
        scraper.close_driver()
