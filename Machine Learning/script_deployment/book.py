import pickle
import numpy as np
from flask import jsonify

# Load data from pickle files
popular_df = pickle.load(open('model/popular.pkl', 'rb'))
pt = pickle.load(open('model/pt.pkl', 'rb'))
books = pickle.load(open('model/books.pkl', 'rb'))
similarity_scores = pickle.load(open('model/similarity_scores.pkl', 'rb'))

def convert_to_python_types(data):
    # Retrieves information about popular books.
    if isinstance(data, np.int64):
        return int(data)
    elif isinstance(data, np.generic):
        return data.item()
    elif isinstance(data, list):
        return [convert_to_python_types(item) for item in data]
    elif isinstance(data, dict):
        return {key: convert_to_python_types(value) for key, value in data.items()}
    else:
        return data

#  Retrieves information about popular books.
def get_popular_books():
    return jsonify({
        "book_name": convert_to_python_types(list(popular_df['Book-Title'].values)),
        "author": convert_to_python_types(list(popular_df['Book-Author'].values)),
        "votes": convert_to_python_types(list(popular_df['num_ratings'].values)),
        "rating": convert_to_python_types(list(popular_df['avg_rating'].values))
    })

#  Recommends books based on user input.
def recommend_books(user_input):
    # # Find the index of the user input in the data
    index = np.where(pt.index == user_input)[0][0]
    # # Get similar items based on similarity scores
    similar_items = sorted(list(enumerate(similarity_scores[index])), key=lambda x: x[1], reverse=True)[1:5]

    data = []
    for i in similar_items:
        item = {}
        #  # Extract information about recommended books
        temp_df = books[books['Book-Title'] == pt.index[i[0]]]
        item["book_title"] = convert_to_python_types(list(temp_df.drop_duplicates('Book-Title')['Book-Title'].values))
        item["author"] = convert_to_python_types(list(temp_df.drop_duplicates('Book-Title')['Book-Author'].values))

        data.append(item)
    # JSON response containing recommended books.
    return jsonify({"data": convert_to_python_types(data)})
