# TIWI Language learning app prototype

##user story
As a **Student** 
I would like to **browse and searches Tiwi words**
I want **Record/Play my pronunciation**
I want to **capture a photo for a word**
I want to **pin some commonly used words **
I should be able to **remove ** items quickly and ** log out** reliably.

#Acceptance Criteria

-	User can select role (Student/ Teacher/ **Itsupport**).
-	Second screen shows:
o	Name
o	Role
o	A block of **N lines** (Task 1)
-	Word screen provides:
o	**Search bar** for words/meanings.
o	**Recycler view list** of words with actions.
o	**Swipe-to-delete* gesture.
o	*Favourite toggle** persisted with `SharedPreferences`.
o	**Record/ Play pronunciation ** (ACC `.m4a` files).
o	**Take a photo** for a word (FileProvider + Camera).
-	**Logout** always returns to the Welcome page, clearing the Back Stack.

## How to Run
Clone repo:
```bash
Git clone < https://github.com/Hem2057/LanguageApp.git>
<img width="451" height="454" alt="image" src="https://github.com/user-attachments/assets/fb204141-1613-4cd0-b9cb-b461c212f978" />
