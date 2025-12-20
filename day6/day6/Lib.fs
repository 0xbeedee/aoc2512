module Lib

// TODO add comments and documentation to all the funcs
let computeOp (column: int64[]) (op: string) =
    match op with
    | "*" -> column |> Array.reduce (*)
    | "+" -> column |> Array.reduce (+)
    | _ -> failwith $"Unknown operation: {op}"


let computeResultsPhase1 (numRows: int64[][]) (operations: string[]) =
    operations |> Array.mapi (fun i op -> 
    let column = numRows |> Array.map (fun row -> row.[i])
    computeOp column op)

let computeResultsPhase2 (numRows: string[]) (operations: string[]) =
    // Read as character grid (exclude last line with operations)
    let charGrid = numRows |> Array.map (fun line -> line.ToCharArray())

    // Find the width (longest line)
    let width = charGrid |> Array.map Array.length |> Array.max

    // Check if a column is a separator (all spaces)
    let isSeparator col =
        charGrid |> Array.forall (fun row ->
            col >= row.Length || row.[col] = ' ')

    // Find all separator column positions
    let separators =
        [| for col in 0 .. width - 1 do
            if isSeparator col then yield col
        |]

    // Split columns into problem ranges
    let bounds = Array.concat [[|-1|]; separators; [|width|]]
    let problemRanges =
        [| for i in 0 .. bounds.Length - 2 do
            let start = bounds.[i] + 1
            let endPos = bounds.[i + 1]
            if start < endPos then yield (start, endPos - 1)
        |]

    // For each problem range, extract and compute
    problemRanges |> Array.mapi (fun i (startCol, endCol) ->
        let op = operations.[i]

        // For each column in this problem, read vertically to form a number
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
            |> Array.rev  // Reverse for right-to-left reading

        // Apply the operation
        computeOp numbers op
    )


