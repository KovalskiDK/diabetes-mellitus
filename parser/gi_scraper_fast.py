#!/usr/bin/env python3
"""
Быстрый парсер для glycemicindex.com
Без задержек, с минимальным выводом
"""

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import json
import re
import sys

class FastGIScraper:
    def __init__(self):
        self.base_url = "https://glycemicindex.com/gi-search/"
        self.products = []
        self.driver = None
        
    def setup_driver(self):
        """Настройка Chrome WebDriver"""
        chrome_options = Options()
        chrome_options.add_argument('--headless')  # Без GUI - быстрее
        chrome_options.add_argument('--no-sandbox')
        chrome_options.add_argument('--disable-dev-shm-usage')
        chrome_options.add_argument('--disable-gpu')
        chrome_options.add_argument('--disable-images')  # Не загружаем картинки
        chrome_options.add_argument('--blink-settings=imagesEnabled=false')
        chrome_options.add_argument('--disable-javascript')  # Если не нужен JS
        
        service = Service(ChromeDriverManager().install())
        self.driver = webdriver.Chrome(service=service, options=chrome_options)
        print("[OK] WebDriver готов", flush=True)
        
    def extract_number(self, text: str) -> float:
        if not text:
            return None
        cleaned = re.sub(r'[^\d.-]', '', text.strip())
        try:
            return float(cleaned) if cleaned else None
        except:
            return None
    
    def scrape_page(self, page_num: int):
        """Быстрый парсинг страницы"""
        products = []
        
        try:
            url = f"{self.base_url}?page={page_num}" if page_num > 1 else self.base_url
            self.driver.get(url)
            
            table = self.driver.find_element(By.TAG_NAME, "table")
            rows = table.find_elements(By.TAG_NAME, "tr")
            
            for i, row in enumerate(rows):
                if i == 0:
                    continue
                
                try:
                    cells = row.find_elements(By.TAG_NAME, "td")
                    if len(cells) < 2:
                        continue
                    
                    name = cells[0].text.strip()
                    gi = self.extract_number(cells[1].text.strip())
                    
                    if name and gi is not None:
                        products.append({
                            "name": name,
                            "gi": int(gi),
                            "gl": 0.0,
                            "carbs": 0.0
                        })
                except:
                    continue
            
        except Exception as e:
            print(f"[ERROR] Page {page_num}: {e}", flush=True)
        
        return products
    
    def scrape_fast(self, max_pages: int = 100):
        """Быстрый парсинг без задержек"""
        print(f"[START] Парсинг {max_pages} страниц...", flush=True)
        
        for page in range(1, max_pages + 1):
            products = self.scrape_page(page)
            
            if not products:
                print(f"[STOP] Нет данных на странице {page}", flush=True)
                break
            
            self.products.extend(products)
            
            # Вывод каждые 10 страниц
            if page % 10 == 0:
                print(f"[{page}/{max_pages}] Собрано: {len(self.products)} продуктов", flush=True)
                self.save(f"checkpoint-{page}.json")
        
        print(f"[DONE] Всего: {len(self.products)} продуктов", flush=True)
        return self.products
    
    def save(self, filename="gi-database-fast.json"):
        with open(filename, 'w', encoding='utf-8') as f:
            json.dump(self.products, f, ensure_ascii=False, indent=2)
    
    def close(self):
        if self.driver:
            self.driver.quit()

if __name__ == "__main__":
    # Количество страниц для парсинга (по умолчанию 100)
    max_pages = int(sys.argv[1]) if len(sys.argv) > 1 else 100
    
    scraper = FastGIScraper()
    
    try:
        scraper.setup_driver()
        scraper.scrape_fast(max_pages)
        scraper.save("gi-database-fast.json")
    except KeyboardInterrupt:
        print("\n[STOP] Прервано", flush=True)
        scraper.save("gi-database-interrupted.json")
    except Exception as e:
        print(f"\n[ERROR] {e}", flush=True)
    finally:
        scraper.close()
