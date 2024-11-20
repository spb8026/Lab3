import requests
import re
import time

# Function to fetch a random English Wikipedia article
def fetch_random_article():
    url = "https://en.wikipedia.org/w/api.php"  # English Wikipedia API endpoint
    params = {
        "action": "query",
        "format": "json",
        "generator": "random",
        "grnnamespace": 0,  # Only fetch content articles (namespace 0)
        "prop": "extracts",
        "explaintext": True,  # Get plain text, no HTML
    }

    response = requests.get(url, params=params)
    if response.status_code == 200:
        data = response.json()
        page = next(iter(data["query"]["pages"].values()))
        return page.get("title"), page.get("extract")
    else:
        print(f"Failed to fetch article: {response.status_code}")
        return None, None

# Function to clean the article text (optional)
def clean_text(text):
    # Remove unwanted characters or patterns (e.g., references)
    text = re.sub(r"\[\d+\]", "", text)  # Remove reference numbers like [1], [2]
    text = re.sub(r"\n+", "\n", text)    # Normalize multiple newlines
    return text.strip()

# Main function to collect articles and save them to a file
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
            time.sleep(1)  # Be polite and avoid overwhelming the API

# Run the script
if __name__ == "__main__":
    output_file = "english_wikipedia_articles.txt"
    num_articles = 200  # Change this to the desired number of articles
    save_english_articles(output_file, article_count=num_articles)
    print(f"Saved {num_articles} English Wikipedia articles to {output_file}")
