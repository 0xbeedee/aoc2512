#pragma once

#include <vector>
#include <string>
#include <fstream>

std::vector<std::vector<std::string>> parseRanges(std::ifstream &inFile);

std::vector<long long> getInvalidIDs(std::vector<std::vector<std::string>> &expandedRanges, bool phase1 = false);
