#include <iostream>
#include <sstream>

#include "lib.hpp"

/****************************** PRIVATE HELPERS ************************************/

// Checks if a string is made up of repeating patterns.
bool isRepeated(const std::string &s, bool phase1)
{
    if (phase1)
    {
        if (s.size() % 2 != 0)
            return false; // odd length strings can never satisfy the repeating condition

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
std::vector<long long> getInvalidIDs(const std::vector<std::vector<std::string>> &ranges, bool phase1)
{
    std::vector<long long> invalidIDs;
    for (const std::vector<std::string> &range : ranges)
    {
        long long lower = std::stoll(range[0]);
        long long upper = std::stoll(range[1]);
        for (long long n = lower; n <= upper; n++)
        {
            std::string id = std::to_string(n);
            if (isRepeated(id, phase1))
                invalidIDs.push_back(n);
        }
    }

    return invalidIDs;
}
