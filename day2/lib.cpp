#include <iostream>
#include <sstream>
#include <tuple>

#include "lib.hpp"

/****************************** PRIVATE HELPERS ************************************/

// Returns all the values between the lower and upper bound, as a vector of strings.
// (The bounds themselves are excluded)
std::vector<std::string> getIntermediateValues(const std::vector<std::string> &bounds)
{
    // use long long for safety (some of these ranges are very large...)
    long long lowerInt = std::stoll(bounds[0]);
    long long upperInt = std::stoll(bounds[1]);

    std::vector<std::string> intermediates;
    for (long long n = lowerInt + 1; n < upperInt; n++)
    {
        intermediates.push_back(std::to_string(n));
    }

    return intermediates;
}

// Checks if a string is symmetric.
// Assumes that the string has even length.
bool isSymmetric(std::string s)
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

// Expands the ranges parsed from the file, including all the intermediate values, in place.
// Note that the values get appended after the bounds, so they are not sorted (we do not need them to be).
void expandRanges(std::vector<std::vector<std::string>> &parsedRanges)
{
    for (size_t i = 0; i < parsedRanges.size(); i++)
    {
        std::vector<std::string> intermediateValues = getIntermediateValues(parsedRanges[i]);
        parsedRanges[i].insert(parsedRanges[i].end(), intermediateValues.begin(), intermediateValues.end());
    }
}

// Returns a vector of numerical invalid IDs.
std::vector<long long> getInvalidIDs(std::vector<std::vector<std::string>> &expandedRanges)
{
    std::vector<long long> invalidIDs;
    for (std::vector<std::string> &range : expandedRanges)
        for (std::string &id : range)
            // even length AND symmetric
            if (id.size() % 2 == 0 && isSymmetric(id))
                invalidIDs.push_back(std::stoll(id));

    return invalidIDs;
}
