module Main where

import Lib

import qualified Data.Set as Set
import qualified Data.Map.Strict as Map

main :: IO ()
main = do
    content <- readFile "diagram.txt"
    let startCol = findStartColumn content
        -- classical
        initialStateClassical = (Set.singleton startCol, 0)
        resultPhase1 = simulate processRowClassical extractSplitCount initialStateClassical content
        -- quantum
        initialStateQuantum = Map.singleton startCol 1
        resultPhase2 = simulate processRowQuantum sumTimelines initialStateQuantum content

    putStrLn $ "[PHASE 1] Total splits: " ++ show resultPhase1
    putStrLn $ "[PHASE 2] Total timelines: " ++ show resultPhase2
