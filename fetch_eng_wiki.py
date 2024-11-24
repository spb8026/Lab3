import requests
import re
import time

def fetch_random_article():
    url = "https://en.wikipedia.org/w/api.php"  
    params = {
        "action": "query",
        "format": "json",
        "generator": "random",
        "grnnamespace": 0, 
        "prop": "extracts",
        "explaintext": True, 
    }

    response = requests.get(url, params=params)
    if response.status_code == 200:
        data = response.json()
        page = next(iter(data["query"]["pages"].values()))
        return page.get("title"), page.get("extract")
    else:
        print(f"Failed to fetch article: {response.status_code}")
        return None, None


def clean_text(text):
    text = re.sub(r"\[\d+\]", "", text)
    text = re.sub(r"\n+", "\n", text)
    return text.strip()

def save_english_articles(filename, article_count=10):
    with open(filename, "w", encoding="utf-8") as file:
        for i in range(article_count):
            print(f"Fetching article {i + 1}...")
            title, content = fetch_random_article()
            if title and content:
                clean_content = clean_text(content)
                file.write(f"\n{clean_content}\n\n")
            else:
                print("Skipping due to fetch error.")
            time.sleep(1)  

if __name__ == "__main__":
    output_file = "englishtest_wikipedia_articles.txt"
    num_articles = 50 
    save_english_articles(output_file, article_count=num_articles)
    print(f"Saved {num_articles} English Wikipedia articles to {output_file}")
