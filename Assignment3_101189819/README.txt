Author: Jaan Soulier
Student Number: 101189819

=== Requirements ===

- IntelliJ IDEA
- JDK

=== Setup ===

1. Extract the files from the archive
2. Open IntelliJ where you extracted the files
3. Open the project explorer
4. Right click on Sample
5. Click 'Run Sample.main()'

=== Files ===

src/Action.java: Java file containing the actions between states
src/Context.java: Java file containing the context for the states
src/State.java: Java file containing the states
src/Sample.java: Java file containing a sample of how to use the context

=== Error ===

If pedestrianFlashCtr is 0, both of the following guards are triggered:
[pedestrianFlashCtr == 0]
[(pedestrianFlashCtr & 1) == 0]

To fix, we can change the guards to the following:
[pedestrianFlashCtr == 0]
[(pedestrianFlashCtr & 1) == 0 && pedestrianFlashCtr > 0]
