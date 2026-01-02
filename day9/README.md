## Running the code

**Prerequisites:** You need sbt installed. Get it from [scala-sbt.org](https://www.scala-sbt.org/download.html).

**Build and run:**
```bash
sbt compile                # Compiles the project
sbt run                    # Builds and runs the program
```

**Interactive mode:**
```bash
sbt                        # Starts sbt shell
> compile                  # Compile in shell
> run                      # Run in shell
> ~run                     # Run on file changes
```

**Clean up:**
```bash
sbt clean                  # Removes build artifacts
```

The project uses Scala 3.7.4 with sbt.

**File structure:**
- `Lib.scala` - Library functions
- `Main.scala` - Main entry point
