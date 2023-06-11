# Melozone
For our final project for Objective Programming course at TCS UJ. We've created a simple desktop application for streaming music. The main goal of the application is to allow users to play music in real-time obtained from firebase.

For the design, we used JavaFX framework and Firebase database.
## Creators
 - [Andrzej Kwaśniewski (akwasniewski)](https://github.com/akwasniewski)
 - [Augustyn Majtyka (AugustynM)](https://github.com/AugustynM)
 - [Rafał Przypek (Shadow0Bit)](https://github.com/Shadow0Bit)
## Features

- User-friendly interface with a modern and intuitive design
- Play, pause, stop, and skip songs
- Searching through songs, albums and artists
- Browsing artist profiles and albums views
- Song queue
- Display song information (title, artist, album, cover)
- Firebase integration for storing and retrieving song and playlist data

## Prerequisites

To run this application locally, you need to have the following installed

- Java Development Kit (JDK) version 20 or later
- Maven version 13 or later
- Solid internet connection
- Either Intellij or Linux/WSL


## Installation (via IntelliJ)

1. Clone the repository using the following command
   ```sh
    git clone https://github.com/meloMPK/MeloZone.git
    ```
2. Open the pom.xml file file located in the root folder of the project via IntelliJ, and when prompted select 'open as project'
   
3. Right click on pom.xml file and click Maven->Reload Project
   
4. Go to MeloApplication.java file located in src/main/java/com/melompk/melo
   
5. Use the IntelliJ run button (the green triangle in upper right corner) to run the application
   
6. Enjoy :)

## Installation (via command line) - Linux only

1. Clone the repository using the following command
   ```sh
    git clone https://github.com/meloMPK/MeloZone.git
    ```
2. In the command line being in the root of the project run to install needed dependencies:
   ```sh
    mvn clean install
    ```
3. Now run the following command to run the project
    ```sh
    mvn clean javafx:run
    ```
4. Enjoy :)

## Acknowledgements

We would like to express our gratitude to the creators and maintainers of the following libraries and frameworks:

- JavaFX: [https://openjfx.io](https://openjfx.io)
- Firebase: [https://firebase.google.com](https://firebase.google.com)

We would also like to extend our sincere thanks to our professor and staff for their guidance and support throughout this project and course.


