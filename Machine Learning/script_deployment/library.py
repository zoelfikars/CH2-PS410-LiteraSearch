from flask import jsonify
from math import radians, sin, cos, sqrt, atan2
import pandas as pd


# Read library data from CSV
perpus_df = pd.read_csv('../datasets/Perpustakaan.csv')

def haversine(lat1, lon1, lat2, lon2):
    lat1, lon1, lat2, lon2 = map(radians, [lat1, lon1, lat2, lon2])
    dlat = lat2 - lat1
    dlon = lon2 - lon1
    a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = 6371 * c  # Radius of Earth in kilometers
    return distance

def recommend_library(user_lat, user_lon):
    try:
        # Calculate distances
        perpus_df['Distance'] = [haversine(user_lat, user_lon, lat, lon) for lat, lon in zip(perpus_df['Lat'], perpus_df['Lon'])]

        # Sort dataframe by distance
        sorted_perpus_df = perpus_df.sort_values(by='Distance')
        
        # Get all recommendations as a list of dictionaries
        recommendations = sorted_perpus_df.to_dict(orient='records')

        # Round 'Distance' to 2 decimals for each recommendation
        for recommendation in recommendations:
            recommendation['Distance'] = round(recommendation['Distance'], 2)
        # Distance on KM

        # Return recommendations as JSON
        return jsonify(recommendations)

    except Exception as e:
        return jsonify({'error': str(e)}), 400
