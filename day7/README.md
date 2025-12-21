## Running the code

**Prerequisites:** You need GHC and Cabal installed. Get them from [GHCup](https://www.haskell.org/ghcup/).

**Build and run:**
```bash
cabal build                 # Compiles the project
cabal run                   # Builds and runs the program
```

**Clean up:**
```bash
cabal clean                 # Removes build artifacts
```

The project uses GHC 9.12.2 with Cabal 3.16.0.0.

**File compilation order:**
- `Lib.hs` - Library functions (compiles first)
- `Main.hs` - Main entry point (compiles second)
