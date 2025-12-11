#pragma once

#include <vector>
#include <string>
#include <fstream>

std::vector<std::vector<std::string>> parseRanges(std::ifstream &inFile);
void expandRanges(std::vector<std::vector<std::string>> &parsedRanges);

std::vector<long long> getInvalidIDs(std::vector<std::vector<std::string>> &expandedRanges);
