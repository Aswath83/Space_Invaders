README.md
md
Copy
Edit
# Space Invaders

## Overview
Space Invaders is a simple 2D arcade shooter game written in Java using the Swing library. The player controls a spaceship and must defeat waves of alien invaders while avoiding enemy bullets. The game is designed with classic retro aesthetics and engaging gameplay mechanics.

## Features
- **Player Movement:** Use the left and right arrow keys to navigate the spaceship.
- **Shooting Mechanic:** Press the spacebar to shoot bullets at enemies.
- **Enemy Waves:** Aliens spawn in waves and move in a sinusoidal pattern.
- **Score System:** Gain points by destroying enemies.
- **Timer Mechanic:** Survive until the timer reaches zero to win.
- **Game Over Conditions:**
  - Aliens reaching the bottom of the screen.
  - Player getting hit by an alien bullet.

## Installation & Execution
### **Option 1: Download Precompiled Game**
1. Download `game.jar` from the provided link.
2. Double-click `game.jar` to run the game. (Ensure Java is installed on your system)
3. Alternatively, use `start.bat` to launch the game.

### **Option 2: Run the Source Code**
1. Clone or download the repository.
2. Open the project in an IDE (such as Eclipse or IntelliJ).
3. Ensure Java (JDK 8+) is installed.
4. Compile and run `App.java` or `Main.java`.

## Controls
- **Left Arrow (←):** Move spaceship left
- **Right Arrow (→):** Move spaceship right
- **Spacebar:** Shoot bullets

## Development Details
- **Language:** Java (Swing, AWT)
- **Libraries Used:** Java Swing, Timer (javax.swing.Timer)
- **UI Components:** JFrame, JPanel
- **Game Loop:** ActionListener-based game loop

## Future Improvements
- Add power-ups for the player.
- Introduce different alien types with varied behavior.
- Implement background music and sound effects.
- Create multiple levels with increasing difficulty.
