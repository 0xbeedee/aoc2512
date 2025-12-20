open Lib

let lines = System.IO.File.ReadAllLines("problems.txt") 

// all lines but the last (which contains mathematical symbols)
let numRows = 
    lines.[..lines.Length - 2] 
    |> Array.map (fun line -> line.Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries) |> Array.map int64)
// last line (operations)
let operations = lines.[lines.Length - 1].Split([|' '|], System.StringSplitOptions.RemoveEmptyEntries)

let results = computeResults numRows operations
let grandTotal = results |> Array.sum
printfn "[PHASE 1] The grand total is: %d" grandTotal
