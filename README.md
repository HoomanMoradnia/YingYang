# YingYang

YingYang is a desktop puzzle game implemented in Java Swing, inspired by the classic "Yin-Yang" logic puzzle. The game challenges players to fill a grid with black and white cells according to specific rules, typically involving connectivity and separation constraints, similar to those found in logic puzzles published in Nikoli magazines.

## Features

- **Graphical User Interface**: Built with Java Swing for an interactive and responsive experience.
- **Customizable Board Size**: Users can select the number of rows and columns before starting a new game.
- **Randomized Puzzles**: Option to generate random puzzles for replayability.
- **Undo/Redo Functionality**: Easily revert or reapply moves to experiment and refine your solution.
- **Error Feedback**: Instant feedback on invalid moves or puzzle states.
- **Modern Look-and-Feel**: Uses the Nimbus theme for a polished appearance.

## Installation

### Prerequisites

- **Java 8 or later** must be installed on your system.

#### Windows

1. **Install Java**  
   Download and install the latest **Java JDK** if you don't have it.

2. **Clone the Repository**
   ```cmd
   git clone https://github.com/HoomanMoradnia/YingYang.git
   cd YingYang
   ```

3. **Compile the Source Files**
   ```cmd
   javac *.java
   ```

4. **Run the Game**
   ```cmd
   java PageOne
   ```

#### Linux

1. **Install Java**  
   Use your package manager to install Java, for example:
   ```bash
   sudo apt-get update
   sudo apt-get install openjdk-11-jdk
   ```

2. **Clone the Repository**
   ```bash
   git clone https://github.com/HoomanMoradnia/YingYang.git
   cd YingYang
   ```

3. **Compile the Source Files**
   ```bash
   javac *.java
   ```

4. **Run the Game**
   ```bash
   java PageOne
   ```

## Gameplay

- Select the desired number of rows and columns, and the number of randomly filled cells.
- Click "New Game" to generate the board.
- Use left-click and right-click to color the cells as black or white.
- The "Undo" and "Redo" buttons allow you to backtrack or reapply moves.
- Ensure that your solution satisfies Yin-Yang puzzle rules (e.g., all white cells are connected, all black cells are connected, no 2x2 block of the same color, etc.).

## Code Overview

- `PageOne.java`: Launches the application and configures the initial game parameters.
- `Start.java`: Main game logic and interface, including grid management, coloring mechanics, and undo/redo actions.
- `Button.java`, `Situation.java`, etc.: Supporting classes for UI components and state management.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for more details.

## Credits

Developed by Hooman Moradnia.

---

Contributions, suggestions, and puzzle rule improvements are welcome! Please open an issue or pull request.
