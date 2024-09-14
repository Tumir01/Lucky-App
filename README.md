**Lucky Fan App**
> This Android application, built using Kotlin, is a casual game where players must find the square of a required color among several others. The UI is designed using XML, with elements pre-selected and adapted from Figma.


**Key features of the app include:**

_1. User Authentication:_ Registration and login functionality powered by Firebase Firestore (NoSQL database).

_2. Customization Options:_ Players can adjust difficulty levels and change the app's background using shared preferences, with radio buttons integrated into the layout for a smooth user experience.

_3. Game Time Tracking:_ The app monitors total game time through UsageStatsManager (permissions required via ACTION_USAGE_ACCESS_SETTINGS).

_4. App Usage Logging:_ Time spent in the app is recorded using Room, an SQL database solution, with a structured architecture divided into model, DAO, and service packages.

_5. Game Over Fragment:_ A dedicated fragment is displayed at the end of each game session to summarize the results.

_6. Code Organization:_ Static data is placed in constants and resources. Custom resources (shapes, selectors, etc.) are defined to enhance the UI.

_7. Animations:_ Smooth transitions and animations are incorporated into the gameplay to provide a more engaging experience.
