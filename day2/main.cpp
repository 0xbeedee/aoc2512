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

    std::vector<long long> invalidIDs = getInvalidIDs(ranges);
    long long sum = std::accumulate(invalidIDs.begin(), invalidIDs.end(), 0LL);
    // for (size_t i = 0; i < invalidIDs.size(); i++)
    //     std::cout << invalidIDs[i] << ", ";
    // std::cout << "\n";
    std::cout << "[PHASE 1] The sum of the invalid IDs is: " << sum << "\n";
}