module Lib where

import qualified Data.Set as Set
import Data.Set (Set)

import qualified Data.Map.Strict as Map
import Data.Map.Strict (Map)

import Data.List (elemIndex)
import Data.Maybe (fromJust)

-- Simulates the progression of the beam through the manifold
simulate :: (state -> String -> state) -> (state -> result) -> state -> String -> result
simulate processRowFn extractResultFn initialState inputDiagram =
    let rows = lines inputDiagram
        finalState = foldl' processRowFn initialState rows
    in extractResultFn finalState

------------------------------------ HELPERS ---------------------------------------------

-- Find the starting column index for the beam (i.e., the index of the 'S' in the first row)
findStartColumn :: String -> Int
findStartColumn input =
    case lines input of
        (firstRow:_) -> fromJust $ elemIndex 'S' firstRow -- 'S' is in the first row by construction
        [] -> 0

-- Checks whether a character is a splitter
isSplitter :: Char -> Bool
isSplitter = (== '^')

-- Checks if a column in a row should split (valid bounds + has splitter)
shouldSplit :: String -> Int -> Bool
shouldSplit row col =
    let rowLen = length row
    in col >= 0 && col < rowLen && isSplitter (row !! col)

-- Computes valid split destinations (left and right columns that are in bounds)
getValidSplits :: Int -> Int -> [Int]
getValidSplits rowLen col =
    filter (\c -> c >= 0 && c < rowLen) [col - 1, col + 1]

------------------------------------ CLASSICAL PART ---------------------------------------------

-- Processes each row classically, updating the number of beams if they hit the splitters
processRowClassical :: (Set Int, Int) -> String -> (Set Int, Int)
processRowClassical (beams, splits) row =
    Set.foldl' processBeam (Set.empty, splits) beams
    where
        rowLen = length row
        processBeam (newBeams, splitCount) col
            | shouldSplit row col =
                let validCols = getValidSplits rowLen col
                    updatedBeams = foldl' (flip Set.insert) newBeams validCols
                in (updatedBeams, splitCount + 1)
            | otherwise =
                (Set.insert col newBeams, splitCount)

-- Extract split count from classical simulation result
extractSplitCount :: (Set Int, Int) -> Int
extractSplitCount (_, splitCount) = splitCount

------------------------------------ QUANTUM PART ---------------------------------------------

-- Processes each row "quantumly", tracking timeline counts per column
processRowQuantum :: Map Int Int -> String -> Map Int Int
processRowQuantum timelineCounts row =
    Map.foldlWithKey' processTimeline Map.empty timelineCounts
    where
        rowLen = length row
        processTimeline newCounts col count
            | shouldSplit row col =
                let validCols = getValidSplits rowLen col
                    addTimelines acc c = Map.insertWith (+) c count acc -- add to current number of timelines, if split
                in foldl' addTimelines newCounts validCols
            | otherwise =
                Map.insertWith (+) col count newCounts -- propagate the number of timelines, if no split

-- Sum all timeline counts from quantum simulation result
sumTimelines :: Map Int Int -> Int
sumTimelines = Map.foldl' (+) 0
