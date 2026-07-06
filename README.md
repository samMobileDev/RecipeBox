# 🍽️ RecipeBox

> *"Cooking should be simple, enjoyable, and accessible to everyone."*

> **RecipeBox** is a modern Android application designed to help users discover, search, and save delicious recipes with a clean interface and a smooth user experience.

Built with **Kotlin**,**Room**, **Retrofit**, and modern Android development practices.

---

#  Features

* 🔍 Search recipes by name
* 🥕 Find recipes using selected ingredients
* ❤️ Save favorite recipes for offline access
* 📖 View detailed cooking instructions
* 🌱 Browse healthy, vegan, quick, popular, and recommended recipes
* ⚡ Fast and responsive UI
* 🛡️ Comprehensive error handling
* 💾 Offline storage with Room Database

---

# 📱 Overview

![Recipe](Recipe.png)

---

#  Home Screen

Display multiple recipe categories fetched from the Spoonacular API.

### Features

* Recommended Recipes
* Popular Recipes
* Healthy Recipes
* Quick Recipes
* Vegan Recipes
* Retrofit API
* Horizontal RecyclerViews
* Glide Image Loading
* Loading States
* Network Error Handling
* HTTP Error Handling
* Fade Animations

**Screenshots from Home Page**
![Home Screen](Home_Screen.png)

---

![Home Bottom](Home_Bottom_Screen.png)

---

# Search Recipes 

Search Recipes by recipe name

### Features

* Recipe Search
* Input Validation
* Empty Query Validation
* Search Result Screen
* Empty Result Handling
* Loading Indicator
* Network Error Handling
* HTTP Error Handling
* RecyclerView
* Fade Animation

**Screenshots from Search page**
![ Home Search Screen](Home_Search_Screen.png)

---

![Second Search Screen](Home_Search_Screen_Input.png)


---

# Search by ingredients

Search recipes based on ingredients available in your fridge.

### Features

* Ingredient Categories
* Multi Selection
* Search by Ingredients
* Loading State
* Empty State
* Placeholder Images
* HTTP Error Handling
* Network Error Handling
* RecyclerView

**Screenshots from Search by Ingredients page**

![Search Screen](Search_Screen.png)

---

![Second Search Screen](Search_Second_Screen.png)

---

#  Recipe Details

View complete recipe information.

### Features

* Recipe Image
* Cooking Time
* Servings
* Ingredients List
* Cooking Instructions
* Save Recipe
* Delete Saved Recipe
* Glide
* Room Database Integration

**Screenshot from Detail page**

![Detail](Detail_Screen.png)



---

## ❤️ Saved Recipes

Offline storage of favorite recipes.

### Features

* Room Database
* Offline Access
* Delete Saved Recipes
* Empty State
* RecyclerView
* Fade Animation

**Screenshot from Saved Page**

![Saved Screen](Saved_Screen.png)

---

# 🛠️ Tech Stack

| Technology      | Description              |
| --------------- | ------------------------ |
| Kotlin          | Programming Language     |
| MVVM            | Architecture Pattern     |
| Retrofit        | REST API Client          |
| Room Database   | Local Storage            |
| Coroutines      | Asynchronous Programming |
| RecyclerView    | Dynamic Lists            |
| Glide           | Image Loading            |
| Material Design | Modern UI Components     |
| ViewBinding     | View Binding             |
| Spoonacular API | Recipe Data              |

---

# 🛡️ Error Handling

RecipeBox includes production-style error handling.

### Input Validation

* Empty query
* Too short query
* Too long query
* Emoji filtering
* Number filtering
* Extra spaces validation

### Network

* No Internet Connection
* Loading State
* HTTP 401
* HTTP 402
* HTTP 429
* HTTP 500
* Unknown Server Errors

### UI States

* Empty Search Result
* Empty Saved Recipes
* Placeholder Images
* Smooth Screen Transitions

---

**Screenshot from Error Internet page**
![Loading Screen](Loading_Screen.png)

---



# 🏗️ Architecture

```
Presentation
│
├── Fragments
├── RecyclerView Adapters
└── ViewBinding

↓

Data

├── Retrofit
├── Room Database
└── Spoonacular API

↓

Storage

└── Room Database
```

---

# Other Screenshots
![Greeting](Greeting_Screen.png)

---

![Menu](Menu_Screen.png)

---

![Second_Menu](Second_Menu_Screen.png)

---



# 🚀 Installation

Clone the repository

```bash
git clone https://github.com/SamMobileDev/RecipeBox.git
```

Open the project in Android Studio.

Add your own Spoonacular API key.

Build and run the application.

---

# 🔑 API

This application uses the Spoonacular REST API.

Generate your own API key from Spoonacular and replace the existing key inside the project before running the application.

---

# 📈 Future Improvements

* Dark Theme
* Pagination
* Recipe Filters
* Search History
* Favorites Synchronization
* Dependency Injection (Hilt)
* Repository Layer
* Unit Testing
* UI Testing

---

# 👨‍💻 Author

**SAM Android Dev**

Currently focused on building modern Android applications with clean architecture and production-ready code.

---

⭐ If you like this project, consider giving it a star!
