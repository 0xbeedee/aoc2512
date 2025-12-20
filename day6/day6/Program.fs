open Lib

let lines = System.IO.File.ReadAllLines("problems.txt") 

// all lines but the last (which contains mathematical symbols)
let numRows = 
    lines.[..lines.Length - 2] 
// last line (operations)
let operations = lines.[lines.Length - 1].Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries)

let numRowsPhase1 = 
    numRows 
    |> Array.map (fun line -> line.Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries))
    |> Array.map (Array.map int64)
let resultsPhase1 = computeResultsPhase1 numRowsPhase1 operations
let grandTotalPhase1 = resultsPhase1 |> Array.sum
printfn "[PHASE 1] The grand total is: %d" grandTotalPhase1

let resultsPhase2 = computeResultsPhase2 numRows operations
let grandTotalPhase2 = resultsPhase2 |> Array.sum
printfn "[PHASE 2] The grand total is: %d" grandTotalPhase2
