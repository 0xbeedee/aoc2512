module Lib where

import qualified Data.Set as Set
import Data.Set (Set)
import Data.List (elemIndex)
import Data.Maybe (fromJust)

-- Checks whether a character is a splitter
isSplitter :: Char -> Bool
isSplitter = (== '^')

-- Processes each row, updating the number of beams if they hit the splitters
processRow :: (Set Int, Int) -> String -> (Set Int, Int)
processRow (beams, splits) row =
    Set.foldl' processBeam (Set.empty, splits) beams
    where
        rowLen = length row
        processBeam (newBeams, splitCount) col 
            | col >= 0 && col < rowLen && isSplitter (row !! col) = 
                let leftCol = col - 1
                    rightCol = col + 1
                    validCols = filter (\c -> c >= 0 && c < rowLen) [leftCol, rightCol] -- ensure we only use valid columns
                    updatedBeams = foldl' (flip Set.insert) newBeams validCols
                in (updatedBeams, splitCount + 1)
            | otherwise =
                (Set.insert col newBeams, splitCount)

-- Simulates the progression of the beam through the manifold
simulate :: String -> Int
simulate inputDiagram =
    let rows = lines inputDiagram
        initialBeams = case rows of 
            (firstRow:_) -> Set.singleton (fromJust $ elemIndex 'S' firstRow) -- 'S' is in the first row, by construction
            [] -> Set.empty
        (_, totalSplits) = foldl' processRow (initialBeams, 0) rows
    in totalSplits


