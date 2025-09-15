# 📱 LanguageApp – Tiwi Language Learning Prototype

## 📖 Overview
LanguageApp is a prototype Android app built in **Kotlin** with **Android Studio** for learning and practicing the Tiwi language.  
It demonstrates user onboarding, vocabulary management, recording pronunciation, photo capture, and persistence of user data — aligned with assignment requirements.

The prototype is **not a full production app** but shows a working flow for **one user story**, supported by additional features.

---

## 🧑‍🤝‍🧑 User Stories

### 1. Student
*As a student learning the Tiwi language,  
I want to browse words, record and play back my pronunciation, and mark favorite words,  
so that I can practice vocabulary and track my learning progress.*

**Acceptance Criteria:**
- Role Selection → choose *Student*.  
- See a list of Tiwi words and meanings.  
- Search for words.  
- Record audio for each word and play it back.  
- Favorite words persist across app restarts.

---

### 2. Teacher
*As a teacher of Tiwi language,  
I want to add new words to the vocabulary list,  
so that my students can learn both common and rare Tiwi words during practice sessions.*

**Acceptance Criteria:**
- Role Selection → choose *Teacher*.  
- Add new words using the “+ Add Word” button.  
- New words appear in the list immediately and remain saved.

---

### 3. Linguist
*As a linguist researching endangered languages,  
I want to capture pronunciations and attach photos to words,  
so that I can document language use with real-world examples for preservation.*

**Acceptance Criteria:**
- Role Selection → choose *Linguist*.  
- Record audio with microphone → save as .m4a file.  
- Capture photos for a word using the device camera.  
- Saved media persists locally and can be reviewed.

---

## ✨ Features
- **Role Selection Screen** (Student, Teacher, Linguist).  
- **Word List** with:
  - Search bar.  
  - Swipe-to-delete gesture.  
  - Favorites stored in SharedPreferences.  
  - Audio recording/playback per word.  
  - Camera photo capture per word.  
- **Add Word Screen** (teachers can add new vocabulary).  
- **Line Test Screen** (demonstrates utility test function).  
- **Logout** → clears user info and returns to Role Selection.  

---

## 🚀 How to Run

1. **Clone this repository**
   ```bash
   [git clone https://](https://github.com/Hem2057/LanguageApp.git)
