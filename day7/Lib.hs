module Lib where

import qualified Data.Set as Set
import Data.Set (Set)
import Data.List (elemIndex)
import Data.Maybe (fromJust)

-- Processes each row, updating the number of beams if they hit the splitters
processRow :: (Set Int, Int) -> String -> (Set Int, Int)
processRow (beams, splits) row =
    Set.foldl' processBeam (Set.empty, splits) beams
    where
        processBeam (newBeams, splitCount) col =
            if col >= 0 && col < length row && row !! col == '^' 
                then (Set.insert (col + 1) (Set.insert (col - 1) newBeams), splitCount + 1)
            else
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


