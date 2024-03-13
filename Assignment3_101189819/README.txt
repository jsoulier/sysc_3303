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

=== Errors ===

1.

When pedestriansFlash is entered, pedestriansFlash is set to 7. When a TIMEOUT occurs,
pedestriansFlash is exited and pedestrianFlashCtr is decremented. If pedestrianFlashCtr
is not equal to 0, pedestriansFlash is entered again.

Since pedestrianFlashCtr is set to 7 whenever pedestriansFlash is entered, pedestrianFlashCtr
is forever reset to 7 creating an infinite loop. To fix, an intermediary state can
be created before pedestriansFlash that does the assignment of pedestrianFlashCtr to 7.

2.

If pedestrianFlashCtr is equal to 0, both of the following guards are triggered:
[pedestrianFlashCtr == 0]
[(pedestrianFlashCtr & 1) == 0]

To fix, we can change the guards to the following:
[pedestrianFlashCtr == 0]
[(pedestrianFlashCtr & 1) == 0 && pedestrianFlashCtr > 0]
