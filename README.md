# java-bookstore-managment-system

# Product Management System: README

## Overview:
This GitHub repository contains the source code for a Java-based Product Management System. This application provides a graphical user interface (GUI) to manage products by adding, updating, finding, and displaying product details.

## Main Features:

1. **Main GUI**:
    - Title of the application is "Product Main GUI".
    - Contains a centralized label "Product Management System".
    - Menu options:
        - **File Menu**: Contains an option to exit the application.
        - **Product Menu**: Options to Add/Update or Find/Display product details.
        
2. **Add/Update Product Window**:
    - Allows users to add or update product details.
    - Fields available: Product ID, Name, Description, Quantity in hand, and Unit Price.
    - Contains buttons to add or update the product information.
    - Navigation buttons to browse through product entries: First, Previous, Next, Last.
    - Uses various validation methods to ensure valid input.
    - Data is saved to a binary file named `data.bin`.
    - Before adding a product, the system checks if the ID is unique.
    
3. **Find/Display Functionality**:
    - Allows users to search for products based on various criteria:
        - Keyword search: Finds and displays products that match the given keyword.
        - Display all: Shows all the products from the file.
        - Price range: Finds and displays products within a specific price range.

## Code Structure:
- **MainGUI**: Main class that initializes the main application window.
- **AddUpdateWindow**: Class responsible for the Add/Update product window.
    - **addProductDetails()**: Function to add product details to the `data.bin` file.
    - **isUniqueId()**: Checks if a given product ID is unique.
    - **isValidId()**: Validates the product ID.
    - **isValidName()**: Validates the product name.
    - **getFirstProduct1()**: Fetches the first product from the file.
- **findplayDetails()**: Functionality to search and display products based on various criteria.

## Dependencies:
- **Java Swing**: Used for creating the GUI.
- **Java IO**: For file handling operations.

## How to Run:
1. Clone this repository.
2. Compile and run the `MainGUI` class.
3. Use the GUI to manage your products.

## Note:
- Ensure that the `data.bin` file (or its specified path) is accessible for reading and writing operations. If it doesn't exist, the system will attempt to create one.

Feedback, issues, and pull requests are always welcome!
