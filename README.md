# Photo Browser Application

## Overview
A Java-based desktop application for managing and organizing photo collections with an intuitive Swing GUI. Key features include customizable photo metadata (titles, tags, dates, descriptions), collection grouping, and advanced search capabilities using filters such as tags, keywords, and date ranges. The application utilizes JFileChooser for seamless file handling and integrates custom data processing classes to maintain clean architecture. Efficient data storage and retrieval are achieved through Set and Map structures. Multithreading is implemented to optimize search performance across large datasets.

## Features
- **Photo Management**: 
  - Add photos with metadata such as titles, tags, dates, and descriptions.
  - Organize photos into collections.
  - Remove photos from the collection.

- **Advanced Search**:
  - Search photos by tags, keywords in descriptions, and date ranges.
  - Supports both "AND" and "OR" search modes for tags.

- **File Handling**:
  - Load photos directly from the file system.
  - Display photo thumbnails and metadata.

- **Data Validation**:
  - Uses regular expressions to validate photo titles.
  - Ensures unique and organized tags and collections.

- **Stream Processing**:
  - Efficiently processes data for searching and filtering.

- **Multithreading**:
  - Optimizes searches in large datasets for better performance.

## Technologies Used
- **Java Swing**: For building the graphical user interface (GUI).
- **Regular Expressions**: For validating input data (e.g., photo titles).
- **Collections Framework**: For managing tags, collections, and photos.
- **Multithreading**: For optimizing search operations.
- **File Handling**: For loading and displaying photos.

## How to Run
1. Clone the repository to your local machine.
2. Open the project in an IDE like IntelliJ IDEA.
3. Run the `S30474Nakonechnyi` class to start the application.
