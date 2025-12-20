## Running the code

**Prerequisites:** You need .NET SDK installed (.NET 6 or later).

**Build and run:**
```bash
dotnet run                  # Compiles and runs in one step
dotnet build                # Just compiles the project
```

**Clean up:**
```bash
dotnet clean                # Removes build artifacts
```

The project uses .NET 9.0 with F#.

**File compilation order:**
- `Lib.fs` - Library functions (compiles first)
- `Program.fs` - Main entry point (compiles second)
