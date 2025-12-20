open Lib

let lines = System.IO.File.ReadAllLines("problems.txt") 

// all lines but the last (which contains mathematical symbols)
let numRows = 
    lines.[..lines.Length - 2] 
    |> Array.map (fun line -> line.Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries))
// last line (operations)
let operations = lines.[lines.Length - 1].Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries)

let numRowsPhase1 = numRows |> Array.map (Array.map int64)
let results = computeResultsPhase1 numRowsPhase1 operations
let grandTotal = results |> Array.sum
printfn "[PHASE 1] The grand total is: %d" grandTotal
