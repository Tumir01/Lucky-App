**Lucky Fan App**

**Key features of the app include:**
User Authentication: Registration and login functionality powered by Firebase Firestore (NoSQL database).
Customization Options: Players can adjust difficulty levels and change the app's background using shared preferences, with radio buttons integrated into the layout for a smooth user experience.
Game Time Tracking: The app monitors total game time through UsageStatsManager (permissions required via ACTION_USAGE_ACCESS_SETTINGS).
App Usage Logging: Time spent in the app is recorded using Room, an SQL database solution, with a structured architecture divided into model, DAO, and service packages.
Game Over Fragment: A dedicated fragment is displayed at the end of each game session to summarize the results.
Code Organization: Static data is placed in constants and resources. Custom resources (shapes, selectors, etc.) are defined to enhance the UI.
Animations: Smooth transitions and animations are incorporated into the gameplay to provide a more engaging experience.
