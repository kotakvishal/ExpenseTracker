# ExpenseTracker

ExpenseTracker is a modern Android application designed to help users efficiently track and manage their daily expenses. Built using the latest Android technologies, it leverages Jetpack Compose for a responsive and intuitive UI, Room for robust local data storage, and Hilt for seamless dependency injection. The app follows the MVVM (Model-View-ViewModel) architecture, ensuring a clean separation of concerns and maintainable code.

## Features

### Expense Entry Screen
- Add expense with:
    - Title
    - Amount (₹)
    - Category (Staff, Travel, Food, Utility)
    - Optional notes (max 100 chars)
    - Optional receipt image
- Shows real-time **Total Spent Today**
- Validates input fields:
    - Amount must be a positive number
    - Title and category are required
    - Notes must not exceed 100 characters
### Expense List Screen
- View expenses by date (default: today)
- Group by category or time
- Displays **Total Entries** and **Total Amount**

### Expense Report Screen
- Weekly report (mocked for last 7 days)
- Shows **daily totals**, **category-wise totals**
- Displays **bar chart**
- Option to **export data to CSV**

### Additional Features
- **Dynamic Theme** based on wallpaper color
- Local persistence with **Room**
- Clean MVVM architecture with **StateFlow**
- Reusable **UI components**
- **Dark Mode** support



## Project Structure

- `data/` – Contains Room entities, DAOs, repositories, and data mappers.
- `domain/` – Houses domain models and repository interfaces.
- `presentation/` – UI screens, navigation logic, and ViewModels.
- `ui/theme/` – Theming resources such as colors and typography.
- `core/di/` – Hilt dependency injection modules.
- `MainActivity.kt` – The main entry point and navigation host for the app.

## Permissions

- The app requests `WRITE_EXTERNAL_STORAGE` permission, which is used to exporting csv.


## AI USAGE

- **"I used agentic AI (Copilot GPT-4.1) to generate data classes, use cases, and ViewModels*
Basically automating repetitive code or code requiring minimal logic, which helped speed up development and maintain consistency."*

## Prompts
- **Data Class Generation**: "Generate a data class for an expense with fields for title, amount, category, notes, and receipt image."
- **Use Case Creation**: "Create a use case for adding an expense that validates input and interacts with the repository."
- **ViewModel Implementation**: "Generate a ViewModel for the expense entry screen that handles state management and user interactions."
- **UI Component Design**: "Design a reusable Jetpack Compose InputField composable in Kotlin that accepts parameters for value, onValueChange, label, placeholder, singleLine, keyboardType, maxLines, minHeight, paddingVertical, cornerRadius, borderColor, and placeholderColor. It should display a label above the text field, show placeholder text when empty, have a rounded border, support multi-line input, and use MaterialTheme colors for text, border, and cursor."
- **CSV Export Logic**: "Implement a function to export expense data to CSV format, ensuring proper formatting and handling of special characters."
- **Bar Chart Visualization**: "Create a bar chart composable in Jetpack Compose that displays daily expense totals for the last 7 days, using dummy data for testing."
- 

