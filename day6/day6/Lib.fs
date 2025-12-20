module Lib

// Computes an operation on an array of values
let computeOp (column: int64[]) (op: string) =
    match op with
    | '*' -> column |> Array.reduce (*)
    | '+' -> column |> Array.reduce (+)
    | _ -> failwith $"Unknown operation: {op}"


// Computes the single results in phase 1
let computeResultsPhase1 (numRows: int64[][]) (operations: string[]) =
    operations |> Array.mapi (fun i op -> 
    let column = numRows |> Array.map (fun row -> row.[i])
    computeOp column op)

// Computes the single results in phase 2
let computeResultsPhase2 (numRows: string[]) (operations: string[]) =
    // get the character grid
    let charGrid = numRows |> Array.map (fun line -> line.ToCharArray())
    let width = charGrid |> Array.map Array.length |> Array.max // == longest line

    // spaces and newlines are separators
    let isSeparator col =
        charGrid |> Array.forall (fun row ->
            col >= row.Length || row.[col] = ' ')

    // find all separator column positions...
    let separators = [| 0 .. width - 1 |] |> Array.filter isSeparator
    // ...and split the columns accordingly
    let bounds = Array.concat [[|-1|]; separators; [|width|]]
    let problemRanges =
        [| for i in 0 .. bounds.Length - 2 do
            let start = bounds.[i] + 1
            let endPos = bounds.[i + 1]
            if start < endPos then yield (start, endPos - 1)
        |]

    // compute the result for each problem range
    problemRanges |> Array.mapi (fun i (startCol, endCol) ->
        let op = operations.[i]
        // read vertically to form a number
        let numbers =
            [| for col in startCol .. endCol do
                let chars = charGrid |> Array.choose (fun row ->
                    if col < row.Length && row.[col] <> ' ' then
                        Some row.[col]
                    else
                        None)
                if chars.Length > 0 then
                    yield System.String(chars) |> int64
            |]
            |> Array.rev  // reverse for right-to-left reading

        computeOp numbers op
    )

