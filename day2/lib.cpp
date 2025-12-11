#include <iostream>
#include <sstream>
#include <tuple>

#include "lib.hpp"

/****************************** PRIVATE HELPERS ************************************/

// Checks if a string is made up of repeating patterns.
bool isRepeated(std::string s, bool phase1)
{
    if (phase1)
    {
        std::string leftSlice(s.begin(), s.begin() + s.size() / 2);
        std::string rightSlice(s.begin() + s.size() / 2, s.end());
        return leftSlice == rightSlice;
    }

    // pattern must repeat >= 2 times
    for (size_t l = 1; l <= s.size() / 2; l++)
    {
        if (s.size() % l == 0)
        {
            std::string possiblePattern(s.begin(), s.begin() + l);
            bool allMatch = true;
            for (size_t j = l; j < s.size(); j += l)
            {
                std::string actualSlice(s.begin() + j, s.begin() + j + l);
                if (possiblePattern != actualSlice)
                {
                    allMatch = false;
                    break; // pattern does not match
                }
            }
            if (allMatch)
                return true;
        }
    }

    return false;
}

/****************************** PUBLIC API ************************************/

// Parses the input file and extracts a vector of ranges, where each element is itself a vector,
// containing the lower bound and the upper bound of the range.
std::vector<std::vector<std::string>> parseRanges(std::ifstream &inFile)
{
    std::vector<std::vector<std::string>> ranges;

    for (std::string range; std::getline(inFile, range, ',');)
    {
        std::istringstream rangeWrapper;
        std::string lowerBound, upperBound;

        rangeWrapper.str(range);
        std::getline(rangeWrapper, lowerBound, '-');
        std::getline(rangeWrapper, upperBound, '-');
        ranges.push_back(std::vector<std::string>{lowerBound, upperBound});
    }

    return ranges;
}

// Returns a vector of numerical invalid IDs.
std::vector<long long> getInvalidIDs(std::vector<std::vector<std::string>> &expandedRanges, bool phase1)
{
    std::vector<long long> invalidIDs;
    for (std::vector<std::string> &range : expandedRanges)
    {
        for (long long n = std::stoll(range[0]); n <= std::stoll(range[1]); n++)
        {
            std::string id = std::to_string(n);
            // even length AND made up of repeating patterns
            if (isRepeated(id, phase1))
                invalidIDs.push_back(std::stoll(id));
        }
    }

    return invalidIDs;
}
