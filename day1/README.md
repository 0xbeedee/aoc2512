## Running the code

**Prerequisites:** You need a C compiler (like `gcc`) and `make` installed.

**Build and run:**
```bash
make        # Compiles the source files (main.c, lib.c) into an executable
./main      # Runs the program
```

**Clean up:**
```bash
make clean  # Removes compiled files and executables
```

The Makefile compiles with `-Wall -Wextra` (all warnings) and `-std=c11` (C11 standard).
