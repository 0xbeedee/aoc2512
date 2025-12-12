use day3::gen_max_right;
use day3::max_joltage_in_bank_part1;
use day3::max_joltage_in_bank_part2;

use std::fs::read_to_string;

fn main() {
    let banks: Vec<String> = read_to_string("banks.txt")
        .unwrap()
        .lines()
        .map(String::from)
        .collect();

    let mut max_joltages_p1: Vec<u64> = vec![];
    let mut max_joltages_p2: Vec<u64> = vec![];
    for bank in banks {
        let chars: Vec<char> = bank.chars().collect();
        let max_right = gen_max_right(&chars);
        max_joltages_p1.push(max_joltage_in_bank_part1(&chars, &max_right));
        max_joltages_p2.push(max_joltage_in_bank_part2(&chars));
    }

    println!(
        "[PHASE 1] Max joltages sum: {}",
        max_joltages_p1.iter().sum::<u64>()
    );
    println!(
        "[PHASE 2] Max joltages sum: {}",
        max_joltages_p2.iter().sum::<u64>()
    );
}
