module Main where

import Lib

main :: IO ()
main = do
    content <- readFile "diagram.txt"
    let resultPhase1 = simulate content
    putStrLn $ "[PHASE 1] Total splits: " ++ show resultPhase1
