#include <iostream>
#include <sstream>
#include <tuple>

#include "lib.hpp"

/****************************** PRIVATE HELPERS ************************************/

// Checks if a string is made up of any subtring repeated twice.
// Assumes that the string has even length.
bool isRepeated(std::string s)
{
    size_t midpoint = s.size() / 2;
    std::string leftSlice(s.begin(), s.begin() + midpoint);
    std::string rightSlice(s.begin() + midpoint, s.end());
    return leftSlice == rightSlice;
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
std::vector<long long> getInvalidIDs(std::vector<std::vector<std::string>> &expandedRanges)
{
    std::vector<long long> invalidIDs;
    for (std::vector<std::string> &range : expandedRanges)
    {
        for (long long n = std::stoll(range[0]); n <= std::stoll(range[1]); n++)
        {
            std::string id = std::to_string(n);
            // even length AND symmetric
            if (id.size() % 2 == 0 && isRepeated(id))
                invalidIDs.push_back(std::stoll(id));
        }
    }

    return invalidIDs;
}
