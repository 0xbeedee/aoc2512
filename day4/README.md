## Running the code

**Prerequisites:** You need Go installed. Get it from [go.dev](https://go.dev/dl/).

**Build and run:**
```bash
go run .           # Runs the program directly (compiles and executes)
# or
go build           # Compiles into an executable named 'day4'
./day4             # Runs the compiled binary
```

**Clean up:**
```bash
go clean           # Removes compiled binaries and build artifacts
```

The project uses Go modules (initialized with `go mod init aoc25/day4`) and compiles both library functions (`lib.go`) and the main program (`main.go`).
