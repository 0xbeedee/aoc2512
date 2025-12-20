module Lib

let computeOp (column: int64[]) (op: string) =
    match op with
    | "*" -> column |> Array.reduce (*)
    | "+" -> column |> Array.reduce (+)
    | _ -> failwith $"Unknown operation: {op}"


let computeResults (numRows: int64[][]) (operations: string[]) =
    operations |> Array.mapi (fun i op -> 
    let column = numRows |> Array.map (fun row -> row.[i])
    computeOp column op)
