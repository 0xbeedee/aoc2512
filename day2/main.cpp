#include <fstream>
#include <iostream>
#include <numeric>

#include "lib.hpp"
int main()
{
    std::ifstream file("id_ranges.txt");
    if (!file.is_open())
    {
        std::cout << "Error opening file.\n";
        return -1;
    }

    std::vector<std::vector<std::string>> ranges = parseRanges(file);

    std::vector<long long> invalidIDs = getInvalidIDs(ranges, true);
    long long sum = std::accumulate(invalidIDs.begin(), invalidIDs.end(), 0LL);
    std::cout << "[PHASE 1] The sum of the invalid IDs is: " << sum << "\n";

    invalidIDs = getInvalidIDs(ranges, false);
    sum = std::accumulate(invalidIDs.begin(), invalidIDs.end(), 0LL);
    std::cout << "[PHASE 2] The sum of the invalid IDs is: " << sum << "\n";
}